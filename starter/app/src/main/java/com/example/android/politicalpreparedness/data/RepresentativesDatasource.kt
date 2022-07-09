package com.example.android.politicalpreparedness.data

import com.example.android.politicalpreparedness.Result
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class RepresentativesDatasource {
    suspend fun getRepresentatives(address: String): Result<List<Representative>> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val (offices, officials) = CivicsApi.retrofitService.getRepresentatives(address)
                val representativesList = offices.flatMap {
                    it.getRepresentatives(officials)
                }
                Result.Success(representativesList)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
}