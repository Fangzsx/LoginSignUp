package com.fangs.loginsignup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.popup_edit_expense.view.*

class PopupEditExpenseActivity : DialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView : View = inflater.inflate(R.layout.popup_edit_expense, container, false)
        rootView.iv_cancel_button2.setOnClickListener {
            dismiss()
        }
        rootView.btn_cancel_edit.setOnClickListener {
            dismiss()
        }
        return rootView
    }
}