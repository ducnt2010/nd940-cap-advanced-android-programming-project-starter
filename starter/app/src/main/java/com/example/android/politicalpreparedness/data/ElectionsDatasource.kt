package com.example.android.politicalpreparedness.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.Result
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ElectionsDatasource(
    private val context: Context,
    private val database: ElectionDatabase = ElectionDatabase.getInstance(context)
) {

    suspend fun getUpcomingElections(): Result<List<Election>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(CivicsApi.retrofitService.getUpcomingElections().elections)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getVoterInfo(
        address: String,
        electionId: Int
    ): Result<VoterInfoResponse> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(CivicsApi.retrofitService.getVoterInfo(address, electionId))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getElectionById(id: Int): Election? =
        withContext(Dispatchers.IO) {
            return@withContext database.electionDao.getElectionById(id)
        }


    suspend fun insertElection(election: Election) = withContext(Dispatchers.IO) {
        database.electionDao.insert(election)
    }


    suspend fun deleteElection(election: Election) = withContext(Dispatchers.IO) {
        database.electionDao.delete(election)
    }

    fun getSavedElections() = database.electionDao.getAllElection()


}