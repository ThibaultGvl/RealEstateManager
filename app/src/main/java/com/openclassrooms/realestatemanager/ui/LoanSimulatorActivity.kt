package com.openclassrooms.realestatemanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityLoanSimulatorBinding

class LoanSimulatorActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityLoanSimulatorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoanSimulatorBinding.inflate(layoutInflater)
        val toolbar = mBinding.loanToolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val returnButton = mBinding.returnLoan
        returnButton.setOnClickListener {
            onBackPressed()
        }
        val amount = mBinding.amountInput
        val rate = mBinding.rateInput
        val contribution = mBinding.contributionInput
        val duration = mBinding.durationInput
        val monthlyPayments = mBinding.monthlyPayment
        val totalInput = mBinding.totalPayments
        mBinding.button.setOnClickListener {
            if (amount.text.isNotEmpty() && rate.text.isNotEmpty() && contribution.text.isNotEmpty()
                    && duration.text.isNotEmpty()) {
                val calculation = ((amount.text.toString().toInt() - contribution.text.toString()
                        .toInt())*((rate.text.toString().toFloat()/100)+1))/duration.text.toString()
                        .toInt()
                val total = calculation*duration.text.toString().toInt() + contribution.text
                        .toString().toInt()
                "You will have to paid $calculation for ${duration.text} month"
                        .also { monthlyPayments.text = it }
                "You will invest $total in total".also { totalInput.text = it }
            }
            else {
                Toast.makeText(this, R.string.information_error,
                        Toast.LENGTH_SHORT).show()
            }
        }
        setContentView(mBinding.root)
    }
}