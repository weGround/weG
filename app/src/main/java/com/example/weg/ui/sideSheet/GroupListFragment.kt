package com.example.weg.ui.sideSheet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        Log.d("TAG", "Create Group list fragment");
        mList.add(GroupRecyclerItem("first"));
        mList.add(GroupRecyclerItem("second"));
        mList.add(GroupRecyclerItem("first"));
        mList.add(GroupRecyclerItem("second"));
        mList.add(GroupRecyclerItem("first"));
        mList.add(GroupRecyclerItem("second"));

        recyclerView = binding.groupRecycler;
        adapter = GroupRecyclerAdapter(mList);
        Log.d("TAG", "this is the size of mlist : " + adapter.getItemCount());
        recyclerView.layoutManager = LinearLayoutManager(context)
        //  inflater.inflate(R.layout.fragment_group_list, container, false)
        // adapter 설정은 항상 위 코드 이후에 수행 되어야하며 따라서 위에서 binding.root를 함
        recyclerView.adapter = adapter;

        // Inflate the layout for this fragment
        return view
    }
}