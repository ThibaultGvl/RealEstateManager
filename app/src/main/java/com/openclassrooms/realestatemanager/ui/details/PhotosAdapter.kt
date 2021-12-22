package com.openclassrooms.realestatemanager.ui.details

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Adapter
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.R

class PhotosAdapter(private val photos: ArrayList<Uri>) : RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val detailsFragmentDetailsBinding = LayoutInflater.from(parent.context)
                .inflate(R.layout.photo_item, parent, false)
        return PhotoViewHolder(detailsFragmentDetailsBinding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.updateWithPhoto(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    class PhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val mImage: ImageView = itemView.findViewById(R.id.photo)

        fun updateWithPhoto(uri: Uri) {
            Glide.with(mImage).load(uri).into(mImage)
        }
    }
}