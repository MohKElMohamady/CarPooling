package de.unidue.inf.is;

import de.unidue.inf.is.domain.Bewertung;
import de.unidue.inf.is.domain.EmailBeschreibungRating;
import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.BewertungStore;
import de.unidue.inf.is.stores.FahrtStore;
import de.unidue.inf.is.stores.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * To Do:
 *
 */
public class NewRatingServlet extends HttpServlet {

    BewertungStore bewertungStore = new BewertungStore();
    int fid=0;
    int count =0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fid= Integer.parseInt(req.getParameter("fid"));
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("The fid is : " + req.getParameter("fid") );
        req.getRequestDispatcher("/new_rating.ftl").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (count ==0)
        {
            doGet(req,resp);
            count++;
        }

        else{

            count =0;
            int userId = UserStore.getCurrentUserIdInSession();

            String bewertungsText = req.getParameter("bewertungsText");

            int rating = Integer.parseInt(req.getParameter("bewertungAddedByUser"));

            try
            {
                bewertungStore.insertBewertung(bewertungsText, rating, fid, userId);
            }
            catch(Exception e){
                e.printStackTrace();
                req.setAttribute("errorCode", 3);
                req.setAttribute("fid", fid);
                req.getRequestDispatcher("/errorPage.ftl").forward(req, resp);
            }


            try(FahrtStore fahrtStore = new FahrtStore();
                BewertungStore bewertungStore = new BewertungStore()){

                List<Fahrt> trip= fahrtStore.getAllInfoForTrip(fid);
                User anbieter= fahrtStore.getAnbieter(fid);

                Map<String, Bewertung> mailBewertungMap =
                        bewertungStore.retreiveAllBewerterAndTheirBewertungen(fid);

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
}
