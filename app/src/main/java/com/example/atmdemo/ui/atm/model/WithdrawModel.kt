package com.example.atmdemo.ui.atm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "withdraw")
data class WithdrawModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var Money:String
)