package com.example.healthapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp1.R

class HospitalAdapter(
    private var hospitals: List<Hospital>,
    private val onItemClick: (Hospital) -> Unit
) : RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {

    class HospitalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val hospitalName: TextView = view.findViewById(R.id.hospitalName)
        val hospitalImage: ImageView = view.findViewById(R.id.hospitalImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hospital, parent, false)  // Reference corrected to items_hospital.xml
        return HospitalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val hospital = hospitals[position]
        holder.hospitalName.text = hospital.name
        holder.hospitalImage.setImageResource(hospital.imageResource)
        holder.itemView.setOnClickListener { onItemClick(hospital) }
    }

    override fun getItemCount() = hospitals.size

    // Add a method to update the dataset
    fun updateHospitals(updatedHospitals: List<Hospital>) {
        this.hospitals = updatedHospitals
        notifyDataSetChanged()
    }
}