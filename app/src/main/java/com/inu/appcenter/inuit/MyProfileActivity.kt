package com.inu.appcenter.inuit

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inu.appcenter.inuit.recycler.item.ClubItem
import com.inu.appcenter.inuit.retrofit.dto.Circle

class MyProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)

        val backButton = findViewById<ImageButton>(R.id.btn_back)
        backButton.setOnClickListener {
            finish()
        }

        val editMyProfile = findViewById<TextView>(R.id.change_my_profile)
        editMyProfile.setOnClickListener {
            val intent = EditProfileActivity.newIntent(this@MyProfileActivity)
            startActivity(intent)
        }

        val recycler_myclub_List = findViewById<RecyclerView>(R.id.recycler_myclub_list)
        recycler_myclub_List.layoutManager = LinearLayoutManager(this)
        val adapter = MyClubsAdapter()
        recycler_myclub_List.adapter = adapter
        adapter.setSampleData()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MyProfileActivity::class.java)
        }
    }

    class MyClubViewHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{

        var id : Int? = null
        val clubName = view.findViewById<TextView>(R.id.tv_recycler_my_club_list)

        init {
            clubName.setOnClickListener(this)
        }

        fun bind(data:ClubItem){
            id = data.id
            clubName.text = data.name
        }

        override fun onClick(v: View?) {
            //해당 동아리 상세 페이지로 이동.
        }
    }

    class MyClubsAdapter() : RecyclerView.Adapter<MyClubViewHolder>(){

        private val items = mutableListOf<ClubItem>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyClubViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.recycler_my_club_list_item, parent, false)
            return MyClubViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyClubViewHolder, position: Int) {
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

        fun addListToItems(list:List<Circle>?){ //List<Circle>은 뷰모델로부터 받는다.
            items.clear()
            list?.forEach {
                addItem(ClubItem(it.id,it.name))
            }
            notifyDataSetChanged()
        }
    }
}