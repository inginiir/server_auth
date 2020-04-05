package main;

import dbservice.DBService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SignInServlet;
import servlets.SignUpServlet;

public class Main {

    public static void main(String[] args) throws Exception {

        DBService dbService = new DBService();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(new SignUpServlet(dbService)),"/signup");
        context.addServlet(new ServletHolder(new SignInServlet(dbService)),"/signin");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("resources");

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, context});

        Server server = new Server(8080);
        server.setHandler(handlerList);

        server.start();
        dbService.printConnectInfo();
        System.out.println("Server started");
        server.join();
    }
}
