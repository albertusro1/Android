package com.example.tugasbesarrpl

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        if (auth.currentUser != null) {
            val database = FirebaseDatabase.getInstance()
            val myref = database.getReference("Users").child(auth.currentUser!!.uid)
            myref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userModel: User? = dataSnapshot.getValue(User::class.java)
                    prof_name.setText(userModel?.name)
                    prof_email.setText(userModel?.email)
                    prof_phone.setText(userModel?.phone)
                    prof_address.setText(userModel?.address)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        this@ProfileActivity,
                        "" + databaseError.code,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } else {}
    }

    fun back(view: View)   {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}