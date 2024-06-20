package com.example.basicbankingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.basicbankingapp.databinding.ActivityTransactionHistoryBinding
import java.util.ArrayList

class TransactionHistory : AppCompatActivity() {

    lateinit var b : ActivityTransactionHistoryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(b.root)

//        val amount = intent.getStringExtra("amt")
//        if (amount != null) {
//            val t = "Rs $amount --> Success"
//            list.add(t)
//        }
        val list = arrayListOf("Rs 5000 --> Faiz : Success","Rs 1000 --> Thomas : Failed","Rs 1000 --> Thomas : Success","Rs 500 --> Sara : Success")

        val adapter = ArrayAdapter<String>(
            this,
            R.layout.layout_history,
            R.id.transaction,
            list
        )

        b.lvHistory.adapter = adapter
    }
}
