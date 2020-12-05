package com.example.tugasbesarrpl

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: PostAdapter
    private lateinit var rvUser: RecyclerView
    private lateinit var dataName: Array<String>
    private var users: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvUser = findViewById(R.id.recycler_post)
        rvUser.setHasFixedSize(true)

        adapter = PostAdapter(users)
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
        dataName = resources.getStringArray(R.array.name)
//        dataUserName = resources.getStringArray(R.array.username)
//        dataPhoto = resources.obtainTypedArray(R.array.avatar)
//        dataLocation = resources.getStringArray(R.array.location)
//        dataFollowers = resources.getStringArray(R.array.followers)
//        dataFollowing = resources.getStringArray(R.array.following)
//        dataCompany = resources.getStringArray(R.array.company)
//        dataRepository = resources.getStringArray(R.array.repository)
    }

    private fun addItem() {
        for (position in dataName.indices) {
            val user = User(
                dataName[position]
            )
            users.add(user)
        }
        adapter.users = users
    }
}