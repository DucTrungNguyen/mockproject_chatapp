package com.rikkei.tranning.chatapp.views.adapters

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.views.uis.message.ChatViewModel


class StickerAdapter(private val itemList: List<Int>, val context: Context, val idUser: String) :
    RecyclerView.Adapter<StickerAdapter.ItemViewHolder>() {


    val chatViewModel =
        ViewModelProviders.of((context as FragmentActivity)).get(
            ChatViewModel::class.java
        )

    lateinit var listener : OnItemClickListener
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageSticker: ImageView = itemView.findViewById(R.id.sticker_preview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.sticker_image_item, parent, false)
        return ItemViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]

        holder.imageSticker.setImageResource(currentItem)
        holder.imageSticker.setOnClickListener(View.OnClickListener {
            val name = it.resources.getResourceEntryName(currentItem)

            if ( listener != null){
                listener?.onItemClick(name)
            }

        })


    }

    interface OnItemClickListener{
        fun  onItemClick(nameSticker : String)
    }


    public  fun  setOnItemClickListener(_listener : OnItemClickListener){
        this.listener = _listener

    }




}