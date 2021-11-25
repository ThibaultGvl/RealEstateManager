package com.openclassrooms.realestatemanager.ui.property

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.OnItemClickListener
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Property

class ListPropertyAdapter(private val listPropertys: List<Property>,
                          private var listener: OnItemClickListener):
        RecyclerView.Adapter<ListPropertyAdapter.PropertyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val fragmentPropertyBinding = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_property, parent, false)
        return PropertyViewHolder(fragmentPropertyBinding)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = listPropertys[position]
        holder.updateWithProperty(property)
        holder.itemView.setOnClickListener{ property.id.let { it1 -> listener.onItemClick(it1) } }
    }

    override fun getItemCount(): Int {
        return listPropertys.size
    }

    class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mPicture: ImageView = itemView.findViewById(R.id.image)
        private val mType: TextView = itemView.findViewById(R.id.type)
        private val mPlace: TextView = itemView.findViewById(R.id.place)
        private val mPrice: TextView = itemView.findViewById(R.id.price)

        fun updateWithProperty(property: Property) {
            val uri = property.photos[0].subSequence(2, property.photos[0].length -2)
            val photo = Uri.parse(uri as String?)
            Glide.with(mPicture).load(photo).into(mPicture)
            mPlace.text = property.address
            mPrice.text = property.price.toString()
            mType.text = property.type
        }
    }
}