package com.fangs.loginsignup


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userInfoFile = Database.getInformationFile(this)
        if (userInfoFile.length() > 0) {
            val intent = Intent(this, SignUpOnlyActivity::class.java)
            startActivity(intent)
            finish()
        }


        btn_signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}