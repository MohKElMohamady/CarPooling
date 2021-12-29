package de.unidue.inf.is;

import de.unidue.inf.is.stores.BewertungStore;
import de.unidue.inf.is.stores.FahrtStore;
import de.unidue.inf.is.stores.ReservierungStore;
import de.unidue.inf.is.stores.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int fid = Integer.parseInt(req.getParameter("fid"));
        try (FahrtStore fahrtStore = new FahrtStore();
            BewertungStore bs= new BewertungStore();
            ReservierungStore rs= new ReservierungStore()) {

            int userIDofRideMaker = fahrtStore.getUserIDofRideMaker(fid);
            if(!(userIDofRideMaker ==UserStore.getCurrentUserIdInSession())){
                req.setAttribute("errorCode", 3);
                req.setAttribute("fid", fid);
                req.getRequestDispatcher("/errorPage.ftl").forward(req, resp);
            }

            //will enter the else if the creator is trying to delete!
            else{
                //delete all the bewertungen related to this trip!
                bs.deleteBewertungWithFid(fid);
                rs.deleteReservierungWithFid(fid);
                fahrtStore.deleteFahrtWithFid(fid);

            }

        }
    }

}
