package com.example.administradordeproyectos.adapters

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.administradordeproyectos.R
import com.example.administradordeproyectos.activities.TaskListActivity
import com.example.administradordeproyectos.model.Task
import com.example.administradordeproyectos.utils.Constants
import java.util.*

open class TaskListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<Task>
) : RecyclerView.Adapter<TaskListItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null
    private var mPositionDraggedFrom = -1
    private var mPositionDraggedTo = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        val layoutParams = LinearLayout.LayoutParams(
            (parent.width * 0.7).toInt(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins((15.toDp()).toPx(), 0, (40.toDp()).toPx(), 0)
        view.layoutParams = layoutParams
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

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
        fun onClick(position: Int, task: Task, action: String)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv_add_task_list : TextView = itemView.findViewById(R.id.tv_add_task_list)
        private val tv_task_list_title : TextView = itemView.findViewById(R.id.tv_task_list_title)
        private val ll_task_item : LinearLayout = itemView.findViewById(R.id.ll_task_item)
        private val cv_add_task_list_name : CardView = itemView.findViewById(R.id.cv_add_task_list_name)
        private val rv_card_list : RecyclerView = itemView.findViewById(R.id.rv_card_list)
        fun bind(task: Task) {
            if (adapterPosition == list.size - 1) {
                tv_add_task_list.visibility = View.VISIBLE
                ll_task_item.visibility = View.GONE
            } else {
                tv_add_task_list.visibility = View.GONE
                ll_task_item.visibility = View.VISIBLE
            }

            tv_task_list_title.text = task.title

            tv_add_task_list.setOnClickListener {
                tv_add_task_list.visibility = View.GONE
                cv_add_task_list_name.visibility = View.VISIBLE
            }

            // Resto del código de enlace de datos aquí...

            val adapter = CardListItemsAdapter(context, task.cards)
            rv_card_list.adapter = adapter

            adapter.setOnClickListener(object : CardListItemsAdapter.OnClickListener {
                override fun onClick(cardPosition: Int) {
                    if (context is TaskListActivity) {
                        context.cardDetails(adapterPosition, cardPosition)
                    }
                }
            })

            // Resto del código de enlace de datos aquí...

            val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            rv_card_list.addItemDecoration(dividerItemDecoration)

            val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    dragged: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val draggedPosition = dragged.adapterPosition
                    val targetPosition = target.adapterPosition

                    if (mPositionDraggedFrom == -1) {
                        mPositionDraggedFrom = draggedPosition
                    }
                    mPositionDraggedTo = targetPosition

                    Collections.swap(list[adapterPosition].cards, draggedPosition, targetPosition)
                    adapter.notifyItemMoved(draggedPosition, targetPosition)

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    // Método onSwiped sin implementación ya que no se necesita para este caso
                }

                override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                    super.clearView(recyclerView, viewHolder)

                    if (mPositionDraggedFrom != -1 && mPositionDraggedTo != -1 && mPositionDraggedFrom != mPositionDraggedTo) {
                        (context as TaskListActivity).updateCardsInTaskList(adapterPosition, list[adapterPosition].cards)
                    }

                    mPositionDraggedFrom = -1
                    mPositionDraggedTo = -1
                }
            })

            helper.attachToRecyclerView(rv_card_list)
        }
    }


    private fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}
