package com.example.weg.ui.sideSheet

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weg.R
import com.example.weg.databinding.FragmentGroupListBinding

class GroupListFragment : Fragment(){
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GroupRecyclerAdapter
    private var _binding: FragmentGroupListBinding? = null
    val mList = ArrayList<GroupRecyclerItem>()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupListBinding.inflate(inflater, container, false);

        val view = binding.root;
        val drawable : Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.user_icon)

        Log.d("TAG", "Create Group list fragment");
        mList.add(GroupRecyclerItem("Section 1", drawable));
        mList.add(GroupRecyclerItem("Section 2", drawable));
        mList.add(GroupRecyclerItem("Section 3", drawable));
        mList.add(GroupRecyclerItem("Section 4", drawable));
        mList.add(GroupRecyclerItem("Section 5", drawable));
        mList.add(GroupRecyclerItem("Section 6", drawable));
        mList.add(GroupRecyclerItem("Section 7", drawable));
        mList.add(GroupRecyclerItem("Section 8", drawable));
        mList.add(GroupRecyclerItem("Section 9", drawable));
        mList.add(GroupRecyclerItem("Section 10", drawable));

        recyclerView = binding.groupRecycler;
        adapter = GroupRecyclerAdapter(mList);
        Log.d("TAG", "this is the size of mlist : " + adapter.getItemCount());
        recyclerView.layoutManager = LinearLayoutManager(context)
        //  inflater.inflate(R.layout.fragment_group_list, container, false)
        // adapter 설정은 항상 위 코드 이후에 수행 되어야하며 따라서 위에서 binding.root를 함
        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

        recyclerView.adapter = adapter;

        // Inflate the layout for this fragment
        return view
    }
}