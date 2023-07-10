package com.example.weg.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weg.ProfData
import com.example.weg.ProfileData
import com.example.weg.R

class HomeMemberRecyclerAdapter(private val memberList: ArrayList<ProfData>, private var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<HomeMemberRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeMemberRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_member_recycler_item, parent, false)
        return ViewHolder(view)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onBindViewHolder(holder: HomeMemberRecyclerAdapter.ViewHolder, position: Int) {
        val item = memberList[position]
        // it는 클릭된 view를 가르키는 변수
        val listener = View.OnClickListener {
            onItemClickListener.onItemClick(position);
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }
    }

    override fun getItemCount(): Int {
        return memberList.size;
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private val memberIconView: ImageView = v.findViewById(R.id.member_icon);
        private val memberNameView: TextView = v.findViewById(R.id.member_name);
        private val memberIntroView: TextView = v.findViewById(R.id.member_intro);
        fun bind(listener: View.OnClickListener, item: ProfData) {
            if(item.getImage() == null){
                memberIconView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.user_icon)!!)
            }else{
                memberIconView.setImageDrawable(item.getImage())
            }
            memberNameView.text = item.getName();
            memberIntroView.text = item.getIntro();
            view.setOnClickListener(listener);
        }
    }
}