package com.example.registroestudiantekotlin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Declaración de variables para los componentes de la interfaz
    private lateinit var etNombre: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etEdad: EditText
    private lateinit var tvResultado: TextView
    private lateinit var btnRegistrar: Button
    private lateinit var btnLimpiar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enlazar los componentes con sus IDs del layout
        etNombre    = findViewById(R.id.etNombre)
        etApellidos = findViewById(R.id.etApellidos)
        etEdad      = findViewById(R.id.etEdad)
        tvResultado = findViewById(R.id.tvResultado)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnLimpiar   = findViewById(R.id.btnLimpiar)

        // Listener del botón Registrar usando lambda de Kotlin
        btnRegistrar.setOnClickListener {
            registrarEstudiante()
        }

        // Listener del botón Limpiar usando lambda de Kotlin
        btnLimpiar.setOnClickListener {
            limpiarCampos()
        }
    }

    // Función que valida y muestra los datos del estudiante
    private fun registrarEstudiante() {
        // Obtener valores con trim() para eliminar espacios en blanco
        val nombre    = etNombre.text.toString().trim()
        val apellidos = etApellidos.text.toString().trim()
        val edad      = etEdad.text.toString().trim()

        // Validar que ningún campo esté vacío
        if (nombre.isEmpty() || apellidos.isEmpty() || edad.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Mostrar los datos registrados usando interpolación de cadenas de Kotlin
        val resultado = "✅ Estudiante Registrado:\n" +
                "Nombre:    $nombre\n" +
                "Apellidos: $apellidos\n" +
                "Edad:      $edad años"

        tvResultado.text = resultado
        Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show()
    }

    // Función que limpia todos los campos del formulario
    private fun limpiarCampos() {
        etNombre.text.clear()
        etApellidos.text.clear()
        etEdad.text.clear()
        tvResultado.text = ""
        etNombre.requestFocus()
        Toast.makeText(this, "Campos limpiados.", Toast.LENGTH_SHORT).show()
    }
}
