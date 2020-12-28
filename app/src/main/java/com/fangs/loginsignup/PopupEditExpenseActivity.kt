package com.fangs.loginsignup

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.popup_edit_expense.*
import kotlinx.android.synthetic.main.popup_edit_expense.view.*
import java.util.*

class PopupEditExpenseActivity(val expenseActivity: ExpenseActivity ) : DialogFragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView : View = inflater.inflate(R.layout.popup_edit_expense, container, false)



        rootView.btn_cancel_edit.setOnClickListener {
            dismiss()
        }


        rootView.btn_set_new_date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                expenseActivity,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    tv_new_date.text = ("$mYear-${mMonth+1}-$mDay")
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }


        rootView.btn_save_update.setOnClickListener {

            val newDate = tv_new_date.text.toString()
            val newDescription = et_set_new_description.text.toString()
            val newAmount = et_set_new_amount.text.toString()

            //check if fields are empty
            if(newDate.isEmpty() || newDescription.isEmpty() || newAmount.isEmpty()){
                Toast.makeText(expenseActivity, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()

            }else{
                if(newDescription.count() < 0 || newDescription.count() > 10){
                    Toast.makeText(expenseActivity, "Description must be 1-10 characters only!", Toast.LENGTH_SHORT).show()
                }


                val currentIndex = Values.index
                //lateinit var currentString : String

                //open the file and extract all the extract the strings
                val file = Database.getExpenseFile1(expenseActivity)
                val contents = Tools.readToFile(file)
                val expenseArrayInfo = contents.split("\n").toTypedArray()
                expenseArrayInfo.sortDescending()

                //store the values of fields in a new string and following the format of file read
                val updateInfo = "$newDate          $newDescription          $newAmount"

                //iterate over the array and check if the current index matches the index in the file
                for(i in expenseArrayInfo.indices){
                    if (expenseArrayInfo.indexOf(expenseArrayInfo[i]) == currentIndex){
                        expenseArrayInfo[i] = updateInfo
                    }
                }

                //delete the old file
                if(file.exists()){
                    file.delete()
                }

                //write the elements of the updated expenseInfo array to the newly created txt file
                for (i in expenseArrayInfo.indices){
                    if(expenseArrayInfo[i].isNotEmpty()){
                        Tools.appendTextToFile(file, "${expenseArrayInfo[i]}\n")
                    }
                }

                dismiss()
                Toast.makeText(expenseActivity, "Item was successfully updated!", Toast.LENGTH_SHORT).show()
                val intent = Intent(expenseActivity, ExpenseActivity::class.java)
                startActivity(intent)



            }








        }














        return rootView
    }
}