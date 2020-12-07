package com.example.tugasbesarrpl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: PostAdapter
    private lateinit var rvUser: RecyclerView
    private lateinit var dataName: Array<String>
    private var posts: ArrayList<Post> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvUser = findViewById(R.id.recycler_post)
        rvUser.setHasFixedSize(true)

        adapter = PostAdapter(posts)
        rvUser.adapter = adapter

        prepare()
        addItem()
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@MainActivity)
        rvUser.layoutManager = layoutManager
        rvUser.adapter = adapter
    }

    fun logout(view: View) {
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, LoginActivity::class.java))
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