package com.ghennadiiganenko.android.scoretable

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        for (rows in 1..7) {

            val sumIdString = "tvSum$rows"
            val sumId = resources.getIdentifier(sumIdString, "id", packageName)

            for (columns in 1..7) {
                if (rows != columns) {
                    val editTextIdString = "et$rows$columns"
                    val editTextId = resources.getIdentifier(editTextIdString, "id", packageName)

                    findViewById<EditText>(editTextId).addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(
                            p0: CharSequence?,
                            p1: Int,
                            p2: Int,
                            p3: Int
                        ) {

                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            if (isRowFilled(rows)) {
                                findViewById<TextView>(sumId).text = sumOfRow(rows).toString()
                            }

                            if (isTableFilled()) {
                                setPlaces()
                            }
                        }

                        override fun afterTextChanged(p0: Editable?) {

                        }

                    })
                }
            }
        }
    }

    private fun isRowFilled(row: Int) : Boolean {
        for (columns in 1..7) {
            if (row != columns) {
                val editTextID = "et$row$columns"
                val resId = resources.getIdentifier(editTextID, "id", packageName)

                val editText = findViewById<EditText>(resId)

                if (editText.text.isNullOrBlank()) {
                    return false
                }
            }
        }
        return true
    }

    private fun sumOfRow(row: Int): Int {
        var totalSum: Int = 0

        for (columns in 1..7) {
            if (row != columns) {
                val editTextID = "et$row$columns"
                val resId = resources.getIdentifier(editTextID, "id", packageName)

                val editText = findViewById<EditText>(resId)

                totalSum += editText.text.toString().toInt()
            }
        }

        return totalSum
    }

    private fun isTableFilled() : Boolean {
        for (rows in 1..7) {
            val sumIdString = "tvSum$rows"
            val sumId = resources.getIdentifier(sumIdString, "id", packageName)

            val sumTextView = findViewById<TextView>(sumId)

            if (sumTextView.text.isNullOrBlank()) {
                return false
            }
        }
        return true
    }

    private fun setPlaces() {
        var listOfSums = mutableListOf<Int>()

        for (rows in 1..7) {
            val sumIdString = "tvSum$rows"
            val sumId = resources.getIdentifier(sumIdString, "id", packageName)
            val sumTextView = findViewById<TextView>(sumId)

            listOfSums.add(sumTextView.text.toString().toInt())
        }

        listOfSums.sortDescending()

        val mapOfPlaces = mutableMapOf<Int,Int>()

        for (rows in 0..6) {
            mapOfPlaces[rows] = listOfSums[rows]
        }

        for (rows in 1..7) {
            val placeIdString = "tvPlace$rows"
            val placeId = resources.getIdentifier(placeIdString, "id", packageName)
            val placeTextView = findViewById<TextView>(placeId)

            val sumIdString = "tvSum$rows"
            val sumId = resources.getIdentifier(sumIdString, "id", packageName)
            val sumTextView = findViewById<TextView>(sumId)

            placeTextView.text = (mapOfPlaces.entries.find { it.value == sumTextView.text.toString().toInt() }?.key?.plus(1)).toString()
            mapOfPlaces.entries.remove(mapOfPlaces.entries.find { it.value == sumTextView.text.toString().toInt()})
        }
    }

}