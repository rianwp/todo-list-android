package com.example.finalproject

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AdapterSelesai(private val data: ArrayList<DataRecycleView>): RecyclerView.Adapter<ViewHolder>() {
    fun filterData(): ArrayList<DataRecycleView>{
        val filteredData = ArrayList<DataRecycleView>()
        for(i in data){
            if(i.doneStatus){
                filteredData.add(DataRecycleView(i.judul, i.jam, i.deskripsi, i.tanggal, i.doneStatus, i.id))
            }
        }
        return filteredData
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tampilData(filterData()[position])
        holder.itemView.findViewById<FloatingActionButton>(R.id.btn_check).backgroundTintList = ColorStateList.valueOf(
            Color.parseColor("#2196f3")
        )
        holder.itemView.setOnClickListener{
            holder.tampilDeskripsi(filterData()[position])
        }
    }
    override fun getItemCount(): Int {
        var dataCount = 0
        for(i in data){
            if(i.doneStatus){
                dataCount += 1
            }
        }
        return dataCount
    }
}