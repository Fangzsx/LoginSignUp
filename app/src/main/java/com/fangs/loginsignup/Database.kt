package com.fangs.loginsignup

import android.content.Context
import java.io.File


class Database{

    companion object {

        var currentIndex = 0


        fun getExpenseFile(context: Context): File {

            //get internal storage path
            val path = context.getExternalFilesDir(null)

            //create a folder
            val folderDirectory = File(path, "User Data")

            //check if the file already exist. If not, create a new file
            if (!folderDirectory.exists()) {
                folderDirectory.mkdirs()
            }

            val file = File(folderDirectory, "expense_file.txt")

            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        }

        fun getInformationFile(context: Context): File {

            //get internal storage path
            val path = context.getExternalFilesDir(null)

            //create a folder
            val folderDirectory = File(path, "User Data")

            //check if the file already exist. If not, create a new file
            if (!folderDirectory.exists()) {
                folderDirectory.mkdirs()
            }

            val file = File(folderDirectory, "user_information.txt")

            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        }

        fun createExpenseFile(context : Context) : File{

            val path = context.getExternalFilesDir(null)
            val folderDirectory = File(path, "Expense")
            if (!folderDirectory.exists()){
                folderDirectory.mkdirs()
            }

            val counter = 1
            val file = File(folderDirectory, "$counter.txt")
            if (file.exists()){
               file.renameTo(File(folderDirectory, "${counter + 1}.txt"))
            }
            return file
        }

        fun getExpenseFile1(context: Context): File {

            //get internal storage path
            val path = context.getExternalFilesDir(null)

            //create a folder
            val folderDirectory = File(path, "User Data")

            //check if the file already exist. If not, create a new file
            if (!folderDirectory.exists()) {
                folderDirectory.mkdirs()
            }

            val file = File(folderDirectory, "expense_file.txt")

            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        }



    }
 }



