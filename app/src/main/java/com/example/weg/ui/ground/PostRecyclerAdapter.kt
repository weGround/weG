package com.example.weg.ui.ground

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weg.R


class PostRecyclerAdapter(private val currentUser: String?,private val postList: ArrayList<PostRecyclerItem>, private var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<PostRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_recycler_item, parent, false)
        return ViewHolder(currentUser, view)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onLikeClick(position: Int, postLikeView: PostRecyclerItem, postLikeView1: ImageButton, postLikeNumView : TextView)
    }

    override fun onBindViewHolder(holder: PostRecyclerAdapter.ViewHolder, position: Int) {
        val item = postList[position]
        // it는 클릭된 view를 가르키는 변수
        val listener = View.OnClickListener {
            onItemClickListener.onItemClick(position);
        }
        holder.apply {
            bind(listener, item)
            itemView.tag = item
            postLikeView.setOnClickListener{
                onItemClickListener.onLikeClick(position, item, postLikeView, postLikesNumView);
            }
        }
    }

    override fun getItemCount(): Int {
        return postList.size;
    }

    class ViewHolder(private val currentUser: String?,v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private val postWriterView: TextView = v.findViewById(R.id.post_writer);
        private val postTitleView: TextView = v.findViewById(R.id.post_title);
        private val postDetailView: TextView = v.findViewById(R.id.post_detail);
        val postLikeView: ImageButton = v.findViewById(R.id.like);
        val postLikesNumView: TextView = v.findViewById(R.id.post_likes_num);
        fun bind(listener: View.OnClickListener, item: PostRecyclerItem) {
            postWriterView.text = item.getPostWriterStr();
            postTitleView.text = item.getPostTitleStr();
            postDetailView.text = item.getPostDetailStr();
            postLikesNumView.text = item.getLikeUsersList().size.toString();
            if(currentUser != null){
                if(item.isLiked(currentUser)){
                    postLikeView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_like_fill)!!);
                }else{
                    postLikeView.setImageDrawable(ContextCompat.getDrawable(view.context, R.drawable.ic_like_outlined)!!);
                }
            }
            view.setOnClickListener(listener);
        }
    }
}