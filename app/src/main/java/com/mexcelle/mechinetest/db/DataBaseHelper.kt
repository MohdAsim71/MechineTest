package com.mexcelle.mechinetest.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.mexcelle.mechinetest.Pojo.ResponseData

val DATABASENAME = "MY DATABASE"
val TABLENAME = "API_DATA"

val COLOUM1 = "full_Name"
val COLOUM2 = "contact"
val COLOUM3 = "emailId"
val COLOUM4 = "membershipNumber"
val COLOUM5 = "address"
val COLOUM6 = "listYear"
val COLOUM7 = "dataType"
val COLOUM8 = "designation"
val COLOUM9 = "profilePicUrl"
val COLOUM10 = "id"

class DataBaseHelper(context: Context):SQLiteOpenHelper(context, DATABASENAME, null, 1)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =  "CREATE TABLE " + TABLENAME + " (" +
                COLOUM10 +" VARCHAR(256) UNIQUE, " +
                COLOUM1 +" VARCHAR(256) , " +
                COLOUM2 + " VARCHAR(256) , " +
                COLOUM3 + " VARCHAR(256) , " +
                COLOUM4 + " VARCHAR(256) , " +
                COLOUM5 + " VARCHAR(256) , " +
                COLOUM6 + " VARCHAR(256) , " +
                COLOUM7 + " VARCHAR(256) , " +
                COLOUM8 + " VARCHAR(256) , " +
                COLOUM9 +" VARCHAR(256)  " +");"
        db?.execSQL(createTable)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS " + TABLENAME)
        onCreate(db)
    }

    fun insertData(responseData: ResponseData):Long
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLOUM1, responseData.fullName)
        contentValues.put(COLOUM2, responseData.contact)
        contentValues.put(COLOUM3, responseData.emailId)
        contentValues.put(COLOUM4, responseData.membershipNumber)
        contentValues.put(COLOUM5, responseData.address)
        contentValues.put(COLOUM6, responseData.listYear)
        contentValues.put(COLOUM7, responseData.dataType)
        contentValues.put(COLOUM8, responseData.designation)
        contentValues.put(COLOUM9, responseData.profilePicUrl)
        contentValues.put(COLOUM10, responseData.id)
        val result = db.insertWithOnConflict(
            TABLENAME,
            null,
            contentValues,
            SQLiteDatabase.CONFLICT_IGNORE
        )
        return result
    }

    fun updateData(responseData: ResponseData):Int
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLOUM1, responseData.fullName)
        contentValues.put(COLOUM2, responseData.contact)
        contentValues.put(COLOUM3, responseData.emailId)
        contentValues.put(COLOUM4, responseData.membershipNumber)
        contentValues.put(COLOUM5, responseData.address)
        contentValues.put(COLOUM6, responseData.listYear)
        contentValues.put(COLOUM7, responseData.dataType)
        contentValues.put(COLOUM8, responseData.designation)
        contentValues.put(COLOUM9, responseData.profilePicUrl)
        val result = db.update(TABLENAME, contentValues, COLOUM10 + "=?", arrayOf(responseData.id))
        return result
    }

    fun getAllData():ArrayList<ResponseData>
    {
        val db = this.writableDatabase
        val res = db.rawQuery("select * from " + TABLENAME, null)
        val localDataList = ArrayList<ResponseData>()

if (res!=null)
{
    while (res.moveToNext())
    {
        var id = res.getString(0)
        var fullName = res.getString(1)
        var contact = res.getString(2)
        var emailId = res.getString(3)
        var membershipNumber = res.getString(4)
        var address = res.getString(5)
        var listYear = res.getString(6)
        var dataType = res.getString(7)
        var designation = res.getString(8)
     //   var profilePicUrl = res.getString(10)

        localDataList.add(
            ResponseData(
                id,
                fullName,
                contact,
                emailId,
                membershipNumber,
                address,
                listYear,
                dataType,
                designation,
               // profilePicUrl
            )
        )
    }
}



        return localDataList

    }

    fun deletaTable()
    {
        val db = this.writableDatabase
        db.delete(TABLENAME, null, null)

    }




}