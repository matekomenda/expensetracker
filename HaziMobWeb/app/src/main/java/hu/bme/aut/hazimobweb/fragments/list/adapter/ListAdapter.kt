package hu.bme.aut.hazimobweb.fragments.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.hazimobweb.R
import hu.bme.aut.hazimobweb.data.ExpenseItem
import hu.bme.aut.hazimobweb.fragments.list.ListFragmentDirections
import kotlinx.android.synthetic.main.row_item.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var sumList: Int = 0
    var dataList = emptyList<ExpenseItem>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.tv_name.text = dataList[position].name
        holder.itemView.tv_category.text = dataList[position].category
        holder.itemView.tv_price.text = dataList[position].price.toString()
        holder.itemView.tv_money.text = dataList[position].money


        holder.itemView.row_background.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToUpdateFragment(
                    dataList[position]
                )
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(expenseItem: List<ExpenseItem>){
        this.dataList = expenseItem
        notifyDataSetChanged()
    }

    fun setSum(sum: Int){
        this.sumList = sum
        notifyDataSetChanged()
    }

}