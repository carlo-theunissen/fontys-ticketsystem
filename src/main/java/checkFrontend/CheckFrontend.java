package checkFrontend;

import checkFrontend.ticketValidation.TicketValidation;
import global.Browser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CheckFrontend extends Application {
    private Scene scene;
    private NotifyWhenCollectionChanged cachedNotifier;
    @Override public void start(Stage stage) {
        stage.setTitle("Web View");
        Browser browser = new Browser(getClass().getResource("/checkFrontend/generate.html").toExternalForm());
        BackendToJavascriptCommunication backendToJavascriptCommunication = new BackendToJavascriptCommunication(browser.getWebEngine());
        TicketValidation validation = new TicketValidation(backendToJavascriptCommunication);
        browser.setJsApp(new JavascriptToBackendCommunication(validation));

        cachedNotifier = new NotifyWhenCollectionChanged(backendToJavascriptCommunication, validation.getTicketCollection());
        scene = new Scene(browser,550,500, Color.web("#666970"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}