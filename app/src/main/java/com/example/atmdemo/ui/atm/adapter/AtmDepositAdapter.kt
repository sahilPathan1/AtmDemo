package com.example.atmdemo.ui.atm.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.atmdemo.databinding.ItemDepositListBinding
import com.example.atmdemo.ui.atm.model.DepositModel


class AtmDepositAdapter(private var itemList: List<DepositModel>) : RecyclerView.Adapter<AtmDepositAdapter.ViewHolder>() {

        init {
            itemList = itemList.reversed()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ItemDepositListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.binding.tvDepositMoeny.text = " ₹  ${itemList[position].Money}"
            Log.d("Tag","jjfhudfhg")
        }

        override fun getItemCount(): Int {
            return itemList.size
        }
        inner class ViewHolder(var binding: ItemDepositListBinding) : RecyclerView.ViewHolder(binding.root) {}
    }


