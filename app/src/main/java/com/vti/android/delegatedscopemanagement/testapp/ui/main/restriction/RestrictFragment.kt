package com.vti.android.delegatedscopemanagement.testapp.ui.main.restriction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.LogAdapter
import com.vti.android.delegatedscopemanagement.testapp.common.adapter.data.Log
import com.vti.android.delegatedscopemanagement.testapp.common.layoutmanager.CustomLinearLayoutManager
import com.vti.android.delegatedscopemanagement.testapp.databinding.FragmentRestrictBinding
import com.vti.android.delegatedscopemanagement.testapp.ui.main.restriction.contract.RestrictViewModel
import com.vti.android.delegatedscopemanagement.testapp.ui.main.restriction.contract.impl.RestrictViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestrictFragment : Fragment() {
    private lateinit var binding: FragmentRestrictBinding
    private val vm: RestrictViewModel by viewModels<RestrictViewModelImpl>()
    private lateinit var adapter: LogAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentRestrictBinding.inflate(inflater, container, false).also {
            binding = it
            binding.vm = vm
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEvent()
        setupUi()
    }

    private fun setupUi() {
        vm.getStatus()
        val logs = mutableListOf<Log>()
        adapter = LogAdapter()
        adapter.setListData(logs)
        binding.apply {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = CustomLinearLayoutManager(requireContext())
        }
    }

    private fun handleEvent() {
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
        vm.apply {
            isEditBookmarksEnable().observe(viewLifecycleOwner) { isChecked ->
                binding.editBookmarks.isChecked = isChecked
                restrict()
            }
            isIncognitoEnable().observe(viewLifecycleOwner) { isChecked ->
                binding.incognito.isChecked = isChecked
                restrict()
            }
            log().observe(viewLifecycleOwner) { log ->
                adapter.addLog(log)
                adapter.notifyItemChanged(adapter.itemCount)
                binding.recyclerView.smoothScrollToPosition(adapter.itemCount)
            }
        }
    }
}