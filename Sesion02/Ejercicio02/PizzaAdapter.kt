package com.example.sesion02ejercicio02

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Adaptador personalizado para el GridView de pizzas.
 *
 * Extiende BaseAdapter, que es la clase base que permite
 * conectar una lista de datos con una vista de colección (GridView / ListView).
 *
 * Métodos obligatorios de BaseAdapter:
 *   - getCount()    → cuántos ítems tiene la lista
 *   - getItem()     → devuelve el objeto en la posición dada
 *   - getItemId()   → devuelve el ID del ítem (usamos la posición)
 *   - getView()     → infla y devuelve la vista de cada celda
 */
class PizzaAdapter(
    private val context : Context,
    private val pizzas  : List<Pizza>
) : BaseAdapter() {

    // ── ViewHolder: patrón para evitar llamadas repetidas a findViewById ──
    private class ViewHolder(view: View) {
        val imgPizza    : ImageView = view.findViewById(R.id.imgPizza)
        val tvNombreItem: TextView  = view.findViewById(R.id.tvNombreItem)
    }

    // Número total de ítems en la cuadrícula
    override fun getCount(): Int = pizzas.size

    // Objeto Pizza en la posición solicitada
    override fun getItem(position: Int): Pizza = pizzas[position]

    // ID del ítem (usamos la posición como identificador único)
    override fun getItemId(position: Int): Long = position.toLong()

    /**
     * getView() es llamado por el GridView para obtener la vista de cada celda.
     *
     * @param position    Índice de la celda actual.
     * @param convertView Vista reciclada (puede ser null la primera vez).
     * @param parent      Contenedor padre (el GridView).
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        // Reutilizar vista reciclada o inflar una nueva
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            // Primera vez: inflar el layout item_pizza.xml
            view   = LayoutInflater.from(context).inflate(R.layout.item_pizza, parent, false)
            holder = ViewHolder(view)
            view.tag = holder          // Guardar el holder en el tag de la vista
        } else {
            // Vista reciclada: recuperar el holder guardado
            view   = convertView
            holder = view.tag as ViewHolder
        }

        // Obtener la pizza correspondiente a esta posición
        val pizza = getItem(position)

        // Asignar imagen y nombre a los componentes de la celda
        holder.imgPizza.setImageResource(pizza.imagen)
        holder.tvNombreItem.text = pizza.nombre

        return view
    }
}
