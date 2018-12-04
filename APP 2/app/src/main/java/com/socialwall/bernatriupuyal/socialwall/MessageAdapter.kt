package com.socialwall.bernatriupuyal.socialwall

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.socialwall.bernatriupuyal.socialwall.model.MessageModel
import kotlinx.android.synthetic.main.row_message.view.*

class MessageAdapter(var list: ArrayList<MessageModel>): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(){

    var onItemClickListener: OnItemClickListener<MessageModel>?=null


    override fun getItemCount(): Int {

        return list.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.row_message,parent,false)
        return MessageViewHolder(view);
    }

    override fun onBindViewHolder(viewHolder: MessageViewHolder, position: Int) {


        viewHolder.text.text = list[position].text;

    }



    class MessageViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        var text: TextView = itemView.messageText
    }
}