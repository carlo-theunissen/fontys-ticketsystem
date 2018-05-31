package issueFrontend;

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
        scene = new Scene(new Browser(getClass().getResource("/issueFrontend/generate.html").toExternalForm()),550,500, Color.web("#666970"));
        stage.setScene(scene);
       // scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}