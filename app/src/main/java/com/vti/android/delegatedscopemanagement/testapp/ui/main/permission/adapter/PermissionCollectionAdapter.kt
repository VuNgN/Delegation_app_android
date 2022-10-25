package com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.grantstate.GrantStateFragment
import com.vti.android.delegatedscopemanagement.testapp.ui.main.permission.policy.PolicyFragment

class PermissionCollectionAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragmentPolicy = PolicyFragment()
        val fragmentGrantState = GrantStateFragment()
        return when (position) {
            0 -> fragmentGrantState
            1 -> fragmentPolicy
            else -> fragmentGrantState
        }
    }
}