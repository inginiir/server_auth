package servlets;

import dbservice.DBException;
import dbservice.DBService;
import dbservice.dataSets.UserDataSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final DBService dbService;

    public SignInServlet(DBService dbService) {
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
        UserDataSet dataSet = null;
        try {
            dataSet = dbService.getUser(login);
        } catch (DBException e) {
            e.printStackTrace();
        }
        if (dataSet != null && dataSet.getPassword().equals(password)) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Authorized: " + login);
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("Unauthorized");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
