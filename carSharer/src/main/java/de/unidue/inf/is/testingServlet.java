package de.unidue.inf.is;

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.stores.UserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class testingServlet extends HttpServlet {

    private static UserStore srv= new UserStore();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);
        System.out.println("Hello there!");
        List<User> userList= srv.getAllUsers();
        userList.stream().forEach(System.out::println);
    }
}
