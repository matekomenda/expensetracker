package hu.bme.aut.hazimobweb.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.hazimobweb.R
import hu.bme.aut.hazimobweb.data.ExpenseItemViewModel
import hu.bme.aut.hazimobweb.fragments.list.adapter.ListAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    //get the arg
    private val args: ListFragmentArgs by navArgs()
    private val mExpenseItemViewModel: ExpenseItemViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }
    private var setamount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val recyclerView = view.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.VERTICAL,false)
        swipedelete(recyclerView)

        mExpenseItemViewModel.findAllItems.observe(viewLifecycleOwner, Observer { data ->
            adapter.setData(data)
        })

        mExpenseItemViewModel.getSum.observe(viewLifecycleOwner, Observer { data ->
            adapter.setSum(data)
        })

        setamount = args.number

        view.floatingActionButton.setOnClickListener {
            checkMoney()
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        setHasOptionsMenu(true)

        return view;
    }

    private fun swipedelete(recyclerView: RecyclerView){
        val swipedeletecallback = object : SwipeDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemDelete = adapter.dataList[viewHolder.adapterPosition]
                mExpenseItemViewModel.deleteItem(itemDelete)
                Toast.makeText(requireContext(),"'${itemDelete.name}' has been deleted by Swiping",Toast.LENGTH_SHORT).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipedeletecallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> deleteAll()
            R.id.menu_set_amount -> findNavController().navigate(R.id.action_listFragment_to_amountFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if (query != null) {
            searchThroughDatabase(query)
        }
        return true

    }

    private fun searchThroughDatabase(query: String) {
        var searchQuery: String = query
        searchQuery = "%$searchQuery%"

        mExpenseItemViewModel.searchDatabase(searchQuery).observe(this, Observer { list ->
            list?.let {
                adapter.setData(it)
            }
        })

    }

    fun deleteAll() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.setPositiveButton("Yes") { _, _ ->
            mExpenseItemViewModel.deleteAll()
            Toast.makeText(
                requireContext(),
                "All Items deleted",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.create().show()
    }

    fun checkMoney(){
        if (adapter.sumList > /*10000*/args.number) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("LIMIT!")
            builder.setMessage("You have just reached your limit: ${args.number}")
            builder.setPositiveButton("Ok"){_,_->
            }
            builder.create().show()
        }
    }

}