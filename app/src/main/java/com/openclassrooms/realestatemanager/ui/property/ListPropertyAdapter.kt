package com.openclassrooms.realestatemanager.ui.property

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.OnItemClickListener
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Property

class ListPropertyAdapter(private val listProperties: List<Property>,
                          private var listener: OnItemClickListener):
        RecyclerView.Adapter<ListPropertyAdapter.PropertyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val fragmentPropertyBinding = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_property, parent, false)
        return PropertyViewHolder(fragmentPropertyBinding)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = listProperties[position]
        holder.updateWithProperty(property)
        holder.itemView.setOnClickListener{ property.id.let { it1 -> listener.onItemClick(it1) } }
    }

    override fun getItemCount(): Int {
        return listProperties.size
    }

    class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mPicture: ImageView = itemView.findViewById(R.id.image)
        private val mType: TextView = itemView.findViewById(R.id.type)
        private val mPlace: TextView = itemView.findViewById(R.id.place)
        private val mPrice: TextView = itemView.findViewById(R.id.price)

        fun updateWithProperty(property: Property) {
            val photos = Property().getPhotos(property.photos)
            if (photos.isNotEmpty()) {
                Glide.with(mPicture).load(photos[0]).circleCrop().into(mPicture)
            }
            else {
                Glide.with(mPicture).load(R.drawable.ic_menu_gallery).into(mPicture)
            }
            val price = property.price.toString() + "$"
            mPrice.text = price
            mPlace.text = property.address
            mType.text = property.type
        }
    }
}