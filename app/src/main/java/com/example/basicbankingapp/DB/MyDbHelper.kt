package com.example.basicbankingapp.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.basicbankingapp.UserModel

class MyDbHelper(context: Context): SQLiteOpenHelper(
    context,
    "users.db",
    null,
    1
)
{

    companion object{
        const val DB_TABLE = "users"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("""
    CREATE TABLE $DB_TABLE (
        "id" INTEGER PRIMARY KEY AUTOINCREMENT,
        "name" TEXT,
        "phoneNo" INTEGER,
        "amount" INTEGER,
        "email" TEXT,
        "accNo" TEXT
    )
""".trimMargin())

        db?.execSQL("""
        CREATE TABLE transactions (
            "id" INTEGER PRIMARY KEY AUTOINCREMENT,
            "amount" INTEGER,
            "fromUser" TEXT,
            "toUser" TEXT,
            "status" TEXT,
            "timestamp" DATETIME DEFAULT CURRENT_TIMESTAMP
        )
    """.trimMargin())
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $DB_TABLE")
        onCreate(db)
    }

    fun insertUser(user: UserModel){
        val db = this.writableDatabase
        val row = ContentValues()
        row.put("name",user.name)
        row.put("phoneNo",user.phNumber)
        row.put("amount",user.amount)
        row.put("email",user.email)
        row.put("accNo",user.accNo)

        db.insert(DB_TABLE, null, row)

    }

    fun fetchUser(): ArrayList<UserModel>{

        val users = ArrayList<UserModel>()
        val db = this.readableDatabase

        val c : Cursor= db.rawQuery("select * from users",null)

//        val c : Cursor = db.query(DB_TABLE,
//            arrayOf("id","name","phoneNo","amount","email","accNo"),
//            null,null,null
//        )

        while (c.moveToNext()){
            val user = UserModel(c.getString(1),c.getInt(2), c.getInt(3), c.getString(4),
                c.getString(5))
            users.add(user)
        }
        return users
    }

    fun updateUserBalance(phoneNo: Int, newBalance: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("amount", newBalance)
        db.update(DB_TABLE, contentValues, "phoneNo = ?", arrayOf(phoneNo.toString()))
    }

    fun isDatabaseEmpty(): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM $DB_TABLE", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count == 0
    }

//    fun insertTransaction(amount: Int, fromUser: String, toUser: String, status: String) {
//        val db = this.writableDatabase
//        val row = ContentValues()
//        row.put("amount", amount)
//        row.put("fromUser", fromUser)
//        row.put("toUser", toUser)
//        row.put("status", status)
//        db.insert("transactions", null, row)
//    }

    fun fetchTransactions(): ArrayList<String> {
        val transactions = ArrayList<String>()
        val db = this.readableDatabase
        val c: Cursor = db.rawQuery("SELECT * FROM transactions ORDER BY timestamp DESC", null)

        while (c.moveToNext()) {
            val amount = c.getInt(1)
            val fromUser = c.getString(2)
            val toUser = c.getString(3)
            val status = c.getString(4)
            val transaction = "Rs $amount --> $toUser : $status"
            transactions.add(transaction)
        }
        c.close()
        return transactions
    }

}