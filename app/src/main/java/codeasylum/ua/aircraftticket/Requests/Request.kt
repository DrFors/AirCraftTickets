package codeasylum.ua.aircraftticket.Requests

import android.util.Log
import codeasylum.ua.aircraftticket.Ticket
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Part

import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.Calendar

/**
 * Created by Андрей on 20.11.2016.
 */



internal class Request(private val origin: String, private val destination: String) {
    private val date: String

    init {
        val builder = StringBuilder()
        val calendar = Calendar.getInstance()
        builder.append(calendar.get(Calendar.YEAR)).append("-")
                .append(calendar.get(Calendar.MONTH) + 1).append("-")
                .append(calendar.get(Calendar.DAY_OF_MONTH))
        date = builder.toString()

    }



    companion object {

        private val key = "search?key=AIzaSyA-EJ2toj9z8tv8HYbNRNqWPvB27akvFss"
        //private static final String key = "?key=AIzaSyCdKk4nUqMm8d1ZVwzwWqOfAf_fKkZAY28";
        private val BASE_URL = "https://www.googleapis.com/qpxExpress/v1/trips/"
        fun doPost(json : String): String {
            var retrofit = Retrofit.Builder().baseUrl(BASE_URL).build()
            var api = retrofit.create(Api::class.java)
            var reqBody = RequestBody.create(MediaType.parse("application/json"), json.toByteArray())
            var call: Call<ResponseBody> = api.loadTicket(reqBody)
            val result = call.execute()


            return result.body().string()

        }
    }



    @Throws(JSONException::class)
    internal fun createJson(): JSONObject {
        val root = JSONObject()
        val request = JSONObject()
        val pasanger = JSONObject()
        //addpasanger
        pasanger.put("adultCount", 1)
        //createslince
        val slince = JSONArray()
        val buff = JSONObject()
        buff.put("origin", origin)
        buff.put("destination", destination)
        buff.put("date", date)
        //addslince
        slince.put(buff)
        request.put("passengers", pasanger)
        request.put("slice", slince)
        request.put("refundable", false)
        request.put("solutions", "20")
        root.put("request", request)


        return root
    }
/*
    companion object {

        private val key = "?key=AIzaSyA-EJ2toj9z8tv8HYbNRNqWPvB27akvFss"
        //private static final String key = "?key=AIzaSyCdKk4nUqMm8d1ZVwzwWqOfAf_fKkZAY28";
        private val BASE_URL = "https://www.googleapis.com/qpxExpress/v1/trips/search"

        @Throws(Exception::class)
        fun doPost(jsonString: String): String {

            val obj = createURL()
            val connection = obj.openConnection() as HttpURLConnection

            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("Content-Length", "" + jsonString.toByteArray().size)

            connection.doOutput = true
            connection.outputStream.write(jsonString.toByteArray())
            /*val outputStream = DataOutputStream(connection.outputStream)
            outputStream.writeBytes(jsonString)
            outputStream.flush()
            outputStream.close() */

            val responseCode = connection.responseCode
            val TAG = "TTTTTTTTTTTT"
            Log.d(TAG, "\n-----------Send http request-----------")
            Log.d(TAG, "\nSending  request to URL : " + createURL())
            Log.d(TAG, "Post parameters : " + jsonString)
            Log.d(TAG, "Response Code : " + responseCode)

            val inputLine = connection.inputStream.bufferedReader().readText()





            Log.d(TAG, "Response string: " + inputLine)
            Log.d(TAG, "-----------end http request-----------\n")
            Log.d(TAG, "  ")

            connection.disconnect()
            return inputLine
        }




        @Throws(MalformedURLException::class)
        private fun createURL(): URL {
            val okURL = BASE_URL + key
            return URL(okURL)
        }
    }
   */

    interface Api{
        @POST("search?key=AIzaSyA-EJ2toj9z8tv8HYbNRNqWPvB27akvFss")
        fun  loadTicket(@Body requestBody: RequestBody) : Call<ResponseBody> }

}
