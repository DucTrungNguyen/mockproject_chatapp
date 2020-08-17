package com.rikkei.tranning.chatapp.views.adapters

import android.app.ProgressDialog
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.rikkei.tranning.chatapp.R
import com.rikkei.tranning.chatapp.views.uis.message.ChatViewModel
import java.io.File
import java.util.*


class ImageAdapter(private val itemList: List<String>, val context: Context, private val idUser: String) :
    RecyclerView.Adapter<ImageAdapter.ItemViewHolder>() {


    private val chatViewModel =
        ViewModelProviders.of((context as FragmentActivity)).get(
            ChatViewModel::class.java
        )

    private var listener: OnItemClickListener?= null

    private var uriImage: String? = null
    private var uploadTask: StorageTask<UploadTask.TaskSnapshot>? = null
    private var storageReference = FirebaseStorage.getInstance().getReference("chat")



    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageSticker: ImageView = itemView.findViewById(R.id.image_preview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.image_item, parent, false)
        return ItemViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        Glide.with(context).load(currentItem).into(holder.imageSticker);
        holder.imageSticker.setOnClickListener(View.OnClickListener {

            if (listener != null && position != RecyclerView.NO_POSITION) {
                listener!!.onItemClick( Uri.fromFile( File(currentItem)))
            }


        })
    }
    private fun getFileExtension(uri: Uri): String? {
        val contentResolver: ContentResolver = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    interface OnItemClickListener{
        fun  onItemClick(uri : Uri)
    }

    fun  setOnItemClickListener(_listener :OnItemClickListener){
        listener = _listener
    }


}