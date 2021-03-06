package com.example.shreyastest.Interfaces

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shreyastest.BeanClasses.Commentary

@Dao
interface CommentDao {

    @Query("SELECT * FROM Commentary WHERE imageid = :imageid")
    fun getAllComment(imageid: String): LiveData<List<Commentary>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertComment(comment: Commentary)


}