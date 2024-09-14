package com.tetsoft.typego.ui.fragment.keynotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.tetsoft.typego.R
import com.tetsoft.typego.databinding.FragmentKeyNotesBinding
import com.tetsoft.typego.ui.fragment.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.IllegalStateException

class KeyNotesFragment : BaseFragment<FragmentKeyNotesBinding>() {

    private val viewModel: KeyNotesViewModel by hiltNavGraphViewModels(R.id.main_navigation)

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentKeyNotesBinding {
        return FragmentKeyNotesBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.closeCarousel.setOnClickListener {
            navigateUp()
            return@setOnClickListener
        }
        val showAll = arguments?.getBoolean("show_all")
        try {
            viewModel.init(showAll ?: true)
        } catch (ise: IllegalStateException) {
            navigateUp()
            return
        }
        viewModel.initIndicators(binding.carouselPageIndicators)
        binding.featureImage.setImageResource(viewModel.currentFeature().imageId)
        binding.featureHeader.text = getString(viewModel.currentFeature().featureHeader)
        binding.featureDescription.text = getString(viewModel.currentFeature().featureDescription)
        if (!viewModel.canGoNext()) binding.buttonNextFeature.text = getString(R.string.close)
        binding.buttonNextFeature.setOnClickListener {
            with(viewModel) {
                if (!canGoNext()) {
                    findNavController().navigateUp()
                    return@setOnClickListener
                }
                binding.featureInformationSection.animation = getRightToLeftOutAnimationSet(TRANSITION_DURATION, resources.displayMetrics.widthPixels)
                switchIndicatorState(binding.carouselPageIndicators, false)
                nextFeature()
                updateUi(getNextFeatureInAnimationSet(TRANSITION_DURATION, resources.displayMetrics.widthPixels))
            }
        }
        binding.buttonPreviousFeature.setOnClickListener {
            if (!viewModel.canGoBack()) {
                return@setOnClickListener
            }
            with(viewModel) {
                binding.featureInformationSection.animation = getLeftToRightOutAnimationSet(TRANSITION_DURATION, resources.displayMetrics.widthPixels)
                switchIndicatorState(binding.carouselPageIndicators, false)
                previousFeature()
                updateUi(getPreviousFeatureInAnimationSet(TRANSITION_DURATION, resources.displayMetrics.widthPixels))
            }
        }
    }

    private fun updateUi(animation: AnimationSet) {
        with(viewModel) {
            switchIndicatorState(binding.carouselPageIndicators, true)
            lifecycleScope.launch {
                binding.buttonNextFeature.isClickable = false
                binding.buttonPreviousFeature.isClickable = false
                delay(TRANSITION_DURATION)
                binding.featureImage.setImageResource(currentFeature().imageId)
                binding.featureHeader.text = getString(currentFeature().featureHeader)
                binding.featureDescription.text = getString(currentFeature().featureDescription)
                binding.featureInformationSection.animation = animation
                binding.buttonNextFeature.isClickable = true
                binding.buttonPreviousFeature.isClickable = true
            }

            if (!canGoNext()) binding.buttonNextFeature.text = getString(R.string.close)
            else binding.buttonNextFeature.text = getString(R.string.next)
            binding.buttonPreviousFeature.isEnabled = canGoBack()
        }
    }

    private companion object {
        const val TRANSITION_DURATION = 250L
    }
}