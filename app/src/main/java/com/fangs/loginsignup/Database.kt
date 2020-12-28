package com.fangs.loginsignup

import android.content.Context
import java.io.File


class Database{

    companion object {


        fun getMonthlyRecord(context : Context, year : String) : File{

            //get internal storage path
            val path = context.getExternalFilesDir(null)

            //create a folder
            val folderDirectory = File(path, "Monthly Record")

            //check if the file already exist. If not, create a new file
            if (!folderDirectory.exists()) {
                folderDirectory.mkdirs()
            }
            val file = File(folderDirectory, "$year.txt")

            if (!file.exists()) {
                file.createNewFile()
            }
            return file

        }


        fun getTotalBillsFile(context : Context) : File{
            //get internal storage path
            val path = context.getExternalFilesDir(null)

            //create a folder
            val folderDirectory = File(path, "User Data")

            //check if the file already exist. If not, create a new file
            if (!folderDirectory.exists()) {
                folderDirectory.mkdirs()
            }
            val file = File(folderDirectory, "total_bills_file.txt")

            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        }

        fun getTotalSavingsFile(context : Context) : File{
            //get internal storage path
            val path = context.getExternalFilesDir(null)

            //create a folder
            val folderDirectory = File(path, "User Data")

            //check if the file already exist. If not, create a new file
            if (!folderDirectory.exists()) {
                folderDirectory.mkdirs()
            }
            val file = File(folderDirectory, "total_savings_file.txt")

            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        }


        fun getTotalExpenseFile(context : Context) : File{
            //get internal storage path
            val path = context.getExternalFilesDir(null)

            //create a folder
            val folderDirectory = File(path, "User Data")

            //check if the file already exist. If not, create a new file
            if (!folderDirectory.exists()) {
                folderDirectory.mkdirs()
            }
            val file = File(folderDirectory, "total_expense_file.txt")

            if (!file.exists()) {
                file.createNewFile()
            }
            return file
        }

        fun getTotalIncomeFile(context : Context) : File{
            //get internal storage path
            val path = context.getExternalFilesDir(null)

            //create a folder
            val folderDirectory = File(path, "User Data")

            //check if the file already exist. If not, create a new file
            if (!folderDirectory.exists()) {
                folderDirectory.mkdirs()
            }
            val file = File(folderDirectory, "total_income_file.txt")

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

        fun getIncomeFile(context : Context) : File{

            //get internal storage path
            val path = context.getExternalFilesDir(null)

            //create a folder
            val folderDirectory = File(path, "User Data")

            //check if the file already exist. If not, create a new file
            if (!folderDirectory.exists()) {
                folderDirectory.mkdirs()
            }

            val file = File(folderDirectory, "income_file.txt")

            if (!file.exists()) {
                file.createNewFile()
            }
            return file

        }
    }
 }



