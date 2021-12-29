package de.unidue.inf.is;

import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.FahrtStore;
import de.unidue.inf.is.stores.ReservierungStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReserveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //checking if the fid and the number of seats reach here or no.
        //yes they do arrive in the servlet.
        int fid = Integer.parseInt(req.getParameter("fid"));
        int anzPlaetze = Integer.parseInt(req.getParameter("anzahlPlaetze"));

        //now make an update in the reservieren table.
        try (ReservierungStore reservierungStore = new ReservierungStore();
             FahrtStore fahrtStore = new FahrtStore()) {

            if (fahrtStore.getNumberFreePlaces(fid) < anzPlaetze) {
                req.setAttribute("errorCode", 1);
                req.setAttribute("fid", fid);
                req.getRequestDispatcher("/errorPage.ftl").forward(req, resp);
            }
            if(fahrtStore.hasUserAlreadyReserved(fid)){
                req.setAttribute("errorCode", 2);
                req.setAttribute("fid", fid);
                req.getRequestDispatcher("/errorPage.ftl").forward(req, resp);

            }
            else{
                reservierungStore.doReservation(fid, anzPlaetze);
                List<Fahrt> trip= fahrtStore.getAllInfoForTrip(fid);
                User anbieter= fahrtStore.getAnbieter(fid);
                req.setAttribute("trip", trip);
                req.setAttribute("email", anbieter.getEmail());
                req.getRequestDispatcher("/fahrt_details.ftl").forward(req, resp);

            }

        }
    }
}
