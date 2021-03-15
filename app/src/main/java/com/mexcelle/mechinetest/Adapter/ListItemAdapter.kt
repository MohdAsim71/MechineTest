package com.mexcelle.mechinetest.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hashmi.MLTClasses.Animation.MyBounceInterpolator
import com.mexcelle.mechinetest.DetailActivity
import com.mexcelle.mechinetest.Pojo.ResponseData
import com.mexcelle.mechinetest.R
import com.squareup.picasso.Picasso
import java.util.ArrayList



class ListItemAdapter(
    private val context: Context?,
    responseData: ArrayList<ResponseData>?
) :
    RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {
    private val responseData: ArrayList<ResponseData>
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v: View =
            LayoutInflater.from(context).inflate(R.layout.list_items, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val mCurrentItem: ResponseData = responseData[position]
        val id: String = mCurrentItem.id.toString()
        val name: String? = mCurrentItem.fullName
        val contact: String = mCurrentItem.contact.toString()
        val emailId: String = mCurrentItem.emailId.toString()
        val membershipNumber: String = mCurrentItem.membershipNumber.toString()
        val address: String = mCurrentItem.address.toString()
        val listYear: String = mCurrentItem.listYear.toString()
        val dataType: String = mCurrentItem.dataType.toString()
        val designation: String? = mCurrentItem.designation

        val mImageurl: String = mCurrentItem.profilePicUrl.toString()
        Log.e("mImageurl", mImageurl.toString())
        Log.e("nameonly", name.toString())

        holder.userNameTextview.text = name
        holder.dataTypeTextview.text=dataType
        holder.designationTextview.text=designation
        holder.addressTextview.text=address
        holder.contactTextview.text=contact
        holder.emailIdTextview.text=emailId
        holder.membrshipTextview.text=membershipNumber
        holder.lastyearTextview.text=listYear

        var animation = AnimationUtils.loadAnimation(context, R.anim.button_animation)
        var interpolator = MyBounceInterpolator(0.1, 20.0)
        animation.setInterpolator(interpolator)
        holder.itemView.animation = animation



        if (mImageurl.isEmpty()) {
            holder.profileImageView.setImageResource(R.drawable.ic_baseline_account_circle_24);
        } else{
            Picasso.with(context).load(mImageurl).placeholder(R.drawable.ic_baseline_account_circle_24).fit().centerCrop()
                .into(holder.profileImageView)
        }
        holder.viewTextview.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
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

            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return responseData.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val profileImageView: ImageView
        val userNameTextview: TextView
        val contactTextview: TextView
        val emailIdTextview: TextView
        val addressTextview: TextView
        val membrshipTextview: TextView
        val lastyearTextview: TextView
        val dataTypeTextview: TextView
        val designationTextview: TextView
        val viewTextview: TextView

        init {
            profileImageView = itemView.findViewById(R.id.imageView)
            userNameTextview = itemView.findViewById(R.id.full_name_tv)
            contactTextview = itemView.findViewById(R.id.contact_tv)
            dataTypeTextview= itemView.findViewById(R.id.dataType_tv)
            membrshipTextview= itemView.findViewById(R.id.membership_number_tv)
            lastyearTextview= itemView.findViewById(R.id.lastyear_tv)
            designationTextview= itemView.findViewById(R.id.designation_tv)
            addressTextview= itemView.findViewById(R.id.address_tv)
            emailIdTextview= itemView.findViewById(R.id.email_id_tv)
            viewTextview= itemView.findViewById(R.id.view_tv)
        }
    }

    init {
        this.responseData = responseData!!
    }
}
