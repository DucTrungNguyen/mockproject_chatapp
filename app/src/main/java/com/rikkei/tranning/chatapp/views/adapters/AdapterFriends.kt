package com.rikkei.tranning.chatapp.views.adapters

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rikkei.tranning.chatapp.services.models.Friend
import kotlinx.android.synthetic.main.item_friend.view.*

class AdapterFriends(val listFriend : List<Friend>) : RecyclerView.Adapter<AdapterFriends.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name : TextView = view.textViewName
        val avatar : ImageView  = view.avatarFriend



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return listFriend.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val friend = listFriend[position]
        holder.avatar.setImageURI(Uri.parse(friend.))
        holder.name.text = friend.name

    }


}