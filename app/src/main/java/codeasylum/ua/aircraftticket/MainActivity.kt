package codeasylum.ua.aircraftticket

import android.graphics.drawable.AnimationDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*

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
                val getDataTask = GetDataTask()
                getDataTask.execute()
                animation!!.start()
                btn!!.isClickable = false
            }
        }


    }

    private fun viewsVisibility(view: View, code: Int){ //установка видимости элементов
        view.visibility = code
    }

    private fun initSpinners() {
        var adapter_end = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.name_of_airports_end))
        adapter_end.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerEnd = findViewById(R.id.end_airport) as Spinner
        spinnerEnd!!.adapter = adapter_end
        spinnerEnd!!.setSelection(0)
        spinnerEnd!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                when (i) {
                    0 -> destination = "1"
                    1 -> destination = "YUL"
                    2 -> destination = "YMX"
                    3 -> destination = "YOW"
                    4 -> destination = "MEX"
                    5 -> destination = "MDW"
                    6 -> destination = "MIA"
                    7 -> destination = "JFK"
                    8 -> destination = "DCA"
                    9 -> destination = "EZE"
                    10 -> destination = "AEP"
                    11 -> destination = "HND"
                    12 -> destination = "BHY"
                    13 -> destination = "KBP"
                    14 -> destination = "CDG"
                    15 -> destination = "AMS"
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {

            }
        }

        var adapter_start = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.name_of_airports_start))
        adapter_start.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerStart = findViewById(R.id.start_airport) as Spinner
        spinnerStart!!.setSelection(0)
        spinnerStart!!.adapter= adapter_start
        spinnerStart!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                when (i) {
                    0 -> origin = "1"
                    1 -> origin = "YUL"
                    2 -> origin = "YMX"
                    3 -> origin = "YOW"
                    4 -> origin = "MEX"
                    5 -> origin = "MDW"
                    6 -> origin = "MIA"
                    7 -> origin = "JFK"
                    8 -> origin = "DCA"
                    9 -> origin = "EZE"
                    10 -> origin = "AEP"
                    11 -> origin = "HND"
                    12 -> origin = "BHY"
                    13 -> origin = "KBP"
                    14 -> origin = "CDG"
                    15 -> origin = "AMS"
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

        override fun onPostExecute(jsonObject: JSONObject?) {
            help_text_view?.visibility = View.INVISIBLE
            try {
                jsonParser = JSONParser(jsonObject)
                customAdapter = CustomAdapter(applicationContext, jsonParser!!.ticketArayList)
                listOfTickets?.adapter = customAdapter
                viewsVisibility(listOfTickets as View, View.VISIBLE)

            }catch (e :Exception){
                viewsVisibility(listOfTickets as View,View.INVISIBLE)
                viewsVisibility(help_text_view as View, View.VISIBLE)
                help_text_view!!.text = getString(R.string.noTickets)
            }
            animation!!.stop()
            btn!!.isClickable = true

        }


    }
}
