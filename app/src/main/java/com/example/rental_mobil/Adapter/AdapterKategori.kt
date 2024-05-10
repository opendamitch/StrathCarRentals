package com.example.rental_mobil.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.rental_mobil.Admin.CategoryActivity
import com.example.rental_mobil.Admin.CategoryDetailFragment
import com.example.rental_mobil.R

class AdapterCategory(val dataCategory: List<HashMap<String,String>>, val parent: CategoryActivity) :
    RecyclerView.Adapter<AdapterCategory.HolderDataCategory>(){
    class HolderDataCategory (v : View) : RecyclerView.ViewHolder(v) {
        val nm = v.findViewById<TextView>(R.id.CategoryNama)
        val cr = v.findViewById<TextView>(R.id.CategoryCreated)
        val dt = v.findViewById<Button>(R.id.btnDetailCategory)
        val hps = v.findViewById<Button>(R.id.btnHapusCategory)
        val cd = v.findViewById<CardView>(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderDataCategory {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_category, parent, false)
        return HolderDataCategory(v)
    }

    override fun getItemCount(): Int {
        return dataCategory.size
    }

    override fun onBindViewHolder(holder: HolderDataCategory, position: Int) {
        val data = dataCategory.get(position)
        holder.nm.setText(data.get("Category"))
        holder.cr.setText("Dibuat pada "+data.get("created"))

        holder.dt.setOnClickListener {
            val dialog = CategoryDetailFragment()

            val bundle = Bundle()
            bundle.putString("id", data.get("id").toString())
            dialog.arguments = bundle
            dialog.show(parent.supportFragmentManager, "CategoryDetailFragment")
        }

        holder.hps.setOnClickListener {
            parent.idK = data.get("id").toString()
            parent.ktg = data.get("Category").toString()
            parent.deleteCategory()
        }
    }
}