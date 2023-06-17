package com.company.pizza.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.company.pizza.databinding.CategoryItemBinding

class CategoryAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categoryList = listOf("Пицца", "Комбо", "Десерты", "Напитки", "Разное")

    interface OnItemClickListener{
        fun onItemClick(view: View)
    }

    inner class CategoryViewHolder(val binding: CategoryItemBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener{
        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onItemClick(itemView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.apply {
            categoryText.text = categoryList[position]
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}