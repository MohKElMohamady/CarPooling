package de.unidue.inf.is;

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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String bewertungsText = req.getParameter("bewertungsText");

        int rating = Integer.parseInt(req.getParameter("bewertungAddedByUser"));





        req.getRequestDispatcher("/new_rating.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.getRequestDispatcher("/new_rating.ftl").forward(req, resp);

    }
}
