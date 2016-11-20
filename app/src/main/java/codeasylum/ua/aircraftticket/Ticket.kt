package codeasylum.ua.aircraftticket

/**
 * Created by Андрей on 18.11.2016.
 */

class Ticket internal constructor(val startTime: String, val endTime: String, val price: String, val numberOfTransfers: Int) {
    private val duration: String? = null

    override fun toString(): String {

        return "StartTime " + startTime + "\n"+"EndTime " + endTime + "\n" +
                "Price " + price + "\n" +
                "Transfers" + numberOfTransfers + "\n" +
                "Duration " + duration
    }
}
