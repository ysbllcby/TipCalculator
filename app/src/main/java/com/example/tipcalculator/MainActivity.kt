package com.example.tipcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val INITIAL_TIP_PERCENT = 15

class MainActivity : AppCompatActivity() {

    private lateinit var baseAmount: EditText
    private lateinit var seekBarTip: SeekBar
    private lateinit var tipPercentLabel: TextView
    private lateinit var tipAmount: TextView
    private lateinit var totalAmount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        baseAmount = findViewById(R.id.baseAmount)
        seekBarTip = findViewById(R.id.seekBarTip)
        tipPercentLabel = findViewById(R.id.tipPercentLabel)
        tipAmount = findViewById(R.id.tipAmount)
        totalAmount = findViewById(R.id.totalAmount)

        //set minimum tip percentage
        seekBarTip.progress = INITIAL_TIP_PERCENT
        tipPercentLabel.text = "$INITIAL_TIP_PERCENT%"

        //Show the corresponding percentage of the Seek Bar
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "on progress change $progress")
                tipPercentLabel.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        baseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                Log.i(TAG, "after text changes $s")
                computeTipAndTotal()
            }

        })
    }

    //TODO: revisit this part.
    //tipAmount != tipSeekbarTip
    private fun computeTipAndTotal() {
        //1. Extract values from baseAmount and tipPercent.
        val baseAmount = baseAmount.text.toString().toDouble()
        val percentTip = seekBarTip.progress

        //2. Compute the tip and total.
        val tipTotal = baseAmount * (percentTip / 100)
        val totalBill = baseAmount + tipTotal

        //3. Update the UI.
        tipAmount.text = tipTotal.toString()
        totalAmount.text = totalBill.toString()

    }
}