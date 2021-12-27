package com.openclassrooms.realestatemanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityLoanSimulatorBinding

class LoanSimulatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoanSimulatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanSimulatorBinding.inflate(layoutInflater)
        val toolbar = binding.loanToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val returnButton = binding.returnLoan
        returnButton.setOnClickListener {
            onBackPressed()
        }
        val amount = binding.amountInput
        val rate = binding.rateInput
        val input = binding.inputInput
        val duration = binding.durationInput
        val monthlyPayments = binding.monthlyPayment
        val totalInput = binding.totalPayments
        binding.button.setOnClickListener {
            if (amount.text != null && rate.text != null && input.text != null && duration.text != null) {
                val calculation = ((amount.text.toString().toInt() - input.text.toString().toInt())*((rate.text.toString().toFloat()/100)+1))/duration.text.toString().toInt()
                val total = calculation*duration.text.toString().toInt() + input.text.toString().toInt()
                "You will have to paid $calculation for ${duration.text} month".also { monthlyPayments.text = it }
                "You will invest $total in total".also { totalInput.text = it }
            }
            else {
                Toast.makeText(this, R.string.information_error, Toast.LENGTH_SHORT).show()
            }
        }
        setContentView(binding.root)
    }
}