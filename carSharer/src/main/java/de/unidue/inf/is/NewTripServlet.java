package de.unidue.inf.is;

import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.domain.TimestampDB2;
import de.unidue.inf.is.stores.FahrtStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class NewTripServlet extends HttpServlet {

    private final FahrtStore fahrtStore = new FahrtStore();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/new_drive.ftl").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int errorMessage = 0;

        final String start = req.getParameter("start");
        System.out.println("The starting point of the trip is " + start);

        final String destination = req.getParameter("destination");
        System.out.println("The destination of the trip is " + destination);

        final int maximumCapacity = Integer.parseInt(req.getParameter("maxCapacity"));
        System.out.println("The maximum capacity is "  + maximumCapacity);

        final int tripCost = Integer.parseInt(req.getParameter("tripCost"));
        System.out.println("The cost is "  + maximumCapacity);

        final int transportmittel = Integer.parseInt(req.getParameter("transportmittel"));
        System.out.println("The type of transport is " + transportmittel);

        String transportmittelString = "";

        switch (transportmittel){
            case 1:
                transportmittelString = "Auto";
                break;
            case 2:
                transportmittelString = "Bus";
                break;
            case 3:
                transportmittelString = "Kleintransporter";
                break;
            default:
                transportmittelString = null;
                break;
        }

        final String description = req.getParameter("description");
        System.out.println("The description of the trip is: " + description);

        String date = req.getParameter("date");

        System.out.println("The date coming from the html form is " + date);

        String time = req.getParameter("time");

        System.out.println("The date coming from the html form is " + time);

        final String db2ReadyTimeStamp = TimestampDB2.htmlTimestampToDB2TimeStamp(date, time);

        System.out.println("The following timestamp will be directly passed to the store to save it : " + db2ReadyTimeStamp);

        final Fahrt tripToBeCreated = new Fahrt.Builder()
                .startOrt(start)
                .zielOrt(destination)
                .maxPlaetze(maximumCapacity)
                .fahrtKosten(tripCost)
                .time(db2ReadyTimeStamp)
                .status("offen")
                .beschreibung(description)
                .build();

        if(maximumCapacity > 10){

            errorMessage = 4;
            req.getRequestDispatcher("/error.ftl");
        }

        try{

            Date convertedDateFromHtml = TimestampDB2.htmlDateToJavaDate(date);

            System.out.println(convertedDateFromHtml);

            TimestampDB2.isDateinPast(date);

            TimestampDB2.htmlTimeToJavaTime(time);




        }catch(Exception e){
            e.printStackTrace();
        }

//        Fahrt newTrip = fahrtStore.createNewTrip(tripToBeCreated, transportmittel);

//        System.out.println("The newly created trip is " + newTrip);

        req.getRequestDispatcher("/new_drive.ftl").forward(req, resp);

    }
}
