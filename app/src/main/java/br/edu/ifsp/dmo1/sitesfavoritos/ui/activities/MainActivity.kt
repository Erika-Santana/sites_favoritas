package br.edu.ifsp.dmo1.sitesfavoritos.ui.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.dmo1.sitesfavoritos.R
import br.edu.ifsp.dmo1.sitesfavoritos.data.model.Site
import br.edu.ifsp.dmo1.sitesfavoritos.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.sitesfavoritos.databinding.SitesDialogBinding
import br.edu.ifsp.dmo1.sitesfavoritos.ui.adapters.SiteAdapter
import br.edu.ifsp.dmo1.sitesfavoritos.ui.listeners.SiteItemClickListener

class MainActivity : AppCompatActivity(), SiteItemClickListener {

    private lateinit var viewModelActivity: ViewModelActivity
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SiteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configListeners()
        configRecyclerView()


        viewModelActivity = ViewModelProvider(this).get(ViewModelActivity::class.java)

        viewModelActivity.sites.observe(this, Observer { sites ->
            adapter.updateData(sites)
        })
    }

    override fun clickSiteItem(position: Int) {
        val site = viewModelActivity.getSiteAtPosition(position)
        val mIntent = Intent(Intent.ACTION_VIEW)
        mIntent.setData(Uri.parse("http://" + site?.url))
        startActivity(mIntent)
    }

    override fun clickHeartSiteItem(position: Int) {
        val site = viewModelActivity.getSiteAtPosition(position)
        if (site != null) {
            site.favorito = !site.favorito
            adapter.notifyItemChanged(position)
        }
    }

    override fun clickDeleteSiteItem(position: Int) {
        val site = viewModelActivity.getSiteAtPosition(position)
        if (site != null) {
            viewModelActivity.deleteSite(site)
            adapter.notifyItemRemoved(position)
        }
    }

    private fun configListeners() {
        binding.buttonAdd.setOnClickListener { handleAddSite() }
    }

    private fun configRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerviewSites.layoutManager = layoutManager
        adapter = SiteAdapter(this, emptyList(), this)
        binding.recyclerviewSites.adapter = adapter
    }

    private fun notifyAdapter() {
        adapter.notifyDataSetChanged()
    }

    private fun handleAddSite() {
        val tela = layoutInflater.inflate(R.layout.sites_dialog, null)
        val bindingDialog: SitesDialogBinding = SitesDialogBinding.bind(tela)

        val builder = AlertDialog.Builder(this)
            .setView(tela)
            .setTitle(R.string.novo_site)
            .setPositiveButton(R.string.salvar) { dialog, which ->
                viewModelActivity.adicionarNovoSite(
                    Site(
                        bindingDialog.edittextApelido.text.toString(),
                        bindingDialog.edittextUrl.text.toString()
                    )
                )
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancelar) { dialog, which ->
                dialog.dismiss()
            }

        builder.create().show()
    }
}
