package com.example.listadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.listadapter.adapter.MyListAdapter
import com.example.listadapter.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val listItem = listOf(
            TypeAItem(
                1,
                "NAME",
                listOf(InnerListItem("greejesh"), InnerListItem("himanshu"), InnerListItem("tommy"))
            ),
            TypeAItem(
                2,
                "vishal",
                listOf(InnerListItem("kartik"), InnerListItem("jaimin"), InnerListItem("jigar"))
            ),
            TypeBItem(
                Random.nextLong(),
                "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg"
            ),
            TypeAItem(
                2,
                "vishal",
                listOf(InnerListItem("shivangi"), InnerListItem("hasti"), InnerListItem("pooja"))
            ),
            TypeAItem(
                3,
                "nirgav",
                listOf(
                    InnerListItem("fsgs"),
                    InnerListItem("fsdfgasfddfvgs"),
                    InnerListItem("12115ghdnd")
                )
            )
        )
        binding.recycle.adapter = MyListAdapter(this) {
            when (it) {
                is TypeAItem -> {}
            }
            Log.d("TAG", "onCreate: $it")
        }.apply {
            submitList(listItem)
        }
    }
}