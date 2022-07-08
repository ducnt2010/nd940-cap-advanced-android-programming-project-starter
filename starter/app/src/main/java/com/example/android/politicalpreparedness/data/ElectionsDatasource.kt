package com.example.android.politicalpreparedness.data

import android.content.Context
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

}