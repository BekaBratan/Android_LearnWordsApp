package com.example.exampleandroid

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.exampleandroid.databinding.ActivityLearnWordBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityLearnWordBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityLearnWordBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLearnWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trainer = LearnWordsTrainer()
        showNextQuestion(trainer)

        with(binding) {
            btnContinue.setOnClickListener {
                layoutResult.isVisible = false
                markAnswerNeutral(layoutVariants1, tvVariantNumber1, tvVariantValue1)
                markAnswerNeutral(layoutVariants2, tvVariantNumber2, tvVariantValue2)
                markAnswerNeutral(layoutVariants3, tvVariantNumber3, tvVariantValue3)
                markAnswerNeutral(layoutVariants4, tvVariantNumber4, tvVariantValue4)
                showNextQuestion(trainer)
            }

            btnSkip.setOnClickListener {
                showNextQuestion(trainer)
            }
        }
    }

    private fun showNextQuestion(trainer: LearnWordsTrainer) {
        val firstQuestion: Question? = trainer.getNextQuestion()
        with(binding) {
            if (firstQuestion == null || firstQuestion.variants.size < NUMBER_OF_ANSWERS) {
                tvQuestionWord.isVisible = false
                layoutVariants.isVisible = false
                btnSkip.isVisible = true
                btnSkip.setText(R.string.complete)
                trainer.resetAnswers()
            } else {
                btnSkip.isVisible = true
                tvQuestionWord.isVisible = true
                layoutVariants.isVisible = true
                tvQuestionWord.text = firstQuestion.correctAnswer.original

                tvVariantValue1.text = firstQuestion.variants[0].translate
                tvVariantValue2.text = firstQuestion.variants[1].translate
                tvVariantValue3.text = firstQuestion.variants[2].translate
                tvVariantValue4.text = firstQuestion.variants[3].translate

                layoutVariants1.setOnClickListener {
                    if (trainer.checkAnswer(0)) {
                        markAnswerCorrect(layoutVariants1, tvVariantNumber1, tvVariantValue1)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutVariants1, tvVariantNumber1, tvVariantValue1)
                        showResultMessage(false)
                    }
                }

                layoutVariants2.setOnClickListener {
                    if (trainer.checkAnswer(1)) {
                        markAnswerCorrect(layoutVariants2, tvVariantNumber2, tvVariantValue2)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutVariants2, tvVariantNumber2, tvVariantValue2)
                        showResultMessage(false)
                    }
                }

                layoutVariants3.setOnClickListener {
                    if (trainer.checkAnswer(2)) {
                        markAnswerCorrect(layoutVariants3, tvVariantNumber3, tvVariantValue3)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutVariants3, tvVariantNumber3, tvVariantValue3)
                        showResultMessage(false)
                    }
                }

                layoutVariants4.setOnClickListener {
                    if (trainer.checkAnswer(3)) {
                        markAnswerCorrect(layoutVariants4, tvVariantNumber4, tvVariantValue4)
                        showResultMessage(true)
                    } else {
                        markAnswerWrong(layoutVariants4, tvVariantNumber4, tvVariantValue4)
                        showResultMessage(false)
                    }
                }
            }
        }

    }

    private fun markAnswerNeutral(
        layoutVariants: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {
        layoutVariants.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_containers)
        tvVariantNumber.apply {
            background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_variants)
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.textVariantsColor))
        }
        tvVariantValue.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.textVariantsColor))
    }

    private fun markAnswerWrong(
        layoutVariants: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {
        layoutVariants.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_containers_wrong)
        tvVariantNumber.apply {
            background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_variants_wrong)
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
        }
        tvVariantValue.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.wrong))
    }

    private fun markAnswerCorrect(
        layoutVariants: LinearLayout,
        tvVariantNumber: TextView,
        tvVariantValue: TextView,
    ) {
        layoutVariants.background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_containers_correct)
        tvVariantNumber.apply {
            background = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_rounded_variants_correct)
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
        }
        tvVariantValue.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.correct))
    }

    private fun showResultMessage(isCorrect: Boolean) {
        val color: Int
        val messageText: String
        val resultIconResource: Int

        if (isCorrect) {
            color = ContextCompat.getColor(this, R.color.correct)
            resultIconResource = R.drawable.ic_correct
            messageText = getString(R.string.title_correct)
        } else {
            color = ContextCompat.getColor(this, R.color.wrong)
            resultIconResource = R.drawable.ic_correct
            messageText = getString(R.string.title_wrong)
        }

        with(binding) {
            btnSkip.isVisible = false
            layoutResult.isVisible = true
            btnContinue.setTextColor(color)
            layoutResult.setBackgroundColor(color)
            tvResultMessage.text = messageText
            ivResultIcon.setImageResource(resultIconResource)
        }
    }
}