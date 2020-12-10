package com.example.tugasbesarrpl

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_edit_post.*

class EditPost : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var desc: TextInputLayout
    private lateinit var slot: TextInputLayout
    private lateinit var gaji: TextInputLayout

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)

        var bundle :Bundle ?=intent.extras
        var message = bundle!!.getString("value")

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        desc = findViewById(R.id.editpost_desc)
        slot = findViewById(R.id.editpost_slot)
        gaji = findViewById(R.id.editpost_gaji)

        textView4.text = message

        btn_saveeditpost.setOnClickListener {
            val database = FirebaseDatabase.getInstance()
            val desc = desc.editText?.text.toString().trim()
            val slot = slot.editText?.text.toString().trim()
            val gaji = gaji.editText?.text.toString().trim()

            val myref = database.getReference("Posts").child(auth.currentUser!!.uid).child(message.toString())

            if (desc.isNotEmpty()) {
                myref.child("description").setValue(desc)
            }
            if (slot.isNotEmpty()) {
                myref.child("jumlah_pekerja").setValue(slot)
            }
            if (gaji.isNotEmpty()) {
                myref.child("gaji").setValue(gaji)
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    fun editback(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}