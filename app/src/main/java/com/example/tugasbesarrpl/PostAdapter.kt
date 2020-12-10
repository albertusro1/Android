package com.example.tugasbesarrpl

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view_item.view.*

class PostAdapter(private val list: ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>(){
    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post){
            with(itemView){
                judulpost.text = post.title.toString().trim()
                gajipost.text = post.gaji.toString().trim()
                descpost.text = post.description.toString().trim()

                itemView.setOnClickListener{
                    val moveDetail = Intent(itemView.context, LihatPost::class.java)
                    moveDetail.putExtra(LihatPost.EXTRA_POST, post)
                    context.startActivity(moveDetail)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.card_view_item,
            parent,
            false
        )
        return PostViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }
}