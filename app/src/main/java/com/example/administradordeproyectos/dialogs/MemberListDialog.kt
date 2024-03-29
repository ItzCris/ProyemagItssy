package com.example.administradordeproyectos.dialogs


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.administradordeproyectos.adapters.MemberListItemsAdapter
import com.example.administradordeproyectos.model.User
import com.example.administradordeproyectos.R
abstract class MembersListDialog(
    context: Context,
    private var list: ArrayList<User>,
    private val title: String = ""
) : Dialog(context) {

    private var adapter: MemberListItemsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())

        val view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null)

        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        setUpRecyclerView(view)
    }

    private fun setUpRecyclerView(view: View) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val rvList: RecyclerView = view.findViewById(R.id.rvList) // Corregir aquí

        tvTitle.text = title

        if (list.size > 0) {
            rvList.layoutManager = LinearLayoutManager(context)
            val adapter = MemberListItemsAdapter(context, list)
            rvList.adapter = adapter

            adapter.setOnClickListener(object : MemberListItemsAdapter.OnClickListener {
                override fun onClick(position: Int, user: User, action: String) {
                    dismiss()
                    onItemSelected(user, action)
                }
            })
        }
    }


    protected abstract fun onItemSelected(user: User, action:String)
}