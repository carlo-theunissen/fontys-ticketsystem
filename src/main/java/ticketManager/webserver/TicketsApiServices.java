package ticketManager.webserver;

import com.google.gson.Gson;
import model.TicketModel;
import model.TicketMutationModel;
import ticketManager.authentication.rest.BasicUserAuthentication;
import ticketManager.authentication.rest.IRESTAuthProvider;
import ticketManager.exceptions.UnauthorisedException;
import ticketManager.logic.*;
import ticketManager.model.RESTUserModel;
import ticketManager.model.TicketExternalCommunicationModel;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/ticket")
public class TicketsApiServices {

    private  final IRESTAuthProvider authProvider;

    public TicketsApiServices() {
        authProvider = new BasicUserAuthentication();
    }

    private RESTUserModel getUserModelOrThrowException(String authString) throws UnauthorisedException {
        if(!authProvider.authStringValid(authString)){
            throw new UnauthorisedException();
        }
        RESTUserModel model = authProvider.getUser(authString);
        if(model == null){
            throw new UnauthorisedException();
        }
        return model;
    }

    @GET
    @Path("/dump")
    public Response getStatus(@HeaderParam("Authorization") String authString) throws UnauthorisedException {
        getUserModelOrThrowException(authString); //authentication

        ITicketCollection collection = new TicketCollectionProvider(EventServer.getRepository());

        ArrayList<TicketExternalCommunicationModel> models = new ArrayList<TicketExternalCommunicationModel>();
        Gson gson = new Gson();
        for (TicketModel model : collection.getStartDump()){
            models.add( new TicketExternalCommunicationModel(model) );
        }
        return Response.status(200).entity(gson.toJson(models)).build();
    }

    @GET
    @Path("/new")
    public Response getNewTicket(@HeaderParam("Authorization") String authString) throws UnauthorisedException {
        getUserModelOrThrowException(authString); //authentication
        ITicketGenerator collection = new TicketCreator(EventServer.getMutationManager(), EventServer.getRepository());
        TicketMutationModel ticket = collection.newTicket();

        new CheckUnitBroadcaster(EventServer.getWebSocketAuthentication()).send(ticket);

        Gson gson = new Gson();
        return Response.status(200).entity(gson.toJson(new TicketExternalCommunicationModel(ticket.getTicket()))).build();
    }


}
