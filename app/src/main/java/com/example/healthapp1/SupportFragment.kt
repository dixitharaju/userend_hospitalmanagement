package com.example.healthapp1
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp1.R

class SupportFragment : Fragment(R.layout.fragment_support) {

    private lateinit var faqAdapter: FAQAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_support, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val faqs = listOf(
            FAQ("How to book an appointment?", "To book an appointment, go to the Appointments section and select your preferred date and time."),
            FAQ("How to reset my password?", "Go to Settings > Account > Reset Password and follow the instructions."),
            FAQ("How to update my profile?", "Navigate to Profile and select Edit. You can update your personal details there."),
            FAQ("What is the cancellation policy?", "Appointments can be canceled up to 24 hours before the scheduled time without any charges."),
            FAQ("How to contact customer support?", "You can reach our support team via the Contact Us section in the app."),
            FAQ("Is my data secure?", "We prioritize data security and follow industry standards to protect your information."),
            FAQ("Can I change my appointment time?", "Yes, go to Appointments, select the one you want to reschedule, and pick a new time."),
            FAQ("How do I get notifications?", "Enable notifications in Settings > Notifications to receive updates on appointments and other important information."),
            // Add more FAQs as needed
        )

        faqAdapter = FAQAdapter(faqs)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_faqs)
        recyclerView.adapter = faqAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val searchView = view.findViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                faqAdapter.filter(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                faqAdapter.filter(newText ?: "")
                return true
            }
        })

        val editTextQuery = view.findViewById<EditText>(R.id.edit_text_query)
        val buttonSendQuery = view.findViewById<Button>(R.id.button_send_query)

        buttonSendQuery.setOnClickListener {
            val userQuery = editTextQuery.text.toString()
            if (userQuery.isNotEmpty()) {
                // Here, handle the query submission as needed
                Toast.makeText(requireContext(), "Query submitted: $userQuery", Toast.LENGTH_SHORT).show()
                editTextQuery.text.clear()  // Clear the input after submission
            } else {
                Toast.makeText(requireContext(), "Please enter a query.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
