package com.example.tugasbesarrpl
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    var title: String?,
    var description: String?,
    var jumlah_pekerja: String?,
    var gaji: String,
    var poster: String?
): Parcelable {
    constructor(): this("","","","","")
}