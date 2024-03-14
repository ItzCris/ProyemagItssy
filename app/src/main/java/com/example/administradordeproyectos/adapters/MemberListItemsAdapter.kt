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

        // Acceso a los elementos dentro de MyViewHolder
        holder.bind(model)

        holder.itemView.setOnClickListener {
            val action = if (model.selected) Constants.UN_SELECT else Constants.SELECT
            onClickListener?.onClick(position, model, action)
        }
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

        private val tv_member_name : TextView = itemView.findViewById(R.id.tv_member_name)
        private val iv_member_image : CircleImageView = itemView.findViewById(R.id.iv_member_image)
        private val tv_member_email : TextView = itemView.findViewById(R.id.tv_member_email)
        private val iv_selected_member : CircleImageView = itemView.findViewById(R.id.iv_selected_member)
        fun bind(user: User) {
            Glide
                .with(context)
                .load(user.image)
                .centerCrop()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(iv_member_image)

            tv_member_name.text = user.name
            tv_member_email.text = user.email

            iv_selected_member.visibility = if (user.selected) View.VISIBLE else View.GONE
        }
    }
}
