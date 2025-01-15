package com.example.healthapp1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.text.NumberFormat
import java.util.Locale

class PaymentFragment : Fragment() {

    private lateinit var totalAmountView: TextView  // Displays total amount
    private var totalAmount: Double = 0.0  // Received from CartFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_payment, container, false)

        // Retrieve the total amount from arguments

        // Payment options (RadioGroup)
        val paymentOptions = view.findViewById<RadioGroup>(R.id.payment_options)

        // Set OnClickListener for "Confirm Payment" button
        val confirmPaymentButton = view.findViewById<Button>(R.id.confirm_payment_button)
        confirmPaymentButton.setOnClickListener {
            val selectedOptionId = paymentOptions.checkedRadioButtonId
            if (selectedOptionId == -1) {
                Toast.makeText(requireContext(), "Please select a payment method.", Toast.LENGTH_SHORT).show()
            } else {
                val selectedOption = view.findViewById<RadioButton>(selectedOptionId).text.toString()
                Toast.makeText(requireContext(), "Order Successfully placed!", Toast.LENGTH_SHORT).show()

                // Optional: Navigate back to a summary page or main fragment
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, PharmacyFragment())
                    .addToBackStack(null)
                    .commit()
            }
        }

        return view
    }

    companion object {
        private const val ARG_TOTAL_AMOUNT = "total_amount"

        fun newInstance(totalAmount: Double): PaymentFragment {
            val fragment = PaymentFragment()
            val args = Bundle().apply {
                putDouble(ARG_TOTAL_AMOUNT, totalAmount)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
