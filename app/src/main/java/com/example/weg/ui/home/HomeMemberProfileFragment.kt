package com.example.weg.ui.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.weg.R
import com.example.weg.databinding.FragmentHomeMainBinding
import com.example.weg.databinding.FragmentHomeMemberProfileBinding

class HomeMemberProfileFragment : Fragment() {

    private var _binding: FragmentHomeMemberProfileBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // member data lists
    private lateinit var userName : String;
    private lateinit var userIntro : String;
    private lateinit var userFace : Drawable;

    companion object {
        fun newInstance(_userName: String, _userIntro: String, _userFace:Drawable?): HomeMemberProfileFragment {
            val fragment = HomeMemberProfileFragment();
            if(_userFace == null){
                fragment.userFace = ContextCompat.getDrawable(fragment.requireContext(), R.drawable.user_icon)!!
            }else{
                fragment.userName = _userName;
                fragment.userIntro = _userIntro;
                fragment.userFace = _userFace;
            }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeMemberProfileBinding.inflate(inflater, container, false)

        val root: View = binding.root;

        // TODO : data 가지고 layout 구성하기
        return root;
    }

}