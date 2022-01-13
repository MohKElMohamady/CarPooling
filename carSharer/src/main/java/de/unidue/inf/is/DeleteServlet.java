package de.unidue.inf.is;

import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.stores.BewertungStore;
import de.unidue.inf.is.stores.FahrtStore;
import de.unidue.inf.is.stores.ReservierungStore;
import de.unidue.inf.is.stores.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int fid = Integer.parseInt(req.getParameter("fid"));
        try (FahrtStore fahrtStore = new FahrtStore();
            BewertungStore bs= new BewertungStore();
            ReservierungStore rs= new ReservierungStore();
            UserStore us= new UserStore()) {

            String errorMessage = "";

            /*
             * We are starting with the process of validating the deletion
             *
             * First step below is checking if the person who is trying to delete the trip is the actual
             * owner i.e the anbieter
             */

            int userIDofRideMaker = fahrtStore.getUserIDofRideMaker(fid);
            if(!(userIDofRideMaker ==UserStore.getCurrentUserIdInSession())){
                req.setAttribute("errorCode", 3);
                req.setAttribute("fid", fid);



                req.getRequestDispatcher("/errorPage.ftl").forward(req, resp);
            }

            //will enter the else if the creator is trying to delete!
            else{
//                delete all the bewertungen related to this trip!
                bs.deleteBewertungWithFid(fid);
                rs.deleteReservierungWithFid(fid);
                fahrtStore.deleteFahrtWithFid(fid);
                //and now the page redirect to the main page or something
//                String emailCurrentUser= us.getEmailCurrentUser();
//                List<Fahrt> reservedTrips= us.getTrips(emailCurrentUser);
//                List<Fahrt> openTrips= us.getTrips(emailCurrentUser);

                try (UserStore userStore = new UserStore())
                {
                    String email= us.getEmailCurrentUser();
                    System.out.println("THE EMAIL OF THE CURRENT USER IS: "+ email);
                    String nameUser = userStore.getNameUser(email);
                    System.out.println("THE NAME OF THE CURRENT USER IS: "+ nameUser);
                    List<Fahrt> reservedTrips = userStore.getTrips(email);
                    //cchecking here if the data arrives in java or no!
                    List<Fahrt> openTrips = fahrtStore.getOpenTrips();

                    //now we need to send this entire list to a ftl page.
                    req.setAttribute("nameUser", nameUser);
                    req.setAttribute("reservedTrips", reservedTrips);
                    req.setAttribute("openTrips", openTrips);

                    req.getRequestDispatcher("/view_main.ftl").forward(req, resp);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

}
