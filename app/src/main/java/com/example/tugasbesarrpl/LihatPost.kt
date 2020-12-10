package com.example.tugasbesarrpl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_lihat_post.*

class LihatPost : AppCompatActivity() {
    companion object {
        const val EXTRA_POST = "EXTRA_POST"
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lihat_post)

        auth = FirebaseAuth.getInstance()

        post = intent.getParcelableExtra<Post>(EXTRA_POST) as Post
        val judul = post.title.toString()
        val desc = post.description.toString()
        val slot = post.jumlah_pekerja.toString()
        val gaji = post.gaji.toString()
        val poster = post.poster.toString()

        lihatpost_judul.text = judul
        lihatpost_desc.text = desc
        lihatpost_slot.text = slot
        lihatpost_gaji.text = gaji
        lihatpost_poster.text = poster
    }

    fun editPost(view: View){
        if (auth.currentUser != null) {
            val database = FirebaseDatabase.getInstance()
            val myref = database.getReference("Users").child(auth.currentUser!!.uid)
            myref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userModel: User? = dataSnapshot.getValue(User::class.java)
                    if(userModel?.email == lihatpost_poster.text.toString())     {
                        val value: String = lihatpost_judul.text.toString()
                        val intent = Intent(applicationContext, EditPost::class.java)
                        intent.putExtra("value", value)
                        startActivity(intent)
                    }else{
                        Toast.makeText(
                            this@LihatPost,
                            "Anda tidak dapat mengakses fitur ini",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        this@LihatPost,
                        "" + databaseError.code,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    fun deletePost(view: View){
        if (auth.currentUser != null) {
            post = intent.getParcelableExtra<Post>(EXTRA_POST) as Post
            val judul = post.title.toString()
            val database = FirebaseDatabase.getInstance()
            val myref = database.getReference("Users").child(auth.currentUser!!.uid)
            val dataref = database.getReference("Posts").child(auth.currentUser!!.uid)
            myref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userModel: User? = dataSnapshot.getValue(User::class.java)
                    if(userModel?.email == lihatpost_poster.text.toString())     {
                        dataref.child(judul).removeValue()
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(
                            this@LihatPost,
                            "Anda tidak dapat mengakses fitur ini",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        this@LihatPost,
                        "" + databaseError.code,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    fun lihatback(view: View){
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }
}