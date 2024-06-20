package com.example.basicbankingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UserAdapter(context: Context, users: List<UserModel>): ArrayAdapter<UserModel>(context,0,users) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val user = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_user,parent, false)

        val tvName : TextView = view.findViewById(R.id.tvName)
        val tvPhone : TextView = view.findViewById(R.id.tvPhone)
        val tvAmount : TextView = view.findViewById(R.id.tvAmount)

        tvName.text = user?.name
        tvPhone.text = user?.phNumber.toString()
        tvAmount.text = user?.amount.toString()

        return view

    }
}