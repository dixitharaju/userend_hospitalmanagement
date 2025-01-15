package com.example.healthapp1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var genderSpinner: Spinner
    private lateinit var registerButton: Button
    private lateinit var loginTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        nameEditText = findViewById(R.id.editTextName)
        emailEditText = findViewById(R.id.editTextEmail)
        passwordEditText = findViewById(R.id.editTextPassword)
        ageEditText = findViewById(R.id.editTextAge)
        dobEditText = findViewById(R.id.editTextDOB)
        genderSpinner = findViewById(R.id.spinnerGender)
        registerButton = findViewById(R.id.buttonRegister)
        loginTextView = findViewById(R.id.textViewLogin)

        setupGenderSpinner()
        setupDateOfBirthPicker()

        registerButton.setOnClickListener { registerUser() }
        loginTextView.setOnClickListener { openLoginActivity() }
    }

    private fun setupGenderSpinner() {
        val genders = arrayOf("Select Gender", "Male", "Female", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genders)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter
    }

    private fun setupDateOfBirthPicker() {
        dobEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = android.app.DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                dobEditText.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
            }, year, month, day)
            datePickerDialog.show()
        }
    }

    private fun registerUser() {
        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString()
        val age = ageEditText.text.toString().trim()
        val dob = dobEditText.text.toString().trim()
        val gender = genderSpinner.selectedItem.toString()

        // Validate fields
        if (name.isEmpty()) {
            nameEditText.error = "Name is required"
            return
        }

        if (!isValidName(name)) {
            nameEditText.error = "Name must contain only letters, spaces, and hyphens"
            return
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.error = "Valid email is required"
            return
        }

        if (password.isEmpty() || !isPasswordStrong(password)) {
            passwordEditText.error = "Password must be at least 6 characters, include a number, and a special character"
            return
        }

        if (age.isEmpty() || !isNumeric(age) || age.toInt() !in 18..120) {
            ageEditText.error = "Enter a valid age (18-120)"
            return
        }

        if (dob.isEmpty()) {
            dobEditText.error = "Date of Birth is required"
            return
        }

        if (gender == "Select Gender") {
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show()
            return
        }

        // Save user data in SharedPreferences if all validations pass
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("email", email)
        editor.putString("password", password)
        editor.putString("age", age)
        editor.putString("dob", dob)
        editor.putString("gender", gender)
        editor.apply()

        // Optionally, navigate to another activity after registration
        Toast.makeText(this, "User Registered: $name", Toast.LENGTH_SHORT).show()
        openLoginActivity()
    }

    private fun isValidName(name: String): Boolean {
        // Ensure the name contains only letters, spaces, and hyphens
        val nameRegex = Regex("^[A-Za-z\\s-]+$")
        return nameRegex.matches(name) && name.length > 1
    }

    private fun isPasswordStrong(password: String): Boolean {
        // Check password strength (6+ chars, at least one digit and special character)
        val passwordRegex = Regex("^(?=.*[0-9])(?=.*[@#\$%^&+=])(?=\\S+\$).{6,}\$")
        return passwordRegex.matches(password)
    }


    private fun isNumeric(input: String): Boolean {
        return input.all { it.isDigit() }
    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}