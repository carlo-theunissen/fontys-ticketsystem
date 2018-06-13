package issueFrontend.Calculation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import global.RestHelper;
import global.model.TicketExternalCommunicationModel;
import global.model.TicketModel;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class GetSalesCalculator {
    private final static String url = "http://localhost:8080/api/ticket/dump";

    private List<TicketModel> TicketExternalCommunicationModelToTicketModel(TicketExternalCommunicationModel[] externalModels){
        ArrayList<TicketModel> result = new ArrayList<TicketModel>();
        for (TicketExternalCommunicationModel externalModel : externalModels) {
            result.add(externalModel.toTicketModel());
        }
        return result;
    }
    private HourAmountModel CreateHourAmountModel(List<TicketModel> tickets){
        HourAmountModel model = new HourAmountModel();
        for (TicketModel externalModel : tickets) {
            model.setAmount( model.getAmount() + externalModel.getAmountChecked());
            model.setDate( externalModel.getDate());
        }
        return model;
    }


    public String Result(){
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").create();;
            Collection<LinkedList<TicketModel>> grouped = SortTicketsByMonthHour(TicketExternalCommunicationModelToTicketModel(getDump())).values();
            ArrayList<HourAmountModel> result = new ArrayList<HourAmountModel>();

            for (LinkedList<TicketModel> ticketModels : grouped) {
                result.add(CreateHourAmountModel(ticketModels));
            }
            return gson.toJson(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    private HashMap<String, LinkedList<TicketModel>> SortTicketsByMonthHour(List<TicketModel> allTickets){
        HashMap<String, LinkedList<TicketModel>> map = new HashMap<String, LinkedList<TicketModel>>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyDDDHH");
        for (TicketModel ticket : allTickets) {
            String date = formatter.format(ticket.getDate());
            if(map.containsKey(date)){
                map.get(date).add(ticket);
            } else {
                LinkedList<TicketModel> models = new LinkedList<TicketModel>();
                models.add(ticket);
                map.put(date, models);
            }
        }
        return map;
    }


    private TicketExternalCommunicationModel[] getDump(){

        try {
            String result = RestHelper.getHttp(url);
            return getGson().fromJson(result, TicketExternalCommunicationModel[].class);
        } catch (IOException e) {
            return null;
        }
    }

    private Gson getGson(){
        Gson gson = new GsonBuilder()
                .setDateFormat(DateFormat.FULL, DateFormat.FULL).create();
        return gson;

    }
}
