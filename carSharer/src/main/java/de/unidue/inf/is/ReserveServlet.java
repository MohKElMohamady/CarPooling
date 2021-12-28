package de.unidue.inf.is;

import de.unidue.inf.is.stores.FahrtStore;
import de.unidue.inf.is.stores.ReservierungStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReserveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //checking if the fid and the number of seats reach here or no.
        //yes they do arrive in the servlet.
        int fid= Integer.parseInt(req.getParameter( "fid"));
        int anzPlaetze= Integer.parseInt(req.getParameter("anzahlPlaetze"));

        //now make an update in the reservieren table.
        try(ReservierungStore reservierungStore= new ReservierungStore();
            FahrtStore fahrtStore= new FahrtStore()){
            //now we will have to test this query.
            //lets check how it is right now in db2
            //lets do all the validations
            //checking if the fahrt is still open
            boolean isOpen= fahrtStore.isTripOpen(fid);
            if(!isOpen){
                System.out.println("WWWWW the RIDE IS ALREADY CLOSEEEEEED. GTFOOOOO!!!");
            }
//            if(fahrtStore.getNumberFreePlaces(fid)<anzPlaetze){
//                //here we will have to go to another error page. (with enum)
//            }



            reservierungStore.doReservation(fid, anzPlaetze);
        }
    }
}
