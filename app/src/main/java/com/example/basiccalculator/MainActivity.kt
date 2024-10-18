package com.example.basiccalculator

import android.health.connect.datatypes.units.Percentage
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private lateinit var textResult:TextView
    private lateinit var historyTextview: TextView
    private var fullhistoryExpression:String=""
    private lateinit var acButton: Button
    private lateinit var oneButton: Button
    private lateinit var twoButton: Button
    private lateinit var threeButton: Button
    private lateinit var fourButton: Button
    private lateinit var fiveButton: Button
    private lateinit var sixButton: Button
    private lateinit var sevenButton: Button
    private lateinit var eigthButton: Button
    private lateinit var nineButton: Button
    private lateinit var commaButton: Button
    private lateinit var zeroButton: Button
    private lateinit var decimalButton: Button
    private lateinit var delButton: Button
    private lateinit var remaiderButton: Button
    private lateinit var divideButton: Button
    private lateinit var multiplyButton: Button
    private lateinit var addButton: Button
    private lateinit var subtractButton: Button
    private lateinit var equalsToButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        id()
        onClick()
    }
    private fun id(){
        historyTextview=findViewById(R.id.History)
        textResult=findViewById(R.id.result)
        acButton= findViewById(R.id.Ac_btn)
        delButton=findViewById(R.id.Del_btn)
        remaiderButton= findViewById(R.id.Remainder_btn)
        divideButton=findViewById(R.id.Divide_btn)
        multiplyButton= findViewById(R.id.Multiply_btn)
        addButton=findViewById(R.id.Add_btn)
        subtractButton= findViewById(R.id.Subtraction_btn)
        equalsToButton=findViewById(R.id.Equalsto_btn)
        oneButton=findViewById(R.id.One_btn)
        twoButton= findViewById(R.id.Two_btn)
        threeButton=findViewById(R.id.Three_btn)
        fourButton= findViewById(R.id.Four_btn)
        fiveButton=findViewById(R.id.Five_btn)
        sixButton= findViewById(R.id.Six_btn)
        sevenButton=findViewById(R.id.Seven_btn)
        eigthButton= findViewById(R.id.Eight_btn)
        nineButton=findViewById(R.id.Nine_btn)
        commaButton= findViewById(R.id.Comma_btn)
        zeroButton=findViewById(R.id.Zero_btn)
        decimalButton= findViewById(R.id.Decimal_btn)

    }
    private fun onClick(){
        acButton.setOnClickListener {
            allclear()
        }
        delButton.setOnClickListener {
            clearLastCharacter()
        }
        remaiderButton.setOnClickListener {
            append("%")
        }
        divideButton.setOnClickListener {
            append("/")
        }
        multiplyButton.setOnClickListener {
            append("*")
        }
        addButton.setOnClickListener {
            append("+")
        }
        subtractButton.setOnClickListener {
            append("-")
        }
        equalsToButton.setOnClickListener {
            calculateResult()
            showhistory()


        }
        oneButton.setOnClickListener {
            append("1")
        }
        twoButton.setOnClickListener {
            append("2")
        }
        threeButton.setOnClickListener {
            append("3")
        }
        fourButton.setOnClickListener {
            append("4")
        }
        fiveButton.setOnClickListener {
            append("5")
        }
        sixButton.setOnClickListener {
            append("6")
        }
        sevenButton.setOnClickListener {
            append("7")
        }
        eigthButton.setOnClickListener {
            append("8")
        }
        nineButton.setOnClickListener {
            append("9")
        }
        commaButton.setOnClickListener {
            append(",")
        }
        zeroButton.setOnClickListener {
            append("0")
        }
        decimalButton.setOnClickListener {
            append(".")
        }




    }
    private fun append(value:String){
        val currentResult = textResult.text.toString()
        if (currentResult == "0") {
            textResult.text = value  // Replace 0 with the first digit
        } else {
            textResult.text = currentResult + value  // Append value
        }
        fullhistoryExpression += value

    }
    private fun showhistory(){

        historyTextview.text=fullhistoryExpression

    }
    private fun allclear(){
        historyTextview.text=" "
        textResult.text="0"
        fullhistoryExpression=""
    }
    private fun clearLastCharacter(){
        val currentResult = textResult.text.toString()
        if (currentResult.length > 1){
            textResult.text=currentResult.dropLast(1)
        }else{
            textResult.text="0"
        }
    }
    private fun calculateResult() {
        val currentExpression = textResult.text.toString()
        // Using Regex to split the expression into numbers and operators
        val regex = "(\\d+|[-+*/])".toRegex()
        val tokens = regex.findAll(currentExpression).map { it.value }.toList()

        val stack = mutableListOf<Int>()
        var currentNumber = 0
        var operation = '+'

        for (token in tokens) {
            if (token.toIntOrNull() != null) { // Check if it's a number
                currentNumber = token.toInt()
            } else {
                when (operation) {
                    '+' -> stack.add(currentNumber)
                    '-' -> stack.add(-currentNumber)
                    '*' -> stack[stack.lastIndex] *= currentNumber
                    '/' -> {
                        // Handle division by zero
                        if (currentNumber != 0) {
                            stack[stack.lastIndex] /= currentNumber
                        } else {
                            Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                            return
                        }
                    }
                }
                operation = token[0] // Update the last operation
            }
        }
        // Adding the last number
        when (operation) {
            '+' -> stack.add(currentNumber)
            '-' -> stack.add(-currentNumber)
            '*' -> stack[stack.lastIndex] *= currentNumber
            '/' -> {
                if (currentNumber != 0) {
                    stack[stack.lastIndex] /= currentNumber
                }
            }
        }

        val result = stack.sum()
        textResult.text = result.toString()
        showhistory()

    }



}


