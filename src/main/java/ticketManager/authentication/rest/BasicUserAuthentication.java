package ticketManager.authentication.rest;

import ticketManager.model.RESTUserModel;

import javax.xml.bind.DatatypeConverter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicUserAuthentication implements IRESTAuthProvider {
    private static final String REGEX = "^Basic (.+)";
    public boolean authStringValid(String authString) {
        return authString.matches(REGEX);
    }

    public RESTUserModel getUser(String authString) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(authString);
        matcher.find();
        String auth = matcher.group(1);
        String parsedAuth = new String(DatatypeConverter.parseBase64Binary(auth));
        String[] split = parsedAuth.split(":");
        if(split[1].equals("password")){
            RESTUserModel model = new RESTUserModel();
            model.setName(split[0]);
            return model;
        }
        return null;
    }
}
