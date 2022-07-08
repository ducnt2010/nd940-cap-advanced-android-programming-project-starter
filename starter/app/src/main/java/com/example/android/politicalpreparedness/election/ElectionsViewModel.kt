package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.Result
import com.example.android.politicalpreparedness.data.ElectionsDatasource
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

private const val TAG = "ElectionsViewModel"

// Construct ViewModel and provide election datasource
class ElectionsViewModel(private val electionsDatasource: ElectionsDatasource) : ViewModel() {

    // Create live data val for upcoming elections
    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    // Create live data val for saved elections
    private val _savedElections = MutableLiveData<List<Election>>()
    val savedElections: LiveData<List<Election>>
        get() = _savedElections

    // select election to navigate to detail screen
    private val _selectedElection = MutableLiveData<Election>()
    val selectedElection: LiveData<Election>
        get() = _selectedElection

    private val _showMessageInt = MutableLiveData<Int>()
    val showMessageInt: LiveData<Int>
        get() = _showMessageInt

    private val _showLoading = MutableLiveData<Boolean>()
    val showLoading: LiveData<Boolean>
        get() = _showLoading

    init {
        getUpcomingElections()
    }

    // Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    fun getUpcomingElections() {
        _showLoading.value = true
        _showMessageInt.value = R.string.loading_upcoming_elections_message
        viewModelScope.launch {
            val result = electionsDatasource.getUpcomingElections()

            when (result) {
                is Result.Success -> {
                    _upcomingElections.value = result.data
                    _showMessageInt.value = R.string.load_upcoming_elections_success

                }
                is Result.Error -> {
                    _upcomingElections.value = emptyList()
                    _showMessageInt.value = R.string.load_upcoming_elections_error
                    Log.i(TAG, "getUpcomingElections ERROR: ${result.exception.toString()}")
                }
            }

            _showLoading.value=false
        }
    }


    // Create functions to navigate to saved or upcoming election voter info
    fun onElectionItemSelected(item: Election) {
        _selectedElection.value = item
    }

    fun navigateElectionDetailComplete() {
        _selectedElection.value = null
    }

}