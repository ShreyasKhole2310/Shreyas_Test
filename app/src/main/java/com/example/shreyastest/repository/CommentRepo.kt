package com.example.shreyastest.Repository

import androidx.lifecycle.LiveData
import com.example.shreyastest.BeanClasses.Commentary
import com.example.shreyastest.Interfaces.CommentDao

class CommentRepo(private val commentDao: CommentDao) {
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    fun allComments(imageId: String): LiveData<List<Commentary>> {
      return commentDao.getAllComment(imageId)
    }

     fun insert(comment: Commentary) {
        commentDao.insertComment(comment)
    }
}