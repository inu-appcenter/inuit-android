package com.inu.appcenter.inuit.recycler.item

data class ClubItem(val id:Int, val name:String, val description:String = "", val ImageId :Int? = 0,
                    val recruit :Boolean = true,
                    val location:String? = null,
                    val scheduleInfo : String?=null,
                    val phone:String? = null,
                    val owner:String? = null,
                    val division:String = "중앙동아리",
                    val category:String = "기타",
                    val detailDescription:String = ""
                    ) : Item


