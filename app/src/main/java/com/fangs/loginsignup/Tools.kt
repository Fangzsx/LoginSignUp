package com.fangs.loginsignup

import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter

class Tools {

    companion object{



        // overwrite text file
        fun writeToFile(file: File, textToBeInserted: String) {
            try {
                val fileWriter = FileWriter(file)
                val bufferWriter = BufferedWriter(fileWriter)
                bufferWriter.write(textToBeInserted)
                bufferWriter.close()


            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        }

        // for reading file
        fun readToFile(file: File): String {

            return file.readText()
        }

        // for appending text in file

        fun appendTextToFile(file : File, textToBeInserted : String){
            file.appendText(textToBeInserted)
        }

        fun writeToFileWithSpace(file :File, textTobeInserted : String){
            file.appendText(textTobeInserted)
        }

    }

}