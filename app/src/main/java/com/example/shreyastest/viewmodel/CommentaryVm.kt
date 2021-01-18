package com.example.shreyastest.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shreyastest.BeanClasses.Commentary
import com.example.shreyastest.Interfaces.CommentDao
import com.example.shreyastest.DatabaseClasses.CommentRoomDatabase
import com.example.shreyastest.Repository.CommentRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CommentaryVm(application: Application) : AndroidViewModel(application){
    private val repository: CommentRepo
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
     var allComment: LiveData<List<Commentary>>
    lateinit var wordsDao: CommentDao

    init {
        wordsDao = CommentRoomDatabase.getDatabase(application).commentDao()
        repository = CommentRepo(wordsDao)
        allComment = MutableLiveData()
//        allComment = repository.allComments
    }

    fun getComments(imageId: String): LiveData<List<Commentary>>{
        return wordsDao.getAllComment(imageId)
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(comment: Commentary) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(comment)
    }
}