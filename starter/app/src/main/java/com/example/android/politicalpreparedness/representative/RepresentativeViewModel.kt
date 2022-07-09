package com.example.android.politicalpreparedness.representative

import android.util.Log
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.Result
import com.example.android.politicalpreparedness.data.ElectionsDatasource
import com.example.android.politicalpreparedness.data.RepresentativesDatasource
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

private const val TAG = "RepresentativeViewModel"

class RepresentativeViewModel(private val representativesDatasource: RepresentativesDatasource = RepresentativesDatasource()) :
    ViewModel() {

    // Establish live data for representatives and address\
    private val _representativesList = MutableLiveData<List<Representative>>()
    val representativesList: LiveData<List<Representative>>
        get() = _representativesList

    // Livedata for address

    var line1Address: String = ""
    var line2Address: String? = ""
    var cityAddress: String = ""
    var stateAddress: String = ""
    var zipAddress: String = ""

    // Create function to fetch representatives from API from a provided address
    fun getRepresentatives(
        address: Address = Address(
            line1Address,
            line2Address,
            cityAddress,
            stateAddress,
            zipAddress
        )
    ) {
        viewModelScope.launch {
            Log.i(TAG, "getRepresentatives: Address= $address")
            val result = representativesDatasource.getRepresentatives(address.toFormattedString())
            when (result) {
                is Result.Success -> {
                    _representativesList.value = result.data
                    Log.i(TAG, "getRepresentatives: Success= ${result.data}")

                }
                is Result.Error -> {
                    Log.i(TAG, "getRepresentatives: ERRO ${result.exception}")
                }
            }
        }
    }

    fun onStateItemSelected(state: String) {
        stateAddress = state
    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}
