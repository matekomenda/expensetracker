package hu.bme.aut.hazimobweb.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseItemViewModel(application: Application):AndroidViewModel(application) {

    private val expenseItemDao = AppDatabase.getDatabase(application).expenseItemDAO()
    private val repository: ExpenseItemRepository
    val findAllItems: LiveData<List<ExpenseItem>>
    val getSum: LiveData<Int>

    init{
        repository = ExpenseItemRepository(expenseItemDao)
        findAllItems= repository.findAllItems
        getSum = repository.getSum
    }

    fun insertItem(expenseItem: ExpenseItem){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertItem(expenseItem)
        }
    }

    fun updateItem(expenseItem: ExpenseItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateItem(expenseItem)
        }
    }

    fun deleteItem(expenseItem: ExpenseItem){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(expenseItem)
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ExpenseItem>>{
        return repository.searchDatabase(searchQuery)
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

}