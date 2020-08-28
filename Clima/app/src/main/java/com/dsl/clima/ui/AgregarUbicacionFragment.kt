package com.dsl.clima.ui

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import com.dsl.clima.R
import com.dsl.clima.databinding.FragmentAgregarUbicacionBinding

class AgregarUbicacionFragment : Fragment() {
    private lateinit var searchItem: MenuItem
    private lateinit var searchView: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAgregarUbicacionBinding.inflate(inflater)

        /**
         * Se habilita opciones de menu a fragmento [ProductFragment]
         */
        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Este metodo se sobrescribe para crear el menú de opciones
     * cuando el usuario abre el menú por primera vez.
     * Muestra los elementos de la barra de app [searchView] y [filter].
     * La finalidad es configurar la interfaz de busqueda y la logica de la misma.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.agregar_ubicacion_menu, menu)

        /**
         * Se crea y configura SearchView [searchView].
         */
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.maxWidth = Int.MAX_VALUE
        searchView.queryHint = getString(R.string.search_hint_agregar_ubicacion)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            // Para buscar cuando aprieta en buscar o enter desde teclado
            @SuppressLint("DefaultLocale")
            override fun onQueryTextSubmit(query: String?): Boolean {
                /**
                 * La busqueda [query] se pasa a minuscula
                 * y se quitan los espacios en los extremos.
                 * Luego se llama a la funcion [filter] para efectuar la busqueda.
                 * Por ultimo, cuando se pulsa en buscar se quita el foco de
                 * SearchView [searchView] y se quita el teclado de la pantalla.
                 */
                //query?.toLowerCase()?.trim()?.let { viewModel.filter(it) }
                searchView.clearFocus()
                searchItem.collapseActionView()
                return true
            }

            // Para buscar cada vez que añade un nuevo caracter
            override fun onQueryTextChange(newText: String?): Boolean {
                //newText?.toLowerCase()?.trim()?.let { viewModel.filter(it) }
                return false
            }

        })
    }
}