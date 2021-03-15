package hu.bme.aut.hazimobweb.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseItemDAO {

    @Query("SELECT * FROM expense_table ORDER BY itemId ASC")
    fun findAllItems(): LiveData<List<ExpenseItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: ExpenseItem)

    @Update
    suspend fun updateItem(item: ExpenseItem)

    @Delete
    suspend fun deleteItem(item: ExpenseItem)

    @Query("SELECT * FROM expense_table WHERE (name LIKE :searchQuery) OR (category LIKE :searchQuery) OR (money LIKE :searchQuery) ")
    fun searchDatabase(searchQuery: String): LiveData<List<ExpenseItem>>

    @Query("SELECT SUM(price) FROM expense_table")
    fun getSum(): LiveData<Int>

    @Query("DELETE FROM expense_table")
    suspend fun deleteAll()

}