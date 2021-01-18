package com.example.shreyastest.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shreyastest.beanclasses.Commentary
import com.example.shreyastest.viewmodel.CommentaryVm
import com.example.shreyastest.adapters.CommentListAdapter
import com.example.shreyastest.R
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity : AppCompatActivity() {

    lateinit var imageComp: AppCompatImageView

    lateinit var rv_comment_layout: RecyclerView
    private var imageId: String = ""
    private var imageLink: String = ""
    lateinit var comment_txt: AppCompatEditText

    private lateinit var mCommentViewModel: CommentaryVm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        comment_txt = findViewById(com.example.shreyastest.R.id.comment_text)
        imageComp = findViewById(R.id.mainImage)
        rv_comment_layout = findViewById(R.id.rv_comment_layout)

        loadImage()
        val commentAdapter = CommentListAdapter(this)

        rv_comment_layout.adapter = commentAdapter
        rv_comment_layout.layoutManager = LinearLayoutManager(this)

        mCommentViewModel = ViewModelProvider(this).get(CommentaryVm::class.java)

        mCommentViewModel.getComments(imageId).observe(this, Observer { lstComments ->
            lstComments?.let {commentAdapter.setCommentary(it)}
        })

//        mCommentViewModel.getComments(imageId)
        btn_submit.setOnClickListener{
            val isTxtEmpty = comment_txt.text?.isEmpty()
            if(!isTxtEmpty!!) {
                val commentary = Commentary()
                commentary.comment = comment_txt.text.toString()
                commentary.imageID = imageId

                mCommentViewModel.insert(commentary)
                mCommentViewModel.getComments(imageId)
                comment_txt.setText("")
            }
        }

    }

    fun loadImage(){
        val bundle: Bundle? = intent.extras
        imageLink = bundle?.get("imagelink").toString()
        imageId = bundle?.get("imageId").toString()
        Glide.with(this)
                .load(imageLink)
                .into(imageComp)
    }
}
