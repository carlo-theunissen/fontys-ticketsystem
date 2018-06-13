package global;

import global.model.TicketExternalCommunicationModel;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RestHelper {
    public static String getHttp(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);

        // add header
        get.setHeader("Authorization", "Basic Y2FybG86cGFzc3dvcmQ=");

        CloseableHttpResponse response = null;
        BufferedReader rd = null;
        try {
            response = httpClient.execute(get);
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Response Code : " +
                    response.getStatusLine().getStatusCode());

            rd = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent()));

            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();

        } finally {
            httpClient.close();
            if(rd != null){
                try {
                    rd.close();
                } catch (IOException ignored) {}
            }
        }
    }
}
