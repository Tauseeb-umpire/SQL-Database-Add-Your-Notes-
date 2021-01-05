package com.tauseebpp.notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(var listNotes: ArrayList<Notes>,private val onItemclickListner:OnItemclickListner):RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val myView=inflater.inflate(R.layout.activity_note,parent,false)
        return MyViewHolder(myView,onItemclickListner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvName.text=listNotes[position].nodeName
        holder.tvDes.text=listNotes[position].nodeDes
//        holder.itemView.setOnClickListener {
//            val context=holder.itemView.context
//            val dbManag=DbManger(context)
//            val sectionAr= arrayOf(listNotes[position].nodeId.toString())
//            dbManag.Delete("ID=?",sectionAr)
//        }
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }

    class MyViewHolder(itemView: View,OnItemclickListner: OnItemclickListner):RecyclerView.ViewHolder(itemView){
        var tvName= itemView.findViewById<TextView>(R.id.tvName)!!
        var tvDes= itemView.findViewById<TextView>(R.id.tvDes)!!
        var buDel=itemView.findViewById<ImageView>(R.id.ivDelete)

        init {

            buDel.setOnClickListener {
                OnItemclickListner.onClick(adapterPosition)
            }
        }

    }

    interface OnItemclickListner{
        fun onClick(position: Int)
    }

}