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

class PhotosAdapter(private val photos: List<String>) : RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>(){

    private val mPhotos = ArrayList<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val detailsFragmentDetailsBinding = LayoutInflater.from(parent.context)
                .inflate(R.layout.photo_item, parent, false)
        return PhotoViewHolder(detailsFragmentDetailsBinding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        for (photo in photos) {
            val uri = Uri.parse(photo)
            mPhotos.add(uri)
        }
        val photosPosition = mPhotos[position]
        holder.updateWithPhoto(photosPosition)
    }

    override fun getItemCount(): Int {
        return mPhotos.size
    }

    class PhotoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val mImage: ImageView = itemView.findViewById(R.id.photo)

        fun updateWithPhoto(uri: Uri) {
            Glide.with(mImage).load(uri).into(mImage)
        }
    }
}