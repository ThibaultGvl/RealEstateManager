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
        binding.button.setOnClickListener {
            if (binding.editText.text?.isNotEmpty() == true && binding.amount.text.isNotEmpty() &&
                    binding.rate.text.isNotEmpty() && binding.duration.text.isNotEmpty()) {
                binding.amount.text = (binding.editText.text.toString().toFloat() * 0.10).toString()
                binding.rate.text = (binding.amount.text.toString().toFloat() * 0.20).toString()
                binding.duration.text = ((binding.editText.text.toString().toFloat()/binding.rate.text
                        .toString().toFloat())).toInt().toString()
            }
            else {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            }
        }
        setContentView(binding.root)
    }
}