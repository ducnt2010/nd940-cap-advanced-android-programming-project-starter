package com.example.android.politicalpreparedness

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election

private const val TAG = "BindingAdapters"

@BindingAdapter("listData")
fun bindingRecyclerView(recyclerView: RecyclerView, data: List<Election>?) {
    Log.i(TAG, "bindingRecyclerView:size $data")
    val adapter = recyclerView.adapter as ElectionListAdapter
    adapter.submitList(data)
}

@BindingAdapter("isShowLoading")
fun isShowLoading(view: View, isShow: Boolean) {
    Log.i(TAG, "isShowLoading: $isShow")
    view.visibility = if (isShow) View.VISIBLE else View.GONE
}