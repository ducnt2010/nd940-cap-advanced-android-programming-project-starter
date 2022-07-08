package com.example.android.politicalpreparedness

import android.util.Log
import android.view.View
import android.widget.Button
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

@BindingAdapter("goneIfUrlNull")
fun goneIfUrlNull(view: View, url: String?) {
    view.visibility = if (url == null || url.isEmpty()) View.GONE else View.VISIBLE
}

@BindingAdapter("customText")
fun customText(button: Button, isFollowing: Boolean) {
    Log.i(TAG, "buttonFollowText: $isFollowing")
    val content = if (!isFollowing) {
        button.context.getString(R.string.follow_election)
    } else {
        button.context.getString(R.string.unfollow_election)
    }
    button.text = content
}

@BindingAdapter("goneIfElectionNull")
fun goneIfElectionNull(view: View, election: Election?) {
    view.visibility = if (election == null) View.GONE else View.VISIBLE
}