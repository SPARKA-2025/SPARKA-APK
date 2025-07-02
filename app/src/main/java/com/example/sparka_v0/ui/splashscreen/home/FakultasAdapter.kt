package com.example.sparka_v0.ui.splashscreen.home

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sparka_v0.R
import com.example.sparka_v0.data.retrofit.response.Fakultas

class FakultasAdapter(
    private val onItemClick: (Fakultas) -> Unit
) : ListAdapter<Fakultas, FakultasAdapter.FakultasViewHolder>(DIFF_CALLBACK) {

    inner class FakultasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNama: TextView = itemView.findViewById(R.id.tvNamaFakultas)
        private val ivImage: ImageView = itemView.findViewById(R.id.ivImageFakultas)
        private val tvDeskripsi: TextView = itemView.findViewById(R.id.tvDeskripsiFakultas)

        fun bind(item: Fakultas) {
            tvNama.text = item.nama
            tvDeskripsi.text = item.deskripsi

            try {
                val imageBytes = Base64.decode(item.image, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                ivImage.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                ivImage.setImageResource(R.drawable.img_8)
            }

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FakultasViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_home, parent, false)
        return FakultasViewHolder(view)
    }

    override fun onBindViewHolder(holder: FakultasViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Fakultas>() {
            override fun areItemsTheSame(oldItem: Fakultas, newItem: Fakultas): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Fakultas, newItem: Fakultas): Boolean {
                return oldItem == newItem
            }
        }
    }
}
