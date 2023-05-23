package com.example.atmdemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.atmdemo.dao.DepositDao
import com.example.atmdemo.ui.atm.model.DepositModel


@Database(entities = [DepositModel::class], version = 1,exportSchema = false)
abstract class DepositDatabase : RoomDatabase() {
    abstract fun depositDao(): DepositDao
}
