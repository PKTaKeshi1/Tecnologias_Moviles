package com.example.sesion02ejercicio02

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Actividad principal de la app "Pizzas del Mundo".
 *
 * Muestra un GridView con imágenes de distintos tipos de pizza.
 * Al tocar una celda, se muestra el nombre de la pizza en un
 * banner superior y en un Toast.
 *
 * IMPORTANTE: Las imágenes deben colocarse en res/drawable/ con los
 * nombres indicados en la lista pizzas (p.ej. pizza_margherita.jpg).
 * Si no se tienen imágenes reales, usar un placeholder como
 * android.R.drawable.ic_menu_gallery o cualquier drawable disponible.
 */
class MainActivity : AppCompatActivity() {

    // ─────────────────────────────────────────────
    //  Componentes de la interfaz
    // ─────────────────────────────────────────────
    private lateinit var gridViewPizzas : GridView
    private lateinit var tvNombrePizza  : TextView

    // ─────────────────────────────────────────────
    //  Catálogo de pizzas
    //
    //  Cada Pizza tiene: nombre, recurso drawable, descripción.
    //
    //  *** IMPORTANTE PARA IMÁGENES REALES ***
    //  Agrega imágenes en res/drawable/ con los nombres:
    //    pizza_margherita.jpg
    //    pizza_pepperoni.jpg
    //    pizza_cuatro_quesos.jpg
    //    pizza_hawaiana.jpg
    //    pizza_napolitana.jpg
    //    pizza_bbq.jpg
    //    pizza_vegetal.jpg
    //    pizza_diavola.jpg
    //    pizza_funghi.jpg
    //    pizza_calzone.jpg
    //
    //  Mientras tanto, se usa el ícono genérico de Android como placeholder.
    // ─────────────────────────────────────────────
    private val pizzas: List<Pizza> by lazy {
        listOf(
            Pizza(
                nombre      = "Margherita",
                imagen      = obtenerDrawable("pizza_margherita"),
                descripcion = "Tomate, mozzarella y albahaca fresca"
            ),
            Pizza(
                nombre      = "Pepperoni",
                imagen      = obtenerDrawable("pizza_pepperoni"),
                descripcion = "Tomate, mozzarella y pepperoni"
            ),
            Pizza(
                nombre      = "Cuatro Quesos",
                imagen      = obtenerDrawable("pizza_cuatro_quesos"),
                descripcion = "Mozzarella, gorgonzola, parmesano y brie"
            ),
            Pizza(
                nombre      = "Hawaiana",
                imagen      = obtenerDrawable("pizza_hawaiana"),
                descripcion = "Tomate, mozzarella, jamón y piña"
            ),
            Pizza(
                nombre      = "Napolitana",
                imagen      = obtenerDrawable("pizza_napolitana"),
                descripcion = "Tomate, mozzarella, anchoas y aceitunas"
            ),
            Pizza(
                nombre      = "BBQ Chicken",
                imagen      = obtenerDrawable("pizza_bbq"),
                descripcion = "Salsa BBQ, pollo, cebolla y mozzarella"
            ),
            Pizza(
                nombre      = "Vegetal",
                imagen      = obtenerDrawable("pizza_vegetal"),
                descripcion = "Pimientos, champiñones, aceitunas y tomate"
            ),
            Pizza(
                nombre      = "Diavola",
                imagen      = obtenerDrawable("pizza_diavola"),
                descripcion = "Tomate, mozzarella y salami picante"
            ),
            Pizza(
                nombre      = "Funghi",
                imagen      = obtenerDrawable("pizza_funghi"),
                descripcion = "Tomate, mozzarella y champiñones mixtos"
            ),
            Pizza(
                nombre      = "Calzone",
                imagen      = obtenerDrawable("pizza_calzone"),
                descripcion = "Pizza doblada: jamón, mozzarella y ricotta"
            )
        )
    }

    // ─────────────────────────────────────────────
    //  Ciclo de vida
    // ─────────────────────────────────────────────
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarVistas()
        configurarGridView()
    }

    // ─────────────────────────────────────────────
    //  Inicializar vistas con findViewById
    // ─────────────────────────────────────────────
    private fun inicializarVistas() {
        gridViewPizzas = findViewById(R.id.gridViewPizzas)
        tvNombrePizza  = findViewById(R.id.tvNombrePizza)
    }

    // ─────────────────────────────────────────────
    //  Configurar GridView: adaptador + listener de click
    // ─────────────────────────────────────────────
    private fun configurarGridView() {

        // 1. Crear y asignar el adaptador personalizado
        val adapter = PizzaAdapter(this, pizzas)
        gridViewPizzas.adapter = adapter

        // 2. Listener de click en cada celda del GridView
        //    AdapterView.OnItemClickListener recibe:
        //      parent   → el GridView
        //      view     → la celda tocada
        //      position → índice de la celda (0..N-1)
        //      id       → ID del ítem
        gridViewPizzas.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val pizza = pizzas[position]
                mostrarNombrePizza(pizza)
            }
    }

    // ─────────────────────────────────────────────
    //  Mostrar nombre de la pizza seleccionada
    //  en el banner superior y en un Toast
    // ─────────────────────────────────────────────
    private fun mostrarNombrePizza(pizza: Pizza) {
        // Mostrar banner con nombre
        tvNombrePizza.text       = "🍕  ${pizza.nombre}"
        tvNombrePizza.visibility = View.VISIBLE

        // Toast con nombre + descripción
        Toast.makeText(
            this,
            "${pizza.nombre}\n${pizza.descripcion}",
            Toast.LENGTH_LONG
        ).show()
    }

    // ─────────────────────────────────────────────
    //  Utilidad: obtener el ID del drawable por nombre.
    //  Si el drawable no existe en res/drawable/, usa
    //  el ícono de galería genérico de Android como
    //  imagen placeholder.
    // ─────────────────────────────────────────────
    private fun obtenerDrawable(nombre: String): Int {
        val id = resources.getIdentifier(nombre, "drawable", packageName)
        return if (id != 0) id else android.R.drawable.ic_menu_gallery
    }
}
