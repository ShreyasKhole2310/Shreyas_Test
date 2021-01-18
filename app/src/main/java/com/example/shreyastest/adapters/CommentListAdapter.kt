package com.example.shreyastest.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shreyastest.beanclasses.Commentary
import com.example.shreyastest.R

class CommentListAdapter internal constructor(context: Context): RecyclerView.Adapter<CommentListAdapter.CommentViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var comments = emptyList<Commentary>() // Cached copy of words

    inner class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val commentItemView: TextView = itemView.findViewById(R.id.comment_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = inflater.inflate(R.layout.comment_layout, parent, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val current = comments[position]
        holder.commentItemView.text = current.comment
    }

    internal fun setCommentary(comments: List<Commentary>) {
        this.comments = comments
        notifyDataSetChanged()
    }

    override fun getItemCount() = comments.size
}