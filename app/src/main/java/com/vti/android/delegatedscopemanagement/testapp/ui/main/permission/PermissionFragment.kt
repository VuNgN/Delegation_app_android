package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vti.android.delegatedscopemanagement.testapp.databinding.FragmentPermissionBinding

class PermissionFragment : Fragment() {
    private lateinit var binding: FragmentPermissionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return FragmentPermissionBinding.inflate(inflater, container, false).also {
            binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleEvent()
    }

    private fun handleEvent() {
        binding.apply {
            topAppBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}