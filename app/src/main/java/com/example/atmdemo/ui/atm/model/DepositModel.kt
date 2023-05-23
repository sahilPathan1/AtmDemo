package com.example.atmdemo.ui.atm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
class DepositModel(var Money:String) {
}
*/

@Entity(tableName = "deposit")
data class DepositModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var Money:String
)
