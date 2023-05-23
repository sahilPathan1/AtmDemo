package com.example.atmdemo.ui.atm

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.atmdemo.R
import com.example.atmdemo.database.DepositDatabase
import com.example.atmdemo.database.WithdrawDatabase
import com.example.atmdemo.databinding.ActivityMainBinding
import com.example.atmdemo.ui.atm.adapter.AtmDepositAdapter
import com.example.atmdemo.ui.atm.adapter.AtmWithdrawAdapter
import com.example.atmdemo.ui.atm.model.DepositModel
import com.example.atmdemo.ui.atm.model.WithdrawModel
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var depositDatabase: DepositDatabase
    lateinit var withdrawDatabase: WithdrawDatabase
    private lateinit var atmDepositAdapter: AtmDepositAdapter
    private var increment2000: Int = 0
    private var increment500: Int = 0
    private var increment100: Int = 0
    private var increment50: Int = 0
    private lateinit var depositList: ArrayList<DepositModel>
    private lateinit var atmWithdrawAdapter: AtmWithdrawAdapter
    private lateinit var withdrawList: ArrayList<WithdrawModel>

    var mainBalance = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        depositDatabase =
            Room.databaseBuilder(applicationContext, DepositDatabase::class.java, "depositDB")
                .build()
        withdrawDatabase =
            Room.databaseBuilder(applicationContext, WithdrawDatabase::class.java, "withdrawDB")
                .build()
    }

    fun depositBtn(view: View) {
        binding.apply {
            depositContainer.visibility = View.VISIBLE
            historyDepositContainer.visibility = View.VISIBLE
            WithDrawContainer.visibility = View.GONE
            historyWithdrawContainer.visibility = View.GONE
            deleteHistory.visibility = View.VISIBLE
        }
    }

    fun withdraw(view: View) {
        binding.apply {
            WithDrawContainer.visibility = View.VISIBLE
            historyWithdrawContainer.visibility = View.VISIBLE
            historyDepositContainer.visibility = View.GONE
            depositContainer.visibility = View.GONE
            deleteHistory.visibility = View.VISIBLE
        }
    }

    fun btnEnterDeposit(view: View) {
        binding.apply {
            if (edtDeposit.text!!.isEmpty()) {
                Toast.makeText(this@MainActivity, "please enter amount", Toast.LENGTH_SHORT)
                    .show()
            } else if (edtDeposit.text.toString().toInt() == 0) {
                Toast.makeText(
                    this@MainActivity,
                    "transaction 0 not be consider",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (edtDeposit.text.toString().toInt() % 50 == 0) {
                mainBalance += edtDeposit.text.toString().toInt()
                Balance.text = mainBalance.toString()


                getNumberOfNotesDeposit(edtDeposit.text.toString().toInt())
                callDepositRecycleView(edtDeposit)
                edtDeposit.text!!.clear()

            } else {
                Toast.makeText(
                    this@MainActivity,
                    "amount should be multiple of 50",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    private fun callDepositRecycleView(edtDeposit: TextInputEditText) {

        GlobalScope.launch {
            depositDatabase.depositDao().insert(DepositModel(0, edtDeposit.text.toString()))
        }
        depositDatabase.depositDao().getDepositHistory().observe(this) {
            depositList = ArrayList()
            Log.d("Data===================", it.toString())
            binding.recyclerViewDepposit.layoutManager = LinearLayoutManager(this)
            depositList.addAll(it)
            atmDepositAdapter = AtmDepositAdapter(depositList)
            binding.recyclerViewDepposit.adapter = atmDepositAdapter
            atmDepositAdapter.notifyDataSetChanged()
        }

    }

    fun btnEnterWithdraw(view: View) {
        binding.apply {
            if (edtWithDraw.text!!.isEmpty()) {

                Toast.makeText(this@MainActivity, "please enter amount", Toast.LENGTH_SHORT).show()

            } else if (edtWithDraw.text.toString().toInt() == 0) {
                Toast.makeText(
                    this@MainActivity,
                    "transaction 0 not be consider",
                    Toast.LENGTH_SHORT
                ).show()

            } else if (mainBalance >= edtWithDraw.text.toString().toInt()) {

                var edtWithdraw = edtWithDraw.text.toString().toInt()

                if (edtWithdraw % 50 == 0) {
                    mainBalance -= edtWithdraw
                    Balance.text = mainBalance.toString()
                    withdrawList = ArrayList()
                    getNumberOfNotesWithdraw(edtWithdraw)
                    callWithdrawRecycleView(edtWithDraw)
                    edtWithDraw.text!!.clear()

                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "amount should be multiple of 50",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "insufficient amount amount", Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun callWithdrawRecycleView(edtWithDraw: TextInputEditText) {

        GlobalScope.launch {
            withdrawDatabase.withdrawDao().insert(WithdrawModel(0, edtWithDraw.text.toString()))
        }

        withdrawDatabase.withdrawDao().getWithdrawHistory().observe(this) {
            withdrawList = ArrayList()
            Log.d("Data===================", it.toString())
            binding.recyclerViewWithdraw.layoutManager = LinearLayoutManager(this)
            withdrawList.addAll(it)
            atmWithdrawAdapter = AtmWithdrawAdapter(withdrawList)
            binding.recyclerViewWithdraw.adapter = atmWithdrawAdapter
            atmWithdrawAdapter.notifyDataSetChanged()
        }

    }


    private fun getNumberOfNotesDeposit(value: Int) {

        var remainder = value

        var count2000 = 0
        var count500 = 0
        var count100 = 0
        var count50 = 0

        if (remainder >= 2000) {
            count2000 = value / 2000
            remainder %= 2000
            increment2000 += count2000
        }


        if (remainder >= 500) {
            count500 = remainder / 500
            remainder %= 500
            increment500 += count500
        }

        if (remainder >= 100) {
            count100 = remainder / 100
            remainder %= 100
            increment100 += count100
        }

        if (remainder >= 50) {
            count50 = remainder / 50
            remainder %= 50
            increment50 += count50
        }

        binding.tvTwoThousendNotes.text = increment2000.toString()
        binding.tvFiveHundredNotes.text = increment500.toString()
        binding.tvThousendNotes.text = increment100.toString()
        binding.tvFiftyNotes.text = increment50.toString()
        println("Number of 2000 notes: $count2000")
        println("Number of 500 notes: $count500")
        println("Number of 100 notes: $count100")
        println("Number of 50 notes: $count50")
    }


    private fun getNumberOfNotesWithdraw(value: Int) {

        var remainder = value

        var count2000 = 0
        var count500 = 0
        var count100 = 0
        var count50 = 0
/*
        if (remainder <= 500 && increment100  !=0 && increment50!=0) {
               count100 = remainder / 500
               remainder %= 500
               increment500 -= count500
           }else{
               binding.edtWithDraw.text = null
               Toast.makeText(this, " please enter multiple of 500", Toast.LENGTH_SHORT).show()

           }

           if (remainder <= 2000 && increment100  !=0 && increment50!= 0 && increment500 !=0) {
               count2000 = remainder / 2000
               remainder %= 2000
               increment2000 -= count2000
           }else{
               binding.edtWithDraw.text = null
               Toast.makeText(this, "please enter multiple of 500", Toast.LENGTH_SHORT).show()
           }
           if (remainder < 100) {
               binding.edtWithDraw.text = null
               Toast.makeText(this, "please enter multiple of 500", Toast.LENGTH_SHORT).show()
           }
*/
        if (value >= 2000 && increment2000 != 0) {
            count2000 = value / 2000
            remainder %= 2000
            increment2000 -= count2000
        }

        if (remainder >= 500 && increment500 != 0) {
            count500 = remainder / 500
            remainder %= 500
            increment500 -= count500
        }

        if (remainder >= 100 && increment100 != 0) {
            count100 = remainder / 100
            remainder %= 100
            increment100 -= count100
        }

        if (remainder >= 50 && increment50 != 0 && increment50 < 0 && increment100 != 0) {
            count50 = remainder / 50
            remainder %= 50
            increment50 -= count50
        }

        binding.tvTwoThousendNotes.text = increment2000.toString()
        binding.tvFiveHundredNotes.text = increment500.toString()
        binding.tvThousendNotes.text = increment100.toString()
        binding.tvFiftyNotes.text = increment50.toString()
        println("Number of 2000 notes: $count2000")
        println("Number of 500 notes: $count500")
        println("Number of 100 notes: $count100")
        println("Number of 50 notes: $count50")
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun deleteHistory(view: View) {
        GlobalScope.launch {
            depositDatabase.depositDao().deleteAll()
            withdrawDatabase.withdrawDao().deleteAll()

        }
    }
}