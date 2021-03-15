package hu.bme.aut.hazimobweb.fragments.amount

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.hazimobweb.R
import kotlinx.android.synthetic.main.fragment_amount.*


class AmountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_amount, container, false)
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.amount_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
           R.id.menu_amount -> setAmount()
        }
        return super.onOptionsItemSelected(item)
    }

    fun setAmount(){
            val mAmount =  amount_et.text.toString().toInt()
            //Log.d("val",mAmount.toString())
            val action =  AmountFragmentDirections.actionAmountFragmentToListFragment(mAmount)
            findNavController().navigate(action)
    }
}