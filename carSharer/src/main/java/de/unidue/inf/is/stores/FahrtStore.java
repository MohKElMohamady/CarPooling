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
                    .prepareStatement("select startort, zielort, fahrtkosten, dbp097.fahrt.fid, freiplaetze " +
                            "from dbp097.anzfreiplaetze JOIN dbp097.fahrt " +
                            "ON dbp097.fahrt.fid= dbp097.anzfreiplaetze.fid ");

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Fahrt f= new Fahrt();
                f.setStartOrt(resultSet.getString("startort"));
                f.setZielOrt(resultSet.getString("zielort"));

                //the below variable is actually storing the free places for each trip. Just reusing the MaxPlaetze Variable.
                // Notice the columnLabel =)
                f.setMaxPlaetze(resultSet.getInt("freiPlaetze"));
                f.setFahrtKosten(resultSet.getDouble("fahrtkosten"));
                openTripsList.add(f);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        openTripsList.stream().forEach(System.out::println);
        System.out.println("#########################################################");
        return openTripsList;

    }}

