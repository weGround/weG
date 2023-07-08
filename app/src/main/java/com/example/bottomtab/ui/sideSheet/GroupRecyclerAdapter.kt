package com.example.bottomtab.ui.sideSheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomtab.R

class GroupRecyclerAdapter(private val groupList: List<GroupRecyclerItem>) : RecyclerView.Adapter<GroupRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroupRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_recycler_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: GroupRecyclerAdapter.ViewHolder, position: Int) {
        val item = groupList[position]
        // it는 클릭된 view를 가르키는 변수
        val listener = View.OnClickListener {it ->
            Toast.makeText(it.context, "Clicked: ${item.getGroupName()}", Toast.LENGTH_SHORT).show()
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun getItemCount(): Int {
        return groupList.size;
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        val textView: TextView = v.findViewById(R.id.group_name)

        fun bind(listener: View.OnClickListener, item: GroupRecyclerItem) {
            textView.text = item.getGroupName();
            view.setOnClickListener(listener)
        }
    }
}