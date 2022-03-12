package com.inu.appcenter.inuit.recycler

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
import com.inu.appcenter.inuit.recycler.item.ImageUrlItem

class PreviewImageAdapter(val mode:Int, val clickListener:OnPreviewImageClick) : RecyclerView.Adapter<PreviewImageAdapter.ImagePreviewViewHolder>(){

    private val items = mutableListOf<ImageUrlItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagePreviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_image_image_item, parent,false)
        return ImagePreviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImagePreviewViewHolder, position: Int) {
        val data = items[position]
        holder.bind(data,position)
    }

    override fun getItemCount(): Int = items.size

    fun addImage(image: Image){
        this.items.add(ImageUrlItem(image.uri.toString()))
        notifyDataSetChanged()
    }

    fun addImageUrl(imagePath : String){
        this.items.add(ImageUrlItem(imagePath))
        notifyDataSetChanged()
    }

    fun itemsSize():Int = items.size

    inner class ImagePreviewViewHolder(view:View): RecyclerView.ViewHolder(view){
        val imagePreview  = view.findViewById<ImageView>(R.id.iv_preview)
        val deleteButton = view.findViewById<ImageButton>(R.id.ibtn_delete_pre_image)
        fun bind(data:ImageUrlItem, position: Int){

            Glide.with(itemView.context)
                .load(data.imageUrl)
                .centerCrop()
                .into(imagePreview)

            imagePreview.setOnClickListener {
                if (mode == 0){
                    val imageList = ArrayList<String>()
                    items.forEach{
                        imageList.add(it.imageUrl)
                    }
                    SlideImageViewer.start(itemView.context, imageList)
                }else if(mode == 1){
                    //clickListener.startPosterSlideImageViewer(position)
                    val imageList = ArrayList<String>()
                    items.forEach{
                        imageList.add(it.imageUrl)
                    }
                    SlideImageViewer.start(itemView.context, imageList,position)
                }
            }

            deleteButton.setOnClickListener {
                items.removeAt(position)
                notifyDataSetChanged()
                if(mode == 1){
                    clickListener.deletePosterImage(position,)
                }
            }
        }
    }
}