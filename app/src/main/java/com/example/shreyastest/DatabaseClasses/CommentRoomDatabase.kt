package com.example.shreyastest.DatabaseClasses

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shreyastest.BeanClasses.Commentary
import com.example.shreyastest.Interfaces.CommentDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Commentary::class), version = 1)
public abstract class CommentRoomDatabase: RoomDatabase() {

    public abstract fun commentDao(): CommentDao;

    private class CommentDataBaseCallback(
            private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var commentDao = database.commentDao()

                    // Delete all content here.
//                    commentDao.deleteAll()

                    // Add sample words.
                  /*  var word = Commentary()
                    commentDao.insert(word)
                    word = Word("World!")
                    commentDao.insert(word)

                    // TODO: Add your own words!
                    word = Word("TODO!")
                    commentDao.insert(word)*/
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CommentRoomDatabase? = null

        fun getDatabase(context: Context): CommentRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        CommentRoomDatabase::class.java,
                        "comment_database"
                )
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}