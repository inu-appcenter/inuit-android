package com.inu.appcenter.inuclub.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inu.appcenter.inuclub.R
import com.inu.appcenter.inuclub.recycler.MultiTypeAdapter
import com.inu.appcenter.inuclub.recycler.item.ClubItem
import com.inu.appcenter.inuclub.recycler.item.TitleItem

class AllClubListFragment : Fragment() {

    private lateinit var recycler_all_club_list : RecyclerView
    private lateinit var adapter: MultiTypeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_all_club_list, container, false)

        recycler_all_club_list = view.findViewById(R.id.recycler_all_club_list)
        recycler_all_club_list.layoutManager = LinearLayoutManager(context)
        adapter = MultiTypeAdapter()
        recycler_all_club_list.adapter = adapter
        setSampleData()

        return view
    }

    fun setSampleData() {
        adapter.addItems(TitleItem("모집 중"))
        adapter.addItems(ClubItem("인유공방","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        adapter.addItems(ClubItem("인스디스","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        adapter.addItems(ClubItem("퍼펙트","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        adapter.addItems(TitleItem("모집 마감"))
        adapter.addItems(ClubItem("인유공방","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        adapter.addItems(ClubItem("인스디스","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        adapter.addItems(ClubItem("퍼펙트","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        adapter.addItems(ClubItem("인유공방","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        adapter.addItems(ClubItem("인스디스","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
        adapter.addItems(ClubItem("퍼펙트","다람쥐 헌쳇바퀴에 타고파",R.drawable.ic_launcher_foreground))
    }
}