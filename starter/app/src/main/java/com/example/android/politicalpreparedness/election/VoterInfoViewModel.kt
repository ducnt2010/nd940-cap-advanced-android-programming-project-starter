package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.Result
import com.example.android.politicalpreparedness.data.ElectionsDatasource
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.jsonadapter.ElectionAdapter
import com.example.android.politicalpreparedness.network.models.AdministrationBody
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

private const val TAG = "VoterInfoViewModel"

class VoterInfoViewModel(
    private val electionsDatasource: ElectionsDatasource,
    private val electionId: Int,
    private val electionDivision: Division
) : ViewModel() {

    //TODO: Add live data to hold voter info

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    //    private val _administrationBody = MutableLiveData<AdministrationBody>()
//    val administrationBody: LiveData<AdministrationBody>
//        get() = _administrationBody
    private val _election = MutableLiveData<Election>()
    val election: LiveData<Election>
        get() = _election

    private val _votingLocationUrl = MutableLiveData<String>()
    val votingLocationUrl: LiveData<String>
        get() = _votingLocationUrl

    private val _ballotInformationUrl = MutableLiveData<String>()
    val ballotInformationUrl: LiveData<String>
        get() = _ballotInformationUrl

    private val _electionInfoUrl = MutableLiveData<String>()
    val electionInfoUrl: LiveData<String>
        get() = _electionInfoUrl

    private val _urlToOpen = MutableLiveData<String>()
    val urlToOpen: LiveData<String>
        get() = _urlToOpen

    private val _showMessageInt = MutableLiveData<Int>()
    val showMessageInt: LiveData<Int>
        get() = _showMessageInt

    private val _isFollowing = MutableLiveData<Boolean>()
    val isFollowing: LiveData<Boolean>
        get() = _isFollowing


    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */
    init {
        loadVoterInfo()
        checkLocalElectionById()
    }

    fun loadVoterInfo() {
        Log.i(TAG, "loadVoterInfo: Division= $electionDivision ")

        // Test
//        val electionAdapter=ElectionAdapter()
//        val division1=electionAdapter.divisionFromJson("ocd-division/country:us/district:dc")
//        val division2=electionAdapter.divisionFromJson("ocd-division/country:us/state:dc")
//
//        Log.i(TAG, "loadVoterInfo: TEST division1= $division1")
//        Log.i(TAG, "loadVoterInfo: TEST division2= $division2")

        //

        viewModelScope.launch {
            val address = "${electionDivision.state},${electionDivision.country}"
            val result = electionsDatasource.getVoterInfo(address, electionId)
            Log.i(TAG, "loadVoterInfo: address= $address  id= $electionId ")

            when (result) {
                is Result.Success -> {
                    _election.value = result.data.election
                    val administrationBody =
                        result.data.state?.get(0)?.electionAdministrationBody
                    administrationBody?.let {
                        _votingLocationUrl.value = it.votingLocationFinderUrl
                        _ballotInformationUrl.value = it.ballotInfoUrl
                        _electionInfoUrl.value = it.electionInfoUrl
                    }

                    Log.i(TAG, "loadVoterInfo Success: $administrationBody")
                }
                is Result.Error -> {
                    _showMessageInt.value = R.string.could_not_load_voter_info
                    Log.i(TAG, "loadVoterInfo Error : ${result.exception}")
                }
            }
        }
    }

    private fun checkLocalElectionById() {
        viewModelScope.launch {
            _isFollowing.value = electionsDatasource.getElectionById(electionId) != null
        }
    }

    fun onFollowButtonClick() {
        viewModelScope.launch {
            if (_isFollowing.value!!) { // is following, need to unfollow when click
                unfollowElection()
            } else {
                followElection()
            }
            checkLocalElectionById()
        }
    }

    private suspend fun unfollowElection() {
        electionsDatasource.deleteElection(_election.value!!)
    }

    private suspend fun followElection() {
        electionsDatasource.insertElection(_election.value!!)
    }

    fun onToastMessageShow() {
        _showMessageInt.value = null
    }

    fun onUrlClick(url: String?) {
        Log.i(TAG, "onUrlClick: $url")
        _urlToOpen.value = url
    }

    fun completeOpenUrl() {
        _urlToOpen.value = null
    }
}