package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.data.ElectionsDatasource

// Create Factory to generate ElectionViewModel with provided election datasource
class ElectionsViewModelFactory(private val electionsDatasource: ElectionsDatasource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        (ElectionsViewModel(electionsDatasource) as T)
}
