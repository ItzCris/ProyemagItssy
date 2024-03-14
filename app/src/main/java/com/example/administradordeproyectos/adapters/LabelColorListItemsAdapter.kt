package com.example.administradordeproyectos.adapters


import android.content.Context
import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.administradordeproyectos.R
import java.util.*
class LabelColorListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<String>,
    private val mSelectedColor: String
) : RecyclerView.Adapter<LabelColorListItemsAdapter.MyViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_label_color,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]

        // Acceso a los elementos dentro de MyViewHolder
        holder.bind(item, mSelectedColor)

        holder.itemView.setOnClickListener {
            onItemClickListener?.onClick(position, item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val view_main : View = itemView.findViewById(R.id.view_main)
        private val iv_selected_color : ImageView = itemView.findViewById(R.id.iv_selected_color)

        fun bind(color: String, selectedColor: String) {
            view_main.setBackgroundColor(Color.parseColor(color))

            if (color == selectedColor) {
                iv_selected_color.visibility = View.VISIBLE
            } else {
                iv_selected_color.visibility = View.GONE
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(position: Int, color: String)
    }
}
