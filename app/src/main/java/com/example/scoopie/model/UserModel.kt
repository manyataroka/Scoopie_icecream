package com.example.scoopie.model

data class UserModel(
    var userID: String = "",
    var email: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var gender: String = "",
    var address: String = "",
    val selectedOptionText: String,
)
