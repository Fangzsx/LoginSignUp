package com.fangs.loginsignup

import adapters.ItemAdapter
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_expense.*
import kotlinx.android.synthetic.main.confirm_dialog.view.*
import kotlinx.android.synthetic.main.popup_expense_deduct_from.view.*
import java.io.Console
import java.io.File
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ExpenseActivity : AppCompatActivity(){


    override fun onBackPressed() {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        //get expenseFile
        val file = Database.getExpenseFile1(this)
        val contents = Tools.readToFile(file)
        val expenseInfoArray = contents.split("\n").toTypedArray()
        expenseInfoArray.sortDescending()

        //iterate over the expense file and extract the dates
        val dateArray = contents.split("       ","\n").toTypedArray()

        //get the current date
        val today = Calendar.getInstance().time
        //get current month from the current date
        val todayMonth = SimpleDateFormat("MMM").format(today)
        //get year
        val todayYear = SimpleDateFormat("yyyy").format(today)


        //create a variable that will hold the amounts

        var totalExpense : Double= 0.0

        //iterate over the array and get the index of all dates
        for(index in dateArray.indices){
            //index of date inside the file follows the 0,3,6,9 .. double check if the date is empty
            if(index % 3 == 0 && dateArray[index].isNotEmpty()){
                // get the current element
                try{
                    val currentDateString = dateArray[index]
                    //convert the Date string to Date
                    val sdf = SimpleDateFormat("yyyy-MM-dd")
                    val expenseDate = sdf.parse(currentDateString)
                    //get the month of the current element
                    val expenseMonth = SimpleDateFormat("MMM").format(expenseDate)
                    val expenseYear = SimpleDateFormat("yyyy").format(expenseDate)

                    //if today's year and month is equal to element's year and month
                    if(expenseMonth == todayMonth && expenseYear == todayYear){
                        //navigate to amount which is 2 elements ahead from the date and add them to the total variable
                        totalExpense += dateArray[index + 2].toDouble()
                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        }


        val expenseTotalFile = Database.getTotalExpenseFile(this)

        rv_expense_track.layoutManager = LinearLayoutManager(this)
        val itemAdapter = ItemAdapter(this, this,expenseInfoArray)
        rv_expense_track.adapter = itemAdapter


        btn_select_date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    tv_date_selected.text = ("$mYear-${mMonth+1}-$mDay")
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }

        btn_add.setOnClickListener {

            val description = et_description.text.toString().trim()
            val amount = et_amount.text.toString()
            val date = tv_date_selected.text.toString()


            val today1 = Calendar.getInstance()
            //get current month from the current date
            val todayMonth1 = today1.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
            //get year
            val todayYear1 = today1.get(Calendar.YEAR)


            if (description.isEmpty() || amount.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
            } else {

                //get current month
                val calendar = Calendar.getInstance()
                val currentMonth = calendar.get(Calendar.MONTH)+1
                val currentYear = calendar.get(Calendar.YEAR)

                //date format
                val sdf = SimpleDateFormat("YYYY-MM-dd")
                val parsedDate = sdf.parse(date)

                val newCalendar = Calendar.getInstance()
                newCalendar.time = parsedDate
                val currentRecordMonth = newCalendar.get(Calendar.MONTH)+1
                val currentRecordYear = newCalendar.get(Calendar.YEAR)+1




                if(currentRecordMonth != currentMonth && currentRecordYear != currentYear){
                    Toast.makeText(this, "Can only add record for current month and year!", Toast.LENGTH_SHORT).show()
                }else{
                    if(description.count() < 1 || description.count() >  10){
                        Toast.makeText(this, "Description must be between 1-10 characters!", Toast.LENGTH_SHORT).show()
                        et_description.text.clear()
                    }else{

                        val expenseFile = Database.getExpenseFile1(this)

                        val deductFromDialog = AlertDialog.Builder(this)
                        val rootView = layoutInflater.inflate(R.layout.popup_expense_deduct_from, null)

                        deductFromDialog.setView(rootView)


                        rootView.btn_expense_deduct_from_savings.setOnClickListener {

                            val confirmDialog = AlertDialog.Builder(this).create()
                            val rootViewSavings = layoutInflater.inflate(R.layout.confirm_dialog, null)

                            confirmDialog.setView(rootViewSavings)

                            rootViewSavings.btn_confirm_yes.setOnClickListener {

                                Tools.appendTextToFile(expenseFile, "$date       $description       $amount       Savings\n")

                                //write to monthly records
                                //open monthly records
                                val monthlyRecordFile = Database.getMonthlyRecord(this, todayYear)
                                val monthlyRecordContents = Tools.readToFile(monthlyRecordFile)
                                val currentDate1 = "$todayMonth1 $todayYear1"

                                if(monthlyRecordContents.isEmpty()){
                                    Tools.writeToFile(monthlyRecordFile,"$currentDate1@$amount@0@0@-$amount")
                                }else{
                                    val entryList = monthlyRecordContents.split("\n").toTypedArray().toMutableList()
                                    var currentEntryExpenseTotal : Double? = null
                                    var currentEntryIncomeTotal : Double? = null
                                    var currentEntryBillsTotal : Double? = null
                                    var currentEntrySavingsTotal : Double? = null
                                    var currentEntryDate : String? = null
                                    for(index in entryList.indices){

                                        val currentEntry = entryList[index]

                                        if(currentEntry.isEmpty()){
                                            entryList.remove(currentEntry)
                                        }

                                        val currentEntryInfo = currentEntry.split("@").toTypedArray()
                                        currentEntryDate = currentEntryInfo[0]
                                        currentEntryExpenseTotal = currentEntryInfo[1].toDouble()
                                        currentEntryIncomeTotal = currentEntryInfo[2].toDouble()
                                        currentEntryBillsTotal = currentEntryInfo[3].toDouble()
                                        currentEntrySavingsTotal = currentEntryInfo[4].toDouble()


                                        if(currentEntryDate == currentDate1){
                                            val newEntry = "$currentDate1@${currentEntryExpenseTotal + amount.toDouble()}@$currentEntryIncomeTotal@$currentEntryBillsTotal@${currentEntrySavingsTotal+(currentEntryIncomeTotal-amount.toDouble())}"
                                            entryList[index] = newEntry
                                        }

                                    }

                                    if(currentEntryDate!= currentDate1){
                                        entryList.add("$currentDate1@$amount@0@0@-$amount")
                                    }

                                    if(monthlyRecordFile.exists()){
                                        monthlyRecordFile.delete()
                                    }

                                    for(elements in entryList){

                                        if(entryList.indexOf(elements) == entryList.lastIndex){
                                            Tools.appendTextToFile(monthlyRecordFile,"$elements")
                                        }else{
                                            Tools.appendTextToFile(monthlyRecordFile,"$elements\n")
                                        }

                                    }
                                }

                                val intent = Intent(this, HomepageActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "Record Saved!", Toast.LENGTH_SHORT).show()

                            }

                            rootViewSavings.btn_confirm_no.setOnClickListener {
                                confirmDialog.dismiss()
                            }

                            confirmDialog.show()
                        }

                        rootView.btn_expense_deduct_from_bills.setOnClickListener {

                            val confirmDialog = AlertDialog.Builder(this).create()
                            val rootViewBills = layoutInflater.inflate(R.layout.confirm_dialog, null)

                            confirmDialog.setView(rootViewBills)

                            rootViewBills.btn_confirm_yes.setOnClickListener {
                                Tools.appendTextToFile(expenseFile, "$date       $description       $amount       Bills\n")


                                //write to monthly records
                                //open monthly records
                                val monthlyRecordFile = Database.getMonthlyRecord(this, todayYear)
                                val monthlyRecordContents = Tools.readToFile(monthlyRecordFile)
                                val currentDate1 = "$todayMonth1 $todayYear1"


                                if(monthlyRecordContents.isEmpty()){
                                    Tools.writeToFile(monthlyRecordFile,"$currentDate1@$amount@0@-$amount@0")
                                }else{
                                    val entryList = monthlyRecordContents.split("\n").toTypedArray().toMutableList()
                                    var currentEntryExpenseTotal : Double? = null
                                    var currentEntryIncomeTotal : Double? = null
                                    var currentEntryBillsTotal : Double? = null
                                    var currentEntrySavingsTotal : Double? = null
                                    var currentEntryDate : String? = null
                                    for(index in entryList.indices){
                                        val currentEntry = entryList[index]

                                        if(currentEntry.isEmpty()){
                                            entryList.remove(currentEntry)
                                        }

                                        val currentEntryInfo = currentEntry.split("@").toTypedArray()
                                        currentEntryDate = currentEntryInfo[0]
                                        currentEntryExpenseTotal = currentEntryInfo[1].toDouble()
                                        currentEntryIncomeTotal = currentEntryInfo[2].toDouble()
                                        currentEntryBillsTotal = currentEntryInfo[3].toDouble()
                                        currentEntrySavingsTotal = currentEntryInfo[4].toDouble()

                                        if(currentEntryDate == currentDate1){

                                            val newEntry = "$currentDate1@${currentEntryExpenseTotal!! +amount.toDouble()}@$currentEntryIncomeTotal@${currentEntryBillsTotal!! +(currentEntryIncomeTotal!!.toDouble()-amount.toDouble())}@$currentEntrySavingsTotal"
                                            entryList[index] = newEntry

                                        }
                                    }

                                    if(currentEntryDate!= currentDate1){
                                        entryList.add("$currentDate1@$amount@0@-$amount@0")
                                    }

                                    if(monthlyRecordFile.exists()){
                                        monthlyRecordFile.delete()
                                    }

                                    for(elements in entryList){

                                        if(entryList.indexOf(elements) == entryList.lastIndex){
                                            Tools.appendTextToFile(monthlyRecordFile, elements)
                                        }else{
                                            Tools.appendTextToFile(monthlyRecordFile,"$elements\n")
                                        }

                                    }
                                }





                                val intent = Intent(this, HomepageActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "Record Saved!", Toast.LENGTH_SHORT).show()
                            }

                            rootViewBills.btn_confirm_no.setOnClickListener {
                                confirmDialog.dismiss()
                            }

                            confirmDialog.show()
                        }
                        deductFromDialog.show()
                    }
                }


            }
        }
    }
}