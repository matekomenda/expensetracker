package hu.bme.aut.hazimobweb.data


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "expense_table")
@Parcelize
data class ExpenseItem(
    @PrimaryKey(autoGenerate = true)
        var itemId: Int,
        var name: String,
        var category: String,
        var price: Int,
        var money: String
): Parcelable