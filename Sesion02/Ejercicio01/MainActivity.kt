package com.example.sesion02ejercicio01

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.sesion02ejercicio01.R

class MainActivity : AppCompatActivity() {

    // ─────────────────────────────────────────────
    //  Componentes de la interfaz
    // ─────────────────────────────────────────────
    private lateinit var etMonto       : EditText
    private lateinit var spinnerOrigen : Spinner
    private lateinit var spinnerDestino: Spinner
    private lateinit var btnConvertir  : Button
    private lateinit var btnLimpiar    : Button
    private lateinit var tvResultado   : TextView
    private lateinit var tvDetalle     : TextView
    private lateinit var tvTasas       : TextView
    private lateinit var cardResultado : CardView

    // ─────────────────────────────────────────────
    //  Tipos de cambio respecto al USD (1 USD = X moneda)
    //  Fuente de referencia aproximada: mercado 2025
    // ─────────────────────────────────────────────
    private val divisas = listOf(
        "Nuevo Sol (PEN)",
        "Dólar (USD)",
        "Euro (EUR)",
        "Libra (GBP)",
        "Rupia (INR)",
        "Real (BRL)",
        "Peso Mexicano (MXN)",
        "Yuan (CNY)",
        "Yen (JPY)"
    )

    // Tasa de cambio respecto al USD para cada divisa (mismo orden que la lista)
    private val tasasUSD = mapOf(
        "Nuevo Sol (PEN)"     to 3.70,
        "Dólar (USD)"         to 1.00,
        "Euro (EUR)"          to 0.92,
        "Libra (GBP)"         to 0.79,
        "Rupia (INR)"         to 83.50,
        "Real (BRL)"          to 5.05,
        "Peso Mexicano (MXN)" to 17.20,
        "Yuan (CNY)"          to 7.24,
        "Yen (JPY)"           to 149.50
    )

    // Símbolos de cada divisa
    private val simbolos = mapOf(
        "Nuevo Sol (PEN)"     to "S/.",
        "Dólar (USD)"         to "$",
        "Euro (EUR)"          to "€",
        "Libra (GBP)"         to "£",
        "Rupia (INR)"         to "₹",
        "Real (BRL)"          to "R$",
        "Peso Mexicano (MXN)" to "MX$",
        "Yuan (CNY)"          to "¥",
        "Yen (JPY)"           to "¥"
    )

    // ─────────────────────────────────────────────
    //  Ciclo de vida
    // ─────────────────────────────────────────────
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarVistas()
        configurarSpinners()
        mostrarTablaDeTasas()
        configurarBotones()
    }

    // ─────────────────────────────────────────────
    //  Inicialización de vistas con findViewById
    // ─────────────────────────────────────────────
    private fun inicializarVistas() {
        etMonto        = findViewById(R.id.etMonto)
        spinnerOrigen  = findViewById(R.id.spinnerOrigen)
        spinnerDestino = findViewById(R.id.spinnerDestino)
        btnConvertir   = findViewById(R.id.btnConvertir)
        btnLimpiar     = findViewById(R.id.btnLimpiar)
        tvResultado    = findViewById(R.id.tvResultado)
        tvDetalle      = findViewById(R.id.tvDetalle)
        tvTasas        = findViewById(R.id.tvTasas)
        cardResultado  = findViewById(R.id.cardResultado)
    }

    // ─────────────────────────────────────────────
    //  Configurar Spinners con ArrayAdapter
    // ─────────────────────────────────────────────
    private fun configurarSpinners() {
        // ArrayAdapter enlaza la lista de divisas con cada Spinner
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            divisas
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        spinnerOrigen.adapter  = adapter
        spinnerDestino.adapter = adapter

        // Valores por defecto: Nuevo Sol → Dólar
        spinnerOrigen.setSelection(0)
        spinnerDestino.setSelection(1)
    }

    // ─────────────────────────────────────────────
    //  Configurar listeners de botones
    // ─────────────────────────────────────────────
    private fun configurarBotones() {
        btnConvertir.setOnClickListener { realizarConversion() }
        btnLimpiar.setOnClickListener   { limpiarCampos()      }
    }

    // ─────────────────────────────────────────────
    //  Lógica principal de conversión
    //  Fórmula: montoOrigen / tasaOrigen * tasaDestino
    //  Se convierte primero a USD (base) y luego a destino
    // ─────────────────────────────────────────────
    private fun realizarConversion() {

        // 1. Validar campo de monto
        val montoTexto = etMonto.text.toString().trim()
        if (montoTexto.isEmpty()) {
            mostrarError(getString(R.string.error_monto_vacio))
            return
        }

        val monto = montoTexto.toDoubleOrNull()
        if (monto == null || monto <= 0) {
            mostrarError(getString(R.string.error_monto_invalido))
            return
        }

        // 2. Obtener monedas seleccionadas en los Spinners
        val origenNombre  = spinnerOrigen.selectedItem.toString()
        val destinoNombre = spinnerDestino.selectedItem.toString()

        if (origenNombre == destinoNombre) {
            mostrarError(getString(R.string.error_misma_moneda))
            return
        }

        // 3. Obtener tasas respecto al USD
        val tasaOrigen  = tasasUSD[origenNombre]  ?: 1.0
        val tasaDestino = tasasUSD[destinoNombre] ?: 1.0

        // 4. Conversión: origen → USD → destino
        val montoEnUSD      = monto / tasaOrigen
        val montoConvertido = montoEnUSD * tasaDestino

        // 5. Obtener símbolo de la moneda destino
        val simboloOrigen  = simbolos[origenNombre]  ?: ""
        val simboloDestino = simbolos[destinoNombre] ?: ""

        // 6. Calcular tasa directa (origen → destino) para mostrar referencia
        val tasaDirecta = tasaDestino / tasaOrigen

        // 7. Extraer código de la moneda (entre paréntesis)
        val codigoOrigen  = origenNombre.substringAfterLast("(").removeSuffix(")")
        val codigoDestino = destinoNombre.substringAfterLast("(").removeSuffix(")")

        // 8. Mostrar resultado en la tarjeta
        tvResultado.text = "$simboloDestino ${"%.4f".format(montoConvertido)}"
        tvDetalle.text   =
            "${"%.2f".format(monto)} $origenNombre\n" +
                    "Tasa: 1 $codigoOrigen = ${"%.4f".format(tasaDirecta)} $codigoDestino"

        cardResultado.visibility = View.VISIBLE
    }

    // ─────────────────────────────────────────────
    //  Tabla de tipos de cambio (base USD)
    // ─────────────────────────────────────────────
    private fun mostrarTablaDeTasas() {
        val sb = StringBuilder()
        tasasUSD.forEach { (moneda, tasa) ->
            val simbolo = simbolos[moneda] ?: ""
            val codigo  = moneda.substringAfterLast("(").removeSuffix(")")
            sb.appendLine("1 USD  =  ${"%.4f".format(tasa).padStart(10)} $simbolo  ($codigo)")
        }
        tvTasas.text = sb.toString().trimEnd()
    }

    // ─────────────────────────────────────────────
    //  Limpiar todos los campos del formulario
    // ─────────────────────────────────────────────
    private fun limpiarCampos() {
        etMonto.text.clear()
        spinnerOrigen.setSelection(0)
        spinnerDestino.setSelection(1)
        tvResultado.text         = ""
        tvDetalle.text           = ""
        cardResultado.visibility = View.GONE
        etMonto.requestFocus()
        Toast.makeText(this, getString(R.string.msg_campos_limpiados), Toast.LENGTH_SHORT).show()
    }

    // ─────────────────────────────────────────────
    //  Mostrar Toast de error
    // ─────────────────────────────────────────────
    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        etMonto.requestFocus()
    }
}
