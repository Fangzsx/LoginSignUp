package com.fangs.loginsignup

import adapters.ItemAdapterIncome
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.text.isDigitsOnly
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.activity_income.*
import kotlinx.android.synthetic.main.activity_income.view.*
import kotlinx.android.synthetic.main.confirm_dialog.view.*
import kotlinx.android.synthetic.main.popup_edit_expense.*
import kotlinx.android.synthetic.main.popup_edit_income.*
import kotlinx.android.synthetic.main.popup_set_percentage_income.*
import kotlinx.android.synthetic.main.popup_set_percentage_income.view.*
import java.lang.Exception
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class IncomeActivity : AppCompatActivity() {

    private lateinit var date : String
    private lateinit var description : String
    private lateinit var amount : String
    private lateinit var bills : String
    private lateinit var savings : String


    override fun onBackPressed() {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income)



        //get the file
        val file = Database.getIncomeFile(this)
        val contents = Tools.readToFile(file)
        val incomeInfoArray = contents.split("\n").toTypedArray()
        incomeInfoArray.sortDescending()


        //iterate over the expense file and extract the dates
        val dateArray = contents.split("      ","\n").toTypedArray()

        //get the current date
        val today = Calendar.getInstance().time
        //get current month from the current date
        val todayMonth = SimpleDateFormat("MMM").format(today)
        //get year
        val todayYear = SimpleDateFormat("yyyy").format(today)



        //create a variable that will hold the amounts
        var totalIncome : Double= 0.0

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
                        totalIncome += (dateArray[index + 2].toDouble() + dateArray[index + 3].toDouble())
                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        }


        //recycler view
        rv_income_track.layoutManager = LinearLayoutManager(this)
        val itemAdapter = ItemAdapterIncome(this,incomeInfoArray)
        rv_income_track.adapter = itemAdapter



        btn_income_select_date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)





            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                    tv_income_date_selected.text = ("$mYear-${mMonth+1}-$mDay")
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }


        //create total income variable

        btn_income_add.setOnClickListener {
            date = tv_income_date_selected.text.toString()
            description = et_income_description.text.toString().trim()
            amount = et_income_amount.text.toString()


            if(date.isEmpty() || description.isEmpty() || amount.isEmpty()){
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
            }
            else{

                //get current month
                val calendar = Calendar.getInstance()
                val currentMonth = calendar.get(Calendar.MONTH)
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
                    if(description.count() > 10){
                        Toast.makeText(this, "Description must be 1-10 characters only!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Please set the following", Toast.LENGTH_SHORT).show()
                        openSetPercentageDialog()

                    }
                }




            }
        }
    }


    private fun openSetPercentageDialog(){

        val dialog = AlertDialog.Builder(this).create()
        val rootView = layoutInflater.inflate(R.layout.popup_set_percentage_income,null)
        dialog.setView(rootView)

        rootView.tv_home_show_total_income.text = "Income: $amount"
        rootView.tv_total_bill.text = "Bills: ${amount.toInt()/2}"
        rootView.tv_total_saving.text = "Savings: ${amount.toInt()/2}"

        rootView.btn_cancel_income.setOnClickListener {
            dialog.dismiss()
        }

        bills = rootView.tv_total_bill.text.toString()
        savings = rootView.tv_total_saving.text.toString()

        rootView.sb_percentage.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                rootView.tv_bills_percentage.text = "$p1%"
                rootView.tv_savings_percentage.text = "${100 - p1}%"
                rootView.tv_total_bill.text = "Bills: ${(amount.toDouble() * (p1.toDouble()/100)).roundToInt()}"
                rootView.tv_total_saving.text = "Savings: ${(amount.toDouble() * ((100-p1).toDouble()/100)).roundToInt()}"
                bills = rootView.tv_total_bill.text.toString()
                savings = rootView.tv_total_saving.text.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {


            }

        })





        rootView.btn_save_income.setOnClickListener {

            val confirmDialog = AlertDialog.Builder(this).create()
            val rootViewIncome = layoutInflater.inflate(R.layout.confirm_dialog,null)
            confirmDialog.setView(rootViewIncome)

            rootViewIncome.btn_confirm_yes.setOnClickListener {
                val incomeFile = Database.getIncomeFile(this)

                //extract numbers
                //bills
                val b = bills.filter { it.isDigit() }

                //savings
                val s = savings.filter { it.isDigit()}
                //income file
                Tools.appendTextToFile(incomeFile, "$date      $description      $b      $s\n")

                //income
                val currentTotalIncome = b.toDouble() + s.toDouble()


                //get monthlyRecord file

                val today = Calendar.getInstance()
                //get current month from the current date
                val todayMonth = today.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                //get year
                val todayYear = today.get(Calendar.YEAR)

                val todayFullDate = "$todayMonth $todayYear"

                val monthlyRecordFile = Database.getMonthlyRecord(this, todayYear.toString())
                val monthlyRecordContent = Tools.readToFile(monthlyRecordFile)

                if(monthlyRecordContent.isEmpty()){
                    Tools.writeToFile(monthlyRecordFile, "$todayFullDate@0@$currentTotalIncome@$b@$s")
                }else{

                    val monthlyRecordArray = monthlyRecordContent.split("\n").toTypedArray().toMutableList()
                    //convert int date to string
                    val dateArray = date.split("-")
                    val dateMonth = DateFormatSymbols().months[dateArray[1].toInt()-1]
                    val dateYear = dateArray[0]
                    val dateMonthYear = "$dateMonth $dateYear"


                    //val date
                    var currentEntryDate : String? = null


                    for(index in monthlyRecordArray.indices){
                        val entry = monthlyRecordArray[index]
                        val currentEntryInfo = entry.split("@").toTypedArray()
                        currentEntryDate = currentEntryInfo[0]
                        val currentEntryExpenseTotal = currentEntryInfo[1].toDouble()
                        val currentEntryIncomeTotal = currentEntryInfo[2].toDouble()
                        val currentEntryBillsTotal = currentEntryInfo[3].toDouble()
                        val currentEntrySavings = currentEntryInfo[4].toDouble()

                        if(dateMonthYear == currentEntryDate){
                            monthlyRecordArray[index] = "$dateMonthYear@$currentEntryExpenseTotal@${currentEntryIncomeTotal+currentTotalIncome}@${currentEntryBillsTotal + b.toDouble()}@${currentEntrySavings + s.toDouble()}"
                        }

                    }

                    if(dateMonthYear!=currentEntryDate){
                        monthlyRecordArray.add("$dateMonthYear@0@$currentTotalIncome@$b@$s")

                    }

                    if(monthlyRecordFile.exists()){
                        monthlyRecordFile.delete()
                    }

                    for(element in monthlyRecordArray){
                        if(monthlyRecordArray.indexOf(element) == monthlyRecordArray.lastIndex){
                            Tools.appendTextToFile(monthlyRecordFile, element)
                        }else{
                            Tools.appendTextToFile(monthlyRecordFile,"$element\n")
                        }
                    }


                }




                Toast.makeText(this, "Record was successfully added!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, IncomeActivity::class.java)
                startActivity(intent)
            }


            rootViewIncome.btn_confirm_no.setOnClickListener {
                confirmDialog.dismiss()
            }

            confirmDialog.show()
        }



        dialog.show()

    }
}