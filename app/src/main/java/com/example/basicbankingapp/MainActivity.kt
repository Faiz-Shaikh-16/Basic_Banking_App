package com.example.basicbankingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.basicbankingapp.DB.MyDbHelper
import com.example.basicbankingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var b: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private val users = ArrayList<UserModel>()
    private val REQUEST_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)
        setSupportActionBar(b.toolbar)

        val db = MyDbHelper(this)

        if (db.isDatabaseEmpty()) {
            db.insertUser(UserModel("Faiz", 934567890, 10000, "faiz@gmail.com", "XXXXXXXXXX1234"))
            db.insertUser(UserModel("Thomas", 1324567890, 5000, "thomas@gmail.com", "XXXXXXXXXX2234"))
            db.insertUser(UserModel("Sara", 845278908, 2000, "sara@gmail.com", "XXXXXXXXXX1255"))
            db.insertUser(UserModel("Rayyan", 736272829, 4000, "rayyan@gmail.com", "XXXXXXXXXX6325"))
            db.insertUser(UserModel("Bateman", 564789032, 700, "bateman@gmail.com", "XXXXXXXXXX8908"))
            db.insertUser(UserModel("Masood", 975362718, 8000, "masood@gmail.com", "XXXXXXXXXX5397"))
            db.insertUser(UserModel("Brock", 672983638, 12000, "brock@gmail.com", "XXXXXXXXXX1257"))
            db.insertUser(UserModel("John", 547389278, 2500, "john@gmail.com", "XXXXXXXXXX6894"))
            db.insertUser(UserModel("Shelby", 728835748, 2000, "shelby@gmail.com", "XXXXXXXXXX2157"))
            db.insertUser(UserModel("Manal", 123456789, 5000, "manal@gmail.com", "XXXXXXXXXX7894"))
            db.insertUser(UserModel("Priya", 234567890, 6000, "priya@gmail.com", "XXXXXXXXXX3255"))
        }

        users.addAll(db.fetchUser())

        adapter = UserAdapter(this, users)
        b.lvUsers.adapter = adapter

        b.lvUsers.setOnItemClickListener { parent, view, position, id ->
            val selectedUser = users[position]

            val i = Intent(this, UserDetails::class.java).apply {
                putExtra("name", selectedUser.name)
                putExtra("phoneNo", selectedUser.phNumber)
                putExtra("amount", selectedUser.amount)
                putExtra("email", selectedUser.email)
                putExtra("accNo", selectedUser.accNo)
            }
            startActivityForResult(i, REQUEST_CODE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.history ->{
                startActivity(Intent(this, TransactionHistory::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val phoneNo = data?.getIntExtra("phoneNo", -1) ?: return
            val newBalance = data.getIntExtra("newBalance", -1)

            val user = users.find { it.phNumber == phoneNo }
            user?.amount = newBalance

            adapter.notifyDataSetChanged()
        }
    }
}
