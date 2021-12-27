package de.unidue.inf.is;


import de.unidue.inf.is.domain.Fahrt;
import de.unidue.inf.is.stores.FahrtStore;
import de.unidue.inf.is.utils.DateTimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ViewSearchServlet extends HttpServlet {

    FahrtStore fahrtStore = new FahrtStore();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        req.getRequestDispatcher("/view_search.ftl").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Fahrt> fahrteFromSearch;

        String start = req.getParameter("start");

        String ziel = req.getParameter("ziel");

        /*
         * In this step we have fetched first the date from the request AND THEN changed the format of the date from
         * YYYY-MM-DD to DD-YY-YYYY to work properly with DB2
         */

        String date = DateTimeUtil.retrieveDateInDDMMYYYY(req.getParameter("abDate"));

        /*
         * Every date coming from the Html file must be concatenated with a time, but since we are using a
         * Html date input, we are only getting a date. Hence, we are adding 00:00 as a starting time.
         * By using the convertDateAndTimeToDB2DateTime() method
         */

        String dateAndTimeToDB2DateTime = DateTimeUtil.convertDateAndTimeToDB2DateTime(date, "00:00");

        System.out.println(DateTimeUtil.convertDateAndTimeToDB2DateTimeModified(date, "00:00"));


        fahrteFromSearch = fahrtStore.getFahrtFromSearch(start, ziel, dateAndTimeToDB2DateTime);

        //fahrteFromSearch.stream().forEach(System.out::println);

        if(!fahrteFromSearch.isEmpty()){
            req.setAttribute("fahrteFromSearch", fahrteFromSearch);
        }else{
            req.setAttribute("isEmpty",true);
        }

        req.getRequestDispatcher("/view_search.ftl").forward(req, resp);

    }
}
