package com.example.goroskop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

private lateinit var zodiacSpinner: Spinner
private lateinit var descriptionTextView: TextView
class MainActivity : AppCompatActivity() {
    // Метод для вывода характеристики выбранного знака Зодиака
    private fun showDescription() {
        // Получаем выбранный знак Зодиака из списка
        val selectedZodiac = zodiacSpinner.selectedItem.toString()
        var badDescription = resources.getStringArray(R.array.horoscope_descrtiptions)
        //badDescription.get)
        // Получаем характеристику для выбранного знака Зодиака
        val description = when (selectedZodiac) {
            "Овен" -> "Характеристика для знака Овен"
            "Телец" -> "Характеристика для знака Телец"
            "Близнецы" -> "Характеристика для знака Близнецы"
            "Рак" -> "Характеристика для знака Рак"
            "Лев" -> "Характеристика для знака Лев"
            "Дева" -> "Характеристика для знака Дева"
            "Весы" -> "Характеристика для знака Весы"
            "Скорпион" -> "Характеристика для знака Скорпион"
            "Стрелец" -> "Характеристика для знака Стрелец"
            "Козерог" -> "Характеристика для знака Козерог"
            "Водолей" -> "Характеристика для знака Водолей"
            "Рыбы" -> "Характеристика для знака Рыбы"
            else -> "Характеристика для знака Зодиака не найдена"
        }

        // Выводим характеристику в текстовое поле
        descriptionTextView.text = description
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        zodiacSpinner = findViewById(R.id.spinner)
        descriptionTextView = findViewById(R.id.textView3)

        // Создаем адаптер для списка знаков Зодиака
        ArrayAdapter.createFromResource(
            this,
            R.array.zodiacs_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            zodiacSpinner.adapter = adapter
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            showDescription()
        }

    }
}