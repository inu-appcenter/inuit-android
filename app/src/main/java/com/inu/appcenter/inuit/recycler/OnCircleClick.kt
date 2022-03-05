package com.inu.appcenter.inuit.recycler

interface OnCircleClick {
    fun startCircleDetail(id:Int,name:String,recruit:Boolean,location:String?,schedule:String?,phone:String?,owner:String?,
                          division:String, category:String, description:String)
}