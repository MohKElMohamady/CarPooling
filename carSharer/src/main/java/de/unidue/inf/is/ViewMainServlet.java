package de.unidue.inf.is;

import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.stores.FahrtStore;
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

    private List<Fahrt> reservedTrips;
    private List<Fahrt> openTrips;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("We RE IN THE VIEW MAIN SEVLET");

        String email = req.getParameter("email");
        try (UserStore userStore = new UserStore();
            FahrtStore fahrtStore= new FahrtStore())
        {
            String nameUser = userStore.getNameUser(email);
            reservedTrips=userStore.getTrips(email);
            //cchecking here if the data arrives in java or no!
            openTrips= fahrtStore.getOpenTrips();

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
