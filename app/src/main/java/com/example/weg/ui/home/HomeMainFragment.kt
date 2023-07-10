package com.example.weg.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.weg.R
import com.example.weg.databinding.FragmentHomeBinding
import com.example.weg.databinding.FragmentHomeMainBinding
import com.example.weg.profileData


class HomeMainFragment : Fragment() {

    private var _binding: FragmentHomeMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    // member data lists
    private lateinit var groupName : String;
    private lateinit var groupIntro : String;
    private lateinit var groupImage : String;
    private lateinit var groupMemList : ArrayList<profileData>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeMainBinding.inflate(inflater, container, false)

        val root: View = binding.root;
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_android)
        }

        return root
    }


}