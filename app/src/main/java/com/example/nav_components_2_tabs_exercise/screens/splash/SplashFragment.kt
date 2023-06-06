package com.example.nav_components_2_tabs_exercise.screens.splash

import android.os.Build.VERSION_CODES.S
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.nav_components_2_tabs_exercise.R
import com.example.nav_components_2_tabs_exercise.Repositories
import com.example.nav_components_2_tabs_exercise.databinding.FragmentSplashBinding
import com.example.nav_components_2_tabs_exercise.utils.observeEvent
import com.example.nav_components_2_tabs_exercise.utils.viewModelCreator

class SplashFragment: Fragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding
    private val viewModel by viewModelCreator { SplashViewModel(Repositories.accountRepository)}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)

        //just some animations example
        renderAnimations()

        viewModel.launchMainScreenEvent.observeEvent(viewLifecycleOwner){ launchMainScreen(it) }

    }

    private fun launchMainScreen(isSignedIn: Boolean){
        TODO("Launch MainActivity here and send isSignedIn flag to it")
    }

    private fun renderAnimations(){
        binding.loadingIndicator.alpha = 0f
        binding.loadingIndicator.animate()
            .alpha(0.7f)
            .setDuration(1000)
            .start()

        binding.pleaseWaitTextView.alpha = 0f
        binding.pleaseWaitTextView.animate()
            .alpha(1f)
            .setStartDelay(500)
            .setDuration(1000)
            .start()
    }


}