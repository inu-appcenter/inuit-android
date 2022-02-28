package com.inu.appcenter.inuit.recycler

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esafirm.imagepicker.model.Image
import com.inu.appcenter.inuit.R
import com.inu.appcenter.inuit.imageviewer.SlideImageViewer
import com.inu.appcenter.inuit.recycler.item.ImageItem

class ImagePreviewAdapter() : RecyclerView.Adapter<ImagePreviewAdapter.ImagePreviewViewHolder>(){

    private val items = mutableListOf<ImageItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePreviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_image_image_item, parent,false)
        return ImagePreviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagePreviewViewHolder, position: Int) {
        val data = items[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = items.size

    fun addImage(image: Image){
        this.items.add(ImageItem(image))
        notifyDataSetChanged()
    }

    fun itemsSize():Int = items.size

    inner class ImagePreviewViewHolder(view:View): RecyclerView.ViewHolder(view){
        val imagePreview  = view.findViewById<ImageView>(R.id.iv_preview)
        val deleteButton = view.findViewById<ImageButton>(R.id.ibtn_delete_pre_image)
        fun bind(data:ImageItem){
            Glide.with(itemView.context)
                .load(data.image.uri)
                .centerCrop()
                .into(imagePreview)

            imagePreview.setOnClickListener {
                SlideImageViewer.start(itemView.context, arrayListOf(data.image))
            }

            deleteButton.setOnClickListener {
                val itemPosition = adapterPosition
                items.removeAt(itemPosition)
                notifyItemRemoved(itemPosition)
            }
        }
    }
}