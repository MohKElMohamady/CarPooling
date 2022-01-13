package de.unidue.inf.is;

import de.unidue.inf.is.stores.BewertungStore;
import de.unidue.inf.is.stores.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * To Do:
 *
 */
public class NewRatingServlet extends HttpServlet {

    BewertungStore bewertungStore = new BewertungStore();
    int fid=0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fid= Integer.parseInt(req.getParameter("fid"));
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("The fid is : " + req.getParameter("fid") );
        req.getRequestDispatcher("/new_rating.ftl").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);



        /* Because the FahrtId was not passed into the query string, it will be hardwired into the code
         *
         */

        int userId = UserStore.getCurrentUserIdInSession();

        String bewertungsText = req.getParameter("bewertungsText");

        int rating = Integer.parseInt(req.getParameter("bewertungAddedByUser"));

        bewertungStore.insertBewertung(bewertungsText,rating, fid, userId);

        req.getRequestDispatcher("/new_rating.ftl").forward(req, resp);

    }
}
