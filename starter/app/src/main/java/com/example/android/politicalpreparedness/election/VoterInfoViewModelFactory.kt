package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.data.ElectionsDatasource
import com.example.android.politicalpreparedness.network.models.Division

// Create Factory to generate VoterInfoViewModel with provided election datasource
class VoterInfoViewModelFactory(
    private val electionsDatasource: ElectionsDatasource,
    private val electionId: Int,
    private val electionDivision: Division
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (VoterInfoViewModel(electionsDatasource, electionId, electionDivision) as T)
}
