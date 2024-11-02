package com.tetsoft.typego.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    private var _binding: B? = null

    protected val binding: B
        get() = _binding!!

    protected abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = initBinding(inflater, container)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun navigateUp() {
        if (isAdded) {
            findNavController().navigateUp()
        }
    }

    /**
     * Navigates safely to a destination using an action id.
     */
    fun navigateTo(@IdRes action: Int) {
        navigateTo(action, null)
    }

    /**
     * Navigates safely to a destination using an action id and bundle.
     */
    fun navigateTo(@IdRes resId: Int, bundle: Bundle?) {
        val action = findNavController().currentDestination?.getAction(resId)
        if (action != null && findNavController().currentDestination?.id != action.destinationId) {
            findNavController().navigate(resId = resId, args = bundle)
        }
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun showToast(@StringRes message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun makeSnackbar(message: String) : Snackbar {
        return Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
    }
}