package codeasylum.ua.aircraftticket

import android.util.Log

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

/**
 * Created by Андрей on 20.11.2016.
 */

internal class JSONParser(private val responceJSONObject: JSONObject?) {

    val ticketArayList: ArrayList<Ticket>
        @Throws(JSONException::class)
        get() {
            val tikets = ArrayList<Ticket>()
            val trips = responceJSONObject!!.getJSONObject("trips")
            val tripOptions = trips.getJSONArray("tripOption")
            for (i in 0..tripOptions.length() - 1) {
                val objectInTripOptions = tripOptions.getJSONObject(i)
                val saleTotal = objectInTripOptions.getString("saleTotal")
                val slice = objectInTripOptions.getJSONArray("slice")
                val objectInSlice = slice.getJSONObject(0)
                val segment = objectInSlice.getJSONArray("segment")
                val countOfTransfer = segment.length() - 1
                var objectInSegment = segment.getJSONObject(0)
                var leg = objectInSegment.getJSONArray("leg")
                var objecInLeg = leg.getJSONObject(0)
                val startTime = objecInLeg.getString("departureTime")
                objectInSegment = segment.getJSONObject(segment.length() - 1)
                leg = objectInSegment.getJSONArray("leg")
                objecInLeg = leg.getJSONObject(0)
                val endTime = objecInLeg.getString("arrivalTime")
                Log.d("!!!!!!!!!!!!!!!!!!!!!!!", "VALUES: $saleTotal $countOfTransfer $startTime $endTime")
                tikets.add(Ticket(startTime, endTime, saleTotal, countOfTransfer))
            }


            return tikets
        }
}
