package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fangs.loginsignup.*
import kotlinx.android.synthetic.main.list_item_expense.view.*

class   ItemAdapter(val expenseActivity : ExpenseActivity, val context : Context, val list : Array<String>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_expense,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        val list = list[position]
        holder.date.text = list

        if (position%2 == 0){
            holder.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    context,R.color.colorLightGray
                )
            )
        }

        else{
            holder.cardView.setBackgroundColor(
                ContextCompat.getColor(
                    context,R.color.colorWhite
                )
            )
        }


    }

    override fun getItemCount(): Int {
        return list.size-1


    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val date = view.tv_show_date
        val cardView = view.cv_item


        init {
            view.setOnClickListener { v: View ->
                val position = adapterPosition
                val expenseDialogBox = ExpenseDialogBox(expenseActivity)
                val support = expenseActivity.supportFragmentManager
                expenseDialogBox.show(support,"expenseDialog")
                Values.currentString = list[position]
                Values.index = position
            }


        }


    }


}