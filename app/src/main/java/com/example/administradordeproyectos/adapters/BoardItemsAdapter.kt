package com.example.administradordeproyectos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.administradordeproyectos.R
import com.example.administradordeproyectos.databinding.DialogProgressBinding
import com.example.administradordeproyectos.model.Board
import com.google.android.material.circularreveal.CircularRevealWidget.CircularRevealScrimColorProperty
import de.hdodenhof.circleimageview.CircleImageView

open class BoardItemsAdapter(
    private val context: Context,
    private var list: ArrayList<Board>
) : RecyclerView.Adapter<BoardItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_board,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        // Aquí se accede a los elementos dentro de MyViewHolder
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: Board)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Se accede a los elementos del layout item_board.xml aquí
        private val ivBoardImage: CircleImageView = itemView.findViewById(R.id.iv_board_image)
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvCreatedBy: TextView = itemView.findViewById(R.id.tv_created_by)

        fun bind(model: Board) {
            // Se establecen los valores en los elementos de la vista aquí
            Glide.with(context)
                .load(model.image)
                .centerCrop()
                .placeholder(R.drawable.ic_board_place_holder)
                .into(ivBoardImage)

            tvName.text = model.name
            tvCreatedBy.text = "Created By: ${model.createdBy}"

            // Se establece el OnClickListener aquí
            itemView.setOnClickListener {
                onClickListener?.onClick(adapterPosition, model)
            }
        }
    }
}
