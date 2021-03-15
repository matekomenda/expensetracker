package hu.bme.aut.hazimobweb.data

import androidx.lifecycle.LiveData


class ExpenseItemRepository (private val expenseItemDAO: ExpenseItemDAO) {


    val findAllItems: LiveData<List<ExpenseItem>> = expenseItemDAO.findAllItems()
    val getSum: LiveData<Int> = expenseItemDAO.getSum()

    suspend fun insertItem(expenseItem: ExpenseItem) {
        expenseItemDAO.insertItem(expenseItem)
    }

    suspend fun updateItem(expenseItem: ExpenseItem) {
        expenseItemDAO.updateItem(expenseItem)
    }

    suspend fun deleteItem(expenseItem: ExpenseItem) {
        expenseItemDAO.deleteItem(expenseItem)
    }

    fun searchDatabase(searchQuery: String): LiveData<List<ExpenseItem>> {
        return expenseItemDAO.searchDatabase(searchQuery)
    }

    suspend fun deleteAll(){
        expenseItemDAO.deleteAll()
    }

}