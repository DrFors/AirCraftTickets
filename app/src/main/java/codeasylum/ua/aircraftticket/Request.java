package codeasylum.ua.aircraftticket;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by Андрей on 20.11.2016.
 */

class Request {

    private static final String key = "?key=AIzaSyCdKk4nUqMm8d1ZVwzwWqOfAf_fKkZAY28";
    private static final String BASE_URL = "https://www.googleapis.com/qpxExpress/v1/trips/search";
    private String origin;
    private String destination;
    private String date;

    Request(String origin, String destination) {
        StringBuilder builder = new StringBuilder();
        Calendar calendar = Calendar.getInstance();
        builder.append(calendar.get(Calendar.YEAR)).append("-")
                .append(calendar.get(Calendar.MONTH) + 1).append("-")
                .append(calendar.get(Calendar.DAY_OF_MONTH));
        this.origin = origin;
        this.destination = destination;
        date = builder.toString();

    }

   /* private String createParams(){
        return " {\n" +
                "  \"request\": {\n" +
                "    \"slice\": [\n" +
                "      {\n" +
                "        \"origin\": \"NYC\",\n" +
                "        \"destination\": \"BOS\",\n" +
                "        \"date\": \"2016-11-25\"\n" +
                "      }\n" +
                "    ],\n" +
                "    \"passengers\": {\n" +
                "      \"adultCount\": 1,\n" +
                "      \"infantInLapCount\": 0,\n" +
                "      \"infantInSeatCount\": 0,\n" +
                "      \"childCount\": 0,\n" +
                "      \"seniorCount\": 0\n" +
                "    },\n" +
                "    \"solutions\": 20,\n" +
                "    \"refundable\": false\n" +
                "  }\n" +
                "}";
    } */


    JSONObject createJson() throws JSONException {
        JSONObject root = new JSONObject();
        JSONObject request = new JSONObject();
        JSONObject pasanger = new JSONObject();
        //addpasanger
        pasanger.put("adultCount", 1);
        JSONArray slince = new JSONArray();
        JSONObject buff = new JSONObject();
        buff.put("origin", origin);
        buff.put("destination", destination);
        buff.put("date", date);
        //addslince
        slince.put(buff);
        request.put("passengers", pasanger);
        request.put("slice", slince);
        request.put("refundable", false);
        request.put("solutions", "5");
        root.put("request", request);


        return root;
    }

    static String doPost(String jsonString)
            throws Exception {

        URL obj = createURL();
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length", "" + jsonString.getBytes().length);

        //Send  request
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(jsonString);
        outputStream.flush();
        outputStream.close();

        int responseCode = connection.getResponseCode();
        String TAG = "TTTTTTTTTTTT";
        Log.d(TAG, "\n-----------Send http request-----------");
        Log.d(TAG, "\nSending  request to URL : " + createURL());
        Log.d(TAG, "Post parameters : " + jsonString);
        Log.d(TAG, "Response Code : " + responseCode);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = bufferedReader.readLine()) != null) {
            response.append(inputLine);
        }
        bufferedReader.close();

//      print result
        Log.d(TAG, "Response string: " + response.toString());
        Log.d(TAG, "-----------end http request-----------\n");
        Log.d(TAG, "  ");

        return response.toString();
    }


    private static URL createURL() throws MalformedURLException {
        String okURL = BASE_URL + key;
        return new URL(okURL);
    }


}
