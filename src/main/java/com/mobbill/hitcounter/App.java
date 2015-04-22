package com.mobbill.hitcounter;

import com.mobbill.hitcounter.config.CassandraConfig;
import com.mobbill.hitcounter.util.connection.CassandraConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.logging.Level;
import java.util.logging.Logger;


public class App 
{

    private final static Logger LOG = Logger.getLogger(App.class.getName());

    private final static String RESTFUL_PACKAGE = "com.mobbill.hitcounter.restful";

    private static boolean live;



    public static void main( String[] args ) throws Exception{

        live = (args.length >0 && "live".equals(args[0]));

        int port        = (live) ? 80 : 8888;
        CassandraConnector.initInstance(new CassandraConfig(live));

        Server jettyServer = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        jerseyServlet.setInitParameter("jersey.config.server.provider.packages", RESTFUL_PACKAGE);


        LOG.log(Level.INFO, "\n\nListening in port " + port + "...\n\n");
        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }


}
