package de.unidue.inf.is.domain;

import java.sql.Time;
import java.sql.Timestamp;

public class TimestampDB2 extends Timestamp {

    /*
     * The time format in Db2 is always in the following format 2022-02-15-18.00.00.000000
     * No need to look in the database anymore
     */

    public TimestampDB2(long time) {
        super(time);
    }

    /*
     * This method is used in the NewTripServlet class
     * Creating a utility method to convert the html timestamp received by the date and the time element to a
     * timestamp that fits db2
     * The date that is coming from the html will be for a example: 2022-01-11
     * The time that is coming from the html will be for a example: 14:31
     */


    public static String HtmlTimestampToDB2TimeStamp(String date, String time){
        StringBuilder stringBuilder = new StringBuilder();

        /*
         * First add the date
         */

        stringBuilder.append(date);

        /*
         * Add a missing dash that should be between the date and time
         */

        stringBuilder.append("-");

        /*
         * Replacing the colon with a dot
         */
        time = time.replace(':', '.');

        /*
         * Adding extra precision to the date
         */
        stringBuilder.append(time)
                .append(".00.000000");

        System.out.println("The converted timestamp is " + stringBuilder.toString());

        return stringBuilder.toString();
    }

    @Override
    public String toString() {

        String originalTimeStampFormat = super.toString();

        String appropriateString;

        String[] stringSplitAtSpace = originalTimeStampFormat.split(" ");

        String yearMonthDay = stringSplitAtSpace[0];

        String hourMinuteSecond = stringSplitAtSpace[1];

        hourMinuteSecond = hourMinuteSecond.replace(':', '.');

        return yearMonthDay + "-" + hourMinuteSecond;

    }
}
