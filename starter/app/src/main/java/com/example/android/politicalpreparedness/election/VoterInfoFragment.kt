package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.data.ElectionsDatasource
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {
    private val args: VoterInfoFragmentArgs by navArgs()

    private val viewModel by viewModels<VoterInfoViewModel> {
        VoterInfoViewModelFactory(
            ElectionsDatasource(requireActivity().applicationContext),
            args.argElectionId,
            args.argDivision
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values
        val binding = FragmentVoterInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        viewModel.showMessageInt.observe(this, Observer {
            if (it!=null){
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.onToastMessageShow()
            }
        })

        viewModel.urlToOpen.observe(this, Observer {
            it?.let {
                openUrl(it)
                viewModel.completeOpenUrl()
            }
        })

        return binding.root

    }

    // Create method to load URL intents

    private fun openUrl(url:String){
        val intent=Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}