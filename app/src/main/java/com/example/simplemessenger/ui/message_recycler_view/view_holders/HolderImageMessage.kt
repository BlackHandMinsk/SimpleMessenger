package com.example.simplemessenger.ui.message_recycler_view.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemessenger.database.CURRENT_UID
import com.example.simplemessenger.ui.message_recycler_view.views.MessageView
import com.example.simplemessenger.utilits.asTime
import com.example.simplemessenger.utilits.downloadAndSetImage
import kotlinx.android.synthetic.main.message_item_image.view.*

class HolderImageMessage(view:View):RecyclerView.ViewHolder(view), MessageHolder {
    private  val blocReceivedImageMessage: ConstraintLayout = view.block_receiver_image_message
    private val blocUserImageMessage: ConstraintLayout = view.block_user_image_message
    private val chatUserImage: ImageView = view.chat_user_image
    private val chatReceivedImage: ImageView = view.chat_received_image
    private val chatUserImageMessageTime: TextView = view.chat_user_image_message_time
    private val chatReceivedImageMessageTIme: TextView = view.chat_receiver_image_message_time


    override fun drawMessage(view: MessageView) {
        if(view.from == CURRENT_UID){
           blocReceivedImageMessage.visibility = View.GONE
          blocUserImageMessage.visibility = View.VISIBLE
           chatUserImage.downloadAndSetImage(view.fileUrl)
            chatUserImageMessageTime.text = view.timeStamp.asTime()
        }else{
           blocReceivedImageMessage.visibility = View.VISIBLE
            blocUserImageMessage.visibility = View.GONE
            chatReceivedImage.downloadAndSetImage(view.fileUrl)
            chatReceivedImageMessageTIme.text = view.timeStamp.asTime()
        }
    }

    override fun onAttach(view: MessageView) {

    }

    override fun onDetach() {

    }
}