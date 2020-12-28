package com.fangs.loginsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up_only.*

class SignUpOnlyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_only)

        val file = Database.getInformationFile(this)
        val contents = Tools.readToFile(file)
        val dataArray = contents.split("+").toTypedArray()

        val username = dataArray[3]
        val password = dataArray[4]
        tv_username_only.text = "Hello, $username"

        btn_signin_only.setOnClickListener {
            if(et_password_only.text.toString() == password){
                Toast.makeText(this,"Login Success!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomepageActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Invalid Account!", Toast.LENGTH_SHORT).show()
            }
        }


    }
}