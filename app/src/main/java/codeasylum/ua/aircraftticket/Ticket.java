package codeasylum.ua.aircraftticket;

/**
 * Created by Андрей on 18.11.2016.
 */

public class Ticket {
    private String startTime;
    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getPrice() {
        return price;
    }

    public int getNumberOfTransfers() {
        return numberOfTransfers;
    }

    private String price;
    private int numberOfTransfers;
    private String duration;

    Ticket(String startTime, String endTime, String price, int numberOfTransfers) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
        this.numberOfTransfers = numberOfTransfers;

    }

    @Override
    public String toString() {

        return "StartTime " + startTime + "\n"
                +"EndTime " + endTime + "\n" +
                "Price " + price + "\n" +
                "Transfers" + numberOfTransfers + "\n" +
                "Duration " + duration;
    }
}
