package com.example.rental_mobil.Admin

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rental_mobil.Adapter.AdapterCategory
import com.example.rental_mobil.R
import com.example.rental_mobil.databinding.ActivityKategoriBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date

class CategoryActivity : AppCompatActivity() {
    private lateinit var b: ActivityKategoriBinding

    val dataCategory = mutableListOf<HashMap<String,String>>()
    lateinit var CategoryAdp : AdapterCategory

    var idK = ""
    var ktg = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityKategoriBinding.inflate(layoutInflater)
        setContentView(b.root)
        supportActionBar?.setTitle("Car Category ")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        CategoryAdp = AdapterCategory(dataCategory, this)
        b.rvCategory.layoutManager = LinearLayoutManager(this)
        b.rvCategory.adapter = CategoryAdp

        b.btnTambah.setOnClickListener {
            val dialog = CategoryTambahFragment()

            dialog.show(supportFragmentManager, "CategoryTambahFragment")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right)
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    override fun onStart() {
        super.onStart()
        showData()
    }

    private fun showData() {
        FirebaseFirestore.getInstance().collection("Category")
            .whereEqualTo("deleted", "")
            .get()
            .addOnSuccessListener { result ->
                dataCategory.clear()
                for (doc in result) {
                    var hm = HashMap<String, String>()
                    hm.put("id", doc.get("id_Category").toString())
                    hm.put("Category", doc.get("Category").toString())
                    hm.put("created", doc.get("created").toString())

                    dataCategory.add(hm)
                }
                CategoryAdp.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                // Handle error
            }
    }

    fun deleteCategory() {
        val currentDateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        b.progressBar.visibility = View.VISIBLE
        AlertDialog.Builder(this)
            .setTitle("Delete Category!")
            .setIcon(R.drawable.warning)
            .setMessage("Do you want to delete this Category?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                FirebaseFirestore.getInstance().collection("Category")
                    .document(idK)
                    .delete()
                    .addOnSuccessListener {
                    Toast.makeText(this, "Successfully deleted Category!", Toast.LENGTH_SHORT).show()

//                    FirebaseFirestore.getInstance().collection("mobil")
//                        .whereEqualTo("Category", ktg)
//                        .get()
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                for (document in task.result!!) {
//                                    val hm = HashMap<String, Any>()
//                                    hm.set("deleted", SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))
//                                    document.reference.update(hm)
//                                        .addOnSuccessListener {
//                                            Log.d(TAG, "Data mobil berhasil diperbarui")
//                                        }
//                                        .addOnFailureListener { exception ->
//                                            Log.e(TAG, "Gagal memperbarui dokumen: $exception")
//                                        }
//                                }
//                            } else {
//                                Log.e(TAG, "Gagal mendapatkan dokumen: ${task.exception}")
//                            }
//                        }

                    b.progressBar.visibility = View.GONE
                    recreate()
                }.addOnFailureListener { e ->

                }
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                b.progressBar.visibility = View.GONE
            })
            .show()
    }
}