package de.unidue.inf.is;

import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.stores.BewertungStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BonusServlet extends HttpServlet {

    private BewertungStore bewertungStore = new BewertungStore();




    /*
     * Notice: Although the bonus servlet fetches data from multiple data i.e
     * 1) the highest rating from bewertung table
     * 2) the email from the benutzer id
     * 3) the list of trips from the fahrt table
     *
     * We are putting all of these methods inside the bewertung store to create less instances of objects
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*
         * We need to create a bewertungStore to fetch the data from the database
         */

        BewertungStore bewertungStore = new BewertungStore();

        /*
         * This map will contain the id and the highest average rating of the best driver
         */

        Map<String, Integer> bestDriverAndHisAvgRating =  bewertungStore.getCreatorIdAndRatingWithHighestAvgRating();

        /*
         * This list will contain all the open trips created by the highest rated driver
         */

        List<Fahrt> listOfAllOpenTripsByHighestRatedDriver = new ArrayList<>();

        int highestAvgRating = bestDriverAndHisAvgRating.get("HighestAverageRating"); // Fetching the rating
        int idOfHighestRatedDriver = bestDriverAndHisAvgRating.get("DriverId"); // Fetching the id

        String emailOfBestDriver = bewertungStore.getEmailOfDriverWithHighestRating(idOfHighestRatedDriver); // Fetching the email

        listOfAllOpenTripsByHighestRatedDriver = bewertungStore
                .getAllOpenDrivesOfHighestRatedDriver(idOfHighestRatedDriver); // Fetching all the open drives

        listOfAllOpenTripsByHighestRatedDriver.stream()
                .forEach(System.out::println);

        req.setAttribute("Email", emailOfBestDriver);
        req.setAttribute("AvgRating",highestAvgRating);
        req.setAttribute("ListOfOpenTrips", listOfAllOpenTripsByHighestRatedDriver);

        req.getRequestDispatcher("/bonus.ftl").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doGet(req, resp);

    }
}
