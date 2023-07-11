package com.example.weg.ui.sideSheet

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.weg.MainActivity
import com.example.weg.R


class GroupDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_group_dialog, null)

            // Dialog 내의 View 요소들 가져오기
            val editTextTitle = view.findViewById<EditText>(R.id.editGroupTitle)
            val buttonSave = view.findViewById<Button>(R.id.buttonSave)

            // 버튼 클릭 이벤트 처리
            buttonSave.setOnClickListener {
                val newGroupName = editTextTitle.text.toString()
                val mainActivity = activity as MainActivity;
                mainActivity.addGroup(newGroupName);
                dismiss()
            }

            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}