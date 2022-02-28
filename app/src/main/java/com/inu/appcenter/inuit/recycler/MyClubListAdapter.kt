package com.inu.appcenter.inuit.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.inu.appcenter.inuit.R
import com.inu.appcenter.inuit.recycler.item.ClubItem

class MyClubListAdapter() : RecyclerView.Adapter<MyClubListAdapter.MyClubViewHolder>() {

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

    fun setSampleData(){
        addItem(ClubItem(1,"인유공방"))
        addItem(ClubItem(1,"인유공방"))
        addItem(ClubItem(1,"인유공방"))
        addItem(ClubItem(1,"인유공방"))
        addItem(ClubItem(1,"인유공방"))
        addItem(ClubItem(1,"인유공방"))
        addItem(ClubItem(1,"인유공방"))
    }

    fun addItem(item: ClubItem) {
        this.items.add(item)
    }

    fun addMyClub(clubId:Int, clubName:String ){ //List<Circle>은 뷰모델로부터 받는다.
        items.clear()
        addItem(ClubItem(clubId,clubName))
        notifyDataSetChanged()
    }

    class MyClubViewHolder(view: View): RecyclerView.ViewHolder(view){

        var id : Int? = null
        val clubName = view.findViewById<TextView>(R.id.tv_recycler_my_club_list)

        fun bind(data:ClubItem){
            id = data.id
            clubName.text = data.name
            itemView.setOnClickListener {
                Toast.makeText(itemView.context,"Selected id = $id", Toast.LENGTH_SHORT).show()
            }
        }
    }
}