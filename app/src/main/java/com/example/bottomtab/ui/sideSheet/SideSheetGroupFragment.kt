package com.example.bottomtab.ui.sideSheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomtab.R
import com.example.bottomtab.databinding.FragmentFirstBinding
import com.example.bottomtab.databinding.FragmentSideSheetGroupBinding



/**
 * A simple [Fragment] subclass.
 * Use the [SideSheetGroupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SideSheetGroupFragment : Fragment(){

    private var _binding: FragmentSideSheetGroupBinding? = null
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

        mList.add(GroupRecyclerItem("first"));
        mList.add(GroupRecyclerItem("second"));

        val adapter = GroupRecyclerAdapter(mList)
        binding.groupRecycler.adapter = adapter;
        binding.groupRecycler.layoutManager = LinearLayoutManager(context)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_side_sheet_group, container, false)
    }


}