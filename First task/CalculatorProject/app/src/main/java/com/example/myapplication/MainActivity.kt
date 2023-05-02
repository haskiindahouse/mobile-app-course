package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numerator1=findViewById<EditText>(R.id.editTextTextPersonName25)
        val denominator1=findViewById<EditText>(R.id.editTextTextPersonName26)

        val numerator2=findViewById<EditText>(R.id.editTextTextPersonName27)
        val denominator2=findViewById<EditText>(R.id.editTextTextPersonName28)

        val resultWholePart=findViewById<EditText>(R.id.editTextTextPersonName33)
        val resultNumerator =findViewById<EditText>(R.id.editTextTextPersonName35)
        val resultDenominator =findViewById<EditText>(R.id.editTextTextPersonName32)

        val buttonPlus = findViewById<Button>(R.id.button26)
        val buttonMinus = findViewById<Button>(R.id.button28)
        val buttonUmn = findViewById<Button>(R.id.button29)
        val buttonDel = findViewById<Button>(R.id.button31)

        val buttonRes = findViewById<Button>(R.id.button32)
        val result=findViewById<TextView>(R.id.textView2)

        fun isValidNumber(text: String): Boolean {
            return try {
                text.toInt()
                true
            } catch (e: NumberFormatException) {
                false
            }
        }

        // Сокращаем дробь, если это возможно
        fun gcd(a: Int, b: Int): Int {
            return if (b == 0) a else gcd(b, a % b)
        }

        fun isAllValid(a1: String, b1: String, a2: String, b2: String): Boolean
        {
            return isValidNumber(a1) && isValidNumber(b1) && isValidNumber(a2) && isValidNumber(b2);
        }

        class OperationResult(resultWhole : Int, resultReducedNum : Int, resultReducedDen : Int)
        {
            val _resultWhole = resultWhole;
            var _resultReducedNum = resultReducedNum;
            var _resultReducedDen = resultReducedDen;
        }

        fun add(num1 : Int, num2: Int, den1 : Int, den2 : Int) : OperationResult
        {
            // Вычисляем сумму дробей
            val resultNum = num1 * den2 + num2 * den1
            val resultDen = den1 * den2

            // Вычисляем целую часть и остаток
            val resultWhole = resultNum / resultDen
            val resultRem = resultNum % resultDen

            // Сокращаем дробь, если это возможно
            val gcd = gcd(resultRem, resultDen)
            val resultReducedNum = resultRem / gcd
            val resultReducedDen = resultDen / gcd

            val result = OperationResult(resultWhole, resultReducedNum, resultReducedDen);
            return result
        }

        fun minus(num1 : Int, num2: Int, den1 : Int, den2 : Int) : OperationResult
        {
            // Находим общее наименьшее кратное знаменателей
            val lcm = (den1 * den2) / gcd(den1, den2)

            // Приводим дроби к общему знаменателю
            val commonNum1 = num1 * (lcm / den1)
            val commonNum2 = num2 * (lcm / den2)



            // Вычитаем числители
            val resultNum = commonNum1 - commonNum2

            // Сокращаем дробь
            val resultGcd = gcd(resultNum, lcm)
            val reducedNum = resultNum / resultGcd
            val reducedDen = lcm / resultGcd

            val result = OperationResult((reducedNum / reducedDen), (reducedNum % reducedDen), reducedDen)
            return result
        }

        fun multiply(num1 : Int, num2: Int, den1 : Int, den2 : Int) : OperationResult
        {
            // Умножаем числитель и знаменатель
            val resultNum = num1 * num2
            val resultDen = den1 * den2

            // Сокращаем дробь
            val resultGcd = gcd(resultNum, resultDen)
            val reducedNum = resultNum / resultGcd
            val reducedDen = resultDen / resultGcd

            val result = OperationResult((reducedNum / reducedDen), (reducedNum % reducedDen), reducedDen)
            return result
        }

        fun divide(num1 : Int, num2: Int, den1 : Int, den2 : Int) : OperationResult
        {
            // Умножаем числитель первой дроби на знаменатель второй дроби и знаменатель первой дроби на числитель второй дроби
            val resultNum = num1 * den2
            val resultDen = den1 * num2

            // Сокращаем дробь
            val resultGcd = gcd(resultNum, resultDen)
            val reducedNum = resultNum / resultGcd
            val reducedDen = resultDen / resultGcd

            val result = OperationResult((reducedNum / reducedDen), (reducedNum % reducedDen), reducedDen)
            return result
        }

        fun samePart(operation: (num1 : Int, num2: Int, den1 : Int, den2 : Int)->OperationResult)
        {
            // Получаем значения числителей и знаменателей
            val num1 = numerator1.text.toString()
            val den1 = denominator1.text.toString()
            val num2 = numerator2.text.toString()
            val den2 = denominator2.text.toString()
            if (isAllValid(num1, den1, num2, den2)) {

                val num1 = num1.toInt()
                val den1 = den1.toInt()
                val num2 = num2.toInt()
                val den2 = den2.toInt()
                if (den1 == 0 || den2 == 0) {
                    Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                }
                else {
                    // Устанавливаем результат в EditText-ы
                    val res = operation(num1, num2, den1, den2)
                    resultWholePart.setText(res._resultWhole.toString())
                    resultNumerator.setText(res._resultReducedNum.toString())
                    resultDenominator.setText(res._resultReducedDen.toString())
                }
            }
            else {
                Toast.makeText(this, "Only numbers can be input", Toast.LENGTH_SHORT).show()
            }
        }

        // Plus button
        buttonPlus.setOnClickListener {
            samePart(::add)
        }

        // Minus
        buttonMinus.setOnClickListener {
            samePart(::minus)
        }

        // Multiply
        buttonUmn.setOnClickListener {
            samePart(::multiply)

        }

        // Divide
        buttonDel.setOnClickListener {
            samePart(::divide)
        }

        // Integer part
        buttonRes.setOnClickListener{

            val integer = resultWholePart.text.toString()
            val numerator = resultNumerator.text.toString()
            val denominator = resultDenominator.text.toString()

            if(isValidNumber(integer) && isValidNumber(numerator) && isValidNumber(denominator)){
                val integer = integer.toInt()
                val numerator = numerator.toInt()
                val denominator = denominator.toInt()

                if (numerator == 0 || denominator == 0) {
                    Toast.makeText(this, "Деление на ноль невозможно", Toast.LENGTH_SHORT).show()
                }
                else{
                    val result1 = numerator.toDouble() / denominator.toDouble() + integer
                    val roundedResult = String.format("%.5f", result1)
                    result.text = roundedResult
                }
            }
        }
    }
}