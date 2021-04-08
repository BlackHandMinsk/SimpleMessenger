package com.example.simplemessenger.ui.fragments.message_recycler_view.views

import android.widget.ImageView

data class ViewImageMessage(override val id: String, override val from: String, override val timeStamp: String, override val fileUrl: String, override val text: String="")
    :MessageView {
    override fun getTypeView(): Int {
        return  MessageView.MESSAGE_IMAGE
    }

    override fun equals(other: Any?): Boolean {
        return (other as MessageView).id == id
    }
}