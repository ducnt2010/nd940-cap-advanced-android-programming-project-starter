package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.data.ElectionsDatasource
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "ElectionsViewModel"
// Construct ViewModel and provide election datasource
class ElectionsViewModel (private val electionsDatasource: ElectionsDatasource): ViewModel() {
    val dummy = mutableListOf<Election>(
        Election(1, " name1", Date(), Division("id", "str", "str")),
        Election(2, " name2", Date(), Division("id", "str", "str")),
        Election(3, " name3", Date(), Division("id", "str", "str")),
        Election(4, " name4", Date(), Division("id", "str", "str")),
        Election(5, " name5", Date(), Division("id", "str", "str")),
        Election(6, " name6", Date(), Division("id", "str", "str")),
        Election(7, " name7", Date(), Division("id", "str", "str"))
    )
    // Create live data val for upcoming elections
    private val _upcomingElections = MutableLiveData<List<Election>>(dummy)
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    // Create live data val for saved elections
    private val _savedElections = MutableLiveData<List<Election>>(dummy)
    val savedElections: LiveData<List<Election>>
        get() = _savedElections

    // select election to navigate to detail screen
    private val _selectedElection = MutableLiveData<Election>()
    val selectedElection: LiveData<Election>
        get() = _selectedElection

    init {
        //viewModelScope.launch {
//            loadUpcomingElections()
//            loadSavedElections()
       // }
    }

    // Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    fun loadUpcomingElections() {
        val dummy = mutableListOf<Election>(
            Election(1, "coming name1", Date(), Division("id", "str", "str")),
            Election(2, "coming name1", Date(), Division("id", "str", "str")),
            Election(3, "coming name1", Date(), Division("id", "str", "str")),
            Election(4, "coming name1", Date(), Division("id", "str", "str"))
        )
        _upcomingElections.postValue( dummy)
        Log.i(TAG, "loadUpcomingElections: ")
    }

    fun loadSavedElections() {
        val dummy = mutableListOf<Election>(
            Election(1, "saved name1", Date(), Division("id", "str", "str")),
            Election(2, "saved name1", Date(), Division("id", "str", "str")),
            Election(3, "saved name1", Date(), Division("id", "str", "str")),
            Election(4, "saved name1", Date(), Division("id", "str", "str"))
        )
        _savedElections.value= dummy
        Log.i(TAG, "loadUpcomingElections: ")

    }


    // Create functions to navigate to saved or upcoming election voter info
    fun onElectionItemSelected(item: Election) {
        _selectedElection.value = item
    }

    fun navigateElectionDetailComplete() {
        _selectedElection.value = null
    }

}