package com.example.weg.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weg.MainActivity
import com.example.weg.ProfData
import com.example.weg.R
import com.example.weg.data.HomeDataSource
import com.example.weg.data.Result
import com.example.weg.databinding.FragmentHomeBinding
import com.example.weg.databinding.FragmentHomeMainBinding
import com.example.weg.ui.sideSheet.GroupRecyclerItem


class HomeMainFragment : Fragment(), HomeMemberRecyclerAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: HomeMemberRecyclerAdapter;
    private var _binding: FragmentHomeMainBinding? = null

    private val homeDataSource = HomeDataSource();
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    // member data lists
    private lateinit var groupName : String;
    private lateinit var groupInfo : String;
    private lateinit var groupImage : Drawable;
    var groupMemList : ArrayList<ProfData> = ArrayList<ProfData>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeMainBinding.inflate(inflater, container, false)

        val root: View = binding.root;

        val mainActivity = activity as MainActivity;
        onGroupChanged(mainActivity.getCurrentGroup());
        val toolbar = mainActivity.findViewById<Toolbar>(R.id.toolbar)
        val menu = toolbar?.menu;
        val item = menu?.findItem(R.id.add_action)

        if (item != null) {
            item.isVisible = false;
        }

        return root
    }

    override fun onItemClick(position: Int) {
        // TODO("Not yet implemented")
    }

    fun onGroupChanged(newGroupName : String?){
        if(newGroupName == null){
            binding.groupName.text = "No data";
        }else{
            Toast.makeText(activity, "Group Change : " + newGroupName, Toast.LENGTH_SHORT).show()
            binding.groupName.text = newGroupName;
            homeDataSource.getGroupInfo(newGroupName){
                activity?.runOnUiThread {
                    if(it is Result.Success){
                        binding.groupInfo.text = it.data;
                        Log.d("HomeMainFragment", "This is groupInfo : " + it.data);
                    }
                }
            }
            homeDataSource.getGroupMem(newGroupName) { result ->
                activity?.runOnUiThread {
                    if (result is Result.Success) {
                        groupMemList.clear()
                        val mainActivity = activity as MainActivity;
                        for (item in result.data) {
                            Log.d("HomeMainFragment", "This is groupMember : " + item);
                            homeDataSource.getUserNickName(item, mainActivity.getCurrentGroup()) {
                                activity?.runOnUiThread {
                                    if (it is Result.Success) {
                                        homeDataSource.getUserDetail(it.data, item, mainActivity.getCurrentGroup()) {
                                            activity?.runOnUiThread {
                                                if (it is Result.Success) {
                                                    groupMemList.add(it.data);
                                                    updateMemListView()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
    }

    private fun updateMemListView() {

        binding.groupRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
        recyclerView = binding.groupRecycler;
        adapter = HomeMemberRecyclerAdapter(groupMemList, this);
        Log.d("TAG", "this is the size of groupMemList : " + adapter.getItemCount());
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter;
    }

}