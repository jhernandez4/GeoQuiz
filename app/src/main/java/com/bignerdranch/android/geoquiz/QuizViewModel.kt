package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_asia, true),
        Question(R.string.question_americas, true)
    )

    private var currentIndex = 0

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val questionBankSize: Int
        get() = questionBank.size

    val currentQuestionIsAnswered: Int
        get() = questionBank[currentIndex].isCorrect
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        currentIndex = (currentIndex - 1) % questionBank.size

        // wrap to last element
        if (currentIndex < 0) {
            currentIndex = questionBank.size - 1
        }
    }

    fun getTotalScore(): Double {
        var total = 0.0

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

        return total
    }


    fun markAnswered(isCorrect: Int){
        questionBank[currentIndex].isCorrect = isCorrect
    }
}