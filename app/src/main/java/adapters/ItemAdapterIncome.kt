package adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fangs.loginsignup.*
import kotlinx.android.synthetic.main.list_item_income.view.*

class ItemAdapterIncome(val incomeActivity : IncomeActivity, val list : Array<String>) : RecyclerView.Adapter<ItemAdapterIncome.ViewHolder>() {

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){


        val text  = view.tv_show_income
        val card  = view.cv_income_item

        init{

            view.setOnClickListener {
                val position = adapterPosition
                Values.currentString = list[position]
                Values.index = position


                // call the income popup dialog
                val incomeDialogBox = IncomeDialogBox(incomeActivity)
                val support = incomeActivity.supportFragmentManager
                incomeDialogBox.show(support, "incomeDialog")



            }



        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {



        return ViewHolder(
            LayoutInflater.from(incomeActivity).inflate(
                R.layout.list_item_income,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = list[position]
        if(list.isNotEmpty()){
            holder.text.text  = currentItem
        }
        if (position%2 == 0){
            holder.card?.setBackgroundColor(
                ContextCompat.getColor(
                    incomeActivity,R.color.colorAccent3
                )
            )
        }

        else{
            holder.card.setBackgroundColor(
                ContextCompat.getColor(
                    incomeActivity,R.color.colorAccent4
                )
            )
        }


    }

    override fun getItemCount(): Int {
        return list.size-1
    }
}