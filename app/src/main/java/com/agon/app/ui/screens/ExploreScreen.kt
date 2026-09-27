package com.agon.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.agon.app.data.*
import com.agon.app.ui.components.*
import com.agon.app.viewmodel.AppViewModel

@Composable
fun ExploreScreen(vm: AppViewModel, onShopClick: (String)->Unit, onAdClick: (String)->Unit) {
    val lang = vm.currentLanguage
    var tab by remember { mutableStateOf(0) }
    Column(Modifier.fillMaxSize()) {
        SearchBarCustom(vm.searchQuery, { vm.searchQuery = it }, lang, Modifier.padding(12.dp))
        CategoryChipsRow(lang, vm.selectedCategory) { vm.setCategory(it) }
        Spacer(Modifier.height(8.dp))
        Row(Modifier.padding(horizontal = 12.dp)) {
            FilterChip(selected = vm.showVerifiedOnly, onClick = { vm.toggleVerifiedFilter() }, label = { Text(tr("verified_only", lang)) })
            Spacer(Modifier.width(8.dp))
            AssistChip(onClick = { vm.setCategory(null); vm.searchQuery=""; vm.showVerifiedOnly=false }, label = { Text("Reset") }, leadingIcon = { Icon(Icons.Filled.Refresh, null) })
        }
        TabRow(selectedTabIndex = tab, modifier = Modifier.padding(top=8.dp)) {
            Tab(selected = tab==0, onClick = { tab=0 }, text = { Text("${tr("shops",lang)} (${vm.getFilteredShops().size})") })
            Tab(selected = tab==1, onClick = { tab=1 }, text = { Text("${tr("ads",lang)} (${vm.getFilteredAds().size})") })
        }
        when(tab) {
            0 -> LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(vm.getFilteredShops()) { s -> ShopCard(s, lang, { onShopClick(s.id) }) }
                if (vm.getFilteredShops().isEmpty()) { item { Box(Modifier.fillMaxWidth().padding(32.dp)) { Text(tr("no_results", lang)) } } }
            }
            1 -> LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(vm.getFilteredAds()) { ad -> AdCardFull(ad, lang) { onAdClick(ad.id) } }
            }
        }
    }
}
