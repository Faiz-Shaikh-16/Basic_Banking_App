package com.example.basicbankingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.basicbankingapp.databinding.ActivityUserDetailsBinding
import com.example.basicbankingapp.databinding.TransferMoneyBinding
import com.example.basicbankingapp.DB.MyDbHelper

class UserDetails : AppCompatActivity() {
    lateinit var b: ActivityUserDetailsBinding
    private lateinit var dbHelper: MyDbHelper
    private var phoneNo: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(b.root)

        dbHelper = MyDbHelper(this)

        val name = intent.getStringExtra("name")
        phoneNo = intent.getIntExtra("phoneNo", -1)
        val amount = intent.getIntExtra("amount", -1)
        val email = intent.getStringExtra("email")
        val accNo = intent.getStringExtra("accNo")

        b.username.text = name
        b.userPhNumber.text = phoneNo.toString()
        b.userEmail.text = email
        b.userAccNo.text = accNo
        b.currBalance.text = amount.toString()

        b.btnTransfer.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.transfer_money, null)
            val dialogBinding = TransferMoneyBinding.bind(dialogView)

            val dialog = AlertDialog.Builder(this)
                .setView(dialogBinding.root)
                .setTitle("Transfer Money?")
                .setCancelable(false)
                .setPositiveButton("Send") { dialog, which ->
                    Toast.makeText(this, "Transaction Approved", Toast.LENGTH_SHORT).show()
                    sendTransaction(dialogBinding)
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    Toast.makeText(this, "Transaction Cancelled", Toast.LENGTH_SHORT).show()
                }
                .create()

            dialog.show()
        }
    }

    private fun sendTransaction(dialogBinding: TransferMoneyBinding) {
        val transferAmount = dialogBinding.enterAmount.text.toString().toInt()
        Log.d("amount", transferAmount.toString())

        val currentBalance = b.currBalance.text.toString().toInt()
        val newBalance = currentBalance + transferAmount

        b.currBalance.text = "$newBalance"

        dbHelper.updateUserBalance(phoneNo, newBalance)

        val resultIntent = Intent()
        resultIntent.putExtra("phoneNo", phoneNo)
        resultIntent.putExtra("newBalance", newBalance)
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
