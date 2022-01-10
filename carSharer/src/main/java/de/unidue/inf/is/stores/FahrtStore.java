package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import de.unidue.inf.is.utils.DateTimeUtil;


/*
 * To Do:
 *
 */

//purpose of this class:
//contains queri3es relating to the table Fahrt
public class FahrtStore implements Closeable {

    private Connection connection;
    private boolean complete;

    public FahrtStore() throws StoreException {
        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public void deleteFahrtWithFid(int fid){

        try{
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE from dbp097.fahrt where fid=?");

            preparedStatement.setInt(1, fid);
            preparedStatement.executeUpdate();
            connection.commit();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }





    public void complete() {
        complete = true;
    }

    @Override
    public void close() throws IOException {
        if (connection != null) {
            try {
                if (complete) {
                    connection.commit();
                }
                else {
                    connection.rollback();
                }
            }
            catch (SQLException e) {
                throw new StoreException(e);
            }
            finally {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    throw new StoreException(e);
                }
            }
        }
    }



    public int getNumberFreePlaces(int fid){
        int numberFreePlaces=0;
        try{
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select freiplaetze from dbp097.anzfreiplaetze where fid= ?");

            preparedStatement.setInt(1, fid);
            ResultSet resultSet = preparedStatement.executeQuery();


            while(resultSet.next()){
                numberFreePlaces= resultSet.getInt("freiplaetze");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return numberFreePlaces;
    }

    public int getUserIDofRideMaker(int fid){
        int userIDofRideMaker=0;
        try{
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select anbieter from dbp097.fahrt where dbp097.fahrt.fid= ?");

            preparedStatement.setInt(1, fid);
            ResultSet resultSet = preparedStatement.executeQuery();


            while(resultSet.next()){
                 userIDofRideMaker= resultSet.getInt("anbieter");

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userIDofRideMaker;


    }

    public boolean hasUserAlreadyReserved(int fid){
        boolean result= false;
        try{
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from dbp097.reservieren where fahrt= ? AND kunde = ?" );

            System.out.println("THE FID AND THE KUNDE ARE: "+ fid + ", " + UserStore.getCurrentUserIdInSession() );

            preparedStatement.setInt(1, fid);
            preparedStatement.setInt(2, UserStore.getCurrentUserIdInSession());

            ResultSet resultSet = preparedStatement.executeQuery();


            if(resultSet.next()){
                result= true;
            }
            else{
                result= false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;

    }


    //this method will give all the open trips/Fahrten
    public List<Fahrt> getOpenTrips(){
        List<Fahrt> openTripsList= new ArrayList<>();
        try {
            //I can directly do this since i have made a new view called "anzfreiplaetze"
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select startort, zielort, fahrtkosten, dbp097.fahrt.fid, freiplaetze, icon " +
                            "from dbp097.anzfreiplaetze JOIN dbp097.fahrt ON dbp097.fahrt.fid= dbp097.anzfreiplaetze.fid " +
                            "JOIN dbp097.transportmittel ON dbp097.fahrt.transportmittel= dbp097.transportmittel.tid");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Fahrt f= new Fahrt();
                f.setStartOrt(resultSet.getString("startort"));
                f.setZielOrt(resultSet.getString("zielort"));
                f.setFahrtId(resultSet.getInt("fid"));

                //the below variable is actually storing the free places for each trip. Just reusing the MaxPlaetze Variable.
                // Notice the columnLabel =)
                f.setMaxPlaetze(resultSet.getInt("freiplaetze"));
                System.out.println("#########################################################");
                System.out.println("THE NUMBER OF FREE PLACES IS: "+ resultSet.getInt("freiplaetze"));
                System.out.println("#########################################################");
                f.setFahrtKosten(resultSet.getDouble("fahrtkosten"));
                String path= f.removePfadKeyword(resultSet.getString("icon"));
                f.setIconPath(path);
                openTripsList.add(f);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        openTripsList.stream().forEach(System.out::println);
        System.out.println("#########################################################");
        return openTripsList;
    }

    public boolean isTripOpen(int fid){
        boolean isOpen=true;
        try{
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select status from dbp097.fahrt where dbp097.fahrt.fid= ?");

            preparedStatement.setInt(1, fid);
            ResultSet resultSet = preparedStatement.executeQuery();


            while(resultSet.next()){
                String status= resultSet.getString("status");
                if (status.equals("offen")){
                    isOpen= true;
                }
                else{
                    isOpen= false;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return isOpen;

    }

    //the below method will get all the information for a particular trip with the ID that gets passed to it!
    public List<Fahrt> getAllInfoForTrip(int fid){

        System.out.println("#########################################################");
        System.out.println("THE FID IS:"+ fid);
        System.out.println("#########################################################");

        List<Fahrt> fahrtWithInfo= new ArrayList<>();
        try {
            //I can directly do this since i have made a new view called "anzfreiplaetze"
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from dbp097.fahrt where dbp097.fahrt.fid= ?");

            preparedStatement.setInt(1, fid);
            ResultSet resultSet = preparedStatement.executeQuery();

            PreparedStatement preparedStatement2 = connection
                    .prepareStatement("select dbp097.anzfreiplaetze.freiplaetze from dbp097.anzfreiplaetze where fid= ?");

            preparedStatement2.setInt(1, fid);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            int freePlaces=6969;

            while(resultSet2.next()){
                freePlaces= resultSet2.getInt("freiplaetze");
            }

            while(resultSet.next() ){
                Fahrt f= new Fahrt();
                f.setAnbieter(resultSet.getInt("anbieter"));
                String DB2TimeStamp= resultSet.getString("fahrtdatumzeit");
                String time= DateTimeUtil.extractTimeFromDB2DateTimeString(DB2TimeStamp);
                String date= DateTimeUtil.extractDateFromDB2DateTimeString(DB2TimeStamp);
                f.setTime(time);
                f.setDate(date);
                f.setStartOrt(resultSet.getString("startort"));
                f.setZielOrt(resultSet.getString("zielort"));
                f.setMaxPlaetze(freePlaces);
                f.setFahrtKosten(resultSet.getDouble("fahrtkosten"));
                f.setStatus(resultSet.getString("status"));
                String beschreibung = resultSet.getString("beschreibung");
                if(beschreibung!= null){
                    f.setBeschreibung(resultSet.getString("beschreibung"));
                }
                else{
                    f.setBeschreibung(null);
                }
                f.setFahrtId(resultSet.getInt("fid"));
//              String path= f.removePfadKeyword(resultSet.getString("icon"));
//                f.setIconPath(path);

                fahrtWithInfo.add(f);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return fahrtWithInfo;

    }

    public User getAnbieter(int fid)  {
        //till now this method is jsut populating the email. when needed change the query and populate with more fields
        User anbieter= new User();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement("select email from dbp097.benutzer where bid IN (SELECT anbieter from dbp097.fahrt where fid = ?)");
            preparedStatement.setInt(1, fid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                anbieter.setEmail(resultSet.getString("email"));
            }
        }
        catch(SQLException e){
        }
        return anbieter;

    }

    /*
     * This is the section where the clean code written by Mohamed starts ;)
     * This code is responsible for te retrieve search results from the view_search
     */

    public List<Fahrt> getFahrtFromSearch(String start, String ziel, String timestamp){

        // To be implemented

        List<Fahrt> fahrteSearchResult = new ArrayList<>();

        /*
         * Queries to be pasted inside the DB2 Shell:
         * To retrieve all fahrts and their dates for debugging
         * select startort, zielort, fahrtkosten, fahrtdatumzeit from dbp097.fahrt
         *
         * The query inside the prepared statement
         * SELECT startort, zielort, fahrtkosten FROM dbp097.fahrt WHERE startort = 'Duisburg' AND zielort = 'Hamburg' AND fahrtdatumzeit >= '2021-12-27-00.00.00.000000' AND status = 'offen'
         */

        try {
            PreparedStatement preparedStatementForSearchResult = connection.
                    prepareStatement("SELECT startort, zielort, fahrtkosten " +
                            "FROM dbp097.fahrt " +
                            "WHERE startort = ? AND zielort =? AND fahrtdatumzeit >= ? AND status = 'offen'");

            preparedStatementForSearchResult.setString(1, start);

            preparedStatementForSearchResult.setString(2,ziel);

            preparedStatementForSearchResult.setString(3, timestamp);

            ResultSet resultSetFoundFahrten = preparedStatementForSearchResult.executeQuery();

            /*if (!resultSetFoundFahrten.next() ) {
                System.out.println("no data");
            }*/

            while(resultSetFoundFahrten.next()){



                System.out.println(resultSetFoundFahrten.getString("startort"));
                System.out.println(resultSetFoundFahrten.getString("zielort"));
                System.out.println(resultSetFoundFahrten.getInt("fahrtkosten"));

                Fahrt fahrt = new Fahrt.Builder()
                        .startOrt(resultSetFoundFahrten.getString("startort"))
                        .zielOrt(resultSetFoundFahrten.getString("zielort"))
                        .fahrtKosten(resultSetFoundFahrten.getInt("fahrtkosten"))
                        .build();

                System.out.println(fahrt);

                fahrteSearchResult.add(fahrt);


            }

        }catch (SQLException e){
            throw new StoreException(e);
        }


        return  fahrteSearchResult;
    }

    /*
     * Section for view drive
     */

    /*
     * What needs to be achieved here?
     * 1) retrieve objects of type Fahrt/Drive
     * 2) Be able to reserve or delete the trips
     * 3) Be able to create to see all the ratings for this specific drive.
     */

    /*
     * This method should retrieve for us:
     * 1) The information for the drive/trip from the first section of the page including the attributes:
     *      Anbieter, Fahrt ,Datum, Start, Ziel, Free places, Price, Status and description.
     *
     *
     */


    public Fahrt getFahrtForViewDrive(int fahrtId){

        Fahrt fahrtToBeViewed = null;

        Fahrt.Builder builder = new Fahrt.Builder();

        try{

            PreparedStatement preparedStatementForViewedFahrt = connection.
                    prepareStatement("SELECT anbieter, fahrtdatumzeit, startort, zielort, maxPlaetze" +
                            ", fahrtkosten, status, beschreibung" +
                            " FROM dbp097.fahrt " +
                            "WHERE fid = ?");

            ResultSet resultSetRetrievedFahrtToBeViewed = preparedStatementForViewedFahrt.executeQuery();

            /*
             * What we will retrieve for now is only:
             * 1) StartOrt
             * 2) ZielOrt
             * 3) FahrtKosten
             * 5) Status
             * 6) beschreibung
             *
             * Now what is remaining is:
             * 1) Number of remaining seats:
             *  We have to retrieve the maximum number of seats, save it then:
             *  Do a group by and sum all of bewertungen for this specific fahrt and then subtract it.
             *
             * 2) Bewertungen:
             *
             * We have to join three tables,
             */

            while(resultSetRetrievedFahrtToBeViewed.next()){

                fahrtToBeViewed = new Fahrt.Builder()
                        .startOrt(resultSetRetrievedFahrtToBeViewed.getString("startort"))
                        .zielOrt(resultSetRetrievedFahrtToBeViewed.getString("zielort"))
                        .fahrtKosten(resultSetRetrievedFahrtToBeViewed.getDouble("fahrtkosten"))
                        .status(resultSetRetrievedFahrtToBeViewed.getString("status"))
                        .beschreibung(resultSetRetrievedFahrtToBeViewed.getString("beschreibung"))
                        .build();



            }


        }catch(Exception e){
            e.printStackTrace();
        }

        return fahrtToBeViewed;

    }



    /* Methods specific to the creating a new Trip for the page new_drive and servlet NewTripServlet */

    public Fahrt createNewTrip(Fahrt fahrt, int transportmittel){

        try{

            System.out.println("Starting inserting a new trip");

            PreparedStatement preparedStatementForViewedFahrt = connection.
                    prepareStatement("INSERT INTO " +
                            "dbp097.fahrt (startort, zielort, fahrtdatumzeit, maxPlaetze, fahrtkosten, anbieter, transportmittel, beschreibung)" +
                            "VALUES (?, ?, ?, ?, ?, ? ,?, ?)");

            System.out.println("The anbieter is " + UserStore.getCurrentUserIdInSession());

            System.out.println("The starting point of the trip is " + fahrt.getStartOrt());
            preparedStatementForViewedFahrt.setString(1,fahrt.getStartOrt());

            System.out.println("The destination of the trip is " + fahrt.getZielOrt());
            preparedStatementForViewedFahrt.setString(2,fahrt.getZielOrt());

            System.out.println("The Timestamp of the trip is " + fahrt.getTime());
            preparedStatementForViewedFahrt.setString(3,fahrt.getTime());

            System.out.println("The maximum places of the trip is " + fahrt.getMaxPlaetze());
            preparedStatementForViewedFahrt.setInt(4,fahrt.getMaxPlaetze());

            System.out.println("The cost of the trip is " + fahrt.getFahrtKosten());
            preparedStatementForViewedFahrt.setDouble(5,fahrt.getFahrtKosten());


            /*preparedStatementForViewedFahrt.setString(6,fahrt.getStatus());*/


            preparedStatementForViewedFahrt.setInt(6,UserStore.getCurrentUserIdInSession());


            preparedStatementForViewedFahrt.setInt(7,transportmittel);


            preparedStatementForViewedFahrt.setString(8,fahrt.getBeschreibung());

            preparedStatementForViewedFahrt.executeUpdate();

            connection.commit();

            System.out.println("Insertion complete!");


        }catch (Exception e){
            e.printStackTrace();
        }

        return fahrt;
    }

}

