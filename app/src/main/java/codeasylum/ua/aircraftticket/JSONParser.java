package codeasylum.ua.aircraftticket;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Андрей on 20.11.2016.
 */

class JSONParser {

    private JSONObject responceJSONObject;

    public JSONParser(JSONObject jsonObject) {
        responceJSONObject = jsonObject;
    }

    public ArrayList<Ticket> getTicketArayList() throws JSONException {
        ArrayList<Ticket> tikets = new ArrayList<>();
        JSONObject trips = responceJSONObject.getJSONObject("trips");
        JSONArray tripOptions = trips.getJSONArray("tripOption");
        for (int i = 0; i < tripOptions.length(); i++) {
            JSONObject objectInTripOptions = tripOptions.getJSONObject(i);
            String saleTotal = objectInTripOptions.getString("saleTotal");
            JSONArray slice = objectInTripOptions.getJSONArray("slice");
            JSONObject objectInSlice = slice.getJSONObject(0);
            JSONArray segment = objectInSlice.getJSONArray("segment");
            int countOfTransfer = segment.length()-1;
            JSONObject objectInSegment = segment.getJSONObject(0);
            JSONArray leg = objectInSegment.getJSONArray("leg");
            JSONObject objecInLeg = leg.getJSONObject(0);
            String startTime = objecInLeg.getString("departureTime");
            objectInSegment = segment.getJSONObject(segment.length() - 1);
            leg = objectInSegment.getJSONArray("leg");
            objecInLeg = leg.getJSONObject(0);
            String endTime = objecInLeg.getString("arrivalTime");
            Log.d("!!!!!!!!!!!!!!!!!!!!!!!", "VALUES: " + saleTotal + " "  + countOfTransfer + " " + startTime + " " + endTime);
            tikets.add(new Ticket(startTime, endTime, saleTotal, countOfTransfer));
        }


        return tikets;
    }
}
