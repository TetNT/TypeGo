package com.tetsoft.typego.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tetsoft.typego.R
import com.tetsoft.typego.TypeGoApp
import com.tetsoft.typego.adapter.GamesHistoryAdapter
import com.tetsoft.typego.adapter.GamesHistoryAdapter.RecyclerViewOnClickListener
import com.tetsoft.typego.adapter.language.LanguageSpinnerAdapter
import com.tetsoft.typego.adapter.language.LanguageSpinnerItem
import com.tetsoft.typego.data.language.Language
import com.tetsoft.typego.data.language.LanguageList
import com.tetsoft.typego.databinding.FragmentAccountBinding
import com.tetsoft.typego.game.mode.GameOnTime
import com.tetsoft.typego.game.result.GameResultList
import com.tetsoft.typego.storage.GameResultListStorage
import com.tetsoft.typego.ui.activity.ResultActivity
import com.tetsoft.typego.utils.AnimationManager
import com.tetsoft.typego.utils.StringKeys

class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    var selectedLanguage: Language? = null
    private var resultList: GameResultList? = null
    private var resultListStorage: GameResultListStorage? = null
    private var inDescendingOrder = true
    private val binding: FragmentAccountBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resultListStorage = (requireActivity().application as TypeGoApp).resultListStorage
        resultList = resultListStorage!!.get()
        // TODO: find out why it can cause an exception
        try {
            initLanguageSpinner()
        } catch (exception: Exception) {
            Log.e("SPINNER", "onViewCreated: " + exception.message)
            Toast.makeText(requireContext(), "Failed to initialize languages. Please, re-enter the page.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun loadAccountData() {
        setupStatFields()
        updateResults()
        loadLastResultsData()
    }

    private fun setupStatFields() {
        binding.tvAverageWPM.text = getString(R.string.average_wpm_pl, 0)
        binding.tvBestResult.text = getString(R.string.best_result_pl, 0)
        binding.tvTestsPassed.text = getString(R.string.tests_passed_pl, 0)
    }

    private fun getResults(language: Language?, inDescendingOrder: Boolean): GameResultList {
        val byLang = resultList!!.getResultsByLanguage(language!!)
        return if (inDescendingOrder) {
            byLang.inDescendingOrder
        } else byLang
    }

    private fun initLanguageSpinner() {
        val languages = LanguageList().getTranslatableListInAlphabeticalOrder(requireContext())
        // an option to show the whole stats
        val spinnerItem = LanguageSpinnerItem(
            Language(StringKeys.LANGUAGE_ALL),
            requireContext().getString(R.string.ALL),
            R.drawable.ic_language
        )
        languages.add(0, spinnerItem)
        val spinnerAdapter = LanguageSpinnerAdapter(requireContext(), languages)
        binding.spinnerResultsLanguageSelection.adapter = spinnerAdapter
        binding.spinnerResultsLanguageSelection.setSelection(0)
        binding.spinnerResultsLanguageSelection.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedLanguage = binding.spinnerResultsLanguageSelection.getSelectedLanguage()
                    loadAccountData()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
    }

    private fun updateResults() {
        val resultsByLanguage = getResults(selectedLanguage, inDescendingOrder)
        if (resultsByLanguage.isEmpty()) {
            binding.tvPassedTestsInfo.text = getString(R.string.msg_nothing_to_show)
            return
        }
        binding.tvPassedTestsInfo.text = getString(R.string.msg_passed_tests_information)
        binding.tvTestsPassed.text = getString(R.string.tests_passed_pl, resultsByLanguage.size)
        val bestResult = resultsByLanguage.bestResultWpm
        binding.tvBestResult.text = getString(R.string.best_result_pl, bestResult)
        val wpmStr: String
        if (resultsByLanguage.size >= 5) {
            var wpmSum = 0.0
            for (res in resultsByLanguage) wpmSum += res!!.wpm
            wpmStr =
                getString(R.string.average_wpm) + ": " + (wpmSum / resultsByLanguage.size).toInt()
            binding.tvAverageWPM.text = wpmStr
        } else binding.tvAverageWPM.text = getString(R.string.msg_average_wpm_unavailable)
    }

    private fun loadLastResultsData() {
        val resultsByLanguage = getResults(selectedLanguage, inDescendingOrder)
        val listener = RecyclerViewOnClickListener { _: View?, position: Int ->
            val resultInfo = resultsByLanguage[position]
            val intent = Intent(context, ResultActivity::class.java)
            intent.putExtra(StringKeys.TEST_CORRECT_WORDS, resultInfo!!.correctWords)
            intent.putExtra(StringKeys.TEST_CORRECT_WORDS_WEIGHT, resultInfo.score)
            intent.putExtra(StringKeys.TOTAL_WORDS, resultInfo.wordsWritten)
            intent.putExtra(StringKeys.TEST_SETTINGS, resultInfo.gameMode as GameOnTime)
            intent.putExtra(StringKeys.CALLED_FROM_PASSED_RESULTS, true)
            startActivity(intent)
        }
        binding.rvPassedTests.adapter = GamesHistoryAdapter(context, resultsByLanguage, listener)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvPassedTests.layoutManager = linearLayoutManager
        val animationManager = AnimationManager()
        val slideIn: Animation = animationManager.getSlideInAnimation(0f, 50f, 500)
        val fadeIn: Animation = animationManager.getFadeInAnimation(500)
        val animationSet = AnimationSet(false)
        animationSet.addAnimation(slideIn)
        animationSet.addAnimation(fadeIn)
        binding.rvPassedTests.animation = animationSet
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}