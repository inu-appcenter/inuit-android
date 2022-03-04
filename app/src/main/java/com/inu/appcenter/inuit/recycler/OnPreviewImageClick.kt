package com.inu.appcenter.inuit.recycler

interface OnPreviewImageClick {
    fun startProfileSlideImageViewer(curIndex:Int)

    fun startPosterSlideImageViewer(curIndex: Int)

    fun deletePosterImage(position : Int)
}