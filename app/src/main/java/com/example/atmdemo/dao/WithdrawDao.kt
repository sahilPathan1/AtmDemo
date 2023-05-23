package com.example.atmdemo.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.atmdemo.ui.atm.model.WithdrawModel

@Dao
interface WithdrawDao {

    @Insert
    suspend fun insert(withdrawModel: WithdrawModel)

    @Query("DELETE FROM withdraw")
    suspend fun deleteAll()

    @Update
    suspend fun update(withdrawModel: WithdrawModel)

    @Query("SELECT  * FROM withdraw")
    fun getWithdrawHistory(): LiveData<List<WithdrawModel>>
}