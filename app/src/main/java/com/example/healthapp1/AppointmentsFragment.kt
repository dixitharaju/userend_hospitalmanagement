package com.example.healthapp1

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AppointmentsFragment : Fragment(R.layout.fragment_appointments) {

    private lateinit var hospitals: List<Hospital> // Declare hospitals list

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_appointments, container, false)

        val hospitalName = arguments?.getString("HOSPITAL_NAME")
        val selectedDate = arguments?.getString("SELECTED_DATE")

        hospitals = getHospitalsList() // Fetch hospitals from a helper method or elsewhere

        val hospital = hospitals.find { it.name == hospitalName }
        val availableDoctors = hospital?.doctors?.filter { doctor ->
            doctor.availability.containsKey(selectedDate)
        } ?: emptyList()

        val appointments = availableDoctors.flatMap { doctor ->
            doctor.availability[selectedDate]?.map { timeSlot ->
                Appointment(doctor.name, doctor.specialization, timeSlot)
            } ?: emptyList()
        }

        val appointmentsRecyclerView: RecyclerView =
            view.findViewById(R.id.appointmentsRecyclerView)
        appointmentsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = AppointmentsAdapter(appointments)
        appointmentsRecyclerView.adapter = adapter

        // Setup Booking Button
        val bookAppointmentButton: Button = view.findViewById(R.id.bookAppointmentButton)
        bookAppointmentButton.setOnClickListener {
            showBookingForm(availableDoctors)
        }
        val cancelAppointmentButton: Button = view.findViewById(R.id.cancelButton)
        cancelAppointmentButton.setOnClickListener {
            Toast.makeText(requireContext(), "Booking Cancelled!", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    // Show a dialog with a form for booking an appointment
    private fun showBookingForm(doctors: List<Doctor>) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_book_appointment, null)
        val patientName: EditText = dialogView.findViewById(R.id.patientName)
        val patientAge: EditText = dialogView.findViewById(R.id.patientAge)
        val timeSlotSpinner: Spinner = dialogView.findViewById(R.id.timeSlotSpinner)
        val datePicker: DatePicker = dialogView.findViewById(R.id.datePicker)

        // Populate Spinner with time slots
        val timeSlots = doctors.flatMap { it.availability.values.flatten() }.distinct()
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, timeSlots)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeSlotSpinner.adapter = adapter

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("Book Appointment")
            .create()

        val confirmButton: Button = dialogView.findViewById(R.id.confirmAppointmentButton)
        confirmButton.setOnClickListener {
            val name = patientName.text.toString()
            val age = patientAge.text.toString()
            val selectedSlot = timeSlotSpinner.selectedItem.toString()

            // Get the selected date from the DatePicker
            val selectedDate = "${datePicker.dayOfMonth}-${datePicker.month + 1}-${datePicker.year}"

            if (name.isNotEmpty() && age.isNotEmpty()) {
                // Process appointment booking (save data, etc.)
                Toast.makeText(
                    requireContext(),
                    "Appointment booked for $name on $selectedDate at $selectedSlot",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            } else {
                Toast.makeText(requireContext(), "Please fill all details", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }



    private fun getHospitalsList(): List<Hospital> {
        return listOf(
            Hospital(
                "City Hospital", R.drawable.img,
                distance = 2.5f, // Example distance in kilometers
                doctors = listOf(
                    Doctor(
                        name = "Dr. Smith",
                        specialization = "Dermatology",
                        availability = mapOf(
                            "24/10/2024" to listOf("10:00 AM - 11:00 AM", "02:00 PM - 03:00 PM"),
                            "25/10/2024" to listOf("10:00 AM - 12:00 PM")
                        )
                    ),
                    Doctor(
                        name = "Dr. Johnson",
                        specialization = "Cardiology",
                        availability = mapOf(
                            "24/10/2024" to listOf("11:00 AM - 12:00 PM", "03:00 PM - 04:00 PM")
                        )
                    )
                )
            ),
            Hospital(
                "Metro Health", R.drawable.img_1,
                distance = 5.0f, // Example distance
                doctors = listOf(
                    Doctor(
                        name = "Dr. Lee",
                        specialization = "Orthopedics",
                        availability = mapOf(
                            "24/10/2024" to listOf("02:00 PM - 03:00 PM"),
                            "26/10/2024" to listOf("09:00 AM - 10:00 AM", "01:00 PM - 02:00 PM")
                        )
                    ),
                    Doctor(
                        name = "Dr. Clark",
                        specialization = "Pediatrics",
                        availability = mapOf(
                            "25/10/2024" to listOf("09:00 AM - 11:00 AM", "01:00 PM - 03:00 PM")
                        )
                    )
                )
            ),
            Hospital(
                "Health Plus", R.drawable.img,
                distance = 3.5f,
                doctors = listOf(
                    Doctor(
                        name = "Dr. Taylor",
                        specialization = "General Medicine",
                        availability = mapOf(
                            "24/10/2024" to listOf("08:00 AM - 09:00 AM", "01:00 PM - 02:00 PM"),
                            "25/10/2024" to listOf("09:00 AM - 10:00 AM")
                        )
                    ),
                    Doctor(
                        name = "Dr. Wilson",
                        specialization = "Gynecology",
                        availability = mapOf(
                            "24/10/2024" to listOf("11:00 AM - 12:00 PM"),
                            "26/10/2024" to listOf("10:00 AM - 11:00 AM")
                        )
                    )
                )
            ),
            Hospital(
                "Sunrise Hospital", R.drawable.img_1,
                distance = 7.0f,
                doctors = listOf(
                    Doctor(
                        name = "Dr. Martinez",
                        specialization = "Neurology",
                        availability = mapOf(
                            "24/10/2024" to listOf("09:00 AM - 11:00 AM"),
                            "25/10/2024" to listOf("11:00 AM - 12:00 PM")
                        )
                    ),
                    Doctor(
                        name = "Dr. Davis",
                        specialization = "Psychiatry",
                        availability = mapOf(
                            "26/10/2024" to listOf("01:00 PM - 03:00 PM")
                        )
                    )
                )
            ),
            Hospital(
                "Sunrise Hospital", R.drawable.img_1,
                distance = 7.0f,
                doctors = listOf(
                    Doctor(
                        name = "Dr. a",
                        specialization = "Neurology",
                        availability = mapOf(
                            "24/10/2024" to listOf("09:00 AM - 11:00 AM"),
                            "25/10/2024" to listOf("11:00 AM - 12:00 PM")
                        )
                    ),
                    Doctor(
                        name = "Dr. b",
                        specialization = "Psychiatry",
                        availability = mapOf(
                            "26/10/2024" to listOf("01:00 PM - 03:00 PM")
                        )
                    )
                )
            ),
            Hospital(
                "Shiv Shakthi", R.drawable.img_1,
                distance = 15.0f, // Example distance
                doctors = listOf(
                    Doctor(
                        name = "Dr. c",
                        specialization = "Orthopedics",
                        availability = mapOf(
                            "24/10/2024" to listOf("02:00 PM - 03:00 PM"),
                            "26/10/2024" to listOf("09:00 AM - 10:00 AM", "01:00 PM - 02:00 PM")
                        )
                    ),
                    Doctor(
                        name = "Dr. d",
                        specialization = "Pediatrics",
                        availability = mapOf(
                            "25/10/2024" to listOf("09:00 AM - 11:00 AM", "01:00 PM - 03:00 PM")
                        )
                    )
                )
            ),
            Hospital(
                "Apollo", R.drawable.img_1,
                distance = 5.0f, // Example distance
                doctors = listOf(
                    Doctor(
                        name = "Dr. e",
                        specialization = "Orthopedics",
                        availability = mapOf(
                            "24/10/2024" to listOf("02:00 PM - 03:00 PM"),
                            "26/10/2024" to listOf("09:00 AM - 10:00 AM", "01:00 PM - 02:00 PM")
                        )
                    ),
                    Doctor(
                        name = "Dr. f",
                        specialization = "Pediatrics",
                        availability = mapOf(
                            "25/10/2024" to listOf("09:00 AM - 11:00 AM", "01:00 PM - 03:00 PM")
                        )
                    )
                )
            ),
            Hospital(
                "Jayadeva", R.drawable.img_1,
                distance = 8.0f, // Example distance
                doctors = listOf(
                    Doctor(
                        name = "Dr. g",
                        specialization = "Orthopedics",
                        availability = mapOf(
                            "24/10/2024" to listOf("02:00 PM - 03:00 PM"),
                            "26/10/2024" to listOf("09:00 AM - 10:00 AM", "01:00 PM - 02:00 PM")
                        )
                    ),
                    Doctor(
                        name = "Dr. h",
                        specialization = "Pediatrics",
                        availability = mapOf(
                            "25/10/2024" to listOf("09:00 AM - 11:00 AM", "01:00 PM - 03:00 PM")
                        )
                    )
                )
            ),
            Hospital(
                "Nimahans", R.drawable.img_1,
                distance =15.0f, // Example distance
                doctors = listOf(
                    Doctor(
                        name = "Dr. i",
                        specialization = "Orthopedics",
                        availability = mapOf(
                            "24/10/2024" to listOf("02:00 PM - 03:00 PM"),
                            "26/10/2024" to listOf("09:00 AM - 10:00 AM", "01:00 PM - 02:00 PM")
                        )
                    ),
                    Doctor(
                        name = "Dr. j",
                        specialization = "Pediatrics",
                        availability = mapOf(
                            "25/10/2024" to listOf("09:00 AM - 11:00 AM", "01:00 PM - 03:00 PM")
                        )
                    )
                )
            )
        )
    }
}
