package de.unidue.inf.is;

import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.stores.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewMainServlet extends HttpServlet {

    private List<Fahrt> trips;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        super.doPost(req, resp);
        String email = req.getParameter("email");
        try (UserStore userStore = new UserStore()) {
            trips=userStore.getTrips(email);
            //now we need to send this entire list to a ftl page.
            req.setAttribute("trips", trips);
            req.getRequestDispatcher("/view_main.ftl").forward(req, resp);

        }
    }
}
