package com.study.app_retrofit_crud.api

import com.study.app_retrofit_crud.model.Employee
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface JsonDBApi {

    @GET("/employees")
    fun getEmployees(): Call<List<Employee>>

    @GET("/employees/{id}")
    fun getEmployee(@Path("id") id: Int): Call<Employee>

    @PATCH("/employees/{id}")
    fun updateSalary(@Path("id") id: Int, @Body employee: Employee): Call<Employee>

}