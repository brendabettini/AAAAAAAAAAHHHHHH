package com.example.projetinho4bim

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projetinho4bim.DataBase
import com.google.android.material.textfield.TextInputEditText
import java.util.Calendar

class Attu : AppCompatActivity() {

    private var currentId: String? = null
    private var myDB: DataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.attu)

        currentId = intent.getStringExtra("bookId")
        myDB = DataBase(this)



        val titleInput = findViewById<TextInputEditText>(R.id.tituloatt)
        val descriptionInput = findViewById<TextInputEditText>(R.id.descricaoatt)
        val tagInput = findViewById<TextInputEditText>(R.id.tagatt)  // Alterado para 'tag'
        val dateInput = findViewById<TextInputEditText>(R.id.dataatt)  // Alterado para 'data'
        val notaInput = findViewById<TextInputEditText>(R.id.notaatt)

        val btnUpdateData = findViewById<Button>(R.id.bttnatt)
        btnUpdateData.setOnClickListener {
            val newTitle = titleInput.text.toString().trim()
            val newDescription = descriptionInput?.text.toString().trim()
            val newType = notaInput.text.toString().trim()
            val newDate = dateInput.text.toString().trim()
            val newTags = tagInput.text.toString().trim()

            if (newTitle.isNotEmpty() && newType.isNotEmpty() && newDate.isNotEmpty() && newDescription.isNotEmpty() && newTags.isNotEmpty()) {
                updateData(newTitle, newDate, newType, newDescription, newTags)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, PesaDetails::class.java)
            startActivity(intent)
        }
    }

    private fun updateData(newTitle: String, newDate: String, newType: String, newDescription: String, newTags: String) {
        currentId?.let {
            val isUpdated = myDB?.updateData(it, newTitle, newDate, newType, newDescription, newTags) == true
            if (isUpdated) {
                Toast.makeText(this, "Dados atualizados com sucesso.", Toast.LENGTH_SHORT).show()
                finish() // Opcional: pode voltar para a tela anterior após atualizar
            } else {
                Toast.makeText(this, "Falha ao atualizar dados.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
