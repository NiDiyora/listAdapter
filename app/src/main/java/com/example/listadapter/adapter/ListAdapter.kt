package com.example.listadapter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listadapter.*
import com.example.listadapter.databinding.InnerListItemBinding

import com.example.listadapter.databinding.ItemLayoutOneBinding
import com.example.listadapter.databinding.ItemLayoutTwoBinding

class MyListAdapter(
    private val context: Context,
    private val onItemClickListener: (Any) -> Unit = {}
) :
    ListAdapter<ListItem, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_layout_one -> {
                val binding = DataBindingUtil.inflate<ItemLayoutOneBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_layout_one,
                    parent,
                    false
                )
                TypeAViewHolder(binding, context)
            }
            R.layout.item_layout_two -> {
                val binding = DataBindingUtil.inflate<ItemLayoutTwoBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_layout_two,
                    parent,
                    false
                )
                TypeBViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener.invoke(getItem(position))
        }
        when (val item = getItem(position)) {
            is TypeAItem -> {
                (holder as TypeAViewHolder).apply {
                    bind(item)

                }
            }
            is TypeBItem -> (holder as TypeBViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TypeAItem -> R.layout.item_layout_one
            is TypeBItem -> R.layout.item_layout_two
        }
    }
}

class TypeAViewHolder(private val binding: ItemLayoutOneBinding, private val context: Context) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: TypeAItem) {
        binding.item = item
        binding.recycleInner.layoutManager = GridLayoutManager(context,3)
        binding.recycleInner.adapter = InnerListAdapter {
            Toast.makeText(context, it.name, Toast.LENGTH_SHORT).show()
        }.apply {
            submitList(item.innerList)
        }
        binding.executePendingBindings()
    }
}


class TypeBViewHolder(private val binding: ItemLayoutTwoBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: TypeBItem) {
        binding.item = item
        binding.executePendingBindings()
    }
}

class InnerListAdapter(
    private val onInnerListItemClick: (InnerListItem) -> Unit
) :
    ListAdapter<InnerListItem, InnerListAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<InnerListItem>() {
        override fun areItemsTheSame(oldItem: InnerListItem, newItem: InnerListItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: InnerListItem, newItem: InnerListItem): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<InnerListItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.inner_list_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val innerItem = getItem(position)
        holder.bind(innerItem)
        holder.itemView.setOnClickListener { onInnerListItemClick(innerItem) }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.inner_list_item
    }

    inner class ViewHolder(private val binding: InnerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InnerListItem) {
            binding.item = item
        }

    }
}




