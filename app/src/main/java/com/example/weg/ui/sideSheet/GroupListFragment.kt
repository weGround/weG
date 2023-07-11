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

        recyclerView.adapter = adapter;
    }

    override fun onItemClick(position: Int) {
//        Toast.makeText(activity, mList.get(position).getGroupName() + " is Selected", Toast.LENGTH_SHORT).show();
//        Toast.makeText(activity, "Move to " + mList.get(position).getGroupName(), Toast.LENGTH_SHORT).show();
//        Log.d("list", "This is current group : " + mList[position].getGroupName());
        sortWithCurrentGroup(position);
        moveGroup(mList[0].getGroupName());
        updateAdapter();
    }

    private fun sortWithCurrentGroup(position:Int){
        val currentGroup = mList[position];
        mList.removeAt(position);
        mList.add(0, currentGroup);
        mList.subList(1, mList.size).sortWith(compareBy { it.getGroupName() });
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null;
    }

    // server로 부터 Group List를 가져와서 idx의 group으로 이동함을 가정하고 나머지를 나열한다.
    fun initGroupList(currentGroupIndex : Int){
        groupDataSource.getGroupList {
            if(it is Result.Success){
                mList.clear();
                for(item in it.data){
                    val drawable : Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.ic_macbook);
                    mList.add(GroupRecyclerItem(item, drawable));
                }
                sortWithCurrentGroup(currentGroupIndex);
                updateAdapter();
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

    fun getGroupNameByIndex(groupIndex:Int) : String?{
        if(mList.size <= groupIndex) {
            return null;
        }
        return mList[groupIndex].getGroupName();
    }
}