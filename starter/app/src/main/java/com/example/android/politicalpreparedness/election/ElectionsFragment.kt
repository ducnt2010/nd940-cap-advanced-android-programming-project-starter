package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.data.ElectionsDatasource
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.jsonadapter.ElectionAdapter

class ElectionsFragment : Fragment() {

    // Declare ViewModel
    private val viewModel by viewModels<ElectionsViewModel> {
        ElectionsViewModelFactory(ElectionsDatasource(requireActivity().applicationContext))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//Add ViewModel values and create ViewModel

        // Add binding values
        val binding = FragmentElectionBinding.inflate(inflater)

        binding.viewModel = viewModel

        binding.upcomingRecyclerview.adapter =
            ElectionListAdapter(ElectionListAdapter.ElectionListener {
                viewModel.onElectionItemSelected(it)
            })

        binding.savedRecyclerview.adapter =
            ElectionListAdapter(ElectionListAdapter.ElectionListener {
                viewModel.onElectionItemSelected(it)
            })

        viewModel.selectedElection.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                findNavController().navigate(
                    ElectionsFragmentDirections.actionShowInfo(
                        it.id,
                        it.division
                    )
                )
                viewModel.navigateElectionDetailComplete()
            }
        })

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters

        return binding.root
    }

    //TODO: Refresh adapters when fragment loads

}