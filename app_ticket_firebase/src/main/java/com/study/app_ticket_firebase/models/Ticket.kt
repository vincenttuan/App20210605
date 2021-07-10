package com.study.app_ticket_firebase.models

data class Ticket(
    val userName: String,
    val allTickets: Int,
    val roundTrip: Int,
    val oneWay: Int,
    val total: Int
)
