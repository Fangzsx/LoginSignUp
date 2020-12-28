package com.fangs.loginsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*



class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_finish_signup.setOnClickListener {
            val name = et_full_name.text.toString()
            val address = et_address.text.toString()
            val contact = et_contact.text.toString()
            val desiredUsername = et_desired_username.text.toString().trim()
            val desiredPassword = et_desired_password.text.toString().trim()

            if (name.isEmpty() ||
                address.isEmpty() ||
                contact.isEmpty() ||
                desiredUsername.isEmpty() ||
                desiredPassword.isEmpty()
            ) {
                Toast.makeText(this, "Fields cannot be empty! Try again.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val file = Database.getInformationFile(this)

                Tools.writeToFile(
                    file,
                    name + "+" + address + "+" + contact + "+" + desiredUsername + "+" + desiredPassword
                )

                val contents = Tools.readToFile(file)
                //split the contents string
                val dataArray = contents.split("+").toTypedArray()

                //set the value of username and password according to its index in the array
                dataArray[3] = desiredUsername
                dataArray[4] = desiredPassword

                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}