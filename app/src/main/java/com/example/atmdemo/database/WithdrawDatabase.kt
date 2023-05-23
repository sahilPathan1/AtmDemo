package com.example.atmdemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.atmdemo.dao.WithdrawDao
import com.example.atmdemo.ui.atm.model.WithdrawModel

@Database(entities = [WithdrawModel::class], version = 1,exportSchema = false)
abstract class WithdrawDatabase : RoomDatabase() {
    abstract fun withdrawDao(): WithdrawDao
}
