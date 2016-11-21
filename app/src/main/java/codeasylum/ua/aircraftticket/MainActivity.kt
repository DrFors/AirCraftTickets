package codeasylum.ua.aircraftticket

import android.graphics.drawable.AnimationDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*

import org.json.JSONException
import org.json.JSONObject

import codeasylum.ua.aircraftticket.Adapters.CustomAdapter
import codeasylum.ua.aircraftticket.Requests.Request

class MainActivity : AppCompatActivity() {


    internal var animation: AnimationDrawable? = null
    internal var listOfTickets: ListView? = null
    internal var spinnerStart: Spinner? = null
    internal var spinnerEnd: Spinner? = null
    internal var origin = "1"
    internal var destination = "1"
    internal var customAdapter: CustomAdapter? = null
    internal var jsonParser: JSONParser? = null
    internal var btn: ImageButton? = null
    internal var help_text_view: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSpinners()
        initListView()

        help_text_view = findViewById(R.id.help_text_view) as TextView?
        btn = findViewById(R.id.animation_btn) as ImageButton
        btn!!.setBackgroundResource(R.drawable.anim_btn)
        animation = btn!!.background as AnimationDrawable

        btn!!.setOnClickListener {
            if(origin == "1" || destination == "1" || origin == destination){
                viewsVisibility(listOfTickets as View,View.INVISIBLE)
                viewsVisibility(help_text_view as View,View.VISIBLE)
                help_text_view?.text = getString(R.string.need_select_points)
            }
            else{
            animation!!.start()
            val getDataTask = GetDataTask()
            getDataTask.execute()}
        }


    }

    private fun viewsVisibility(view: View, code: Int){
        view.visibility = code
    }

    private fun initSpinners() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.name_of_airporst))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerEnd = findViewById(R.id.end_airport) as Spinner
        spinnerEnd!!.adapter = adapter
        spinnerEnd!!.setSelection(0)
        spinnerEnd!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                when (i) {
                    0 -> destination = "YUL"
                    1 -> destination = "YMX"
                    2 -> destination = "YOW"
                    3 -> destination = "MEX"
                    4 -> destination = "MDW"
                    5 -> destination = "MIA"
                    6 -> destination = "JFK"
                    7 -> destination = "DCA"
                    8 -> destination = "EZE"
                    9 -> destination = "AEP"
                    10 -> destination = "HND"
                    11 -> destination = "BHY"
                    12 -> destination = "KBP"
                    13 -> destination = "CDG"
                    14 -> destination = "AMS"
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }
        spinnerStart = findViewById(R.id.start_airport) as Spinner
        spinnerStart!!.setSelection(0)
        spinnerStart!!.adapter = adapter
        spinnerStart!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                when (i) {
                    0 -> origin = "YUL"
                    1 -> origin = "YMX"
                    2 -> origin = "YOW"
                    3 -> origin = "MEX"
                    4 -> origin = "MDW"
                    5 -> origin = "MIA"
                    6 -> origin = "JFK"
                    7 -> origin = "DCA"
                    8 -> origin = "EZE"
                    9 -> origin = "AEP"
                    10 -> origin = "HND"
                    11 -> origin = "BHY"
                    12 -> origin = "KBP"
                    13 -> origin = "CDG"
                    14 -> origin = "AMS"
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }


    }

    private fun initListView() {
        listOfTickets = findViewById(R.id.list_view) as ListView
    }


    internal inner class GetDataTask : AsyncTask<Void, Void, JSONObject>() {

        var requestToServer = Request(origin, destination)

        override fun onPreExecute() {
            viewsVisibility(listOfTickets as View,View.INVISIBLE)
        }

        override fun doInBackground(vararg voids: Void): JSONObject? {
            try {
                return JSONObject(Request.doPost(requestToServer.createJson().toString()))
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(jsonObject: JSONObject) {
            help_text_view?.visibility = View.INVISIBLE
            jsonParser = JSONParser(jsonObject)
            try {
                customAdapter = CustomAdapter(applicationContext, jsonParser!!.ticketArayList)
            } catch (e: JSONException) {
                viewsVisibility(listOfTickets as View,View.INVISIBLE)
                viewsVisibility(help_text_view as View, View.VISIBLE)
                help_text_view?.text = getString(R.string.no_aircraft)
            }

            listOfTickets?.adapter = customAdapter
            viewsVisibility(listOfTickets as View, View.VISIBLE)
            animation?.stop()
            btn?.setBackgroundResource(R.drawable.anim_btn)


        }


    }
}
