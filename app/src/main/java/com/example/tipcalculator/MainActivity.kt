package com.example.tipcalculator

import android.animation.ArgbEvaluator
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15
class MainActivity : AppCompatActivity() {
    private lateinit var baseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tipPercentLabel: TextView
    private lateinit var tipAmount: TextView
    private lateinit var totalAmount: TextView
//    private lateinit var tvTipDescription: TextView
//    private lateinit var tvFooter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        baseAmount = findViewById(R.id.baseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tipPercentLabel = findViewById(R.id.tipPercentLabel)
        tipAmount = findViewById(R.id.tipAmount)
        totalAmount = findViewById(R.id.totalAmount)
        //tvTipDescription = findViewById(R.id.)
        //tvFooter = findViewById(R.id.tvFooter)

        seekBarTip.progress = INITIAL_TIP_PERCENT
        tipPercentLabel.text = "$INITIAL_TIP_PERCENT%"
        //updateTipDescription(INITIAL_TIP_PERCENT)
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                tipPercentLabel.text = "$progress%"
                //updateTipDescription(progress)
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        baseAmount.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }
        })

    }

//    private fun updateTipDescription(tipPercent: Int) {
//        val tipDescription = when (tipPercent) {
//            in 0..9 -> "Poor"
//            in 10..14 -> "Acceptable"
//            in 15..19 -> "Good"
//            in 20..24 -> "Great"
//            else -> "Amazing"
//        }
//        tvTipDescription.text = tipDescription
//        val color = ArgbEvaluator().evaluate(
//            tipPercent.toFloat() / seekBarTip.max,
//            ContextCompat.getColor(this, R.color.color_worst_tip),
//            ContextCompat.getColor(this, R.color.color_best_tip)
//        ) as Int
//        tvTipDescription.setTextColor(color)
//    }

    private fun computeTipAndTotal() {
        if (baseAmount.text.isEmpty()) {
            tipAmount.text = ""
            totalAmount.text = ""
            return
        }
        // Get the value of the base and tip percent
        val baseAmount = baseAmount.text.toString().toDouble()
        val tipPercent = seekBarTip.progress

        // Compute the tip and update the UI
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = baseAmount + tipAmount
        this.tipAmount.text = "%.2f".format(tipAmount)
        this.totalAmount.text = "%.2f".format(totalAmount)
    }
}