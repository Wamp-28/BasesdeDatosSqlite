package com.example.basesdedatossqlitefuncional

import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edtCodigo = findViewById<EditText>(R.id.edtCodigo)
        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtTelefono = findViewById<EditText>(R.id.edtTelefono)

        val btnInsertar = findViewById<Button>(R.id.btnInsertar)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val btnModificar = findViewById<Button>(R.id.btnModificar)

        btnInsertar.setOnClickListener {
            val admin = OpenHelper(this, "bdAdmin", null, 1)
            val SQLiteDatabase = admin.writableDatabase

            val codigo = edtCodigo.text.toString()
            val nombre = edtNombre.text.toString()
            val telefono = edtTelefono.text.toString()

            if (!codigo.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty()) {
                val registro = ContentValues()
                registro.put("codigo", codigo)
                registro.put("nombre", nombre)
                registro.put("telefono", telefono)
                SQLiteDatabase.insert("estudiante", null, registro)
                SQLiteDatabase.close()
                edtCodigo.setText("")
                edtNombre.setText("")
                edtTelefono.setText("")
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnBuscar.setOnClickListener {
            val admin = OpenHelper(this, "bdAdmin", null, 1)
            val SQLiteDatabase = admin.writableDatabase

            val codigo = edtCodigo.text.toString()

            if (!codigo.isEmpty()) {
                val fila: Cursor
                fila = SQLiteDatabase.rawQuery(
                    "select nombre, telefono from estudiante where codigo = $codigo",
                    null
                )
                if (fila.moveToFirst()) {
                    edtNombre.setText(fila.getString(0))
                    edtTelefono.setText(fila.getString(1))
                    SQLiteDatabase.close()
                } else {
                    Toast.makeText(this, "Estudiante no existe", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Falta codigo a buscar", Toast.LENGTH_SHORT).show()
            }
        }

        btnEliminar.setOnClickListener {
            val admin = OpenHelper(this, "bdAdmin", null, 1)
            val SQLiteDatabase = admin.writableDatabase

            val codigo = edtCodigo.text.toString()

            if (!codigo.isEmpty()) {
                val cantidad = SQLiteDatabase.delete("estudiante", "codigo = $codigo", null)
                SQLiteDatabase.close()
                edtCodigo.setText("")
                edtNombre.setText("")
                edtTelefono.setText("")
                if (cantidad == 1) {
                    Toast.makeText(this, "Estudiante eliminado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Estudiante NO existe", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Introduzca codigo estudiante", Toast.LENGTH_SHORT).show()
            }
        }

        btnModificar.setOnClickListener {
            val admin = OpenHelper(this, "bdAdmin", null, 1)
            val SQLiteDatabase = admin.writableDatabase

            val codigo = edtCodigo.text.toString()
            val nombre = edtNombre.text.toString()
            val telefono = edtTelefono.text.toString()
            if (!codigo.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty()) {
                val registro = ContentValues()

                registro.put("codigo", codigo)
                registro.put("nombre", nombre)
                registro.put("telefono", telefono)

                val cantidad =
                    SQLiteDatabase.update("estudiante", registro, "codigo= $codigo", null)
                SQLiteDatabase.close()

                if (cantidad == 1) {
                    Toast.makeText(this, "Estudiante modificado", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Estudiante no existe", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "Diligencie los campos", Toast.LENGTH_SHORT).show()
            }

        }
    }
}