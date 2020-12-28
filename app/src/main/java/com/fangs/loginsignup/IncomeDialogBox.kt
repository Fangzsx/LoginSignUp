package com.fangs.loginsignup

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.popup_edit_income.view.*
import kotlinx.android.synthetic.main.popup_income.view.*
import java.util.*
import kotlin.math.roundToInt

class IncomeDialogBox(val incomeActivity: IncomeActivity) :DialogFragment() {

    //create lateinit vars for date, description and amount
    lateinit var date :String
    lateinit var description : String
    lateinit var amount: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //create root view to access elements inside the layout resource file
        val root = inflater.inflate(R.layout.popup_income, container, false)


        //recall the value of the current string from the Values class which holds temporary data
        //value of current string = current item in the recycler view CLICKED.
        val string = Values.currentString

        // the current string holds date, description, bills and savings
        //split the current string and extract each data and push them into their respective text views
        val array = string.split("      ").toTypedArray()

        val date = array[0]
        val description = array[1]
        val bills = array[2]
        val savings = array[3]




        root.tv_income_edit_date.text = date
        root.tv_income_edit_description.text = description
        root.tv_income_edit_bills.text = bills
        root.tv_income_edit_savings.text = savings
        root.tv_income_edit_total_income.text = "${root.tv_income_edit_bills.text.toString().toInt() +
                root.tv_income_edit_savings.text.toString().toInt()}"


        Values.currentBills = bills.toDouble()
        Values.currentSavings = savings.toDouble()
        Values.currentTotalIncome = root.tv_income_edit_total_income.text.toString().toDouble()

        //remove
        root.btn_remove_income.setOnClickListener {
            val dialog = PopupRemoveIncomeActivity(incomeActivity)
            val support = incomeActivity.supportFragmentManager
            dialog.show(support, "removeIncome")
        }

        return root
    }
}