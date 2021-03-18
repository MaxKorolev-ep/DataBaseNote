package com.example.database.db

import android.provider.BaseColumns

object MyDbMainClass {
            const val TABLE_NAME = "my_table"
            const val COLUMN_NAME_TITLE = "title"
            const val COLUMN_NAME_CONTENT = "content"
            const val COLUMN_NAME_Image_URL = "url"

            const val DATABASE_VERSION = 2
            const val DATABASE_NAME = "MyDb.db"
//0    title     content

     const val CREATE_TABLE ="CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_TITLE TEXT, $COLUMN_NAME_CONTENT TEXT, $COLUMN_NAME_Image_URL TEXT)"
     const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

}