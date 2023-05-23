package com.example.atmdemo.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.atmdemo.ui.atm.model.DepositModel

@Dao
interface DepositDao {

    @Insert
    suspend fun insert(depositModel: DepositModel)

    @Query("DELETE FROM deposit")
    suspend fun deleteAll()

    @Update
    suspend fun update(depositModel: DepositModel)

    @Query("SELECT  * FROM deposit")
    fun getDepositHistory(): LiveData<List<DepositModel>>
}