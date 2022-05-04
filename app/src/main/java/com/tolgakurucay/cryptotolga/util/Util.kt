package com.tolgakurucay.cryptotolga.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


//Glide doesn't support svg format
fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable?) {

    val requestOptions = RequestOptions()

        .error(com.google.android.material.R.drawable.mtrl_ic_error)
        .placeholder(progressDrawable)




    Glide.with(context)
        .setDefaultRequestOptions(requestOptions)
        .load(url)
        .into(this)


}

fun placeHolderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        this.strokeWidth = 10f
        this.arrowEnabled = true
        this.centerRadius = 30f
        start()
    }


}

fun ImageView.downloadFromUrlWithCoil(url: String?, placeHolder: CircularProgressDrawable) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry {
            add((SvgDecoder(this@downloadFromUrlWithCoil.context)))
        }.build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(placeHolder)
        .error(com.google.android.material.R.drawable.mtrl_ic_error)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)


}

/*@BindingAdapter("android:downloadImage")
fun downloadImage(imageView:ImageView,url:String){

    imageView.downloadFromUrl(url, placeHolderProgressBar(imageView.context))
}*/



@BindingAdapter("android:downloadImage")
fun downloadImage(imageView: ImageView, url: String?) {
    imageView.downloadFromUrlWithCoil(url, placeHolderProgressBar(imageView.context))
}