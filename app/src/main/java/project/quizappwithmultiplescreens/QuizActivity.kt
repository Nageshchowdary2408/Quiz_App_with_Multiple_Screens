package project.quizappwithmultiplescreens

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuizActivity : AppCompatActivity() {

    private val questions = arrayOf(
        "Android is based on which OS?",
        "Which language is used for Android?",
        "Which file defines UI layout?"
    )

    private val options = arrayOf(
        arrayOf("Windows", "Linux", "Mac", "iOS"),
        arrayOf("Java", "Kotlin", "Python", "Swift"),
        arrayOf("Java File", "Manifest", "XML", "Gradle")
    )

    private val answers = arrayOf(1, 1, 2)

    private var currentQuestion = 0
    private var score = 0
    private var timeLeft = 10

    private lateinit var timer: CountDownTimer
    
    private lateinit var txtQuestion: TextView
    private lateinit var rb1: RadioButton
    private lateinit var rb2: RadioButton
    private lateinit var rb3: RadioButton
    private lateinit var rb4: RadioButton
    private lateinit var btnNext: Button
    private lateinit var txtTimer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        
        txtQuestion = findViewById(R.id.txtQuestion)
        rb1 = findViewById(R.id.rb1)
        rb2 = findViewById(R.id.rb2)
        rb3 = findViewById(R.id.rb3)
        rb4 = findViewById(R.id.rb4)
        btnNext = findViewById(R.id.btnNext)
        txtTimer = findViewById(R.id.txtTimer)

        loadQuestion()
    }

    private fun loadQuestion() {
        txtQuestion.text = questions[currentQuestion]
        rb1.text = options[currentQuestion][0]
        rb2.text = options[currentQuestion][1]
        rb3.text = options[currentQuestion][2]
        rb4.text = options[currentQuestion][3]

        // Reset radio buttons
        rb1.isChecked = false
        rb2.isChecked = false
        rb3.isChecked = false
        rb4.isChecked = false

        startTimer()

        btnNext.setOnClickListener {
            checkAnswer()
            nextQuestion()
        }
    }

    private fun checkAnswer() {
        val selected =
            when {
                rb1.isChecked -> 0
                rb2.isChecked -> 1
                rb3.isChecked -> 2
                rb4.isChecked -> 3
                else -> -1
            }

        if (selected == answers[currentQuestion]) {
            score++
        }
    }

    private fun nextQuestion() {
        if (::timer.isInitialized) {
            timer.cancel()
        }
        currentQuestion++

        if (currentQuestion < questions.size) {
            loadQuestion()
        } else {
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("SCORE", score)
            startActivity(intent)
            finish()
        }
    }

    private fun startTimer() {
        if (::timer.isInitialized) {
            timer.cancel()
        }
        timeLeft = 10
        timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                txtTimer.text = "Time Left: ${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                nextQuestion()
            }
        }.start()
    }
}