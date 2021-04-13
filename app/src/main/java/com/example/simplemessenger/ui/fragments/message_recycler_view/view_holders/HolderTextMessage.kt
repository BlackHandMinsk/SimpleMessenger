package com.example.simplemessenger.ui.fragments.message_recycler_view.view_holders

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemessenger.database.CURRENT_UID
import com.example.simplemessenger.ui.fragments.message_recycler_view.views.MessageView
import com.example.simplemessenger.utilits.asTime
import kotlinx.android.synthetic.main.message_item_text.view.*

class HolderTextMessage(view: View):RecyclerView.ViewHolder(view) {
    val blocUserMessage: ConstraintLayout = view.block_user_message
    val chatUserMessage: TextView = view.chat_user_message
    val chatUserMessageTime: TextView = view.chat_user_message_time

    val blocReceivedMessage: ConstraintLayout = view.block_receiver_message
    val chatReceivedMessage: TextView = view.chat_receiver_message
    val chatReceivedMessageTime: TextView = view.chat_receiver_message_time

     fun drawMessageText(holder: HolderTextMessage,view: MessageView ) {

        if(view.from == CURRENT_UID){
            holder.blocUserMessage.visibility = View.VISIBLE
            holder.blocReceivedMessage.visibility = View.GONE
            holder.chatUserMessage.text = view.text
            holder.chatUserMessageTime.text = view.timeStamp.asTime()
        }else{
            holder.blocUserMessage.visibility = View.GONE
            holder.blocReceivedMessage.visibility = View.VISIBLE
            holder.chatReceivedMessage.text = view.text
            holder.chatReceivedMessageTime.text = view.timeStamp.asTime()
        }
    }
}