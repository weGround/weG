package com.example.weg.ui.sideSheet

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weg.MainActivity
import com.example.weg.R
import com.example.weg.data.Result
import com.example.weg.data.groupDataSource
import com.example.weg.databinding.FragmentGroupListBinding

class GroupListFragment : Fragment(), GroupRecyclerAdapter.OnItemClickListener{
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GroupRecyclerAdapter
    private var _binding: FragmentGroupListBinding? = null
    var mList = ArrayList<GroupRecyclerItem>()

    private val groupDataSource = groupDataSource();

    private lateinit var currentGroup : GroupRecyclerItem;
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
        binding.groupRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        currentGroup = mList[0];
        updateAdapter();

        // Register Button clilck event
        binding.addGroup.setOnClickListener{
            Toast.makeText(activity, "Button Clicked!", Toast.LENGTH_SHORT).show()
            val dialogFragment = GroupDialogFragment()
            dialogFragment.show(childFragmentManager, "Group Add Dialog")
        }

        binding.makeGroup.setOnClickListener{
            Toast.makeText(activity, "Button Clicked!", Toast.LENGTH_SHORT).show()
            val dialogFragment = GroupMakeDialogFragment()
            dialogFragment.show(childFragmentManager, "Group Make Dialog")
        }
        return view
    }

    private fun updateGroupList(groupList: ArrayList<GroupRecyclerItem>) {
        mList.clear();
        mList = groupList;
        updateAdapter();
    }

    private fun updateAdapter() {
        recyclerView = binding.groupRecycler;
        adapter = GroupRecyclerAdapter(mList, this);
        Log.d("TAG", "this is the size of mlist : " + adapter.getItemCount());
        recyclerView.layoutManager = LinearLayoutManager(context)
        //  inflater.inflate(R.layout.fragment_group_list, container, false)
        // adapter 설정은 항상 위 코드 이후에 수행 되어야하며 따라서 위에서 binding.root를 함
//        recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter;
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(activity, mList.get(position).getGroupName() + " is Selected", Toast.LENGTH_SHORT).show();
        Toast.makeText(activity, "Move to " + mList.get(position).getGroupName(), Toast.LENGTH_SHORT).show();
        currentGroup = mList[position];
        Log.d("list", "This is current group : " + currentGroup.getGroupName());

        mList.removeAt(position);
        mList.add(0, currentGroup);
        mList.subList(1, mList.size).sortWith(compareBy { it.getGroupName() });

        moveGroup(currentGroup.getGroupName());
        updateAdapter();
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }

    fun initGroupList(){
        groupDataSource.getGroupList {
            if(it is Result.Success){
                for(item in it.data){
                    val drawable : Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.ic_macbook);
                    mList.add(GroupRecyclerItem(item, drawable));
                }
            }
        }
    }

    fun addGroup(newGroupName: String) {
        groupDataSource.addGroup(newGroupName){
            // todo : add group callback
        }
    }

    fun makeGroup(newGroupName: String) {
        groupDataSource.makeGroup(newGroupName){
            // todo : add group callback
        }
    }

    fun moveGroup(newGroupName: String){
        val mainActivity = activity as MainActivity;
        mainActivity.onGroupChanged(newGroupName);
    }
}