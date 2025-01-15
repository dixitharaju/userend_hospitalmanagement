package com.example.healthapp1
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthapp1.R

class FAQAdapter(private var faqList: List<FAQ>) : RecyclerView.Adapter<FAQAdapter.FAQViewHolder>() {

    var filteredList = faqList.toMutableList()

    inner class FAQViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionTextView: TextView = itemView.findViewById(R.id.question_text)
        private val answerTextView: TextView = itemView.findViewById(R.id.answer_text)

        fun bind(faq: FAQ) {
            questionTextView.text = faq.question
            answerTextView.text = faq.answer
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_faq, parent, false)
        return FAQViewHolder(view)
    }

    override fun onBindViewHolder(holder: FAQViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            faqList.toMutableList()
        } else {
            faqList.filter {
                it.question.contains(query, ignoreCase = true) ||
                        it.answer.contains(query, ignoreCase = true)
            }.toMutableList()
        }
        notifyDataSetChanged()
    }
}
