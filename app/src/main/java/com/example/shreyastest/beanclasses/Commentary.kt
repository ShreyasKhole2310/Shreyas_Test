package com.example.shreyastest.beanclasses

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Commentary")
data class Commentary(
        @PrimaryKey(autoGenerate = true) val id: Int,
        @ColumnInfo(name = "comment") var comment: String?,
        @ColumnInfo(name = "imageid") var imageID: String?): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    constructor(): this(0, "","")
    init {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(comment)
        parcel.writeString(imageID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Commentary> {
        override fun createFromParcel(parcel: Parcel): Commentary {
            return Commentary(parcel)
        }

        override fun newArray(size: Int): Array<Commentary?> {
            return arrayOfNulls(size)
        }
    }
}