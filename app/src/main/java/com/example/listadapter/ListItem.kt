package com.example.listadapter

import com.example.listadapter.adapter.InnerListAdapter

sealed class ListItem {
    abstract val id: Long
}

data class TypeAItem(override val id: Long, val name: String, val innerList: List<InnerListItem>) :
    ListItem()

data class TypeBItem(override val id: Long, val imageUrl: String) : ListItem()
data class InnerListItem(val name: String)

