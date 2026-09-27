package com.agon.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.agon.app.data.*
import com.agon.app.ui.components.*
import com.agon.app.ui.theme.*
import com.agon.app.viewmodel.AppViewModel

@Composable
fun OffersScreen(vm: AppViewModel, onOfferClick: (OfferModel)->Unit, onAdClick: (String)->Unit) {
    val lang = vm.currentLanguage
    var selectedTab by remember { mutableStateOf(0) }
    Column(Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            Tab(selected = selectedTab==0, onClick = { selectedTab=0 }, text = { Text(tr("offers", lang)) })
            Tab(selected = selectedTab==1, onClick = { selectedTab=1 }, text = { Text(tr("ads", lang)) })
        }
        if (selectedTab==0) {
            LazyVerticalGrid(columns = GridCells.Fixed(2), contentPadding = PaddingValues(12.dp), horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxSize()) {
                items(FakeData.offers) { off -> OfferCard(off, lang) { onOfferClick(off) } }
            }
        } else {
            LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(FakeData.ads) { ad -> AdCardFull(ad, lang) { onAdClick(ad.id) } }
            }
        }
    }
}

@Composable
fun OfferDetailScreen(offer: OfferModel, lang: AppLanguage, onBack: ()->Unit, onContact: ()->Unit) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize()) {
        item {
            Row {
                FilledTonalButton(onClick = onBack) { Text("←") }
                Spacer(Modifier.weight(1f))
                Surface(color = OfferRed, shape = RoundedCornerShape(8.dp)) {
                    Text("-${offer.discountPercent}% ${tr("discount", lang)}", color = Color.White, modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp))
                }
            }
        }
        item {
            AsyncImage(model = offer.image, contentDescription = null, modifier = Modifier.fillMaxWidth().height(220.dp).clip(RoundedCornerShape(16.dp)), contentScale = ContentScale.Crop)
        }
        item {
            Text(offerTitleForLang(offer, lang), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${offer.newPrice.toInt()} DZD", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = OfferRed)
                Spacer(Modifier.width(12.dp))
                Text("${offer.oldPrice.toInt()} DZD", style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough), color = Color.Gray)
            }
            Spacer(Modifier.height(8.dp))
            Text("${tr("expiry", lang)}: ${offer.expiryDate}", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("${tr("shops", lang)}: ${offer.shopName}", fontWeight = FontWeight.Medium)
        }
        item {
            Button(onClick = onContact, modifier = Modifier.fillMaxWidth()) { Text(tr("contact_shop", lang)) }
        }
    }
}
