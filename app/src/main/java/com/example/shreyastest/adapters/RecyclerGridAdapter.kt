package com.example.shreyastest.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.shreyastest.adapters.RecyclerGridAdapter.GridViewHolder
import android.view.LayoutInflater
import androidx.lifecycle.MutableLiveData
import android.view.ViewGroup
import com.example.shreyastest.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import android.graphics.drawable.Drawable
import com.bumptech.glide.load.engine.GlideException
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import com.example.shreyastest.activity.ImageActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.request.target.Target
import com.example.shreyastest.beanclasses.Images
import java.util.stream.Collectors

class RecyclerGridAdapter(private val context: Context) : RecyclerView.Adapter<GridViewHolder>() {
    private val mInflater: LayoutInflater
    private var lstImages: MutableList<Images>? = null
    private var values = 0
    var loadingProgress = MutableLiveData<Int>()
    fun setLstImages(lstImages: List<Images>) {
        if (this.lstImages != null) this.lstImages!!.clear()

//        notifyDataSetChanged();
        values = 0
        loadingProgress.postValue(values)
        this.lstImages = lstImages.stream()
                .filter { s: Images -> s.type == "image/png" || s.type == "image/jpeg" }
                .collect(Collectors.toList())
        Log.d("List SIze", this.lstImages!!.size.toString() + "")
        notifyDataSetChanged()
    }

    fun getLstImages(): List<Images>? {
        return lstImages
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = mInflater.inflate(R.layout.image_layout, parent, false) // inflate the layout
        return GridViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridViewHolder, i: Int) {

//        icon.setImageResource(lstImages.get(i).getLink()); // set logo images


//        Log.d("Link", lstImages.get(i).getLink());
        val actualLink = lstImages!![i].link
        val Link1 = actualLink.substring(0, actualLink.length - 4)
        val Link2 = actualLink.substring(actualLink.length - 4)
        val finalLink = Link1 + "t" + Link2

//        Log.d("Link", finalLink);
        Glide.with(context)
                .load(finalLink)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any,
                                                 target: Target<Drawable?>,
                                                 dataSource: DataSource,
                                                 isFirstResource: Boolean): Boolean {
                        values++
                        loadingProgress.postValue(values)
                        return false
                    }
                })
                .into(holder.icon)
        holder.icon.scaleType = ImageView.ScaleType.FIT_CENTER
        holder.icon.setOnClickListener { v: View? ->
            val activity = context as Activity
            val options = ActivityOptions.makeSceneTransitionAnimation(activity,
                    holder.icon, "image1")
            val secondActivity = Intent(context, ImageActivity::class.java)
            val bundle = Bundle()
            bundle.putString("imagelink", actualLink)
            bundle.putString("imageId", lstImages!![i].id)
            secondActivity.putExtras(bundle)
            context.startActivity(secondActivity, options.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return if (lstImages != null) lstImages!!.size else 0
    }

    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView

        init {
            icon = itemView.findViewById(R.id.img1)
        }
    }

    init {
        mInflater = LayoutInflater.from(context)
    }
}