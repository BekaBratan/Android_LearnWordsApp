package com.example.exampleandroid

data class Word(
    val original: String,
    val translate: String,
    var learned: Boolean = false,
)

data class Question(
    val variants: List<Word>,
    val correctAnswer: Word,
)

class LearnWordsTrainer {

    private val dictionary = listOf(
        Word("Vagon", "Вагон"),
        Word("Babel fish", "Бабел-рыба"),
        Word("Gargle blaster", "Громоглот"),
        Word("Hyper drive", "Гипердвигатель"),
        Word("Hooloovoo", "Хулуву"),
        Word("Magrathea", "Магратея"),
        Word("Infinite improbability", "Бесконечная вероятность"),
        Word("Hyper space", "Гиперпространство")
    )

    private var currentQuestion: Question? = null

    fun getNextQuestion(): Question? {
        val notLearnedList = dictionary.filter { !it.learned }
        if (notLearnedList.isEmpty()) return null

        val questionWords =
            if (notLearnedList.size < NUMBER_OF_ANSWERS) {
                val learnedList = dictionary.filter { it.learned }.shuffled()
                (notLearnedList.shuffled() + learnedList.take(NUMBER_OF_ANSWERS - notLearnedList.size)).shuffled()
            } else {
                notLearnedList.shuffled().take(NUMBER_OF_ANSWERS)
            }

        val correctAnswer: Word = questionWords.random()

        currentQuestion = Question(
            variants = questionWords,
            correctAnswer = correctAnswer,
        )
        return currentQuestion
    }

    fun checkAnswer(userAnswerIndex: Int?): Boolean {
        return currentQuestion?.let {
            val correctAnswerId = it.variants.indexOf(it.correctAnswer)
            if (correctAnswerId == userAnswerIndex) {
                it.correctAnswer.learned = true
                true
            } else {
                false
            }
        } ?: false
    }

    fun resetAnswers() {
        dictionary.forEach {
            it.learned = false
        }
    }
}

const val NUMBER_OF_ANSWERS: Int = 4
