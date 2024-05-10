package com.example.rental_mobil.Admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.rental_mobil.databinding.FragmentKategoriDetailBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class CategoryDetailFragment : DialogFragment() {
    private lateinit var b: FragmentKategoriDetailBinding
    lateinit var v: View

    var ktg = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentKategoriDetailBinding.inflate(layoutInflater)
        v = b.root

        b.close.setOnClickListener {
            dismiss()
        }

        b.btnEditMobil.setOnClickListener {
            val dialog = CategoryEditFragment()

            val bundle = Bundle()
            bundle.putString("id", arguments?.get("id").toString())
            dialog.arguments = bundle
            dialog.show(childFragmentManager, "CategoryEditFragment")
        }

        detail()
        return v
    }

    private fun detail() {
        FirebaseFirestore.getInstance().collection("Category")
            .document(arguments?.get("id").toString())
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val st1 = document.get("Category").toString()
                    val st2 = document.get("deskripsi").toString()

                    ktg = st1
                    b.detailCategory.setText(st1)
                    b.detailDeskripsi.setText(st2)
                } else {
                    // Dokumen tidak ditemukan
                }
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }
}