package com.example.projetinho4bim

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AppCompatActivity
import com.example.projetinho4bim.DataBase
import java.util.Calendar

class Add: AppCompatActivity() {
    private lateinit var dbHelper: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addpesa)

        dbHelper = DataBase(this)

        val titleInput = findViewById<TextInputEditText>(R.id.titulo)
        val descriptionInput = findViewById<TextInputEditText>(R.id.descricao)
        val tagInput = findViewById<TextInputEditText>(R.id.tag)  // Alterado para 'tag'
        val dateInput = findViewById<TextInputEditText>(R.id.data)  // Alterado para 'data'
        val notaInput = findViewById<TextInputEditText>(R.id.nota)
        val buttonAdd = findViewById<Button>(R.id.bttnadd)


        buttonAdd.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val description = descriptionInput?.text.toString().trim()
            val type = notaInput.text.toString().trim()
            val date = dateInput.text.toString().trim()
            val type2 = tagInput.text.toString().trim()

            if (title.isNotEmpty() && date != "Data de Instalação") {
                val result = dbHelper.insertData(title, description, type, date, type2)
                if (result != -1L) {
                    Toast.makeText(this, "Dados inseridos com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Erro ao inserir dados", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, preencha o título e selecione uma data", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
