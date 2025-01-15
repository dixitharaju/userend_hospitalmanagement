package com.example.healthapp1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HospitalListFragment : Fragment(R.layout.fragment_hospital_list) {

    private lateinit var hospitalRecyclerView: RecyclerView

    private val hospitals = getHospitalsList()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hospital_list, container, false)

        hospitalRecyclerView = view.findViewById(R.id.hospitalRecyclerView)
        hospitalRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = HospitalAdapter(hospitals) { hospital ->
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AppointmentsFragment().apply {
                    arguments = Bundle().apply {
                        putString("HOSPITAL_NAME", hospital.name)
                        putString("SELECTED_DATE", "24/10/2024") // Replace with user-selected date
                    }
                })
                .addToBackStack(null)
                .commit()
        }
        hospitalRecyclerView.adapter = adapter

        // Set up filtering
        val distanceFilter: EditText = view.findViewById(R.id.distanceFilter)
        val filterButton: Button = view.findViewById(R.id.filterButton)

        filterButton.setOnClickListener {
            val maxDistance = distanceFilter.text.toString().toFloatOrNull() ?: Float.MAX_VALUE
            filterHospitalsByDate("24/10/2024", maxDistance)
        }

        return view
    }

    // Method to filter hospitals by availability on a specific date and additional filters
    fun filterHospitalsByDate(date: String, maxDistance: Float = Float.MAX_VALUE, selectedTime: String? = null) {
        val filteredHospitals = hospitals.filter { hospital ->
            hospital.doctors.any { doctor ->
                doctor.availability.containsKey(date) &&
                        (hospital.distance <= maxDistance)
            }
        }
        (hospitalRecyclerView.adapter as HospitalAdapter).updateHospitals(filteredHospitals)
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
