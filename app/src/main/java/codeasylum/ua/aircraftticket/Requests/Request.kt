package codeasylum.ua.aircraftticket.Requests

import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable
import java.util.*

/**
 * Created by Андрей on 20.11.2016.
 */


class Request() {
    private var origin: String = ""
    private var destination: String = ""
    private val date: String

    internal fun setOriginAndDestinations(a: String, b: String) {
        origin = a
        destination = b
    }

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

        fun doPost(json: String): JSONObject {


            var retrofit = Retrofit.Builder().baseUrl(BASE_URL).build()
            var api = retrofit.create(Api::class.java)
            var reqBody = RequestBody.create(MediaType.parse("application/json"), json.toByteArray())
            var call: Call<ResponseBody> = api.loadTicket(reqBody)
            val result = call.execute().body().string()

            return JSONObject(result)

        }
    }

    @Throws(JSONException::class)
    fun createJson(): JSONObject {
        var root = JSONObject()
        Observable.just(JSONObject())
                .map { buff ->
                    buff.put("origin", origin)
                            .put("destination", destination)
                            .put("date", date)
                }
                .map { buff -> JSONArray().put(buff) }
                .map { slice ->
                    JSONObject().put("passengers", JSONObject().put("adultCount", 1))
                            .put("slice", slice)
                            .put("refundable", false)
                            .put("solutions", 20)
                }
                .map { request -> JSONObject().put("request", request) }
                .subscribe { rt -> root = rt }
        return root

    }


    /*  @Throws(JSONException::class)
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
      */
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

    interface Api {
        @POST("search?key=AIzaSyCdKk4nUqMm8d1ZVwzwWqOfAf_fKkZAY28")
        fun loadTicket(@Body requestBody: RequestBody): Call<ResponseBody>
    }

}


