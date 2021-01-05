package com.tauseebpp.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManger(context: Context) {

    val dbName = "MyNotes"
    val dbTable = "Notes"
    val colId = "ID"
    val colTitle="title"
    val colDes="Description"
    val dbVersion=1

    val sqlCreateTable=( "CREATE TABLE $dbTable ($colId INTEGER PRIMARY KEY,$colTitle TEXT,$colDes TEXT) ")

    var sqlDB:SQLiteDatabase?=null

    init {
        val db=DatabaseHelperNotes(context)
        sqlDB=db.writableDatabase
    }

    inner class DatabaseHelperNotes(context: Context) :
        SQLiteOpenHelper(context, dbName, null, dbVersion) {

        private var context:Context?= context
        override fun onCreate(p0: SQLiteDatabase?) {
            p0!!.execSQL(sqlCreateTable)
            Toast.makeText(context, "database is created", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            p0!!.execSQL("Drop table IF EXISTS $dbTable")
        }
    }

    fun Insert(value: ContentValues):Long{
        return sqlDB!!.insert(dbTable,"",value)
    }

    fun Query(projection:Array<String>,selection:String,selectionArgs:Array<String>,sortOrder:String):Cursor{

        val qb=SQLiteQueryBuilder()
        qb.tables=dbTable
        return qb.query(sqlDB,projection,selection,selectionArgs,null,null,sortOrder)
    }

    fun Delete(selection:String, selectionArgs:Array<String>):Int{

        return sqlDB!!.delete(dbName,selection,selectionArgs)
    }
}