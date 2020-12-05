package com.example.tugasbesarrpl

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User (
    var name: String?
//    var phone: String?,
//    var address: String?

//    constructor() {}
//    constructor(name: String?, phone: String?, address: String?) {
//        this.name = name
//        this.phone = phone
//        this.address = address
//    }
):Parcelable