package hu.bme.aut.hazimobweb.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import hu.bme.aut.hazimobweb.R
import hu.bme.aut.hazimobweb.data.ExpenseItem
import hu.bme.aut.hazimobweb.data.ExpenseItemViewModel
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {

    private val mExpenseItemViewModel: ExpenseItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_add){
            insertItemToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertItemToDb() {
        val mName = name_et.text.toString()
        val mCategory = category_et.text.toString()
        val mPrice = price_et.text.toString()
        val mMoney = card_et.text.toString()

        val validation = verifyDataFromUser(mName,mCategory,mPrice,mMoney)

        if(validation){
            val newData = ExpenseItem(
                0,
                mName,
                mCategory,
                mPrice.toInt(),
                mMoney
            )
            mExpenseItemViewModel.insertItem(newData)

            Toast.makeText(requireContext(), "Expense has been successfully added",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(), "\"Missing input\"",Toast.LENGTH_SHORT).show()
            //findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
    }

    private fun verifyDataFromUser(name: String,ct: String,price: String,money: String): Boolean{
        return  if(TextUtils.isEmpty(name) || TextUtils.isEmpty(ct) || TextUtils.isEmpty(money)
            || TextUtils.isEmpty(price) ){
            false
        } else !(name.isEmpty() || ct.isEmpty() || money.isEmpty() ||   price.isEmpty())
    }
}