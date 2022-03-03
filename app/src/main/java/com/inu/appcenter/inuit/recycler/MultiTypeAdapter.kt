package com.inu.appcenter.inuit.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.inu.appcenter.inuit.R
import com.inu.appcenter.inuit.recycler.item.ClubItem
import com.inu.appcenter.inuit.recycler.item.Item
import com.inu.appcenter.inuit.recycler.item.TitleItem
import com.inu.appcenter.inuit.retrofit.dto.Circle

class MultiTypeAdapter(val clickListener : OnCircleClick): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
                holder.bind(items[position] as ClubItem,clickListener)
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
        var id : Int? = null
        private val iv_img = itemView.findViewById(R.id.iv_recycler_item_img) as ImageView
        private val tv_name = itemView.findViewById(R.id.tv_recycler_item_name) as TextView
        private val tv_desc = itemView.findViewById(R.id.tv_recycler_item_desc) as TextView

        fun bind(item: ClubItem,clickListener : OnCircleClick){
            id = item.id
            tv_name.text = item.name
            tv_desc.text = item.description
            iv_img.clipToOutline = true
            if(item.ImageId != null){
                Glide.with(itemView.context)
                    .load("http://da86-125-180-55-163.ngrok.io:80/circles/view/photo/${item.ImageId}")
                    .fitCenter()
                    .override(200,200)
                    .into(iv_img)
            }else{
                iv_img.setImageResource(R.drawable.ic_null_profile_sample)
            }
            itemView.setOnClickListener {
                //Toast.makeText(itemView.context,"Selected id = $id",Toast.LENGTH_SHORT).show()
                clickListener.startCircleDetail(item.id,item.name)
            }
        }

        companion object Factory{
            fun create(parent: ViewGroup) : ClubHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.recycler_club_item,parent,false)
                return ClubHolder(view)
            }
        }
    }

    companion object {
        private const val TYPE_TITLE = 0
        private const val TYPE_CLUB = 1
    }

    fun addItem(item: Item) {
        this.items.add(item)
        //this.notifyDataSetChanged()
    }

    fun setSampleData() {
        addItem(TitleItem("모집 중"))
        addItem(ClubItem(0,"인유공방","다람쥐 헌쳇바퀴에 타고파",R.drawable.profile_sample))
        addItem(ClubItem(1,"인스디스","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        addItem(ClubItem(2,"퍼펙트","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        addItem(TitleItem("모집 마감"))
        addItem(ClubItem(3,"인유공방","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        addItem(ClubItem(4,"인스디스","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        addItem(ClubItem(5,"퍼펙트","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        addItem(ClubItem(6,"인유공방","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        addItem(ClubItem(7,"인스디스","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        addItem(ClubItem(8,"퍼펙트","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
    }

    fun addListToItems(list:List<Circle>?){

        items.clear()
        if(list?.size == 0) {
            addItem(TitleItem("해당하는 동아리/소모임이 없습니다"))
        }
        else {

            run loop@{
                list?.forEach {
                    if(it.recruit) {
                        addItem(TitleItem("모집 중"))
                        return@loop
                    }
                }
            }

            list?.forEach {
                if(it.recruit) addItem(ClubItem(it.id,it.name,it.oneLineIntroduce,it.photoId))
            }

            run loop@{
                list?.forEach {
                    if(!it.recruit) {
                        addItem(TitleItem("모집 마감"))
                        return@loop
                    }
                }
            }

            list?.forEach {
                if(!it.recruit) addItem(ClubItem(it.id, it.name,it.oneLineIntroduce,it.photoId))
            }
        }
        notifyDataSetChanged()
    }
}