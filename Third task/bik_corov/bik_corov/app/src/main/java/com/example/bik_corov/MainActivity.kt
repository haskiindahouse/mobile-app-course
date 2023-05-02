package com.example.bik_corov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.view.View
import android.content.Intent
class MainActivity : AppCompatActivity() {
    private val words = arrayOf("кот", "молоко", "человек", "дом", "река", "звезда")
    private lateinit var secretWord: String
    private var attemptCount = 0
    private fun generateSecretWord(): String {
        return words.random()
    }

    private fun checkGuessWord(guessWord: String) {
        attemptCount++

        var bulls = 0
        var cows = 0

        for (i in secretWord.indices) {
            if (guessWord[i] == secretWord[i]) {
                bulls++
            } else if (secretWord.contains(guessWord[i])) {
                cows++
            }
        }

        val resultTextView: TextView = findViewById(R.id.textView)
        resultTextView.text = "Быки: $bulls, коровы: $cows"

        val attemptTextView: TextView = findViewById(R.id.textView4)
        attemptTextView.text = "Попыток: $attemptCount"

        if (bulls == secretWord.length) {
            val successTextView: TextView = findViewById(R.id.textView3)
            successTextView.text = "Вы отгадал загаданное слово в игре 'Быки и коровы' за $attemptCount попыток!"
            val ResetButton: Button = findViewById(R.id.button2)
            ResetButton.setVisibility(View.VISIBLE)
            val PodButton: Button = findViewById(R.id.button3)
            PodButton.setVisibility(View.VISIBLE)
        }
    }
    private fun resetGame() {
        // Сбрасываем значения элементов интерфейса
        val successTextView: TextView = findViewById(R.id.textView3)
        val attemptTextView: TextView = findViewById(R.id.textView4)
        successTextView.setText("")
        attemptTextView.text = "Попыток: "
        val resultTextView: TextView = findViewById(R.id.textView)
        resultTextView.text = "Быки: , коровы: "
        val texxx: EditText = findViewById(R.id.editTextTextPersonName3)
        texxx.setText("")
        // Генерируем новое слово для игры
        secretWord = generateSecretWord()
        val proverka: TextView = findViewById(R.id.textView5)
        proverka.text="$secretWord"
        val ResetButton: Button = findViewById(R.id.button2)
        ResetButton.setVisibility(View.INVISIBLE)
        val PodButton: Button = findViewById(R.id.button3)
        PodButton.setVisibility(View.INVISIBLE)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        secretWord = generateSecretWord()
        val proverka: TextView = findViewById(R.id.textView5)
        proverka.text="$secretWord"
        val submitButton: Button = findViewById(R.id.button)
        val PodButton: Button = findViewById(R.id.button3)
        submitButton.setOnClickListener {
            val guessWord: String = findViewById<EditText>(R.id.editTextTextPersonName3).text.toString()
            if (guessWord.length != secretWord.length) {
                Toast.makeText(this, "Слово должно состоять из ${secretWord.length} букв", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            checkGuessWord(guessWord)
        }
        val ResetButton: Button = findViewById(R.id.button2)
        ResetButton.setOnClickListener{
            resetGame()
        }
        PodButton.setOnClickListener{
            val message = "Я отгадал загаданное слово в игре \"Быки и коровы\" за $attemptCount попыток!"
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(shareIntent, "Поделиться результатом"))
        }



    }
}