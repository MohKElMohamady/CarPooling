package de.unidue.inf.is.domain;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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


    public static String htmlTimestampToDB2TimeStamp(String date, String time){
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



// this method converts the date coming from the html to java date. used to check if date is in the past or no.!!
    //used by the newFahrt servlet
    public static Date htmlDateToJavaDate(String dateFromHtml) throws ParseException {


        Date date1=new SimpleDateFormat("yyy-MM-dd").parse(dateFromHtml);
        System.out.println("the below is the date form the method!!!!!!!!!");
        System.out.println(dateFromHtml+ "  " + date1);
        return date1;
    }

    public static Date htmlTimeToJavaTime(String timeFromHtml) throws ParseException {

        String strTime = timeFromHtml;
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Date d = dateFormat.parse(strTime);
        System.out.println("Resultant  Time = " + d);
        return null;
    }



    public static boolean isDateinPast(String dateFromHtml) throws ParseException {
        Date convertedDate= htmlDateToJavaDate(dateFromHtml);

        Date datefromJava=java.util.Calendar.getInstance().getTime();
        System.out.println("this is the current java time" + " " + datefromJava);
        if (convertedDate.after(datefromJava)){
            System.out.println("YAY you entered a date in the future!!!!");
            return true;

        }
        else{
            System.out.println("bruh u enterted a date in the past");
            return false;
        }

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
