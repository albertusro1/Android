package com.example.tugasbesarrpl

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view_item.view.*

class PostAdapter(private val listPost: ArrayList<User>) :
    RecyclerView.Adapter<PostAdapter.CardViewViewHolder>() {
    internal var users = arrayListOf<User>()

    inner class CardViewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: User) {
            with(itemView) {
                judul_card.text = post.name

                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "Kamu memilih " + post.name,
                        Toast.LENGTH_SHORT
                    ).show()
//                    val moveDetail = Intent(itemView.context, DetailActivity::class.java)
//                    moveDetail.putExtra(DetailActivity.EXTRA_USER, user)
//                    context.startActivity(moveDetail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_view_item, parent, false)
        return CardViewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPost.size
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listPost[position])
    }
}