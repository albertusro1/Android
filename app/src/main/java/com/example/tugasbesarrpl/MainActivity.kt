package com.example.tugasbesarrpl

import android.R.attr.name
import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.internal.service.Common
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.view.*


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: PostAdapter
    private lateinit var rvPost: RecyclerView
    private lateinit var dataName: Array<String>
    private var posts: ArrayList<Post> = arrayListOf()
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        rvPost = findViewById(R.id.recycler_post)
        rvPost.setHasFixedSize(true)

        adapter = PostAdapter(posts)
        rvPost.adapter = adapter

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
        } else {}

        prepare()
        addItem()
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        rvPost.layoutManager = layoutManager
        rvPost.adapter = adapter
    }

    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    fun profile(view: View)   {
        startActivity(Intent(this, ProfileActivity::class.java))
        finish()
    }

    private fun prepare() {
        dataName = resources.getStringArray(R.array.title)
    }

    private fun addItem() {
        for (position in dataName.indices) {
            val post = Post(
                dataName[position]
            )
            posts.add(post)
        }
        adapter.posts = posts
    }
}