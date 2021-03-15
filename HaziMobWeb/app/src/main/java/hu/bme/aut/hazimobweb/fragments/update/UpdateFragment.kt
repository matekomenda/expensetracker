package hu.bme.aut.hazimobweb.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hu.bme.aut.hazimobweb.R
import hu.bme.aut.hazimobweb.data.ExpenseItem
import hu.bme.aut.hazimobweb.data.ExpenseItemViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mExpenseItemViewModel: ExpenseItemViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)

        setHasOptionsMenu(true)
        view.act_name_et.setText(args.currentItem.name)
        view.act_category_et.setText(args.currentItem.category)
        view.act_price_et.setText(args.currentItem.price.toString())
        view.act_card_et.setText(args.currentItem.money)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete '${args.currentItem.name}'?")
        builder.setMessage("Are you sure you want to remove: '${args.currentItem.name}'?")
        builder.setPositiveButton("Yes") { _, _ ->
            mExpenseItemViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                requireContext(),
                " ${args.currentItem.name} has been successfully removed",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.create().show()
    }

    private fun updateItem() {
        val name = act_name_et.text.toString()
        val category = act_category_et.text.toString()
        val price = act_price_et.text.toString()
        val money = act_card_et.text.toString()

        val validation = verifyDataFromUser(name,category,price,money)
        if(validation){
            val updateItem = ExpenseItem(
                args.currentItem.itemId,
                name,
                category,
                price.toInt(),
                money
            )
            mExpenseItemViewModel.updateItem(updateItem)
            Toast.makeText(requireContext(),"Expense has been successfully updated",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }else{
            Toast.makeText(requireContext(),"Missing input",Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyDataFromUser(name: String,ct: String,price: String,money: String): Boolean{
        return  if(TextUtils.isEmpty(name) || TextUtils.isEmpty(ct) || TextUtils.isEmpty(money)
            || TextUtils.isEmpty(price)) {
            false
        } else !(name.isEmpty() || ct.isEmpty() || money.isEmpty() || price.isEmpty())

    }
}