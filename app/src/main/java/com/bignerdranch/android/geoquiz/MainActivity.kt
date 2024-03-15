package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_asia, true),
        Question(R.string.question_americas, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener {view: View ->
            checkAnswer(true)
            checkAnswered(currentIndex)
        }

        binding.falseButton.setOnClickListener {view: View ->
            checkAnswer(false)
            checkAnswered(currentIndex)
        }

        binding.previousButton.setOnClickListener{
            decreaseCounter()
            checkAnswered(currentIndex)
        }

        binding.nextButton.setOnClickListener {
            increaseCounter()
            checkAnswered(currentIndex)
        }

        binding.questionTextView.setOnClickListener {
            increaseCounter()
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
        currentIndex = (currentIndex + 1) % questionBank.size
        updateQuestion()
    }

    private fun decreaseCounter() {
        currentIndex = (currentIndex - 1) % questionBank.size

        // wrap to last element
        if (currentIndex < 0) {
            currentIndex = questionBank.size - 1
        }

        updateQuestion()
    }
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        markAnswered(currentIndex)

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }

    private fun markAnswered(index: Int) {
       questionBank[index].isAnswered = true
    }

    private fun checkAnswered(index: Int) {
        if (questionBank[index].isAnswered) {
            binding.trueButton.isClickable = false
            binding.falseButton.isClickable = false
        }
        else {
            binding.trueButton.isClickable = true
            binding.falseButton.isClickable = true
        }
    }
}