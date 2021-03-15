package com.mexcelle.mechinetest

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.mexcelle.mechinetest.Pojo.ResponseData
import com.mexcelle.mechinetest.db.DataBaseHelper
import java.lang.reflect.Type
import java.util.ArrayList

class UpdateScreen : AppCompatActivity() {

    lateinit var idEt: TextView
    lateinit var nameEt: EditText
    lateinit var contactET: EditText
    lateinit var emailEt: EditText
    lateinit var membershipEt: EditText
    lateinit var addressEt: EditText
    lateinit var lastyearEt: EditText
    lateinit var datatypeEt: EditText
    lateinit var designationEt: EditText
    lateinit var profileImageViewTV: ImageView
    lateinit var updateTV: TextView
    var localResponselist: ArrayList<String>? = null
    private lateinit var dataBaseHelper: DataBaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_screen)

        init()
        getArrayList("responselist")

        localResponselist = ArrayList<String>()

        localResponselist!!.add(getArrayList("responselist").toString())
        dataBaseHelper = DataBaseHelper(this)


        Log.e("arraylist","localResponselist"+localResponselist)

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val dataType = intent.getStringExtra("dataType")
        val designation = intent.getStringExtra("designation")
        val address = intent.getStringExtra("address")
        val contact = intent.getStringExtra("contact")
        val emailId = intent.getStringExtra("emailId")
        val membershipNumber = intent.getStringExtra("membershipNumber")
        val listYear = intent.getStringExtra("listYear")
        val mImageurl = intent.getStringExtra("mImageurl")


        Log.e("arraylist","index"+ localResponselist!!.indexOf(id.toString()))
        Log.e("arraylist","index data"+ localResponselist!!.get(0))


        idEt.setText(id)
        nameEt.setText(name)
        contactET.setText(contact)
        emailEt.setText(emailId)
        membershipEt.setText(membershipNumber)
        addressEt.setText(address)
        lastyearEt.setText(listYear)
        datatypeEt.setText(dataType)
        designationEt.setText(designation)


        updateTV.setOnClickListener {
            if (idEt.text.length > 0) {
                if (nameEt.text.length > 0) {
                    if (contactET.text.length > 0) {
                        if (emailEt.text.length > 0) {
                            if (membershipEt.text.length > 0) {
                                if (addressEt.text.length > 0) {
                                    if (lastyearEt.text.length > 0) {
                                        if (datatypeEt.text.length > 0) {
                                            if (designationEt.text.length > 0) {

                                                var nameUpdate = nameEt.text.toString()
                                                var contactUpdate = contactET.text.toString()
                                                var emailIpdate = emailEt.text.toString()
                                                var membershipNumberUpdate = membershipEt.text.toString()
                                                var addressUpdate = addressEt.text.toString()
                                                var listYearUpdate = lastyearEt.text.toString()
                                                var dataTypeUpdate = datatypeEt.text.toString()
                                                var designationUpdate  = designationEt.text.toString()

                                                val result = dataBaseHelper.updateData(ResponseData(id,nameUpdate,contactUpdate,emailIpdate,membershipNumberUpdate,addressUpdate,listYearUpdate,dataTypeUpdate,designationUpdate,mImageurl))
                                                val toast = Toast.makeText(
                                                    applicationContext,
                                                    "Update",
                                                    Toast.LENGTH_SHORT
                                                )
                                                toast.show()
                                                var intent = Intent(this,MainActivity::class.java)
                                                intent.putExtra("key","DataFromLocal")
                                                startActivity(intent)

                                            } else {
                                                val toast = Toast.makeText(
                                                    applicationContext,
                                                    "Please enter designation",
                                                    Toast.LENGTH_SHORT
                                                )
                                                toast.show()
                                            }
                                        } else {
                                            val toast = Toast.makeText(
                                                applicationContext,
                                                "Please enter last year",
                                                Toast.LENGTH_SHORT
                                            )
                                            toast.show()
                                        }
                                    } else {
                                        val toast = Toast.makeText(
                                            applicationContext,
                                            "Please enter last year",
                                            Toast.LENGTH_SHORT
                                        )
                                        toast.show()
                                    }
                                } else {
                                    val toast = Toast.makeText(
                                        applicationContext,
                                        "Please enter address",
                                        Toast.LENGTH_SHORT
                                    )
                                    toast.show()
                                }
                            } else {
                                val toast = Toast.makeText(
                                    applicationContext,
                                    "Please enter emai id",
                                    Toast.LENGTH_SHORT
                                )
                                toast.show()
                            }
                        } else {
                            val toast = Toast.makeText(
                                applicationContext,
                                "Please enter email id",
                                Toast.LENGTH_SHORT
                            )
                            toast.show()
                        }
                    } else {
                        val toast = Toast.makeText(
                            applicationContext,
                            "Please enter contact number",
                            Toast.LENGTH_SHORT
                        )
                        toast.show()
                    }
                } else {
                    val toast =
                        Toast.makeText(applicationContext, "Please enter name", Toast.LENGTH_SHORT)
                    toast.show()
                }

            } else {
                val toast =
                    Toast.makeText(applicationContext, "Please enter id", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    private fun init() {

        idEt = findViewById(R.id.id_et1)
        nameEt = findViewById(R.id.name_et1)
        membershipEt = findViewById(R.id.membership_number_et)
        addressEt = findViewById(R.id.address_rt)
        lastyearEt = findViewById(R.id.list_year_et)
        datatypeEt = findViewById(R.id.data_type_et)
        contactET = findViewById(R.id.contact_number_et)
        emailEt = findViewById(R.id.email_et)
        designationEt = findViewById(R.id.designation_et)
        updateTV = findViewById(R.id.update_btn)
        profileImageViewTV = findViewById(R.id.profile_img_view)


    }


    fun getArrayList(key: String?): java.util.ArrayList<ResponseData?>? {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val gson = Gson()
        val json: String? = prefs.getString(key, null)
        val type: Type = object : TypeToken<ArrayList<JsonObject?>?>() {}.getType()
        return gson.fromJson(json, type)
    }
}