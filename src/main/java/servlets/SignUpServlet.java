package servlets;

import dbservice.DBException;
import dbservice.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpServlet extends HttpServlet {

    private final DBService dbService;

    public SignUpServlet(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login == null || password == null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        long id = 0L;

        try {
            id = dbService.addUser(login, password);
        } catch (DBException e) {
            e.printStackTrace();
        }
        if (id != 0) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Successfully sign up");
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Error of enter");
            resp.setStatus(HttpServletResponse.SC_OK);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("Hello");
    }
}
