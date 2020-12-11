package com.example.tugasbesarrpl

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.android.synthetic.main.activity_profile.*


class CreatePost : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var editname: EditText
    private lateinit var editdesc: EditText
    private lateinit var editslot: EditText
    private lateinit var editgaji: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        editname = findViewById(R.id.et_post_name)
        editdesc = findViewById(R.id.et_post_desc)
        editslot = findViewById(R.id.et_slot)
        editgaji = findViewById(R.id.et_salary)
    }

    fun addPost(view: View) {
        val postname: String = editname.text.toString().trim()
        val postdesc: String = editdesc.text.toString().trim()
        val postslot: String = editslot.text.toString().trim()
        val postgaji: String = editgaji.text.toString().trim()

        if (postname.isEmpty()) {
            editname?.error = "Masukkan nama Anda"
            editname?.requestFocus()
            return
        } else if (postdesc.isEmpty()) {
            editdesc?.error = "Masukkan deskripsi singkat pekerjaan"
            editdesc?.requestFocus()
            return
        } else if (postslot.isEmpty()) {
            editslot?.error = "Masukkan jumlah pekerja yang dibutuhkan"
            editslot?.requestFocus()
            return
        } else if (postgaji.isEmpty()) {
            editgaji?.error = "Masukkan gaji yang Anda tawarkan"
            editgaji?.requestFocus()
            return
        } else {
            if (auth.currentUser != null) {
                val database = FirebaseDatabase.getInstance()
                val myref = database.getReference("Users").child(auth.currentUser!!.uid)
                myref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val userModel: User? = dataSnapshot.getValue(User::class.java)
                        val post = Post(
                            postname,
                            postdesc,
                            postslot,
                            postgaji,
                            userModel?.email
                        )
                        FirebaseDatabase.getInstance().getReference("Posts")
                            .child(FirebaseAuth.getInstance().currentUser!!.uid)
                            .child(postname)
                            .setValue(post)
                            .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        this@CreatePost,
                                        "Pos lowongan berhasil dibuat",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    //display a failure message
                                }
                            })
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(
                            this@CreatePost,
                            "" + databaseError.code,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}