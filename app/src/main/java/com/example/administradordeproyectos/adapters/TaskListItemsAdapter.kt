package com.example.administradordeproyectos.adapters

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
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
import com.example.administradordeproyectos.activities.TaskListActivity
import com.example.administradordeproyectos.model.Task
import com.example.administradordeproyectos.R
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

        holder.bind(model) // Pasar el objeto `model` al método `bind` de `MyViewHolder`

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
        val tv_add_task_list: TextView = itemView.findViewById(R.id.tv_add_task_list)
        val tv_task_list_title: TextView = itemView.findViewById(R.id.tv_task_list_title)
        val ll_task_item: LinearLayout = itemView.findViewById(R.id.ll_task_item)
        val cv_add_task_list_name: CardView = itemView.findViewById(R.id.cv_add_task_list_name)
        val rv_card_list: RecyclerView = itemView.findViewById(R.id.rv_card_list)
        val ib_done_list_name: ImageButton = itemView.findViewById(R.id.ib_done_list_name)
        val et_task_list_name: EditText = itemView.findViewById(R.id.et_task_list_name)
        val ib_delete_list: ImageButton = itemView.findViewById(R.id.ib_delete_list)
        val tv_add_card: TextView = itemView.findViewById(R.id.tv_add_card)
        val cv_add_card: CardView = itemView.findViewById(R.id.cv_add_card)
        val ib_close_card_name: ImageButton = itemView.findViewById(R.id.ib_close_card_name)
        val ib_done_card_name: ImageButton = itemView.findViewById(R.id.ib_done_card_name)
        val et_card_name: EditText = itemView.findViewById(R.id.et_card_name)
        val ib_edit_list_name: ImageButton = itemView.findViewById(R.id.ib_edit_list_name)
        val et_edit_task_list_name: EditText = itemView.findViewById(R.id.et_edit_task_list_name)
        val ll_title_view: LinearLayout = itemView.findViewById(R.id.ll_title_view)
        val cv_edit_task_list_name: CardView = itemView.findViewById(R.id.cv_edit_task_list_name)
        val ib_close_editable_view: ImageButton = itemView.findViewById(R.id.ib_close_editable_view)
        val ib_done_edit_list_name: ImageButton = itemView.findViewById(R.id.ib_done_edit_list_name)





        fun bind(model: Task) {
            tv_task_list_title.text = model.title

            if (adapterPosition == list.size - 1) {
                tv_add_task_list.visibility = View.VISIBLE
                ll_task_item.visibility = View.GONE
            } else {
                tv_add_task_list.visibility = View.GONE
                ll_task_item.visibility = View.VISIBLE
            }

            tv_add_task_list.setOnClickListener {
                tv_add_task_list.visibility = View.GONE
                cv_add_task_list_name.visibility = View.VISIBLE
            }

            ib_done_list_name.setOnClickListener {
                val listName = et_task_list_name.text.toString()

                if (listName.isNotEmpty()) {
                    // Here we check the context is an instance of the TaskListActivity.
                    if (context is TaskListActivity) {
                        context.createTaskList(listName)
                    }
                } else {
                    Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show()
                }
            }

            ib_delete_list.setOnClickListener {
                alertDialogForDeleteList(adapterPosition, model.title)
            }
            ib_edit_list_name.setOnClickListener {

                et_edit_task_list_name.setText(model.title) // Set the existing title
                ll_title_view.visibility = View.GONE
                cv_edit_task_list_name.visibility = View.VISIBLE
            }

            ib_close_editable_view.setOnClickListener {
                ll_title_view.visibility = View.VISIBLE
                cv_edit_task_list_name.visibility = View.GONE
            }

            ib_done_edit_list_name.setOnClickListener {
                val listName = et_edit_task_list_name.text.toString()

                if (listName.isNotEmpty()) {
                    if (context is TaskListActivity) {
                        context.updateTaskList(position, listName, model)
                    }
                } else {
                    Toast.makeText(context, "Please Enter List Name.", Toast.LENGTH_SHORT).show()
                }
            }

            tv_add_card.setOnClickListener {
                tv_add_card.visibility = View.GONE
                cv_add_card.visibility = View.VISIBLE

                ib_close_card_name.setOnClickListener {
                    tv_add_card.visibility = View.VISIBLE
                    cv_add_card.visibility = View.GONE
                    cv_add_card.visibility = View.GONE
                }

                ib_done_card_name.setOnClickListener {
                    val cardName = et_card_name.text.toString()

                    if (cardName.isNotEmpty()) {
                        if (context is TaskListActivity) {
                            context.addCardToTaskList(adapterPosition, cardName)
                        }
                    } else {
                        Toast.makeText(context, "Please Enter Card Detail.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                rv_card_list.layoutManager = LinearLayoutManager(context)
                rv_card_list.setHasFixedSize(true)

                val adapter =
                    CardListItemsAdapter(context, model.cards)
                rv_card_list.adapter = adapter

                adapter.setOnClickListener(object :
                    CardListItemsAdapter.OnClickListener {
                    override fun onClick(cardPosition: Int) {
                        if (context is TaskListActivity) {
                            context.cardDetails(position, cardPosition)
                        }
                    }
                })


                // Resto del código de enlace de datos aquí...
            }

        }

        private fun alertDialogForDeleteList(position: Int, title: String) {
            val builder = AlertDialog.Builder(context)
            //set title for alert dialog
            builder.setTitle("Alert")
            //set message for alert dialog
            builder.setMessage("Are you sure you want to delete $title.")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            //performing positive action
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                dialogInterface.dismiss() // Dialog will be dismissed

                if (context is TaskListActivity) {
                    context.deleteTaskList(position)
                }
            }

            //performing negative action
            builder.setNegativeButton("No") { dialogInterface, which ->
                dialogInterface.dismiss() // Dialog will be dismissed
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
            alertDialog.show()  // show the dialog to UI
        }


    }
    private fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}