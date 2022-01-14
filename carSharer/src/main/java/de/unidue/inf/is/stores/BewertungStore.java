package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.Bewertung;
import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.domain.TimestampDB2;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BewertungStore extends Store {

    /*
     * General question: After the creation of the Bewertung, we need to retrieve it back again (inefficient?)
     * because how will we insert it back again?
     */

    public Bewertung insertBewertung(String textNachricht, int rating, int fahrtId, int userId) throws SQLException {

        //we are checking here first if the benuzter has made a bewertung to the same fahrt before or no.

        boolean result;

        /*
         * There is no need to fetch the date from the Servlet or the Html page it will be
         * created here automatically
         */
        PreparedStatement preparedStatementForCheckingBewertungExists = connection.prepareStatement
                ("SELECT * FROM dbp097.schreiben  WHERE benutzer = ? AND fahrt = ?");
        preparedStatementForCheckingBewertungExists.setInt(1, userId);
        preparedStatementForCheckingBewertungExists.setInt(2, fahrtId);

        ResultSet ExistingBewertungenFromThisUserForThisFahrt= preparedStatementForCheckingBewertungExists.executeQuery();


        if(ExistingBewertungenFromThisUserForThisFahrt.next()){
            result= true;
        }
        else{
            result= false;
        }

        if(result==false){
            try {

                /*
                 * First, we have to create the Bewertung itself and persist it in the database.
                 * Second, we have to update the schreiben table.
                 * I will place dummy values for fahrts and their users because they have to be passed by the page which
                 * has not been implemented yet.
                 *
                 * The SQL Statement that needs to be executed is:
                 * insert into dbp097.bewertung (textnachricht, erstellungsdatum, rating) values ('bad', '2021-12-27-21.32.27.267', 1)
                 */

                PreparedStatement preparedStatementForInsertingBewertung = connection.prepareStatement
                        ("INSERT INTO dbp097.bewertung (textnachricht, rating, erstellungsdatum) VALUES (? , ?, ?)");

                /*
                 * Here we are extracting the current time at which this method is called using currentTimeMillis();
                 * Then we are passing this long containing the time into the constructor of the timestap so that it properly
                 * converted into the equivalent sql timestamp object.
                 * Because the Html page does not have the ability to send the date as a query string.
                 */

                Long currentTimeMillis = System.currentTimeMillis();
                Timestamp currentTimeStamp = new TimestampDB2(currentTimeMillis);

                System.out.println("The current timestamp is " + currentTimeStamp);

                System.out.println("Rating is " + rating);

                preparedStatementForInsertingBewertung.setString(1, textNachricht);
                preparedStatementForInsertingBewertung.setInt(2, rating);
                /*
                 * Unsure what method to use on the prepared statement to insert the date? How would it be possible to fit
                 * the DB2 data in the preparedStatement.
                 */
                preparedStatementForInsertingBewertung.setString(3, currentTimeStamp.toString());

                int i = preparedStatementForInsertingBewertung.executeUpdate();

                connection.commit();

                System.out.println("What is this number? " + i);

                System.out.println("Query for inserting rating successfully executed!");

                System.out.println("Now it is the time to insert the rating into the schreiben table!");

                System.out.println("First we have to retrieve the id of the recently added bewertung using the timestamp ");

                System.out.println("The timestamp is " + currentTimeStamp);

                PreparedStatement mySpecialPs = connection.prepareStatement("SELECT beid FROM dbp097.bewertung WHERE erstellungsdatum = ?");


                mySpecialPs.setString(1, currentTimeStamp.toString());

                final ResultSet resultSetBewertungId = mySpecialPs.executeQuery();

                /*
                 * Next step is the retrieval of the Bewertung we just created so that we can insert it into the
                 * Schreiben table along with the userId of who created this Bewertung and the fahrtId of the Trip
                 * we are rating.
                 */

                /*
                 * Id retrieved
                 */

                int bewertungId = 0;

                while (resultSetBewertungId.next()) {

                    bewertungId = resultSetBewertungId.getInt("beid");

                    System.out.println("The retrieved id of the bewertung is " + bewertungId);
                }

                /*
                 * Time for insertion into the Schreiben table
                 */

                PreparedStatement preparedStatementForInsertingSchreiben =
                        connection.prepareStatement("INSERT INTO dbp097.schreiben (benutzer, fahrt, bewertung) VALUES (?, ?, ?)");

                preparedStatementForInsertingSchreiben.setInt(1, userId);
                preparedStatementForInsertingSchreiben.setInt(2, fahrtId);
                preparedStatementForInsertingSchreiben.setInt(3, bewertungId);

                int i1 = preparedStatementForInsertingSchreiben.executeUpdate();



                connection.commit();
                System.out.println("{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{{THE ERROR  NUMBER IS:"+ i1);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else{
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            //now if the user has made a bewerting beofre, then we will just update the current bewertung.
            Long currentTimeMillis = System.currentTimeMillis();
            Timestamp currentTimeStamp = new TimestampDB2(currentTimeMillis);

            //first update the erstellunsdatum of the old bewertung
            System.out.println("THE TEXT NACHRICHT IS: "+ textNachricht);

            PreparedStatement preparedStatement3 = connection.prepareStatement("UPDATE DBP097.bewertung SET dbp097.bewertung.erstellungsdatum=?, dbp097.bewertung.textnachricht=? WHERE " +
                    "dbp097.bewertung.beid IN (SELECT beid from dbp097.bewertung JOIN dbp097.schreiben ON dbp097.schreiben.bewertung=dbp097.bewertung.beid WHERE dbp097.schreiben.benutzer= ? AND dbp097.schreiben.fahrt = ?)");
            preparedStatement3.setString(1, currentTimeStamp.toString());
            preparedStatement3.setString(2, textNachricht);
            preparedStatement3.setInt(3, userId);
            preparedStatement3.setInt(4, fahrtId);
            preparedStatement3.executeUpdate();

            //after the erstellungsdatum has been updated and t textnachricht has been updated, we done need to do anything.

        }


        Bewertung bewertung = new Bewertung();

        return bewertung;
    }

    public void deleteBewertungWithFid(int fid){

        try{
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE from dbp097.schreiben where fahrt=?");

            preparedStatement.setInt(1, fid);
            preparedStatement.executeUpdate();
            connection.commit();


            PreparedStatement preparedStatement2 = connection
                    .prepareStatement("DELETE from dbp097.bewertung where beid NOT IN (SELECT bewertung FROM dbp097.schreiben)");

            preparedStatement2.executeUpdate();
            connection.commit();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    /*
     * This section of the code is for implementing the the third last section of view drive.
     * What needs to be achieved?
     * First we have to retrieve all of the Bewertungen from the schreiben table so that we can retrieve the information
     * for it.
     * Second, we also have to retrieve the email of the person who wrote the Bewertungen.
     * Third, we have to calculate the average bewertung
     */


    public Map<String, Bewertung> retreiveAllBewerterAndTheirBewertungen(int fahrtId) {
        /*
         * We have to join the benutzer table, the bewertung table to retrieve all the suitable information on the
         * schreiben table.
         */

        Map<String, Bewertung> mapListOfUserAndHisRating = new HashMap<>();

        List<Integer> totalBewertungForATrip = new ArrayList<>();


        /*
         * Create the prepared statement with the Joins.
         * The query that we are using in this method:
         * SELECT benutzer.email, bewertung.textnachricht, bewertung.rating
           FROM dbp097.schreiben INNER JOIN dbp097.benutzer ON dbp097.schreiben.benutzer = dbp097.benutzer.bid
           INNER JOIN dbp097.bewertung ON dbp097.schreiben.bewertung = dbp097.bewertung.beid
           WHERE dbp097.schreiben.fahrt = 1
         */

        try {
            PreparedStatement preparedStatementAllUserAndRating =
                    connection.
                            prepareStatement("SELECT email, textnachricht, rating" +
                    " FROM dbp097.schreiben INNER JOIN dbp097.benutzer ON dbp097.schreiben.benutzer = dbp097.benutzer.bid " +
                    "INNER JOIN dbp097.bewertung ON dbp097.schreiben.bewertung = dbp097.bewertung.beid" +
                    " WHERE dbp097.schreiben.fahrt = ?"
            );

            preparedStatementAllUserAndRating.setInt(1, fahrtId);

            ResultSet resultSetAllUserAndRating = preparedStatementAllUserAndRating.executeQuery();

            while(resultSetAllUserAndRating.next()){

                /*
                 * Two steps to be done: We have to add each rating into the list so that we can calculate the average
                 * Retrieve the email, create the Bewertung object and then finally pass it to the list of Maps
                 */

                int rating = resultSetAllUserAndRating.getInt("rating");

                String textnachricht = resultSetAllUserAndRating.getString("textnachricht");

                totalBewertungForATrip.add(rating);

                Bewertung bewertung = new Bewertung.Builder().rating(rating).textNachricht(textnachricht).build();
//                Bewertung bewertung = new Bewertung();
//                bewertung.setRating(rating);
//                bewertung.setTextNachricht(textnachricht);

                String email = resultSetAllUserAndRating.getString("email");
                mapListOfUserAndHisRating.put(email, bewertung);
            }

        } catch (Exception e) {
            throw new StoreException(e);
        }

        System.out.println(mapListOfUserAndHisRating);

        return mapListOfUserAndHisRating;

    }

    /* Methods for the bonus page */

    public Map<String, Integer> getCreatorIdAndRatingWithHighestAvgRating(){

        /*
         * What we will be done here is the following:
         * A hashmap will be created that will have two entries:
         * A key called userId having the value of Anbieter's Id with the highest rating
         * A key called avgRating having the value of the average id
         */

        Map<String, Integer> mapContainingIdAndAvgRating = new HashMap<>();

        int highestAvgRating = 0;
        int driverWithHighestAvgRating =0;

        /*
         *  Now we will create a query that fetches the id of the user and his highest average rating by calculating
         * the average and then sort it in descending order and finally fetch the row that has the highest average rating
             SELECT anbieter, AVG(rating) AS avg_rating
             FROM bewertung
             INNER JOIN schreiben ON bewertung.beid = schreiben.bewertung
             INNER JOIN fahrt ON fahrt.fid = schreiben.fahrt
             GROUP BY anbieter
             ORDER BY avg_rating DESC
             FETCH FIRST 1 ROWS ONLY
         */



        try{
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT anbieter, AVG(rating) AS avg_rating FROM dbp097.bewertung " +
                            "INNER JOIN dbp097.schreiben ON dbp097.bewertung.beid = dbp097.schreiben.bewertung "+
                            "INNER JOIN dbp097.fahrt ON dbp097.fahrt.fid = dbp097.schreiben.fahrt " +
                            "GROUP BY anbieter ORDER BY avg_rating DESC FETCH FIRST 1 ROWS ONLY");

            /*
             * Now it is time to fetch the result set i.e the id of the driver and his average rating
             */


            ResultSet resultSetHighestRatedDriver = preparedStatement.executeQuery();

            System.out.println("Finding out if the result set has any values or not");

            while(resultSetHighestRatedDriver.next()){

                System.out.println("Fetching the id of the highest driver");
                driverWithHighestAvgRating = resultSetHighestRatedDriver.getInt("anbieter");
                System.out.println(driverWithHighestAvgRating);


                System.out.println("Fetching the highest average rating");
                highestAvgRating = resultSetHighestRatedDriver.getInt("avg_rating");
                System.out.println(highestAvgRating);



            }

            /*
             * Now after capturing the id of the driver and his highest rating, we need to set these values in the
             * map
             */

            mapContainingIdAndAvgRating.put("HighestAverageRating", highestAvgRating);
            mapContainingIdAndAvgRating.put("DriverId",driverWithHighestAvgRating);


        }catch(Exception e){

            e.printStackTrace();

        }


        return mapContainingIdAndAvgRating;
    }


    /*
     * Now we need a method that retrieves the email of best driver by providing the id
     */


    public String getEmailOfDriverWithHighestRating(int driverId){

        String emailOfDriverWithHighestRating = "";


        try{

            PreparedStatement preparedStatementForEmailOfHighestRatedDriver = connection
                    .prepareStatement("SELECT email FROM dbp097.benutzer WHERE bid = ?");

            preparedStatementForEmailOfHighestRatedDriver.setInt(1,driverId);

            ResultSet resultSetToGetEmail = preparedStatementForEmailOfHighestRatedDriver.executeQuery();

            while(resultSetToGetEmail.next()){

                System.out.println("Fetching the email of the highest rated driver");

                emailOfDriverWithHighestRating = resultSetToGetEmail.getString("email");

                System.out.println(emailOfDriverWithHighestRating);
            }

        }catch(Exception e){
                e.printStackTrace();
        }

        return emailOfDriverWithHighestRating;

    }




    /*
     * the second method needed to retrieve all the drives of the driver with the highest rating and they must be open
     */


    public List<Fahrt> getAllOpenDrivesOfHighestRatedDriver(int driverId){

        List<Fahrt> listOfOpenTripsCreatedByHighestRatedDriver = new ArrayList<>();

        try{

            /*
             *
             SELECT fid, startort, zielort
                    FROM dbp097.fahrt
                    WHERE anbieter = 1 AND status = 'offen'
             */
            PreparedStatement preparedStatementListOfAllDrives = connection.prepareStatement("SELECT fid, startort, zielort " +
                    "FROM dbp097.fahrt " +
                    "WHERE anbieter = ? AND status = 'offen' ");

            preparedStatementListOfAllDrives.setInt(1, driverId);

            ResultSet resultSetOfAllOpenDrives = preparedStatementListOfAllDrives.executeQuery();

            while(resultSetOfAllOpenDrives.next()){

                int tripId = resultSetOfAllOpenDrives.getInt("fid");

                System.out.println("Fetching the id of each open trip");
                System.out.println(tripId);

                Fahrt fahrt = new Fahrt.Builder()
                        .fahrtId(tripId)
                        .startOrt(resultSetOfAllOpenDrives.getString("startort"))
                        .zielOrt(resultSetOfAllOpenDrives.getString("zielort"))
                        .build();

                listOfOpenTripsCreatedByHighestRatedDriver.add(fahrt);

            }

        }catch(Exception e){

        }

        return listOfOpenTripsCreatedByHighestRatedDriver;
    }

}