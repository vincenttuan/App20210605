package com.study.app_retrofit_crud.model

data class Employee(
    var email: String,
    val id: Int,
    var name: String,
    var phone: String,
    var salary: Salary
)