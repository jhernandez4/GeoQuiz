package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import kotlin.math.round

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
            getScore()
        }

        binding.falseButton.setOnClickListener {view: View ->
            checkAnswer(false)
            checkAnswered(currentIndex)
            getScore()
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

        var isCorrect = -1

        val messageResId = if (userAnswer == correctAnswer) {
            isCorrect = 1
            R.string.correct_toast
        } else {
            isCorrect = 0
            R.string.incorrect_toast
        }

        markAnswered(currentIndex, isCorrect)

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }

    private fun markAnswered(index: Int, isCorrect: Int) {
       questionBank[index].isCorrect = isCorrect
    }

    private fun checkAnswered(index: Int) {
        if (questionBank[index].isCorrect >= 0) {
            binding.trueButton.isClickable = false
            binding.falseButton.isClickable = false
        }
        else if (questionBank[index].isCorrect < 0){
            binding.trueButton.isClickable = true
            binding.falseButton.isClickable = true
        }
    }

    private fun getScore(){
        var total = 0.0
        var percentage = 0.0

        for(question in questionBank){
            if (question.isCorrect < 0){
                total = -1.0
                break
            }
            else {
                total += question.isCorrect
                Log.d(TAG, "$total")
            }
        }

        // check if all questions have been answered
        if (total >= 0) {
            percentage = round((total / questionBank.size) * 100)
            val scoreMessage = "Your score is $percentage%"

            Toast.makeText(this, scoreMessage, Toast.LENGTH_SHORT)
                .show()
        }
    }
}