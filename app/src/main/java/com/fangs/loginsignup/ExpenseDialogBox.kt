package com.fangs.loginsignup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.popup_expense.view.*

class ExpenseDialogBox(val expenseActivity : ExpenseActivity): DialogFragment(){


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView : View = inflater.inflate(R.layout.popup_expense, container, false)
        rootView.iv_cancel_button.setOnClickListener {
            dismiss()
        }

        val string = Values.currentString
        val array = string.split("          ").toTypedArray()
        rootView.tv_item_current_date.text = array[0]
        rootView.tv_item_current_description.text = array[1]
        rootView.tv_item_current_amount.text = array[2]


        // edit button onclick listener
        rootView.btn_edit_expense.setOnClickListener {
            val editExpenseActivity = PopupEditExpenseActivity()
            val support = expenseActivity.supportFragmentManager
            editExpenseActivity.show(support, "editExpense")
        }

        rootView.btn_remove_expense.setOnClickListener {
            val removeExpenseActivity = PopupRemoveExpenseActivity(expenseActivity)
            val support = expenseActivity.supportFragmentManager
            removeExpenseActivity.show(support, "removeExpense")
        }



        return rootView
    }


}