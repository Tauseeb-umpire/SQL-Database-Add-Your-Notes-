package com.tauseebpp.notesapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(),MyAdapter.OnItemclickListner{

    private var listNotes= mutableListOf<Notes>()
    private var displayList=ArrayList<Notes>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Add data

//        listNotes.add(
//            Notes(
//                "Meet the Professer",
//                "noun. a brief record of something written down to assist the memory or for future reference. notes, a record or outline of a speech, statement, testimony, etc., or of one's impressions of something. ... a brief written or printed statement giving particulars or information."
//            )
//        )
//        listNotes.add(
//            Notes(
//                "Meet the Doctor",
//                "noun. a brief record of something written down to assist the memory or for future reference. notes, a record or outline of a speech, statement, testimony, etc., or of one's impressions of something. ... a brief written or printed statement giving particulars or information."
//            )
//        )
//        listNotes.add(
//            Notes(
//                "Meet the Friend",
//                "noun. a brief record of something written down to assist the memory or for future reference. notes, a record or outline of a speech, statement, testimony, etc., or of one's impressions of something. ... a brief written or printed statement giving particulars or information."
//            )
//        )
//        listNotes.add(
//            Notes(
//                "Meet the Shopkeeper",
//                "noun. a brief record of something written down to assist the memory or for future reference. notes, a record or outline of a speech, statement, testimony, etc., or of one's impressions of something. ... a brief written or printed statement giving particulars or information."
//            )
//        )
//        listNotes.add(
//            Notes(
//                "Meet the Girl",
//                "noun. a brief record of something written down to assist the memory or for future reference. notes, a record or outline of a speech, statement, testimony, etc., or of one's impressions of something. ... a brief written or printed statement giving particulars or information."
//            )
//        )

//        lvNotes.adapter=MyAdapter(listNotes)
//        lvNotes.layoutManager=LinearLayoutManager(this)

        //Load from the database

        LoadQuery("%")
    }


    fun LoadQuery(Title:String)
    {
        val dbManger=DbManger(this)
        val projection= arrayOf("ID","title","Description")
        val selectionArgs= arrayOf(Title)
        val cursor=dbManger.Query(projection,"title like ?",selectionArgs,"title")
        listNotes.clear()
        if (cursor.moveToFirst()){
            do {
                val ID=cursor.getInt(cursor.getColumnIndex("ID"))
                val title=cursor.getString(cursor.getColumnIndex("title"))
                val description=cursor.getString(cursor.getColumnIndex("Description"))

                listNotes.add(Notes(ID,title,description))
                displayList.add(Notes(ID,title,description))

            }while (cursor.moveToNext())
        }

//        listNotes.add(Notes(6,"Meet the Friend","noun. a brief record of something written down to assist the memory or for future reference. notes, a record or outline of a speech, sta"))

        lvNotes.adapter=MyAdapter(displayList,this)
        lvNotes.layoutManager=LinearLayoutManager(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val searchItem=menu!!.findItem(R.id.app_bar_search)
        if (searchItem!=null)
        {
            val searchView=searchItem.actionView as androidx.appcompat.widget.SearchView

            val editText=searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            editText.hint="Search..."
            searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if(newText!!.isNotEmpty()){
                        displayList.clear()
                        val search=newText!!.toLowerCase(Locale.getDefault())
                        listNotes.forEach {
                            if (it.nodeName.toLowerCase(Locale.getDefault()).contains(search)) {
                                displayList.add(it)
                            }
                        }

                        lvNotes.adapter!!.notifyDataSetChanged()
                    }

                    else{
                        displayList.clear()
                        displayList.addAll(listNotes)
                        lvNotes.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.addNote->{
                //go to add page
                val intent=Intent(this,add_notes::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(position: Int) {
        val dbManager=DbManger(this)
        val sectionArgs= arrayOf(listNotes[position].nodeId.toString())
        dbManager.Delete("ID= ?",sectionArgs)
        LoadQuery("%")
    }
}