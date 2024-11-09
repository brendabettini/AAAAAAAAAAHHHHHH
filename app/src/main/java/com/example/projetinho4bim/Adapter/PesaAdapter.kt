package com.example.projetinho4bim.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetinho4bim.R

class PesaAdapter(
    private val context: Context,
    private var IdPesa: ArrayList<String>,
    private var TitlePesa: ArrayList<String>,
    private var dataPesa: ArrayList<String>,
    private var notaPesa: ArrayList<String>,
    private var descrPesa: ArrayList<String>,
    private var tags: ArrayList<String>,
    private val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<PesaAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bookTitleTextView.text = TitlePesa[position]
    }

    override fun getItemCount(): Int {
        return IdPesa.size
    }

    fun updateList(
        newIds: ArrayList<String>,
        newTitles: ArrayList<String>,
        newAuthors: ArrayList<String>,
        newPages: ArrayList<String>,
        newData: ArrayList<String>,
        newtags: ArrayList<String>
    ) {
        IdPesa = newIds
        TitlePesa = newTitles
        descrPesa = newAuthors
        notaPesa = newPages
        dataPesa = newData
        tags = newtags
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View, onItemClick: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val bookTitleTextView: TextView = itemView.findViewById(R.id.book_title_txt)

        init {
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }
}

