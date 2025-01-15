package com.example.healthapp1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppointmentsAdapter(private val appointments: List<Appointment>) :
    RecyclerView.Adapter<AppointmentsAdapter.AppointmentsViewHolder>() {

    class AppointmentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val doctorName: TextView = view.findViewById(R.id.doctorName)
        val specialization: TextView = view.findViewById(R.id.specialization)
        val timeSlot: TextView = view.findViewById(R.id.timeSlot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)
        return AppointmentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentsViewHolder, position: Int) {
        val appointment = appointments[position]
        holder.doctorName.text = appointment.doctorName
        holder.specialization.text = appointment.specialization
        holder.timeSlot.text = appointment.timeSlot
    }

    override fun getItemCount() = appointments.size
}
