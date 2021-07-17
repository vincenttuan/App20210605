package com.study.app_ticket_firebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.study.app_ticket_firebase.R
import com.study.app_ticket_firebase.models.Order
import kotlinx.android.synthetic.main.order.view.*

// 適配器（配置每一筆訂單記錄）
class RecyclerViewAdapter(val listener: OrderOnItemClickListener) : RecyclerView.Adapter<RecyclerViewAdapter.OrderViewHolder>() {
    private var orderList: List<Order> = ArrayList<Order>()
    fun setOrderList(orderList: List<Order>) {
        this.orderList = orderList
    }

    fun getOrderList(): List<Order> {
        return this.orderList
    }

    // View 配置方式
    class OrderViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val key        = view.tv_key
        private val userName   = view.tv_userName
        private val allTickets = view.tv_allTickets
        private val roundTrip  = view.tv_roundTrip
        private val onWay      = view.tv_oneWay
        private val total      = view.tv_total
        fun bind(order: Order) {
            key.text        = "key：${order.key}"
            userName.text   = order.ticket.userName
            allTickets.text = order.ticket.allTickets.toString()
            roundTrip.text  = order.ticket.roundTrip.toString()
            onWay.text      = order.ticket.oneWay.toString()
            total.text      = String.format("%,d", order.ticket.total)
            total.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.itemView.setOnClickListener {
            listener.onItemClickListener(order)
        }
        holder.itemView.setOnLongClickListener {
            listener.onItemLongClickListener(order)
            true
        }
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    interface OrderOnItemClickListener {
        fun onItemClickListener(order: Order)
        fun onItemLongClickListener(order: Order)
    }
}