package br.com.leandro.weather.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class City(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
) : Parcelable
