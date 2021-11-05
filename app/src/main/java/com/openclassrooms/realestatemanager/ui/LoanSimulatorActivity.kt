package com.openclassrooms.realestatemanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityLoanSimulatorBinding

class LoanSimulatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoanSimulatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanSimulatorBinding.inflate(layoutInflater)
        binding.button.setOnClickListener {
            binding.amount.text = (binding.editText.text.toString().toFloat() * 0.10).toString()
            binding.rate.text = (binding.amount.text.toString().toFloat() * 0.20).toString()
            binding.duration.text = ((binding.editText.text.toString().toFloat()/binding.rate.text
                    .toString().toFloat())).toInt().toString()
        }
        setContentView(binding.root)
    }
}