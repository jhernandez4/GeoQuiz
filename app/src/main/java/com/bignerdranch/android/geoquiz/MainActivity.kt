package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import kotlin.math.round

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener {view: View ->
            checkAnswer(true)
            checkIsAnswered()
            getScore()
        }

        binding.falseButton.setOnClickListener {view: View ->
            checkAnswer(false)
            checkIsAnswered()
            getScore()
        }

        binding.previousButton.setOnClickListener{
            decreaseCounter()
            checkIsAnswered()
        }

        binding.nextButton.setOnClickListener {
            increaseCounter()
            checkIsAnswered()
        }

        binding.questionTextView.setOnClickListener {
            checkIsAnswered()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun increaseCounter() {
        quizViewModel.moveToNext()
        updateQuestion()
    }

    private fun decreaseCounter() {
        quizViewModel.moveToPrevious()
        updateQuestion()
    }
    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        var isCorrect = -1

        val messageResId = if (userAnswer == correctAnswer) {
            isCorrect = 1
            R.string.correct_toast
        } else {
            isCorrect = 0
            R.string.incorrect_toast
        }

        quizViewModel.markAnswered(isCorrect)

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }

    private fun checkIsAnswered() {
        if (quizViewModel.currentQuestionIsAnswered >= 0) {
            binding.trueButton.isClickable = false
            binding.falseButton.isClickable = false
        }
        else if (quizViewModel.currentQuestionIsAnswered < 0){
            binding.trueButton.isClickable = true
            binding.falseButton.isClickable = true
        }
    }

    private fun getScore(){
        var total = quizViewModel.getTotalScore()

        // check if all questions have been answered
        if (total >= 0) {
            var percentage = round(((total / quizViewModel.questionBankSize) * 100))
            val scoreMessage = "Your score is $percentage%"

            Toast.makeText(this, scoreMessage, Toast.LENGTH_SHORT)
                .show()
        }
    }
}