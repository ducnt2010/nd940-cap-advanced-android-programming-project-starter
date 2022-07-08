package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    // Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(election: Election)

    // Add select all election query

    @Query("select * from election_table")
    fun getAllElection(): LiveData<List<Election>>

    // Add select single election query
    @Query("select * from election_table where id =:electionId")
     fun getElectionById(electionId: Int): Election?

    // Add delete query
    @Delete
     fun delete(election: Election)

    // Add clear query
    @Query("delete from election_table")
     fun clearAll()

}