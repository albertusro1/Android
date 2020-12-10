package com.example.tugasbesarrpl

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_sign_up.view.*
import com.example.tugasbesarrpl.Post as Post


class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    var adapter: PostAdapter? = null
    var posts: ArrayList<Post> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        recyclerView = findViewById(R.id.recycler_post)
        recyclerView.setHasFixedSize(true)

        val database = FirebaseDatabase.getInstance()
        val databaseref = database.getReference("Posts")

        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    posts.clear()
                    for (h: DataSnapshot in dataSnapshot.children) {
                        for (x: DataSnapshot in h.children) {
                            val post: Post? = x.getValue(Post::class.java)
                            if (post != null) {
                                posts.add(post)
                            }
                        }
                    }
                    adapter = PostAdapter(posts)
                    var layoutManager: RecyclerView.LayoutManager =
                        LinearLayoutManager(this@MainActivity)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "" + databaseError.code,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        if (auth.currentUser != null) {
            val database = FirebaseDatabase.getInstance()
            val myref = database.getReference("Users").child(auth.currentUser!!.uid)
            myref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userModel: User? = dataSnapshot.getValue(User::class.java)
                    txt_name.setText("Halo, " + userModel?.name)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(this@MainActivity, "" + databaseError.code, Toast.LENGTH_SHORT)
                        .show()
                }
            })
        } else {
            txt_name.setText("Halo, Tamu")
        }
    }

    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun profile(view: View) {
        if (auth.currentUser != null) {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        } else {
            Toast.makeText(
                this@MainActivity,
                "Tamu tidak dapat mengakses fitur ini",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun c_post(view: View) {
        if (auth.currentUser != null) {
            startActivity(Intent(this, CreatePost::class.java))
            finish()
        } else {
            Toast.makeText(
                this@MainActivity,
                "Tamu tidak dapat mengakses fitur ini",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}