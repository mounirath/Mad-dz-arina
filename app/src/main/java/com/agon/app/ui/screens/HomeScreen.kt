package com.agon.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.agon.app.data.AppLanguage
import com.agon.app.data.FakeData
import com.agon.app.data.tr
import com.agon.app.ui.components.*
import com.agon.app.viewmodel.AppViewModel

@Composable
fun HomeScreen(
    vm: AppViewModel = viewModel(),
    onNavigateShop: (String) -> Unit = {},
    onNavigateAd: (String) -> Unit = {},
    onNavigateOffer: () -> Unit = {},
    onNavigateExplore: () -> Unit = {},
    onLogin: () -> Unit = {},
    onProfile: () -> Unit = {}
) {
    val lang = vm.currentLanguage
    val filteredShops = vm.getFilteredShops()
    val filteredAds = vm.getFilteredAds()
    val verifiedShops = FakeData.shops.filter { it.isVerified }.take(6)

    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(bottom = 16.dp)) {
        item {
            AppTopHeader(
                lang = lang,
                onLanguageChange = { vm.setLanguage(it) },
                isLoggedIn = vm.isLoggedIn,
                userType = vm.currentUserType,
                onLoginClick = onLogin,
                onProfileClick = onProfile
            )
        }
        item {
            SearchBarCustom(vm.searchQuery, { vm.searchQuery = it }, lang, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp))
        }
        if (!vm.isLoggedIn) {
            item { GuestBanner(lang, onLogin) }
        }
        item {
            Row(Modifier.padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                FilterChip(selected = vm.showVerifiedOnly, onClick = { vm.toggleVerifiedFilter() }, label = { Text(tr("verified_only", lang)) }, leadingIcon = { Icon(Icons.Filled.Verified, null, modifier = Modifier.size(16.dp)) })
                Spacer(Modifier.weight(1f))
                Text("${filteredShops.size} ${tr("shops", lang)} • ${filteredAds.size} ${tr("ads", lang)}", style = MaterialTheme.typography.labelSmall)
            }
            Spacer(Modifier.height(8.dp))
        }
        item { SectionHeader(tr("categories", lang), { onNavigateExplore() }, lang) }
        item { CategoryChipsRow(lang = lang, selected = vm.selectedCategory, onSelect = { vm.setCategory(it) }); Spacer(Modifier.height(12.dp)) }

        item { SectionHeader(tr("latest_shops", lang), { onNavigateExplore() }, lang) }
        item {
            LazyRow(contentPadding = PaddingValues(horizontal = 12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filteredShops.take(10)) { shop ->
                    Box(modifier = Modifier.width(300.dp)) { ShopCard(shop, lang, onClick = { onNavigateShop(shop.id) }) }
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        item { SectionHeader(tr("latest_ads", lang), { onNavigateExplore() }, lang) }
        item {
            LazyRow(contentPadding = PaddingValues(horizontal = 12.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(filteredAds) { ad -> AdCard(ad, lang) { onNavigateAd(ad.id) } }
            }
            Spacer(Modifier.height(12.dp))
        }

        item { SectionHeader(tr("offers_discounts", lang), { onNavigateOffer() }, lang) }
        item {
            LazyRow(contentPadding = PaddingValues(horizontal = 12.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(FakeData.offers) { off -> OfferCard(off, lang) { onNavigateOffer() } }
            }
            Spacer(Modifier.height(12.dp))
        }

        item { SectionHeader(tr("verified_shops", lang), null, lang) }
        items(verifiedShops) { shop ->
            Box(Modifier.padding(horizontal = 12.dp, vertical = 6.dp)) { ShopCard(shop, lang, onClick = { onNavigateShop(shop.id) }) }
        }
    }
}
