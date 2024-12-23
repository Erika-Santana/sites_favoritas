package br.edu.ifsp.dmo1.sitesfavoritos.ui.activities

import android.icu.text.Transliterator.Position
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo1.sitesfavoritos.data.model.Site

class ViewModelActivity : ViewModel() {

    private val _sites = MutableLiveData<List<Site>>()
    val sites: LiveData<List<Site>> = _sites

    fun adicionarNovoSite(novoSite: Site) {
        val listaAtual = _sites.value ?: emptyList()
        val listaAtualizada = listaAtual + novoSite
        _sites.value = listaAtualizada
    }

    fun getSiteAtPosition(position: Int): Site? {

        val currentList = _sites.value ?: emptyList()

        return if (position in currentList.indices) currentList[position] else null
    }



    fun deleteSite(site: Site) {
        val listaAtual = _sites.value ?: return
        if (listaAtual.contains(site)) {
            val listaAtualizada = listaAtual.toMutableList()
            listaAtualizada.remove(site)
            _sites.value = listaAtualizada
        }
    }


}