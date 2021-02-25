package com.example.recycleswipe

data class ContactData(
    var nameSurname: String = "No name",
    var phoneNumber: String = "No number",
    var photo: Int = R.drawable.noavatar,
    var lastCall: String = "No last calls",
    var operator: String = "Unknown oper"
)