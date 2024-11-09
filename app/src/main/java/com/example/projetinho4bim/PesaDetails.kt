package com.example.projetinho4bim

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetinho4bim.DataBase

class PesaDetails : AppCompatActivity() {

    private var currentId: String? = null
    private var myDB: DataBase? = null

    private lateinit var tvDispName: TextView
    private lateinit var tvDispTipo: TextView
    private lateinit var tvDispLocal: TextView
    private lateinit var tvDispDtInst: TextView
    private lateinit var tvTags: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    private val bookId: ArrayList<String> = ArrayList()
    private val bookTitle: ArrayList<String> = ArrayList()
    private val bookAuthor: ArrayList<String> = ArrayList()
    private val bookPages: ArrayList<String> = ArrayList()
    private val bookdata: ArrayList<String> = ArrayList()
    private val tags: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pesa_details)

        myDB = DataBase(this)

        tvDispName = findViewById(R.id.tvDispName)
        tvDispTipo = findViewById(R.id.tvDispTipo)
        tvDispLocal = findViewById(R.id.tvDispLocal)
        tvDispDtInst = findViewById(R.id.tvDispDtInst)
        tvTags = findViewById(R.id.tvTags)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)


        currentId = intent.getStringExtra("bookId")
        if (currentId == null) {
            Toast.makeText(this, "Erro: ID não encontrado.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        displayData()

        btnUpdate.setOnClickListener {
            val intent3 = Intent(this, Attu::class.java)
            intent3.putExtra("bookId", currentId)
            startActivity(intent3)
        }

        btnDelete.setOnClickListener {
            deleteData()
        }
    }

    private fun displayData() {
        currentId?.let { id ->
            val cursor = myDB?.readDataById(id)
            if (cursor != null && cursor.moveToFirst()) {
                tvDispName.text = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                tvDispTipo.text = cursor.getString(cursor.getColumnIndexOrThrow("description")) //data
                tvDispLocal.text = cursor.getString(cursor.getColumnIndexOrThrow("type")) //nota
                tvDispDtInst.text = cursor.getString(cursor.getColumnIndexOrThrow("date")) //descrição
                tvTags.text = cursor.getString(cursor.getColumnIndexOrThrow("type2"))
                cursor.close()
            } else {
                Toast.makeText(this, "Dados não encontrados para o ID fornecido.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun storeDataInArrays() {
        val cursor = myDB?.readAllData()
        if (cursor?.count == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor?.moveToNext() == true) {
                bookId.add(cursor.getString(0))
                bookTitle.add(cursor.getString(1))
                bookPages.add(cursor.getString(2))
                bookdata.add(cursor.getString(3))
                bookAuthor.add(cursor.getString(4))
                tags.add(cursor.getString(5))
            }
        }
    }

    private fun deleteData() {
        currentId?.let {
            val isDeleted = myDB?.deleteData(it) == true
            if (isDeleted) {
                Toast.makeText(this, "Dados deletados com sucesso.", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Falha ao deletar dados.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
