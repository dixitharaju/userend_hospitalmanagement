package com.example.healthapp1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PharmacyFragment : Fragment(R.layout.fragment_pharmacy) {

    private val cartList = mutableListOf<CartItem>() // Cart to store added items

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pharmacy, container, false)

        // Initialize RecyclerView for Cart
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_cart)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CartAdapter(cartList.map { it.name }) // For displaying item names only

        // Set up click listeners for each item button with item name and price
        setupButton(view, R.id.btn_add_paracetamol, "Paracetamol", 50.0)
        setupButton(view, R.id.btn_add_amoxicillin, "Amoxicillin", 100.0)
        setupButton(view, R.id.btn_add_aspirin, "Aspirin", 20.5)
        setupButton(view, R.id.btn_add_ibuprofen, "Ibuprofen", 30.0)
        setupButton(view, R.id.btn_add_cough_syrup, "Cough Syrup", 150.0)
        setupButton(view, R.id.btn_add_vitamin_c, "Vitamin C", 200.0)
        setupButton(view, R.id.btn_add_ointment, "Antibiotic Ointment", 70.0)
        setupButton(view, R.id.btn_add_antacid, "Antacid Tablets", 120.5)
        setupButton(view, R.id.btn_add_anti_allergy, "Anti-Allergy Pills", 300.5)
        setupButton(view, R.id.btn_add_multivitamins, "Multivitamins", 80.0)

        // Set up "Go to Cart" button to navigate to CartFragment
        val goToCartButton = view.findViewById<FloatingActionButton>(R.id.btn_go_to_cart)
        goToCartButton.setOnClickListener {
            navigateToCartFragment()
        }

        return view
    }

    // Helper function to set up buttons and add items to cart
    private fun setupButton(view: View, buttonId: Int, itemName: String, price: Double) {
        val button = view.findViewById<Button>(buttonId)
        button.setOnClickListener {
            cartList.add(CartItem(name = itemName, quantity = 1, price = price))
            showToast("$itemName added to cart!")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToCartFragment() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CartFragment.newInstance(cartList))
            .addToBackStack(null)
            .commit()
    }
}
