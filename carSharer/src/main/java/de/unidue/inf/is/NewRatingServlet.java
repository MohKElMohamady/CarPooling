package de.unidue.inf.is;

import de.unidue.inf.is.stores.BewertungStore;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {





        req.getRequestDispatcher("/new_rating.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /* Because the FahrtId was not passed into the query string, it will be hardwired into the code
         *
         */

        int fahrtId = 3;

        int userId = 4;

        String bewertungsText = req.getParameter("bewertungsText");

        int rating = Integer.parseInt(req.getParameter("bewertungAddedByUser"));


        bewertungStore.insertBewertung(bewertungsText,rating, fahrtId, userId);

        req.getRequestDispatcher("/new_rating.ftl").forward(req, resp);

    }
}
