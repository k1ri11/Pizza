package com.company.pizza.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.company.pizza.data.models.MenuModelItem
import com.company.pizza.databinding.PizzaItemBinding

class DiffUtilPizzaCallback(

    private var oldList: List<MenuModelItem>,
    private var newList: List<MenuModelItem>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[oldItemPosition]
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[oldItemPosition]
        return oldItem == newItem
    }
}

class PizzaAdapter : RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder>() {

    var pizzaList: List<MenuModelItem> = emptyList()
        set(newValue) {
            val diffCallback = DiffUtilPizzaCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    inner class PizzaViewHolder(val binding: PizzaItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val binding = PizzaItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PizzaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {
        val prefix = "от "
        val postfix = " р"
        val currentItem = pizzaList[position]
        holder.binding.apply {
            name.text = currentItem.name
            Glide.with(this.root).load(currentItem.img).centerCrop().into(pizzaImg)
            description.text = currentItem.description
            price.text = prefix.plus(currentItem.price.toString()).plus(postfix)
        }
    }

    override fun getItemCount(): Int {
        return pizzaList.size
    }
}