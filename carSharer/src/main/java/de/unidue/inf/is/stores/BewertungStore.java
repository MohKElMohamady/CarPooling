package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.Bewertung;
import de.unidue.inf.is.domain.TimestampDB2;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class BewertungStore extends Store{

    /*
     * General question: After the creation of the Bewertung, we need to retrieve it back again (inefficient?)
     * because how will we insert it back again?
     */

    public Bewertung insertBewertung(String textNachricht, int rating, int fahrtId, int userId){

        /*
         * There is no need to fetch the date from the Servlet or the Html page it will be
         * created here automatically
         */

        try{

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

            preparedStatementForInsertingBewertung.setString(1,  textNachricht);
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

            while(resultSetBewertungId.next()){

                bewertungId = resultSetBewertungId.getInt("beid");

                System.out.println("The retrieved id of the bewertung is " + bewertungId);
            }

            /*
             * Time for insertion into the Schreiben table
             */

            PreparedStatement preparedStatementForInsertingSchreiben =
                    connection.prepareStatement("INSERT INTO dbp097.schreiben (benutzer, fahrt, bewertung) VALUES (?, ?, ?)");

            preparedStatementForInsertingSchreiben.setInt(1,userId);
            preparedStatementForInsertingSchreiben.setInt(2,fahrtId);
            preparedStatementForInsertingSchreiben.setInt(3,bewertungId);

            preparedStatementForInsertingSchreiben.executeUpdate();

            connection.commit();

        }catch (Exception e){
            e.printStackTrace();
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

}
