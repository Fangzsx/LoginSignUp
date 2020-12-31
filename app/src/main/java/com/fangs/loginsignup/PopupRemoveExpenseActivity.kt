package com.fangs.loginsignup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.popup_remove_expense.view.*
import java.text.DateFormatSymbols
import java.util.*
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
            //get currentString
            val currentString = result[Values.index].split("       ")
            val date = currentString[0].split("-")
            val currentMonth = DateFormatSymbols().months[date[1].toInt()-1]
            val currentYear = date[0]
            val currentFullDate = "$currentMonth $currentYear"


            val amount = currentString[2]
            val deductFrom = currentString[3]

            val today = Calendar.getInstance()
            //get year
            val todayYear = today.get(Calendar.YEAR)

            val monthlyRecord = Database.getMonthlyRecord(expenseActivity, todayYear.toString())

            if(deductFrom == "Savings"){
                val monthlyRecordContent = Tools.readToFile(monthlyRecord).split("\n").toMutableList()
                for(entry in monthlyRecordContent){
                    val currentEntry = entry.split("@").toTypedArray()
                    val currentEntryDate = currentEntry[0]

                    val currentEntryExpense = currentEntry[1].toDouble()
                    val currentEntryIncome = currentEntry[2].toDouble()
                    val currentEntryBills = currentEntry[3].toDouble()
                    val currentEntrySavings = currentEntry[4].toDouble()

                    if(currentEntryDate == currentFullDate){
                        monthlyRecordContent[monthlyRecordContent.indexOf(entry)] = "$currentEntryDate@${currentEntryExpense-amount.toDouble()}@$currentEntryIncome@$currentEntryBills@${currentEntrySavings+amount.toDouble()}"
                    }

                    if(monthlyRecord.exists()){
                        monthlyRecord.delete()
                    }

                    for(entry1 in monthlyRecordContent){
                        if(monthlyRecordContent.indexOf(entry1) == monthlyRecordContent.lastIndex){
                            Tools.appendTextToFile(monthlyRecord, entry1)
                        }else{
                            Tools.appendTextToFile(monthlyRecord,"$entry1\n")
                        }
                    }
                }


            }else{

                val monthlyRecordContent = Tools.readToFile(monthlyRecord).split("\n").toMutableList()
                for(entry in monthlyRecordContent){
                    val currentEntry = entry.split("@").toTypedArray()
                    val currentEntryDate = currentEntry[0]

                    val currentEntryExpense = currentEntry[1].toDouble()
                    val currentEntryIncome = currentEntry[2].toDouble()
                    val currentEntryBills = currentEntry[3].toDouble()
                    val currentEntrySavings = currentEntry[4].toDouble()

                    if(currentEntryDate == currentFullDate){
                        monthlyRecordContent[monthlyRecordContent.indexOf(entry)] = "$currentEntryDate@${currentEntryExpense-amount.toDouble()}@$currentEntryIncome@${currentEntryBills+amount.toDouble()}@$currentEntrySavings"
                    }

                    if(monthlyRecord.exists()){
                        monthlyRecord.delete()
                    }

                    for(entry1 in monthlyRecordContent){
                        if(monthlyRecordContent.indexOf(entry1) == monthlyRecordContent.lastIndex){
                            Tools.appendTextToFile(monthlyRecord, entry1)
                        }else{
                            Tools.appendTextToFile(monthlyRecord,"$entry1\n")
                        }
                    }




                }


            }

            result.removeAt(Values.index)

            if (file.exists()){
                file.delete()
            }

            file.createNewFile()


            for(i in result.indices){
                val currentElement = result[i]
                if(currentElement.isNotEmpty()){
                    Tools.appendTextToFile(file, "${result[i]}\n")
                }
            }

            Toast.makeText(expenseActivity, "Item was removed!", Toast.LENGTH_SHORT).show()
            dismiss()
            val intent = Intent(expenseActivity, ExpenseActivity::class.java)
            startActivity(intent)

        }

        rootView.btn_remove_no.setOnClickListener {
            dismiss()
        }
        return rootView
    }

}