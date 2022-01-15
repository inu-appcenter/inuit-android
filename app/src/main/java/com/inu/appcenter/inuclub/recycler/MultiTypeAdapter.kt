package com.inu.appcenter.inuclub.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inu.appcenter.inuclub.R
import com.inu.appcenter.inuclub.recycler.item.ClubItem
import com.inu.appcenter.inuclub.recycler.item.Item
import com.inu.appcenter.inuclub.recycler.item.TitleItem

class MultiTypeAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        TYPE_TITLE -> {
            TitleHolder.create(parent)
        }
        TYPE_CLUB -> {
            ClubHolder.create(parent)
        }
        else -> {
            throw IllegalStateException("Not Found ViewHolder Type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TitleHolder ->{
                holder.bind(items[position] as TitleItem)
            }
            is ClubHolder -> {
                holder.bind(items[position] as ClubItem)
            }
        }
    }

    override fun getItemCount(): Int  = items.size

    override fun getItemViewType(position: Int) = when(items[position]){
        is TitleItem -> {
            TYPE_TITLE
        }
        is ClubItem -> {
            TYPE_CLUB
        }
        else ->{
            throw java.lang.IllegalStateException("Not Found ViewHolder Type")
        }
    }

    class TitleHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tv_title = itemView.findViewById(R.id.tv_recycler_item_title) as TextView

        fun bind(item: TitleItem){
            tv_title.text = item.title
        }

        companion object Factory{
            fun create(parent: ViewGroup) : TitleHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycler_title_item,parent,false)
                return TitleHolder(view)
            }
        }
    }

    class ClubHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        //동아리 아이템에 있는 뷰들, 동아리 이름, 사진, 설명 등
        private val iv_img = itemView.findViewById(R.id.iv_recycler_item_img) as ImageView
        private val tv_name = itemView.findViewById(R.id.tv_recycler_item_name) as TextView
        private val tv_desc = itemView.findViewById(R.id.tv_recycler_item_desc) as TextView

        fun bind(item: ClubItem){
            iv_img.setImageResource(item.ImageId)
            tv_name.text = item.name
            tv_desc.text = item.description
        }

        companion object Factory{
            fun create(parent: ViewGroup) : ClubHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycler_club_item,parent,false)
                return ClubHolder(view)
            }
        }

    }

    fun addItems(item: Item) {
        this.items.add(item)
        this.notifyDataSetChanged()
    }

    companion object {
        private const val TYPE_TITLE = 0
        private const val TYPE_CLUB = 1
    }

}