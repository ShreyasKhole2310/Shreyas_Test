package com.example.shreyastest.beanclasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Commentary")
data class Commentary(
        @PrimaryKey(autoGenerate = true) val id: Int,
        @ColumnInfo(name = "comment") var comment: String,
        @ColumnInfo(name = "imageid") var imageID: String) {
    constructor(): this(0, "","")
    init {

    }
}