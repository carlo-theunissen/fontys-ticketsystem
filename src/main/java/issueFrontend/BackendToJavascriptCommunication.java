package issueFrontend;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;

public class BackendToJavascriptCommunication implements INetworkStatusUpdate {
    private final WebEngine webEngine;
    private volatile boolean isOnline = true;
    private volatile int amountOffline;
    public BackendToJavascriptCommunication(WebEngine webEngine) {
        this.webEngine = webEngine;
    }


    public void online() {
        isOnline = true;
        Platform.runLater(new Runnable() {
            public void run() {
                if(isOnline) {
                    webEngine.executeScript("online()");
                }
            }
        });

    }

    public void offline(int amount) {
        amountOffline= amount;
        isOnline = false;
        Platform.runLater(new Runnable() {
            public void run() {
                if(!isOnline) {
                    webEngine.executeScript("offline(" + amountOffline + ")");
                }
            }
        });

    }
}
