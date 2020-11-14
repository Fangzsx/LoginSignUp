package com.fangs.loginsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_homepage.*

class HomepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)


        btn_add_expense.setOnClickListener {
            val intent = Intent(this, ExpenseActivity::class.java)
            startActivity(intent)










        }

        btn_add_income.setOnClickListener {

        }

        btn_your_profile.setOnClickListener {

        }

    }
}