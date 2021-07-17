package com.study.app_ticket_firebase.models

data class Order(val key: String, val ticket: Ticket) : Comparable<Order> {
    companion object {
        var orderDelta = 1
    }
    override fun compareTo(order: Order): Int {
        return  (ticket.total - order.ticket.total) * orderDelta
    }

}
