package org.bmsk.presentation.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.bmsk.presentation.R
import org.bmsk.presentation.databinding.FragmentResultBinding
import org.bmsk.presentation.ui.adapter.ResultListAdapter

@AndroidEntryPoint
class ResultFragment : Fragment() {
    private val viewModel: ResultViewModel by viewModels()
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { ResultListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate<FragmentResultBinding>(
            inflater,
            R.layout.fragment_result,
            container,
            false
        ).apply {
            lifecycleOwner = this@ResultFragment.viewLifecycleOwner
            recyclerView.adapter = adapter
            fragment = this@ResultFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateToCameraFragment() {
        findNavController().navigate(ResultFragmentDirections.actionResultFragmentToCameraFragment())
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataState.collectLatest { uiState ->
                    adapter.submitList(uiState)
                }
            }
        }
    }
}