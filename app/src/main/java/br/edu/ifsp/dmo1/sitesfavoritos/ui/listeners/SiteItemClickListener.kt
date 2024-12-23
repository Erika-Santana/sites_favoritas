package br.edu.ifsp.dmo1.sitesfavoritos.ui.listeners

interface SiteItemClickListener {
    fun clickSiteItem(position: Int)
    fun clickHeartSiteItem(position: Int)
    fun clickDeleteSiteItem(position: Int)
}