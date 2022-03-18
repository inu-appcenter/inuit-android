package com.inu.appcenter.inuit.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inu.appcenter.inuit.R
import com.inu.appcenter.inuit.recycler.item.ClubItem

class MyClubListAdapter(val clickListener : OnMyCircleClick) : RecyclerView.Adapter<MyClubListAdapter.MyClubViewHolder>() {

    private val items = mutableListOf<ClubItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyClubViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_my_club_list_item, parent, false)
        return MyClubViewHolder(view)
    }

    override fun onBindViewHolder(holder:MyClubViewHolder, position: Int) {
        val data = items[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = items.size

    fun addItem(item: ClubItem) {
        this.items.add(item)
    }

    fun setMyClub(clubId:Int?, clubName:String ){ //List<Circle>은 뷰모델로부터 받는다.
        items.clear()
        if(clubId != null){
            addItem(ClubItem(clubId,clubName))
        }
        notifyDataSetChanged()
    }

    fun clearAll(){
        items.clear()
        notifyDataSetChanged()
    }

    fun getItemSize() = items.size

    inner class MyClubViewHolder(view: View): RecyclerView.ViewHolder(view){

        var id : Int? = null
        val clubName = view.findViewById<TextView>(R.id.tv_recycler_my_club_list)

        fun bind(data:ClubItem){
            id = data.id
            clubName.text = data.name
            itemView.setOnClickListener {
                //Toast.makeText(itemView.context,"Selected id = $id", Toast.LENGTH_SHORT).show()
                val pop = PopupMenu(itemView.context, itemView)
                pop.inflate(R.menu.menu_my_circle)
                pop.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_show_circle -> {
                            //동아리 상세페이지로 이동
                            clickListener.onMyCircleDescClick(id!!)
                        }
                        R.id.menu_delete_circle -> {
                            //MyPrifile에 있는 delete 메서드 실행
                            clickListener.showDeleteDialog(id!!)
                        }
                        R.id.menu_edit_circle -> {
                            //내 동아리 수정하기 메서드 실행행
                            clickListener.onMyCircleEditClick(id!!)
                       }
                    }
                    false
                }
                pop.show()
            }
        }
    }
}