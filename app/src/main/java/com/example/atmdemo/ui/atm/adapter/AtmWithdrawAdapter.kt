package com.example.atmdemo.ui.atm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.atmdemo.databinding.ItemWithdrawListBinding
import com.example.atmdemo.ui.atm.model.WithdrawModel

class AtmWithdrawAdapter(private var itemList: List<WithdrawModel>) : RecyclerView.Adapter<AtmWithdrawAdapter.ViewHolder>() {

    init {
        itemList = itemList.reversed()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWithdrawListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvWithdrawMoney.text = "₹ ${itemList[position].Money}"
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(var binding: ItemWithdrawListBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}