package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.domain.Reservieren;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;



public final class UserStore implements Closeable {


    private Connection connection;
    private boolean complete;


    public UserStore() throws StoreException {
        try {
            connection = DBUtil.getConnection();
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }


    public void getReservations(String email)  {

        List<Reservieren> reservierenList= new ArrayList<>();
        List<Fahrt> fahrtList= new ArrayList<>();

        try {

            //first get the ID of the user, whose email was passed as the parameter
            PreparedStatement preparedStatement = connection.prepareStatement("select bid from dbp097.benutzer where email = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            int ID = 0;
            while (resultSet.next()) {
                ID = resultSet.getInt("bid");
            }

            System.out.println("the id of the fetcched user: "+ ID);

            //Now get the reservations of the user with the above ID
            PreparedStatement preparedStatement2 = connection.prepareStatement("select * from dbp097.reservieren where kunde = ?");
            preparedStatement2.setInt(1, ID);
            ResultSet resultSet2 = preparedStatement2.executeQuery();

            while (resultSet2.next()) {
                Reservieren reservieren = new Reservieren();
               reservieren.setReservierenderUserId(resultSet2.getInt("kunde"));
               reservieren.setAnzahlPlaetze(resultSet2.getInt("anzPlaetze"));
               reservieren.setFahrtId(resultSet2.getInt("fahrt"));
               reservierenList.add(reservieren);
            }

            reservierenList.stream().forEach(System.out::println);


            //Now get the particular trip.
            for (Reservieren r: reservierenList) {
                System.out.println(r);
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM dbp097.fahrt WHERE dbp097.fahrt.fid = ?");
                System.out.println(r.getFahrtId());
                ps.setInt(1, r.getFahrtId());
                ResultSet resultSet3 = ps.executeQuery();

                while(resultSet3.next()){
                    Fahrt fahrt = new Fahrt();
                    fahrt.setStartOrt(resultSet3.getString("startort"));
                    fahrt.setZielOrt(resultSet3.getString("zielort"));
                    fahrt.setStatus(resultSet3.getString("status"));
                    fahrtList.add(fahrt);
                }

            }

            fahrtList.stream().forEach(System.out::println);

        }
        catch(SQLException e)
        {

        }



    }




    public void addUser(User userToAdd) throws StoreException {
        try {
            PreparedStatement preparedStatement = connection
                            .prepareStatement("insert into dbp097.benutzer (name, email) values (?, ?)");
            preparedStatement.setString(1, userToAdd.getName());
            preparedStatement.setString(2, userToAdd.getEmail());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public List<User> getAllUsers(){
        List<User> userList= new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from dbp097.benutzer");
            System.out.println("hello there 2!!");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User u= new User();
                u.setName(resultSet.getString("name"));
                u.setEmail(resultSet.getString("email"));
                userList.add(u);
            }

        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
        return userList;
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

}