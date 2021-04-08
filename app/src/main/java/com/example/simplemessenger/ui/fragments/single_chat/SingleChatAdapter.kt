package com.example.simplemessenger.ui.fragments.single_chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.simplemessenger.R
import com.example.simplemessenger.models.CommonModel
import com.example.simplemessenger.database.CURRENT_UID
import com.example.simplemessenger.ui.fragments.message_recycler_view.view_holders.AppHolderFactory
import com.example.simplemessenger.ui.fragments.message_recycler_view.view_holders.HolderImageMessage
import com.example.simplemessenger.ui.fragments.message_recycler_view.view_holders.HolderTextMessage
import com.example.simplemessenger.ui.fragments.message_recycler_view.views.MessageView
import com.example.simplemessenger.utilits.*

class SingleChatAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return mListMessagesCache[position].getTypeView()
    }

   private var mListMessagesCache = mutableListOf<MessageView>()
    private lateinit var mDiffResult: DiffUtil.DiffResult


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return AppHolderFactory.getHolder(parent,viewType)
    }

    override fun getItemCount(): Int = mListMessagesCache.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HolderImageMessage->drawMessageImage(holder,position)
            is HolderTextMessage->drawMessageText(holder,position)
            else->{

            }
        }
    }

    private fun drawMessageImage(holder: HolderImageMessage, position: Int) {

        if(mListMessagesCache[position].from == CURRENT_UID){
            holder.blocReceivedImageMessage.visibility = View.GONE
            holder.blocUserImageMessage.visibility = View.VISIBLE
            holder.chatUserImage.downloadAndSetImage(mListMessagesCache[position].fileUrl)
            holder.chatUserImageMessageTime.text = mListMessagesCache[position].timeStamp.asTime()
        }else{
            holder.blocReceivedImageMessage.visibility = View.VISIBLE
            holder.blocUserImageMessage.visibility = View.GONE
            holder.chatReceivedImage.downloadAndSetImage(mListMessagesCache[position].fileUrl)
            holder.chatReceivedImageMessageTIme.text = mListMessagesCache[position].timeStamp.asTime()
        }

    }

    private fun drawMessageText(holder: HolderTextMessage, position: Int) {

        if(mListMessagesCache[position].from == CURRENT_UID){
            holder.blocUserMessage.visibility = View.VISIBLE
            holder.blocReceivedMessage.visibility = View.GONE
            holder.chatUserMessage.text = mListMessagesCache[position].text
            holder.chatUserMessageTime.text = mListMessagesCache[position].timeStamp.asTime()
        }else{
            holder.blocUserMessage.visibility = View.GONE
            holder.blocReceivedMessage.visibility = View.VISIBLE
            holder.chatReceivedMessage.text = mListMessagesCache[position].text
            holder.chatReceivedMessageTime.text = mListMessagesCache[position].timeStamp.asTime()
        }
    }

    fun setList(list: List<CommonModel>){


     ///   notifyDataSetChanged()
    }

    fun addItemToBottom(item:MessageView,onSuccess:()->Unit){
        if (!mListMessagesCache.contains(item)) {
            mListMessagesCache.add(item)
            notifyItemInserted(mListMessagesCache.size)
        }
        onSuccess()
    }

    fun addItemToTop(item:MessageView,onSuccess:()->Unit){
        if (!mListMessagesCache.contains(item)) {
            mListMessagesCache.add(item)
            mListMessagesCache.sortBy { it.timeStamp.toString() }
            notifyItemInserted(0)
        }
        onSuccess()
    }
}


