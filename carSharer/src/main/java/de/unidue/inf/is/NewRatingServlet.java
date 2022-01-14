package de.unidue.inf.is;

import de.unidue.inf.is.stores.BewertungStore;
import de.unidue.inf.is.stores.UserStore;

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

            req.getRequestDispatcher("/new_rating.ftl").forward(req, resp);
        }


    }
}
