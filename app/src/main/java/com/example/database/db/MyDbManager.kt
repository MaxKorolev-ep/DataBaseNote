package com.example.database.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class MyDbManager(context: Context){
    val dbHelper = MyDbHelper(context)
    var db: SQLiteDatabase? = null


    fun openDb()
    {
        db = dbHelper.writableDatabase
    }
    fun insertToDb(title:String, content: String, url:String)
    {
        val values = ContentValues().apply {
            put(MyDbMainClass.COLUMN_NAME_TITLE, title)
            put(MyDbMainClass.COLUMN_NAME_CONTENT, content)
            put(MyDbMainClass.COLUMN_NAME_Image_URL, url)
        }
  // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(MyDbMainClass.TABLE_NAME, null, values)
    }

    fun removeItemFromDb(id:String)
    {
        val selection = BaseColumns._ID + "=$id"
        db?.delete(MyDbMainClass.TABLE_NAME, selection, null)
    }

    fun updateItemInDb(title:String, content: String, url:String, id:Int)
    {
        val selection = BaseColumns._ID + "=$id"

        val values = ContentValues().apply {
            put(MyDbMainClass.COLUMN_NAME_TITLE, title)
            put(MyDbMainClass.COLUMN_NAME_CONTENT, content)
            put(MyDbMainClass.COLUMN_NAME_Image_URL, url)
        }
        // Insert the new row, returning the primary key value of the new row
        db?.update(MyDbMainClass.TABLE_NAME, values,selection,null)
    }

    fun readDbData(searchText : String): ArrayList<ListItem> {
        val dataList = ArrayList<ListItem>()
        val selection = "${MyDbMainClass.COLUMN_NAME_TITLE} like ?"
        val cursor = db?.query(MyDbMainClass.TABLE_NAME, null, selection, arrayOf("%$searchText%"), null, null, null)

        while (cursor?.moveToNext()!!) {
            val dataTitle = cursor.getString(cursor.getColumnIndex(MyDbMainClass.COLUMN_NAME_TITLE))
            val dataDisc = cursor.getString(cursor.getColumnIndex(MyDbMainClass.COLUMN_NAME_CONTENT))
            val dataUrl = cursor.getString(cursor.getColumnIndex(MyDbMainClass.COLUMN_NAME_Image_URL))
            val dataId =  cursor.getInt(cursor.getColumnIndex(BaseColumns._ID))
            var item = ListItem()
            item.title = dataTitle
            item.disc = dataDisc
            item.url = dataUrl
            item.id = dataId
            dataList.add(item)
        }
        cursor.close()
        return dataList
    }
    fun closeDb()
    {
        dbHelper.close()
    }
}