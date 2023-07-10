package com.example.weg.ui.ground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weg.databinding.FragmentGroundBinding

class GroundFragment : Fragment() {

    private var _binding: FragmentGroundBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGroundBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGround;
        textView.text = "This is Ground Fragment";
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}