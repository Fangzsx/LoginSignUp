package com.fangs.loginsignup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.popup_expense.view.*
import kotlinx.android.synthetic.main.popup_remove_expense.view.*
import kotlin.math.exp

class PopupRemoveExpenseActivity(val expenseActivity: ExpenseActivity) : DialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val file = Database.getExpenseFile1(expenseActivity)
        val contents = Tools.readToFile(file)
        val expenseInfoArray = contents.split("\n").toTypedArray()
        expenseInfoArray.sortDescending()


        val rootView = inflater.inflate(R.layout.popup_remove_expense, container, false)
        rootView.btn_remove_yes.setOnClickListener {
            val result = expenseInfoArray.toMutableList()
            result.removeAt(Values.index)


            val newContent = result.toString().substring(1, result.toString().length - 1)
            Tools.writeToFile(file, newContent)
            dismiss()
            Toast.makeText(expenseActivity, "Item removed!", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, HomepageActivity::class.java)
            startActivity(intent)

        }
        return rootView

    }
}