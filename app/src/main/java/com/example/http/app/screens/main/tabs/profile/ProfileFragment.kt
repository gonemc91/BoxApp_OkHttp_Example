package com.example.http.app.screens.main.tabs.profile


import android.os.Bundle
import android.os.Parcel
import android.view.View
import androidx.fragment.app.viewModels
import com.example.http.app.model.accounts.entities.Account
import com.example.http.app.screens.base.BaseFragment
import com.example.http.app.utils.findTopNavController
import com.example.http.app.utils.observeResults
import com.example.nav_components_2_tabs_exercise.R
import com.example.nav_components_2_tabs_exercise.databinding.FragmentProfileBinding
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment() : BaseFragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override val viewModel by viewModels<ProfileViewModel>()

    constructor(parcel: Parcel) : this() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

        binding.editProfileButton.setOnClickListener { onEditProfileButtonPressed() }
        binding.logoutButton.setOnClickListener { logout() }
        binding.resultView.setTryAgainAction { viewModel.reload() }



        observeAccountDetails()

    }

    private fun observeAccountDetails() {
        val formatter = SimpleDateFormat.getDateTimeInstance()
        viewModel.account.observeResults(this, binding.root, binding.resultView) { account ->
            binding.emailTextView.text = account.email
            binding.usernameTextView.text = account.userName
            binding.createdAtTextView.text = if (account.createdAt == Account.UNKNOWN_CREATED_AT)
                getString(R.string.placeholder)
            else
                formatter.format(Date(account.createdAt))
        }
    }

    private fun onEditProfileButtonPressed() {
        findTopNavController().navigate(R.id.editProfileFragment)
    }


}