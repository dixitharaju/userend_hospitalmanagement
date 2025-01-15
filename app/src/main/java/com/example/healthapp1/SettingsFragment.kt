package com.example.healthapp1
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var listView: ListView

    // List of settings options
    private val settingsOptions = arrayOf(
        "Notifications",
        "Change Password",
        "Help & Support",
        "About"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        listView = view.findViewById(R.id.settings_list_view)

        // Setting up the ArrayAdapter for the ListView
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, settingsOptions)
        listView.adapter = adapter

        // Handling click events on list items
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = settingsOptions[position]
            when (selectedItem) {
                "Notifications" -> openNotificationSettingsFragment()
                "Help & Support" -> openSupportFragment()
                "Change Password"->openChangePasswordFragment()
                "About"-> openAboutFragment()
                else -> Toast.makeText(requireContext(), "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun openNotificationSettingsFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NotificationSettingsFragment())
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }


    private fun openSupportFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, SupportFragment())
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
    private fun openChangePasswordFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ChangePasswordFragment())
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
    private fun openAboutFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CourseListFragment())
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}
