package com.example.android.politicalpreparedness

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election

private const val TAG = "BindingAdapters"
@BindingAdapter("listData")
fun bindingRecyclerView(recyclerView: RecyclerView, data: List<Election>?) {
    Log.i(TAG, "bindingRecyclerView:size ${data?.size}")
    val adapter = recyclerView.adapter as ElectionListAdapter
    adapter.submitList(data)
}