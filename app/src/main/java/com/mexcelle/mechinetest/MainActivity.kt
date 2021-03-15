package com.mexcelle.mechinetest

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.mexcelle.assignment.Util.Utility
import com.mexcelle.mechinetest.Adapter.ListItemAdapter
import com.mexcelle.mechinetest.Pojo.ResponseData
import com.mexcelle.mechinetest.db.DataBaseHelper
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var statusImageView: ImageView? = null
    private var statusTextView: TextView? = null
    private var listItemAdapter: ListItemAdapter? = null
    private var mRequestQueue: RequestQueue? = null
    var responselist: ArrayList<ResponseData>? = null
    private var mWaveSwipeRefreshLayout: SwipeRefreshLayout? = null
    var shimmerFrameLayout: ShimmerFrameLayout? = null
    var statusTimer: Timer? = Timer()
    private lateinit var dataBaseHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Init()
        responselist = ArrayList<ResponseData>()
        dataBaseHelper = DataBaseHelper(this)


        var updateIntentString = intent.getStringExtra("key")


        Log.e("intent","updateIntentString"+updateIntentString)

        if (updateIntentString.equals("DataFromLocal"))
        {
            listItemAdapter = ListItemAdapter(this, dataBaseHelper.getAllData())
            mRecyclerView!!.adapter = listItemAdapter
            shimmerFrameLayout!!.stopShimmerAnimation()
            shimmerFrameLayout!!.visibility = View.GONE
            mWaveSwipeRefreshLayout!!.setRefreshing(false);
        }
        else
        {
            callAPi()

        }


        mWaveSwipeRefreshLayout!!.setOnRefreshListener {
            if (updateIntentString.equals("DataFromLocal"))
            {
                listItemAdapter = ListItemAdapter(this, dataBaseHelper.getAllData())
                mRecyclerView!!.adapter = listItemAdapter
                shimmerFrameLayout!!.stopShimmerAnimation()
                shimmerFrameLayout!!.visibility = View.GONE
                mWaveSwipeRefreshLayout!!.setRefreshing(false);
            }
            else
            {
                callAPi()

            }
        }

    }

    private fun Init() {


        mRecyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView
        mRecyclerView!!.setHasFixedSize(true)
        mRecyclerView!!.setLayoutManager(LinearLayoutManager(this))
        mRequestQueue = Volley.newRequestQueue(this)
        shimmerFrameLayout = findViewById<ShimmerFrameLayout>(R.id.shimmer4)
        mWaveSwipeRefreshLayout = findViewById(R.id.main_swipe) as SwipeRefreshLayout
    }


    override fun onResume() {
        super.onResume()
        shimmerFrameLayout!!.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        shimmerFrameLayout!!.stopShimmerAnimation()
    }

    fun callAPi() {

       // dataBaseHelper.deletaTable()

        if (Utility.chkStatus(this)) {
            val mUrl = "https://api.jsonbin.io/b/5da98dd0120fad237297b2fd"
            val request: JsonArrayRequest = object : JsonArrayRequest(Method.GET,
                    mUrl,
                    null,
                    Response.Listener { response ->

                        val gson = Gson()
                        var json: JSONObject? = null;
                        val listResponse = response.toString()
                        Log.e("response", listResponse.toString())

                        for (i in 0 until response.length()) {
                            json = response.getJSONObject(i)
                            val id = json.getString("id")
                            val full_name = json.getString("full_name")
                            val contact = json.getString("contact")
                            val email_id = json.getString("email_id")
                            val membership_number = json.getString("membership_number")
                            val address = json.getString("address")
                            val list_year = json.getString("list_year")
                            val data_type = json.getString("data_type")
                            val designation = json.getString("designation")
                            val profile_pic_url = json.getString("profile_pic_url")
                            responselist!!.add(ResponseData(id, full_name, contact, email_id, membership_number, address, list_year, data_type, designation, profile_pic_url))
                            dataBaseHelper.insertData(ResponseData(id,full_name,contact,email_id,membership_number,address,list_year,data_type,designation,profile_pic_url))

                        }

                        Log.e("arraylist","responselist"+responselist)
                        saveArrayList(responselist, "responselist")
                        listItemAdapter = ListItemAdapter(this, responselist)
                        mRecyclerView!!.adapter = listItemAdapter
                        shimmerFrameLayout!!.stopShimmerAnimation()
                        shimmerFrameLayout!!.visibility = View.GONE
                        mWaveSwipeRefreshLayout!!.setRefreshing(false);





                    },
                    Response.ErrorListener { error ->

                        Log.e("response", "error" + error.toString());

                    }) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["User-Agent"] = "Morzilla/5.0"
                    headers.put("Content-type", "application/json");
                    val key = "Lp7zj4F3Un4QTExnLKmi3uZuXmx5gx2hbtoj2nmR8iF75RyVkxQd6"
                    headers.put("secret-key", "$2b$10$" + key);

                    return headers
                }


            }
            val requestQueue = Volley.newRequestQueue(this)
            requestQueue.add(request)
        } else {
            val toast = Toast.makeText(
                    applicationContext,
                    "No internet connection",
                    Toast.LENGTH_SHORT
            )
            toast.show()
        }

    }


    fun saveArrayList(list: ArrayList<ResponseData>?, key: String?) {



        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }
}