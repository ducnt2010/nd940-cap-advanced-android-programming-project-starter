package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.android.politicalpreparedness.data.ElectionsDatasource
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter

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

        // Add binding values
        val binding = FragmentElectionBinding.inflate(inflater)

        binding.lifecycleOwner=this
        binding.viewModel = viewModel

        binding.upcomingRecyclerview.adapter =
            ElectionListAdapter(ElectionListAdapter.ElectionListener {
                viewModel.onElectionItemSelected(it)
            })

        binding.savedRecyclerview.adapter =
            ElectionListAdapter(ElectionListAdapter.ElectionListener {
                viewModel.onElectionItemSelected(it)
            })

//        viewModel.upcomingElections.observe(this, Observer {
//            if (it != null) {
//                (binding.upcomingRecyclerview.adapter as ElectionListAdapter).submitList(it)
//            }
//        })

        viewModel.selectedElection.observe(this, Observer {
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

        viewModel.showMessageInt.observe(this, Observer {
            if (it != null) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters

        return binding.root
    }

    //TODO: Refresh adapters when fragment loads

}