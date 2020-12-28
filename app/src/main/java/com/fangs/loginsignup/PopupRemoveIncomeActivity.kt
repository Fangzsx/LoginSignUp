package com.fangs.loginsignup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.popup_remove_income.view.*
import java.text.DateFormatSymbols
import java.util.*

class PopupRemoveIncomeActivity(val incomeActivity: IncomeActivity) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = layoutInflater.inflate(R.layout.popup_remove_income,container,false)
        //open file, read and sort
        val incomeFile = Database.getIncomeFile(incomeActivity)
        val contents = Tools.readToFile(incomeFile)
        val incomeInfoArray = contents.split("\n").toTypedArray()
        incomeInfoArray.sortDescending()

        //convert array to list to remove an element at specified index
        val result = incomeInfoArray.toMutableList()
        //get current index of the selected item


        rootView.btn_remove_income_no.setOnClickListener {
            dismiss()
        }

        rootView.btn_remove_income_yes.setOnClickListener {

            val currentIndex = Values.index
            //remove the current item in the list
            val currentEntry = result[currentIndex].split("      ").toTypedArray()
            val currentEntryDate = currentEntry[0].split("-")

            val currentEntryMonth = DateFormatSymbols().months[currentEntryDate[1].toInt()-1]
            val currentEntryYear = currentEntryDate[0]
            val currentEntryMonthYear = "$currentEntryMonth $currentEntryYear"


            //read monthlyRecords

            //open monthlyRecordFiles
            val today = Calendar.getInstance()
            //get current month from the current date
            val todayMonth = today.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
            //get year
            val todayYear = today.get(Calendar.YEAR)

            val todayFullDate = "$todayMonth $todayYear"
            val monthlyRecordFile = Database.getMonthlyRecord(incomeActivity, todayYear.toString())
            val monthlyRecordContents = Tools.readToFile(monthlyRecordFile).split("\n").toTypedArray().toMutableList()

            //get sum of currentBills and currentSavings for total of income
            val currentTotalIncome = Values.currentBills + Values.currentSavings
            val currentBills = Values.currentBills
            val currentSavings = Values.currentSavings

            for(index in monthlyRecordContents.indices){
                val currentMonthlyEntry = monthlyRecordContents[index].split("@")
                val currentMonthlyDate = currentMonthlyEntry[0]
                val currentMonthlyExpenseTotal = currentMonthlyEntry[1].toDouble()
                val currentMonthlyIncomeTotal = currentMonthlyEntry[2].toDouble()
                val currentMonthlyBillsTotal = currentMonthlyEntry[3].toDouble()
                val currentMonthlySavingsTotal = currentMonthlyEntry[4].toDouble()

                if(currentEntryMonthYear == currentMonthlyDate){
                    val newEntry = "$currentEntryMonthYear@$currentMonthlyExpenseTotal@${currentMonthlyIncomeTotal-currentTotalIncome}@${currentMonthlyBillsTotal-currentBills}@${currentMonthlySavingsTotal-currentSavings}"
                    monthlyRecordContents[index] = newEntry
                }
            }

            if(monthlyRecordFile.exists()){
                monthlyRecordFile.delete()
            }

            for(record in monthlyRecordContents){
                if(monthlyRecordContents.indexOf(record) == monthlyRecordContents.lastIndex){
                    Tools.appendTextToFile(monthlyRecordFile, record)
                }else{
                    Tools.appendTextToFile(monthlyRecordFile,"$record\n")
                }
            }

            result.removeAt(currentIndex)

            if (incomeFile.exists()){
                incomeFile.delete()
            }

            incomeFile.createNewFile()


            for(i in result.indices){
                val currentElement = result[i]
                if(currentElement.isNotEmpty()){
                    Tools.appendTextToFile(incomeFile, "${result[i]}\n")
                }
            }

            Toast.makeText(incomeActivity, "Record was removed!", Toast.LENGTH_SHORT).show()
            dismiss()
            val intent = Intent(incomeActivity, IncomeActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }
}