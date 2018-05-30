package ticketManager.webserver;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.glassfish.jersey.servlet.ServletContainer;
import ticketManager.authentication.websocket.SimpleAuthenticationGuard;
import ticketManager.databaseAccessLayer.DatabaseTicketContext;
import ticketManager.logic.MutationManager;
import ticketManager.logic.TicketRepository;

import javax.websocket.server.ServerContainer;

// https://github.com/jetty-project/embedded-jetty-websocket-examples/tree/master/javax.websocket-example/src/main/java/org/eclipse/jetty/demo

/**
 * https://github.com/jetty-project/embedded-jetty-websocket-examples/blob/
 * master/javax.websocket-example/src/main/java/org/eclipse/
 * jetty/demo/EventServer.java
 *
 * @author Nico Kuijpers, copied from github, adapted by Marcel Koonen
 */

/**
 var test = function() {
 var ws = new WebSocket("ws://localhost:8080/ws/");
 ws.onmessage = function (message) {
 console.log(message.data);
 };
 setTimeout(function(){ ws.send("password");}, 500);
 setTimeout(function(){ ws.send("181480200001");}, 1000);
 };
 */
public class EventServer {

    private static MutationManager mutationManager = new MutationManager();
    private static TicketRepository repository = new TicketRepository(new DatabaseTicketContext());
    private static SimpleAuthenticationGuard authentication = new SimpleAuthenticationGuard();

    public static MutationManager getMutationManager() {
        return mutationManager;
    }

    public static TicketRepository getRepository() {
        return repository;
    }

    public static SimpleAuthenticationGuard getWebSocketAuthentication() {
        return authentication;
    }

    public static void main(String[] args) {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        ServletHolder jerseyServlet =
                context.addServlet(ServletContainer.class, "/api/*");
        jerseyServlet.setInitOrder(0);
        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
                TicketsApiServices.class.getCanonicalName());

        try {
            // Initialize javax.websocket layer
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);

            // Add WebSocket endpoint to javax.websocket layer
            wscontainer.addEndpoint(EventServerSocket.class);

            server.start();
            server.dump(System.out);

            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }
}
