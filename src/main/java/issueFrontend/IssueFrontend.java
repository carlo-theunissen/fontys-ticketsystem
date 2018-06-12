package issueFrontend;

import global.Browser;
import global.JavascriptToBackendCommunication;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class IssueFrontend extends Application {
    private Scene scene;
    @Override public void start(Stage stage) {
        stage.setTitle("Web View");
        Browser browser = new Browser(getClass().getResource("/issueFrontend/generate.html").toExternalForm());
        scene = new Scene(browser,550,500, Color.web("#666970"));
        browser.setJsApp(new JavascriptToBackendCommunication(new ClientTicketCreator(new BackendToJavascriptCommunication(browser.getWebEngine()))));
        stage.setScene(scene);
       // scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}