package com.mexcelle.mechinetest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {

    lateinit var idTv: TextView
    lateinit var nameTV: TextView
    lateinit var contactTV: TextView
    lateinit var emailTV: TextView
    lateinit var membershipTV: TextView
    lateinit var addressTV: TextView
    lateinit var lastyearTV: TextView
    lateinit var datatypeTV: TextView
    lateinit var designationTv: TextView
    lateinit var profileImageViewTV: ImageView
    lateinit var updateTV: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        init()


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

        idTv.text=id
        nameTV.text=name
        contactTV.text=contact
        emailTV.text=emailId
        membershipTV.text=membershipNumber
        addressTV.text=address
        lastyearTV.text=listYear
        datatypeTV.text=dataType
        designationTv.text=designation

        updateTV.setOnClickListener {
            val intent = Intent(this, UpdateScreen::class.java)
            intent.putExtra("id", id)
            intent.putExtra("name", name)
            intent.putExtra("dataType", dataType)
            intent.putExtra("designation", designation)
            intent.putExtra("address", address)
            intent.putExtra("contact", contact)
            intent.putExtra("emailId", emailId)
            intent.putExtra("membershipNumber", membershipNumber)
            intent.putExtra("listYear", listYear)
            intent.putExtra("mImageurl", mImageurl)

            startActivity(intent)
        }


    }

    private fun init() {

        idTv = findViewById(R.id.id_tv)
        nameTV = findViewById(R.id.name_tv)
        membershipTV = findViewById(R.id.memberhsip_tv)
        addressTV = findViewById(R.id.address_tv)
        lastyearTV = findViewById(R.id.lastyear_tv)
        datatypeTV = findViewById(R.id.data_type_tv)
        contactTV = findViewById(R.id.call_tv)
        emailTV = findViewById(R.id.email_tv)
        designationTv = findViewById(R.id.designation_tv)
        updateTV = findViewById(R.id.update_btn)
        profileImageViewTV = findViewById(R.id.person_img)


    }
}