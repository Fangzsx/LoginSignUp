package com.fangs.loginsignup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_homepage.*
import java.io.File
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        btn_add_expense.setOnClickListener {
            val intent = Intent(this, ExpenseActivity::class.java)
            startActivity(intent)

        }

        btn_your_profile.setOnClickListener {
            val intent = Intent(this, ViewProfileActivity::class.java)
            startActivity(intent)
        }

        btn_add_income.setOnClickListener {
            val intent = Intent(this, IncomeActivity::class.java)
            startActivity(intent)
        }







        /* We created a txt file for saving total of income and expense so that we can
        fetch their value before going to their respective activities
         */

        //open total expense file
        /*val expenseTotalFile = Database.getTotalExpenseFile(this)
        val expenseContents = Tools.readToFile(expenseTotalFile)
        //set text to content of total expense file

        if(expenseContents.isEmpty()){
            Tools.writeToFile(expenseTotalFile,"0.0")
            tv_home_total_expenses.text = "Total Expense\n 0.0"
        }else{
            tv_home_total_expenses.text = "Total Expense\n$expenseContents"
        }




        //do the same thing with total income file
        val incomeTotalFile = Database.getTotalIncomeFile(this)
        val incomeContents = Tools.readToFile(incomeTotalFile)

        if(incomeContents.isEmpty()){
            Tools.writeToFile(incomeTotalFile, "0.0")
        }else{
            tv_home_total_income.text = "Total Income\n$incomeContents"
        }



        //open total bills file
        val billsTotalFile = Database.getTotalBillsFile(this)
        val billsContents = Tools.readToFile(billsTotalFile)
        if(billsContents.isEmpty()){
            Tools.writeToFile(billsTotalFile, "0.0")
        }else{
            //set text to content of total bills file
            tv_home_total_bills.text = "Total Bills\n$billsContents"
        }





        //open total savings file
        val savingsTotalFile = Database.getTotalSavingsFile(this)
        val savingsContents = Tools.readToFile(savingsTotalFile)

        if(savingsContents.isEmpty()){
            Tools.writeToFile(savingsTotalFile,"0.0")
        }else{
            //set text to content of total savings file
            tv_home_total_savings.text = "Total Savings\n$savingsContents"
        }
        */


        //get the current date
        val today = Calendar.getInstance()
        //get current month from the current date
        val todayMonth = today.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        //get year
        val todayYear = today.get(Calendar.YEAR)

        val todayFullDate = "$todayMonth $todayYear"
        //set text
        tv_current_month.text = todayFullDate


        //write to monthly records file
        val monthlyRecordFile = Database.getMonthlyRecord(this, todayYear.toString())
        val monthlyRecordContents = Tools.readToFile(monthlyRecordFile)

        if(monthlyRecordContents.isEmpty()){
            tv_home_total_expenses.text = "Total Expense\n0.0"
            tv_home_total_bills.text = "Total Bills\n0.0"
            tv_home_total_savings.text = "Total Savings\n0.0"
            tv_home_total_income.text = "Total Income\n0.0"
        }else{
            val monthlyRecordArray = monthlyRecordContents.split("\n").toTypedArray()
            val dateList = mutableListOf<String>()
            var currentEntryDate : String? = null

            for(entry in monthlyRecordArray){
                val currentEntry = entry.split("@").toTypedArray()
                currentEntryDate = currentEntry[0]
                val currentEntryExpenseTotal = currentEntry[1]
                val currentEntryIncomeTotal = currentEntry[2]
                val currentEntryBillsTotal = currentEntry[3]
                val currentEntrySavingsTotal = currentEntry[4]
                if(currentEntryDate == todayFullDate){
                    tv_home_total_expenses.text = "Total Expense\n$currentEntryExpenseTotal"
                    tv_home_total_bills.text = "Total Bills\n$currentEntryBillsTotal"
                    tv_home_total_savings.text = "Total Savings\n$currentEntrySavingsTotal"
                    tv_home_total_income.text = "Total Income\n$currentEntryIncomeTotal"
                    dateList.add(currentEntryDate)
                    break
                }
            }
            if(!dateList.contains(currentEntryDate)){
                tv_home_total_expenses.text = "Total Expense\n0.0"
                tv_home_total_bills.text = "Total Bills\n0.0"
                tv_home_total_savings.text = "Total Savings\n0.0"
                tv_home_total_income.text = "Total Income\n0.0"
            }

        }







    }
}