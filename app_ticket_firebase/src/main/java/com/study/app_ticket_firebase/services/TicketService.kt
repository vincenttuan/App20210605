package com.study.app_ticket_firebase.services

import com.study.app_ticket_firebase.models.Ticket
import com.study.app_ticket_firebase.models.TicketsStock
import kotlinx.android.synthetic.main.activity_main.*

class TicketService {
    fun submit(allTickets: Int, roundTrip: Int, userName: String, ticketsStock: TicketsStock): Ticket {
        val oneWay = allTickets - (roundTrip*2)
        val total = ((allTickets - oneWay) * ticketsStock.discount + oneWay) * ticketsStock.price
        val ticket = Ticket(userName, allTickets, roundTrip, oneWay, total.toInt())
        return ticket
    }
}