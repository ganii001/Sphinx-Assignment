package com.example.sphinxassignment.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sphinxassignment.R
import com.example.sphinxassignment.databinding.RvItemBinding
import com.example.sphinxassignment.network.responses.CityList
import com.example.sphinxassignment.network.responses.LoginResponse

class AdapterFood constructor(
    private val context: Context,
    private val layoutInflater: LayoutInflater = LayoutInflater.from(context),
) : RecyclerView.Adapter<AdapterFood.ViewHolder>() {

    private lateinit var list: List<LoginResponse>

    class ViewHolder(val rvItemBinding: RvItemBinding) :
        RecyclerView.ViewHolder(rvItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                layoutInflater,
                R.layout.rv_item,
                parent,
                false
            )
        )
    }

    fun refresh(list: List<LoginResponse>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val response = list[position].also { holder.rvItemBinding.response = it }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}