package de.unidue.inf.is;

import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.FahrtStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class FahrtDetailsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getParameter("fid"));
        int fahrtId= Integer.parseInt(req.getParameter("fid"));
        try(FahrtStore fahrtStore = new FahrtStore()){
            List<Fahrt> trip= fahrtStore.getAllInfoForTrip(fahrtId);
            User anbieter= fahrtStore.getAnbieter(fahrtId);





            req.setAttribute("trip", trip);
            req.setAttribute("email", anbieter.getEmail());
            req.getRequestDispatcher("/fahrt_details.ftl").forward(req, resp);

        }
    }
}
