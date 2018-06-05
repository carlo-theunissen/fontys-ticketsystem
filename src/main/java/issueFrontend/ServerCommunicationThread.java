package issueFrontend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import global.interfaces.INetworkStatusUpdate;
import global.model.TicketModel;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerCommunicationThread extends Thread{



    /**
     * guarded by this.processTickets itself
     */
    private final BlockingQueue<TicketModel> processTickets = new LinkedBlockingQueue<TicketModel>();

    private final INetworkStatusUpdate network;

    ServerCommunicationThread(INetworkStatusUpdate network){
        super("CommunicationThread");
        this.network = network;
    }
    public void sendNewTicketToServer(TicketModel model){
        try {
            processTickets.put(model);
        } catch (InterruptedException ignored) {

        }
    }

    @Override
    public void run() {
        super.run();
        while (true){
            System.out.println("Tick");
            try {
                TicketModel model = processTickets.take();
                handleTicketSending(model);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    private void handleTicketSending(TicketModel model){
        String url = "http://localhost:8080/api/ticket/new";
        try {

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(url);

            // add header
            post.setHeader("Authorization", "Basic Y2FybG86cGFzc3dvcmQ=");

            List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();

            Gson gson = new GsonBuilder()
                    .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
            urlParameters.add(new BasicNameValuePair("global/model", gson.toJson(model)));


            post.setEntity(new UrlEncodedFormEntity(urlParameters));

        CloseableHttpResponse response = httpClient.execute(post);
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + post.getEntity());
            System.out.println("Response Code : " +
                    response.getStatusLine().getStatusCode());


            httpClient.close();
            this.network.online();
        } catch (UnsupportedEncodingException e) {
           // e.printStackTrace();
            retry(model);

        } catch (IOException e){
            // e.printStackTrace();
            retry(model);
        }

    }

    private void retry(TicketModel model){
        this.network.offline(this.processTickets.size() + 1);
        try {
            Thread.sleep(1000);
            System.out.println("Retry " + model.getTicketNumber());
            handleTicketSending(model);
        } catch (InterruptedException e) {

        }
    }

}
