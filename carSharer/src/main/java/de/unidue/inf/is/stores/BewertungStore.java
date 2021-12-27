package de.unidue.inf.is.stores;

import de.unidue.inf.is.domain.Bewertung;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;

public class BewertungStore extends Store{

    /*
     * General question: After the creation of the Bewertung, we need to retrieve it back again (inefficient?)
     * because how will we insert it back again?
     */

    public Bewertung insertBewertung(String textNachricht, int rating){

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
             *
             */

            PreparedStatement preparedStatementForInsertingBewertung = connection.prepareStatement
                    ("INSERT INTO dbp097.bewertung (textnachricht, rating, erstellungsdatum) VALUES ( ? , ?, ?, ?)");

            /*
             * Here we are extracting the current time at which this method is called using currentTimeMillis();
             * Then we are passing this long containing the time into the constructor of the timestap so that it properly
             * converted into the equivalent sql timestamp object.
             */

            Long currentTimeMillis = System.currentTimeMillis();
            Timestamp currentTimeStamp = new Timestamp(currentTimeMillis);

            System.out.println("Current time in timestamp is " + currentTimeStamp);

            preparedStatementForInsertingBewertung.setString(1, textNachricht);
            preparedStatementForInsertingBewertung.setInt(2, rating);
             /*
              * Unsure what method to use on the prepared statement to insert the date? How would it be possible to fit
              * the DB2 data in the preparedStatement.
              */
            preparedStatementForInsertingBewertung.setTimestamp(3, currentTimeStamp);
        }catch (Exception e){
            e.printStackTrace();
        }

        Bewertung bewertung = new Bewertung();

        return bewertung;

    }


}
