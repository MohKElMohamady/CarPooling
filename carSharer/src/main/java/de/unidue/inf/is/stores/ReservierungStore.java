package de.unidue.inf.is.stores;

import de.unidue.inf.is.utils.DBUtil;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservierungStore implements Closeable {



        private Connection connection;
        private boolean complete;

        public ReservierungStore() throws StoreException {
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

        //now lets implement the update method which is going to be used in the ReserveServlet

    public void doReservation(int fid, int anzPlaetze){
        try{
            int userId= UserStore.getCurrentUserIdInSession();
//            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO DBP097.RESERVIEREN VALUES (kunde, fahrt, anzplaetze ");

              PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from dbp097.reservieren WHERE  kunde = ? AND fahrt = ?");
              preparedStatement.setInt(1, userId);
              preparedStatement.setInt(2, fid);
              ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                System.out.println("The result set has a query!!");


                PreparedStatement preparedStatement3 = connection.prepareStatement("UPDATE DBP097.RESERVIEREN SET anzplaetze=? " +
                "WHERE kunde= ? AND fahrt = ?");
                preparedStatement3.setInt(1, anzPlaetze);
                System.out.println("The anzplaetze is: " + anzPlaetze);

                preparedStatement3.setInt(2, userId);
                System.out.println("The userID is: " + userId);

                preparedStatement3.setInt(3, fid);
                System.out.println("The fahrtid  is: " + fid);

                preparedStatement3.executeUpdate();
                System.out.println("siggg");

                connection.commit();

            }
            else{

                PreparedStatement preparedStatement3 = connection.prepareStatement("INSERT INTO DBP097.RESERVIEREN (kunde, fahrt, anzplaetze) VALUES (?,?,?)");
                preparedStatement3.setInt(1, userId);
                preparedStatement3.setInt(2, fid);
                preparedStatement3.setInt(3, anzPlaetze);
                preparedStatement3.executeUpdate();
                connection.commit();
            }


//
//             if(!resultSet.isBeforeFirst()){
//                //this means we will have to make a new insert into the reservieren table. because there is no entry done by this user for this particular
//                PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO DBP097.RESERVIEREN (kunde, fahrt, anzplaetze) VALUES (?,?,?)");
//                preparedStatement2.setInt(1, userId);
//                preparedStatement2.setInt(2, fid);
//                preparedStatement2.setInt(3, anzPlaetze);
//                preparedStatement2.executeUpdate();
//                System.out.println("The anzplaetze is: " + anzPlaetze);
//                System.out.println("The userID is: " + userId);
//                System.out.println("The fahrtid  is: " + fid);
//            }
//            else{
//                //here we will need to do an update.
//                PreparedStatement preparedStatement3 = connection.prepareStatement("UPDATE DBP097.RESERVIEREN SET (anzplaetze) = (?) " +
//                        "WHERE kunde= ? AND fahrt = ?");
//                preparedStatement3.setInt(1, anzPlaetze);
//                preparedStatement3.setInt(2, userId);
//                preparedStatement3.setInt(3, fid);
//                preparedStatement3.executeUpdate();
//
//                System.out.println("The anzplaetze is: " + anzPlaetze);
//                System.out.println("The userID is: " + userId);
//                System.out.println("The fahrtid  is: " + fid);
//
//            }
        }
        catch(SQLException e){
        }
    }
}
