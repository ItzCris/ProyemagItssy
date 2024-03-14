package com.example.administradordeproyectos.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.administradordeproyectos.R
import com.example.administradordeproyectos.activities.TaskListActivity
import com.example.administradordeproyectos.model.Card
import com.example.administradordeproyectos.model.SelectedMembers
import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label

open class CardListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<Card>
) : RecyclerView.Adapter<CardListItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        // Acceso a los elementos dentro de MyViewHolder
        holder.bind(model)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(cardPosition: Int)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Acceso a los elementos de la vista aquí
        private val tvCardName: TextView = itemView.findViewById(R.id.tv_card_name)
        private val viewLabelColor: View = itemView.findViewById(R.id.view_label_color)
        private val rvCardSelectedMembersList: RecyclerView = itemView.findViewById(R.id.rv_card_selected_members_list)

        fun bind(model: Card) {
            if (model.labelColor.isNotEmpty()) {
                viewLabelColor.visibility = View.VISIBLE
                viewLabelColor.setBackgroundColor(Color.parseColor(model.labelColor))
            } else {
                viewLabelColor.visibility = View.GONE
            }

            tvCardName.text = model.name

            if ((context as TaskListActivity).mAssignedMembersDetailList.size > 0) {
                val selectedMembersList: ArrayList<SelectedMembers> = ArrayList()
                for (i in context.mAssignedMembersDetailList.indices) {
                    for (j in model.assignedTo) {
                        if (context.mAssignedMembersDetailList[i].id == j) {
                            val selectedMember = SelectedMembers(
                                context.mAssignedMembersDetailList[i].id,
                                context.mAssignedMembersDetailList[i].image
                            )

                            selectedMembersList.add(selectedMember)
                        }
                    }
                }

                if (selectedMembersList.size > 0) {
                    if (selectedMembersList.size == 1 && selectedMembersList[0].id == model.createdBy) {
                        rvCardSelectedMembersList.visibility = View.GONE
                    } else {
                        rvCardSelectedMembersList.visibility = View.VISIBLE
                        rvCardSelectedMembersList.layoutManager =
                            GridLayoutManager(context, 4)
                        val adapter = CardMemberListItemsAdapter(context, selectedMembersList, false)
                        rvCardSelectedMembersList.adapter = adapter
                        adapter.setOnClickListener(object :
                            CardMemberListItemsAdapter.OnClickListener {
                            override fun onClick() {
                                onClickListener?.onClick(adapterPosition)
                            }
                        })
                    }
                } else {
                    rvCardSelectedMembersList.visibility = View.GONE
                }
            }

            itemView.setOnClickListener {
                onClickListener?.onClick(adapterPosition)
            }
        }
    }

}
