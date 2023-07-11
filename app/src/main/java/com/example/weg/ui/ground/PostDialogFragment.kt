package com.example.weg.ui.ground

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
import com.example.weg.R


class PostDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.fragment_post_dialog, null)

            // Dialog 내의 View 요소들 가져오기
            val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
            val editTextContent = view.findViewById<EditText>(R.id.editTextContent)
            val buttonSave = view.findViewById<Button>(R.id.buttonSave)

            // 버튼 클릭 이벤트 처리
            buttonSave.setOnClickListener {
                val title = editTextTitle.text.toString()
                val content = editTextContent.text.toString()
                // TODO: 입력된 일기 제목과 내용을 처리하는 로직 구현
                dismiss()
            }

            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}