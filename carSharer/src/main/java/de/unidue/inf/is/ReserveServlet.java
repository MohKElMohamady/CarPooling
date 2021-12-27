package de.unidue.inf.is;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReserveServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //checking if the fid and the number of seats reach here or no.
        System.out.println(req.getParameter("fid"));
        System.out.println(req.getParameter("anzahlPlaetze"));
    }



}
