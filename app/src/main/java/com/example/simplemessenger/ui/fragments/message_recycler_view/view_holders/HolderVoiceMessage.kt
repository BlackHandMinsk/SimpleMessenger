package com.example.simplemessenger.ui.fragments.message_recycler_view.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemessenger.database.CURRENT_UID
import com.example.simplemessenger.ui.fragments.message_recycler_view.views.MessageView
import com.example.simplemessenger.utilits.asTime
import kotlinx.android.synthetic.main.message_item_image.view.*
import kotlinx.android.synthetic.main.message_item_voice.view.*

class HolderVoiceMessage(view:View):RecyclerView.ViewHolder(view) {
    val blocReceivedVoiceMessage: ConstraintLayout = view.block_receiver_voice_message
    val blocUserVoiceMessage: ConstraintLayout = view.block_user_voice_message
    val chatUserVoiceMessageTime: TextView = view.chat_user_voice_message_time
    val chatReceivedVoiceMessageTIme: TextView = view.chat_receiver_voice_message_time


    val chatReceivedBtnPlay:ImageView = view.chat_received_btn_play
    val chatReceivedBtnStop:ImageView = view.chat_received_btn_stop
    val chatUserBtnPlay:ImageView = view.chat_user_btn_play
    val chatUserBtnStop:ImageView = view.chat_user_btn_stop


     fun drawMessageVoice(holder: HolderVoiceMessage,view:MessageView) {
        if(view.from == CURRENT_UID){
            holder.blocReceivedVoiceMessage.visibility = View.GONE
            holder.blocUserVoiceMessage.visibility = View.VISIBLE
            holder.chatUserVoiceMessageTime.text = view.timeStamp.asTime()
        }else{
            holder.blocReceivedVoiceMessage.visibility = View.VISIBLE
            holder.blocUserVoiceMessage.visibility = View.GONE
            holder.chatReceivedVoiceMessageTIme.text = view.timeStamp.asTime()
        }

    }
}