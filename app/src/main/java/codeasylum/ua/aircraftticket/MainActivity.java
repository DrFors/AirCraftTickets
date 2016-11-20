package codeasylum.ua.aircraftticket;

import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import codeasylum.ua.aircraftticket.Adapters.CustomAdapter;

public class MainActivity extends AppCompatActivity {


    AnimationDrawable animation;
    ListView listOfTickets;
    Spinner spinnerStart, spinnerEnd;
    String origin = "1", destination = "1";
    CustomAdapter customAdapter;
    JSONParser jsonParser;
    ImageButton btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSpinners();
        initListView();
        btn = (ImageButton) findViewById(R.id.animation_btn);
        btn.setBackgroundResource(R.drawable.anim_btn);
        animation = (AnimationDrawable) btn.getBackground();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animation.start();
                GetDataTask getDataTask = new GetDataTask();
                getDataTask.execute();
            }
        });



    }

    private void initSpinners() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.code_of_airporst));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerEnd = (Spinner) findViewById(R.id.end_airport);
        spinnerEnd.setAdapter(adapter);
        spinnerEnd.setSelection(1);
        spinnerEnd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        destination = "YUL";
                        break;
                    case 1:
                        destination = "YMX";
                        break;
                    case 2:
                        destination = "YOW";
                        break;
                    case 3:
                        destination = "MEX";
                        break;
                    case 4:
                        destination = "MDW";
                        break;
                    case 5:
                        destination = "MIA";
                        break;
                    case 6:
                        destination = "JFK";
                        break;
                    case 7:
                        destination = "DCA";
                        break;
                    case 8:
                        destination = "EZE";
                        break;
                    case 9:
                        destination = "AEP";
                        break;
                    case 10:
                        destination = "HND";
                        break;
                    case 11:
                        destination = "BHY";
                        break;
                    case 12:
                        destination = "DWC";
                        break;
                    case 13:
                        destination = "CDG";
                        break;
                    case 14:
                        destination = "AMS";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerStart = (Spinner) findViewById(R.id.start_airport);
        spinnerStart.setSelection(1);
        spinnerStart.setAdapter(adapter);
        spinnerStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        origin = "YUL";
                        break;
                    case 1:
                        origin = "YMX";
                        break;
                    case 2:
                        origin = "YOW";
                        break;
                    case 3:
                        origin = "MEX";
                        break;
                    case 4:
                        origin = "MDW";
                        break;
                    case 5:
                        origin = "MIA";
                        break;
                    case 6:
                        origin = "JFK";
                        break;
                    case 7:
                        origin = "DCA";
                        break;
                    case 8:
                        origin = "EZE";
                        break;
                    case 9:
                        origin = "AEP";
                        break;
                    case 10:
                        origin = "HND";
                        break;
                    case 11:
                        origin = "BHY";
                        break;
                    case 12:
                        origin = "DWC";
                        break;
                    case 13:
                        origin = "CDG";
                        break;
                    case 14:
                        origin = "AMS";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void initListView(){
        listOfTickets = (ListView) findViewById(R.id.list_view);
    }



    class GetDataTask extends AsyncTask<Void, Void, JSONObject> {

        Request requestToServer = new Request(origin, destination);

        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                return new JSONObject(Request.doPost(requestToServer.createJson().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            jsonParser = new JSONParser(jsonObject);
            try {
                customAdapter = new CustomAdapter(getApplicationContext(),jsonParser.getTicketArayList());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listOfTickets.setAdapter(customAdapter);
            animation.stop();
            btn.setBackgroundResource(R.drawable.anim_btn);


        }


    }
}
