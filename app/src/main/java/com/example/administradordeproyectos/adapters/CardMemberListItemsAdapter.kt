package com.example.administradordeproyectos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.administradordeproyectos.R
import com.example.administradordeproyectos.model.SelectedMembers
import de.hdodenhof.circleimageview.CircleImageView

open class CardMemberListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<SelectedMembers>,
    private val assignMembers: Boolean
) : RecyclerView.Adapter<CardMemberListItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_card_selected_member,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        // Acceso a los elementos dentro de MyViewHolder
        holder.bind(model, position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val iv_add_member: CircleImageView = itemView.findViewById(R.id.iv_add_member)
        private val iv_selected_member_image : CircleImageView = itemView.findViewById(R.id.iv_selected_member_image)

        fun bind(model: SelectedMembers, position: Int) {
            if (position == list.size - 1 && assignMembers) {
                iv_add_member.visibility = View.VISIBLE
                iv_selected_member_image.visibility = View.GONE
            } else {
                iv_add_member.visibility = View.GONE
                iv_selected_member_image.visibility = View.VISIBLE

                Glide
                    .with(context)
                    .load(model.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_user_place_holder)
                    .into(iv_selected_member_image)
            }

            itemView.setOnClickListener {
                onClickListener?.onClick()
            }
            if (position == list.size - 1 && assignMembers) {
                iv_add_member.visibility = View.VISIBLE
                iv_selected_member_image.visibility = View.GONE
            } else {
                iv_add_member.visibility = View.GONE
                iv_selected_member_image.visibility = View.VISIBLE

                Glide
                    .with(context)
                    .load(model.image)
                    .centerCrop()
                    .placeholder(R.drawable.ic_user_place_holder)
                    .into(iv_selected_member_image)
            }

            itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick()
                }
            }
        }
    }
}
