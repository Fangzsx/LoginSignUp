package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fangs.loginsignup.R
import kotlinx.android.synthetic.main.list_item_records.view.*

class ItemAdapterMonthlyRecord(val context : Context, val list : ArrayList<String>) : RecyclerView.Adapter<ItemAdapterMonthlyRecord.ViewHolder>() {

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val month = view.tv_record_month
        val total = view.tv_record_total
        val card = view.cv_record_item
        val counter = view.tv_counter
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemAdapterMonthlyRecord.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_records, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemAdapterMonthlyRecord.ViewHolder, position: Int) {
        val currentItem = list[position]
        val currentItemArray = currentItem.split("@")

        if(currentItem.isNotEmpty()){
            val currentMonth = currentItemArray[0]
            val currentTotal = currentItemArray[1]
            holder.month.text = currentMonth
            holder.total.text = currentTotal
            holder.counter.text = (position+1).toString()
        }


        if (position%2 == 0){
            holder.card.setBackgroundColor(
                ContextCompat.getColor(
                    context,R.color.colorAccent3
                )
            )
        }

        else{
            holder.card.setBackgroundColor(
                ContextCompat.getColor(
                    context,R.color.colorAccent4
                )
            )
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }


}