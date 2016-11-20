package codeasylum.ua.aircraftticket.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import codeasylum.ua.aircraftticket.R;
import codeasylum.ua.aircraftticket.Ticket;

/**
 * Created by Андрей on 20.11.2016.
 */

public class CustomAdapter extends ArrayAdapter<Ticket> {

    private ArrayList<Ticket> tickets;

    private static class ViewHolder {
        TextView departureDate;
        TextView arrivalDate;
        TextView countOfTransfers;
        TextView price;
    }

    public CustomAdapter(Context context, ArrayList<Ticket> tickets) {
        super(context, -1, tickets);
        this.tickets = tickets;
    }


    @NonNull
    public View getView(int i, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView =
                    inflater.inflate(R.layout.custom_list_view, parent, false);
            viewHolder.departureDate =
                    (TextView) convertView.findViewById(R.id.start_date);
            viewHolder.arrivalDate =
                    (TextView) convertView.findViewById(R.id.end_date);
            viewHolder.price =
                    (TextView) convertView.findViewById(R.id.price);
            viewHolder.countOfTransfers =
                    (TextView) convertView.findViewById(R.id.cout_of_transgers);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.departureDate.setText(tickets.get(i).getStartTime());
        viewHolder.arrivalDate.setText(tickets.get(i).getEndTime());
        viewHolder.price.setText(tickets.get(i).getPrice());
        viewHolder.countOfTransfers.setText(tickets.get(i).getNumberOfTransfers()+"");



        return convertView;
    }
}
