package com.example.tugasbesarrpl


abstract class User {
    var name: String? = null
    var phone: String? = null
    var address: String? = null

    constructor() {}
    constructor(name: String?, phone: String?, address: String?) {
        this.name = name
        this.phone = phone
        this.address = address
    }
}