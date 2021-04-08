package com.example.simplemessenger.ui.fragments.message_recycler_view.view_holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_item_image.view.*

class HolderImageMessage(view:View):RecyclerView.ViewHolder(view) {
    val blocReceivedImageMessage: ConstraintLayout = view.block_receiver_image_message
    val blocUserImageMessage: ConstraintLayout = view.block_user_image_message
    val chatUserImage: ImageView = view.chat_user_image
    val chatReceivedImage: ImageView = view.chat_received_image
    val chatUserImageMessageTime: TextView = view.chat_user_image_message_time
    val chatReceivedImageMessageTIme: TextView = view.chat_receiver_image_message_time
}