package com.tauseebpp.notesapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.room.Insert
import kotlinx.android.synthetic.main.activity_add_notes.*

class add_notes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
    }

    fun addNotes(view: View) {

        val dbManger=DbManger(this)
        val values=ContentValues()
        values.put("Title",etTitle.text.toString())
        values.put("Description",etDes.text.toString())
        val id=dbManger.Insert(values)


        if (id>0){
            Toast.makeText(this, "note is created", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(this, "cant created", Toast.LENGTH_LONG).show()

        }
    }
}