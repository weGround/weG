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
import com.example.weg.databinding.FragmentHomeBinding
import com.example.weg.databinding.FragmentHomeMainBinding
import com.example.weg.ui.sideSheet.GroupRecyclerItem


class HomeMainFragment : Fragment(), HomeMemberRecyclerAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView;
    private lateinit var adapter: HomeMemberRecyclerAdapter;
    private var _binding: FragmentHomeMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    // member data lists
    private lateinit var groupName : String;
    private lateinit var groupIntro : String;
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
//        val actionBar = (activity as AppCompatActivity).supportActionBar
//        actionBar?.apply {
//            setDisplayHomeAsUpEnabled(true)
//            setHomeAsUpIndicator(R.drawable.ic_android)
//        }
        groupMemList.add(ProfData("jihwan", "I'm jihwan", null));
        groupMemList.add(ProfData("yinae", "I'm yinae", null));
        groupMemList.add(ProfData("jinah", "I'm jinah", null));
        groupMemList.add(ProfData("yejin", "I'm yejin", null));
        groupMemList.add(ProfData("OH", "I'm jihwan", null));
        groupMemList.add(ProfData("Park", "I'm yinae", null));
        groupMemList.add(ProfData("PARK", "I'm jinah", null));
        groupMemList.add(ProfData("Kwon", "I'm yejin", null));

        binding.groupRecycler.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))

        recyclerView = binding.groupRecycler;
        adapter = HomeMemberRecyclerAdapter(groupMemList, this);
        Log.d("TAG", "this is the size of groupMemList : " + adapter.getItemCount());
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter;

        val mainActivity = activity as MainActivity;

        val toolbar = mainActivity.findViewById<Toolbar>(R.id.toolbar)
        val menu = toolbar?.menu;
        val item = menu?.findItem(R.id.add_action)
//        val item = toolbar?.findViewById<View>(R.id.add_action)
        if (item != null) {
            item.isVisible = false;
        }

        return root
    }

    override fun onItemClick(position: Int) {
        // TODO("Not yet implemented")
    }


}