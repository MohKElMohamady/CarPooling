package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    //now lets implement the methods needed for the Fahrt table.

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
                f.setMaxPlaetze(resultSet.getInt("freiPlaetze"));
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

    //the below method will get all the information for a particular trip with the ID that gets passed to it!
    public List<Fahrt> getAllInfoForTrip(int fid){
        List<Fahrt> fahrtWithInfo= new ArrayList<>();
        try {
            //I can directly do this since i have made a new view called "anzfreiplaetze"
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from dbp097.fahrt where dbp097.fahrt.fid= ?");

            preparedStatement.setInt(1, fid);
            ResultSet resultSet = preparedStatement.executeQuery();


            while(resultSet.next()){
                Fahrt f= new Fahrt();
                f.setAnbieter(resultSet.getInt("anbieter"));
                String time= resultSet.getString("fahrtdatumzeit");
                //for now i Just want to check if the time comes as a string or no
                System.out.println(time);
                f.setStartOrt(resultSet.getString("startort"));
                f.setZielOrt(resultSet.getString("zielort"));
                //f.setMaxPlaetze(resultSet.getInt("freiplaetze"));
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


}

