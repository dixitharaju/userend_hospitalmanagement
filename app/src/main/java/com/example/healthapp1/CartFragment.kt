package com.example.healthapp1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale

class CartFragment : Fragment() {

    private lateinit var cartItems: MutableList<CartItem>  // CartItem to hold name, quantity, price
    private lateinit var totalAmountView: TextView  // For displaying total amount

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        // Retrieve cart items from arguments
        cartItems = arguments?.getParcelableArrayList(ARG_CART_ITEMS) ?: mutableListOf()

        // Set up RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_cart)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val cartAdapter = CartAdapter(cartItems)
        recyclerView.adapter = cartAdapter

        // Total price TextView
        totalAmountView = view.findViewById(R.id.total_amount)
        updateTotalAmount()  // Initial calculation

        // Set OnClickListener for the "Place Order" button
        val orderButton = view.findViewById<Button>(R.id.orderButton)
        orderButton.setOnClickListener {
            Toast.makeText(requireContext(), "Entering payment portal", Toast.LENGTH_SHORT).show()
            // Optionally navigate to another fragment
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PaymentFragment())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun updateTotalAmount() {
        val total = cartItems.sumOf { it.quantity * it.price }
        // Set total amount text in INR
        totalAmountView.text = "Total: " + NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(total)

    }

    companion object {
        private const val ARG_CART_ITEMS = "cart_items"

        fun newInstance(cartItems: List<CartItem>): CartFragment {
            val fragment = CartFragment()
            val args = Bundle().apply {
                putParcelableArrayList(ARG_CART_ITEMS, ArrayList(cartItems))
            }
            fragment.arguments = args
            return fragment
        }
    }

    inner class CartAdapter(private val items: MutableList<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
            return CartViewHolder(view)
        }

        override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
            holder.bind(items[position])
        }

        override fun getItemCount(): Int = items.size

        inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val itemName: TextView = itemView.findViewById(R.id.cart_item_name)
            private val itemQuantity: TextView = itemView.findViewById(R.id.cart_item_quantity)
            private val itemPrice: TextView = itemView.findViewById(R.id.cart_item_price)
            private val btnRemove: TextView = itemView.findViewById(R.id.btn_remove_item)
            private val btnIncrease: TextView = itemView.findViewById(R.id.btn_increase_quantity)
            private val btnDecrease: TextView = itemView.findViewById(R.id.btn_decrease_quantity)

            fun bind(item: CartItem) {
                itemName.text = item.name
                itemQuantity.text = "Quantity: ${item.quantity}"
                itemPrice.text = "Price: ${NumberFormat.getCurrencyInstance(Locale("en", "IN")).format(item.price)}"

                // Remove item functionality
                btnRemove.setOnClickListener {
                    items.remove(item)
                    notifyItemRemoved(adapterPosition)
                    updateTotalAmount()
                    Toast.makeText(requireContext(), "${item.name} removed from cart!", Toast.LENGTH_SHORT).show()
                }

                // Increase quantity
                btnIncrease.setOnClickListener {
                    item.quantity++
                    itemQuantity.text = "Quantity: ${item.quantity}"
                    notifyItemChanged(adapterPosition)
                    updateTotalAmount()
                }

                // Decrease quantity
                btnDecrease.setOnClickListener {
                    if (item.quantity > 1) {
                        item.quantity--
                        itemQuantity.text = "Quantity: ${item.quantity}"
                        notifyItemChanged(adapterPosition)
                        updateTotalAmount()
                    }
                }
            }
        }
    }
}
