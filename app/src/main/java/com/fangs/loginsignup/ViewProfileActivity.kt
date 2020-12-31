package com.fangs.loginsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up_only.*
import kotlinx.android.synthetic.main.activity_view_profile.*
import kotlinx.android.synthetic.main.popup_edit_profile.view.*

class ViewProfileActivity : AppCompatActivity() {

    override fun onBackPressed() {
        val intent = Intent(this, HomepageActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)

        //read data from file
        val userInfoFile = Database.getInformationFile(this)
        val contents= Tools.readToFile(userInfoFile)
        val userInfoArray = contents.split("+").toTypedArray()

        tv_profile_full_name.text = "Full Name: ${userInfoArray[0]}"
        tv_profile_address.text = "Address: ${userInfoArray[1]}"
        tv_profile_contact.text = "Contact: ${userInfoArray[2]}"
        tv_profile_username.text = "Username: ${userInfoArray[3]}"

        et_profile_password.keyListener = null
        et_profile_password.setText(userInfoArray[4])

        iv_password.setOnClickListener {
            if (iv_password.tag.toString() == "show"){
                iv_password.setImageResource(R.drawable.ic_show_password)
                et_profile_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                iv_password.tag = "hide"
            }else{
                iv_password.setImageResource(R.drawable.ic_hide_password)
                et_profile_password.transformationMethod = PasswordTransformationMethod.getInstance()
                iv_password.tag ="show"
            }
        }

        btn_profile_edit.setOnClickListener {
            //create a dialog
            val dialog = AlertDialog.Builder(this).create()
            val rootView = layoutInflater.inflate(R.layout.popup_edit_profile, null)
            dialog.setView(rootView)


            rootView.btn_profile_edit_save.setOnClickListener {
                // store edit text's text to new variables
                val newName = rootView.et_profile_new_full_name.text.toString()
                val newAddress = rootView.et_profile_new_address.text.toString()
                val newContact = rootView.et_profile_new_contact.text.toString()
                val newDesiredUsername = rootView.et_profile_new_username.text.toString().trim()
                val newDesiredPassword = rootView.et_profile_new_password.text.toString().trim()

                //check if edit texts are empty
                if (newName.isEmpty() ||
                    newAddress.isEmpty() ||
                    newContact.isEmpty() ||
                    newDesiredUsername.isEmpty() ||
                    newDesiredPassword.isEmpty()
                ) {
                    Toast.makeText(this, "Fields cannot be empty! Try again.", Toast.LENGTH_SHORT)
                        .show()
                }else {
                    val file = Database.getInformationFile(this)

                    Tools.writeToFile(
                        file,
                        "$newName,$newAddress,$newContact,$newDesiredUsername,$newDesiredPassword"
                    )
                    //show success message
                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                    //dismiss the popup
                    dialog.dismiss()
                    val intent = Intent(this, ViewProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                }




            }


            rootView.btn_profile_edit_cancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }
}