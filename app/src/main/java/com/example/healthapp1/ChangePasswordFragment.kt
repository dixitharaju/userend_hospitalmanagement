package com.example.healthapp1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

class ChangePasswordFragment : Fragment() {

    private lateinit var oldPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var changePasswordButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_change_password, container, false)

        oldPasswordEditText = view.findViewById(R.id.editTextOldPassword)
        newPasswordEditText = view.findViewById(R.id.editTextNewPassword)
        confirmPasswordEditText = view.findViewById(R.id.editTextConfirmPassword)
        changePasswordButton = view.findViewById(R.id.buttonChangePassword)

        changePasswordButton.setOnClickListener {
            changePassword()
        }

        return view
    }

    private fun changePassword() {
        val oldPassword = oldPasswordEditText.text.toString()
        val newPassword = newPasswordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val storedPassword = sharedPreferences.getString("password", null)

        if (oldPassword != storedPassword) {
            Toast.makeText(requireContext(), "Old password is incorrect", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmPassword) {
            Toast.makeText(requireContext(), "New passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Update password in SharedPreferences
        sharedPreferences.edit().putString("password", newPassword).apply()
        Toast.makeText(requireContext(), "Password changed successfully", Toast.LENGTH_SHORT).show()

        // Optionally, navigate back to the previous fragment or main screen
    }
}
