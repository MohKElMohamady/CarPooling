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




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try(UserStore srv= new UserStore()) {
            System.out.println("Hello there!!");

            User userToAdd= new User();
            userToAdd.setName("ebrahim");
            userToAdd.setEmail("ebrahim@uni-due.de");
            srv.addUser(userToAdd);

            //now print all the users
            List<User> userList = srv.getAllUsers();
            userList.stream().forEach(System.out::println);

        }
    }
}
