package com.fangs.loginsignup

import adapters.ItemAdapter

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_expense.*
import java.util.*


class ExpenseActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)

        //get expenseFile
        val file = Database.getExpenseFile1(this)
        val contents = Tools.readToFile(file)
        val expenseInfoArray = contents.split("\n").toTypedArray()
        expenseInfoArray.sortDescending()




        rv_expense_track.layoutManager = LinearLayoutManager(this)
        val itemAdapter = ItemAdapter(this, this,expenseInfoArray)
        rv_expense_track.adapter = itemAdapter


        // create an array of amounts
        val amountArray = contents.split("          ","\n").toTypedArray()
        var total = 0.0

        for (i in amountArray.indices){
            if (amountArray.indexOf(amountArray[i]) % 3 == 2){
                total += amountArray[i].toDouble()
            }
        }




        tv_total.text = "Total: $total"




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

            val description = et_description.text.toString()
            val amount = et_amount.text.toString()
            val date = tv_date_selected.text.toString()

            if (description.isEmpty() || amount.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
            } else {

                if(description.count() < 1 || description.count() >  10){
                    Toast.makeText(this, "Description must be between 1-10 characters!", Toast.LENGTH_SHORT).show()
                    et_description.text.clear()
                }else{

                    val expenseFile = Database.getExpenseFile1(this)
                    Tools.appendTextToFile(expenseFile, "$date          $description          $amount\n")

                    Toast.makeText(this, "Expense was Successfully added!", Toast.LENGTH_SHORT).show()


                    val intent = Intent(this, HomepageActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }

}