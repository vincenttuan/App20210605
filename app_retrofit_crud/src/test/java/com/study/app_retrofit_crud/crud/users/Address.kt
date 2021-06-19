package com.study.app_retrofit_crud.crud.users

data class Address(
    var city: String,
    val geo: Geo,
    val street: String,
    val suite: String,
    val zipcode: String
)