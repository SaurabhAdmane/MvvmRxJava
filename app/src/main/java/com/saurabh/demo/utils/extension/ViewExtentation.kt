package com.saurabh.demo.utils.extension

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.saurabh.demo.DemoApp
import com.saurabh.demo.utils.LazyClickListener
import com.saurabh.demo.utils.glide.GlideImageModel
import java.io.File

/**
 * Created by SaurabhA on 03,October,2020
 *
 */
fun <T : View> T.lazyClick(block: (T) -> Unit) =
    setOnClickListener(object : LazyClickListener(ONE_SECOND) {
        override fun performClick(v: View) {
            block(v as T)
        }
    })

fun ImageView.loadAWSImage(imageName: String, isCircular: Boolean, placeHolderId: Int) {
    val placeHolderDrawable: Drawable? = if (placeHolderId > 0) getDrawable(placeHolderId) else null


    setImageDrawable(placeHolderDrawable)

    val glideImageModel = GlideImageModel()
    glideImageModel.id = imageName
    glideImageModel.localPath = File(DemoApp.context.cacheDir, "/" + glideImageModel.id)
//    glideImageModel.bucketName = NSDLAPI.AWS_BUCKET_NAME

    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(placeHolderDrawable)
        .fallback(placeHolderDrawable)

    if (isCircular)
        requestOptions.optionalCircleCrop()


//    GlideApp.with(NSDLApp.context)
//        .setDefaultRequestOptions(requestOptions)
//        .load(glideImage)
//        .into(this)
}

fun getDrawable(@DrawableRes id: Int): Drawable {
    return try {
        VectorDrawableCompat.create(DemoApp.context.resources, id, null)!!
    } catch (e: Exception) {
        ContextCompat.getDrawable(DemoApp.context, id)!!
    }
}


fun showToast(msg: String?, duration: Int) {
    if (DemoApp.context != null)
        Toast.makeText(DemoApp.context, msg ?: "", duration).show()
}

fun showToast(msg: String?) {
    showToast(msg ?: "", Toast.LENGTH_SHORT)
}
