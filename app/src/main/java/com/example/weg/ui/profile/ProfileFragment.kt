package com.example.weg.ui.profile

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.weg.R
import com.example.weg.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // member data lists
    private lateinit var userName : String;
    private lateinit var userIntro : String;
    private var userImage : Drawable? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false);
        val root: View = binding.root;
        val nameText = binding.profileName;
        val editNameText = binding.profileNameEdit;

        binding.profileName.setOnClickListener(View.OnClickListener {
            nameText.visibility =View.GONE
            editNameText.visibility = View.VISIBLE
            editNameText.setText(nameText.text)
            editNameText.requestFocus()

            val introTextView: TextView = binding.introText;
            val layoutParams = introTextView.layoutParams as ConstraintLayout.LayoutParams
            layoutParams.topToBottom = R.id.profile_name_edit;
            introTextView.layoutParams = layoutParams;

            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editNameText, InputMethodManager.SHOW_IMPLICIT)
        })

        editNameText.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER ||
                actionId == EditorInfo.IME_ACTION_DONE
            ) {
                val newName = editNameText.text.toString()
                nameText.text = newName
                nameText.visibility = View.VISIBLE
                editNameText.visibility = View.GONE

                val introTextView: TextView = binding.introText;
                val layoutParams = introTextView.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.topToBottom = R.id.profile_name;
                introTextView.layoutParams = layoutParams;

                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editNameText.windowToken, 0)
                userName = newName

                // TODO : data base에 이름 변경 요청보내기
                true
            } else {
                false
            }
        }

        val profileIntroText = binding.profileIntro;
        val profileIntroEdit = binding.profileIntroEdit;
        profileIntroText.setOnClickListener(View.OnClickListener {
            profileIntroText.visibility = View.GONE
            profileIntroEdit.visibility = View.VISIBLE
            profileIntroEdit.setText(profileIntroText.text)
            profileIntroEdit.requestFocus()

            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(profileIntroText, InputMethodManager.SHOW_IMPLICIT)
        })

        profileIntroEdit.setOnEditorActionListener { _, actionId, event ->
            if (event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER ||
                actionId == EditorInfo.IME_ACTION_DONE
            ) {
                val newIntro = profileIntroEdit.text.toString();
                profileIntroText.text = newIntro;
                profileIntroText.visibility = View.VISIBLE
                profileIntroEdit.visibility = View.GONE

                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(profileIntroEdit.windowToken, 0)
                userIntro = newIntro;
                // TODO : data base에 intro 변경 요청보내기
                true
            } else {
                false
            }
        }

        val deleteBtn = binding.deleteButton;
        deleteBtn.setOnClickListener {
            Log.d("Button", "삭제 버튼이 클릭되었습니다.")
        }

        // TODO : user image icon 설정하기
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}