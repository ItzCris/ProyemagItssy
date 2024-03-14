package com.example.administradordeproyectos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.administradordeproyectos.R
import com.example.administradordeproyectos.model.User
import com.example.administradordeproyectos.utils.Constants
import de.hdodenhof.circleimageview.CircleImageView

open class MemberListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<User>
) : RecyclerView.Adapter<MemberListItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_member,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, user: User, action: String)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvMemberName: TextView = view.findViewById(R.id.tv_member_name)
        private val ivMemberImage: CircleImageView = view.findViewById(R.id.iv_member_image)
        private val tvMemberEmail: TextView = view.findViewById(R.id.tv_member_email)
        private val ivSelectedMember: CircleImageView = view.findViewById(R.id.iv_selected_member)

        fun bind(user: User) {
            Glide.with(context)
                .load(user.image)
                .centerCrop()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(ivMemberImage)

            tvMemberName.text = user.name
            tvMemberEmail.text = user.email

            if (user.selected) {
                ivSelectedMember.visibility = View.VISIBLE
            } else {
                ivSelectedMember.visibility = View.GONE
            }

            itemView.setOnClickListener {
                if (onClickListener != null) {
                    val action = if (user.selected) Constants.UN_SELECT else Constants.SELECT
                    onClickListener!!.onClick(adapterPosition, user, action)
                }
            }
        }

    }
}
