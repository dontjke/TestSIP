package com.stepanov.testsip.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stepanov.testsip.R
import com.stepanov.testsip.databinding.UsersRecyclerItemBinding
import com.stepanov.testsip.repository.dto.ResponseApiItem

class UsersRecyclerViewAdapter(
    private val onUserClickListener: OnUserClickListener,
    private var data: List<ResponseApiItem> = listOf()
) : RecyclerView.Adapter<UsersRecyclerViewAdapter.UsersHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setData(dataNew: List<ResponseApiItem>) {
        this.data = dataNew
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersHolder {
        val binding = UsersRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsersHolder(binding.root)
    }

    override fun getItemCount(): Int = data.size


    override fun onBindViewHolder(holder: UsersHolder, position: Int) {
        holder.bind(data[position])
    }


    inner class UsersHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(users: ResponseApiItem) {
            UsersRecyclerItemBinding.bind(itemView).apply {
                nameTextView.text = itemView.context.getString(R.string.name_adapter, users.name)

                phoneTextView.text = itemView.context.getString(R.string.phone_adapter, users.phone)
                root.setOnClickListener {
                    onUserClickListener.onItemClick(users.id - 1)
                }
            }
        }
    }
}