package ticketManager.authentication.rest;

import ticketManager.model.RESTUserModel;

public interface IRESTAuthProvider {
    boolean authStringValid(String authString);
    RESTUserModel getUser(String authString);
}
