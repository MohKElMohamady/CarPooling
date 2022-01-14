package de.unidue.inf.is;

import de.unidue.inf.is.domain.Bewertung;
import de.unidue.inf.is.domain.EmailBeschreibungRating;
import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.BewertungStore;
import de.unidue.inf.is.stores.FahrtStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class FahrtDetailsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        int fahrtId= Integer.parseInt(req.getParameter("fid"));
        try(FahrtStore fahrtStore = new FahrtStore();
            BewertungStore bewertungStore = new BewertungStore()){

            List<Fahrt> trip= fahrtStore.getAllInfoForTrip(fahrtId);
            User anbieter= fahrtStore.getAnbieter(fahrtId);

            Map<String, Bewertung> mailBewertungMap =
                    bewertungStore.retreiveAllBewerterAndTheirBewertungen(fahrtId);

            List<Bewertung> totalBewertung = new ArrayList<>();

            for(Map.Entry<String, Bewertung> entry : mailBewertungMap.entrySet()){
                totalBewertung.add(entry.getValue());
            }

            double averageRating=0;
            System.out.println(totalBewertung);

            if(totalBewertung.size()>0){
                try {

                    averageRating = totalBewertung.stream().
                            mapToDouble(Bewertung::getRatingAsDouble)
                            .average()
                            .getAsDouble();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            List<EmailBeschreibungRating> mailBewertungList= new ArrayList<>();
            for (Map.Entry<String, Bewertung> entry : mailBewertungMap.entrySet()){
                EmailBeschreibungRating obj= new EmailBeschreibungRating();
                obj.setBeschreibung(entry.getValue().getTextNachricht());
                obj.setEmail(entry.getKey());
                obj.setRating(entry.getValue().getRating());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println(obj);
                mailBewertungList.add(obj);
            }


            System.out.println("The average rating is " + averageRating);

            req.setAttribute("trip", trip);
            req.setAttribute("email", anbieter.getEmail());
            req.setAttribute("avgRating",averageRating);
            req.setAttribute("emailsAndTheirRatings", mailBewertungList);
            req.getRequestDispatcher("/fahrt_details.ftl").forward(req, resp);

        }
    }
}
