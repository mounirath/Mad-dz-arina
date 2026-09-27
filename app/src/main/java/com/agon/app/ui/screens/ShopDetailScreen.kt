package com.agon.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.agon.app.data.*
import com.agon.app.ui.components.*
import com.agon.app.ui.theme.*

@Composable
fun ShopDetailScreen(
    shop: ShopModel,
    lang: AppLanguage,
    isLoggedIn: Boolean,
    onBack: ()->Unit,
    onContact: ()->Unit,
    onAdClick: (String)->Unit,
    onComplaint: ()->Unit,
    onMap: ()->Unit
) {
    var tab by remember { mutableStateOf(0) }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Box {
                AsyncImage(model = shop.coverImage, contentDescription = null, modifier = Modifier.fillMaxWidth().height(200.dp), contentScale = ContentScale.Crop)
                Surface(color = Color.Black.copy(0.45f), shape = CircleShape, modifier = Modifier.align(Alignment.TopStart).padding(12.dp)) { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, null, tint = Color.White) } }
            }
        }
        item {
            Column(Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(model = shop.logo, contentDescription = null, modifier = Modifier.size(56.dp).clip(CircleShape), contentScale = ContentScale.Crop)
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(shopNameForLang(shop, lang), fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            if (shop.isVerified) { Spacer(Modifier.width(6.dp)); Icon(Icons.Filled.Verified, null, tint = VerifiedBlue, modifier = Modifier.size(20.dp)) }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Star, null, tint = RatingGold, modifier = Modifier.size(16.dp))
                            Text(" ${shop.rating} (${shop.ratingCount} ${tr("ratings", lang)})", fontSize = 12.sp)
                        }
                        Surface(color = CategoryInfo.getColor(shop.category).copy(0.15f), shape = RoundedCornerShape(6.dp)) { Text(CategoryInfo.getName(shop.category, lang), modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp), fontSize = 11.sp, color = CategoryInfo.getColor(shop.category)) }
                    }
                }
                Spacer(Modifier.height(12.dp))
                if (shop.isVerified) VerifiedBadge(lang)
                if (shop.verificationStatus != VerificationStatus.VERIFIED) {
                    Spacer(Modifier.height(6.dp))
                    val statusText = when(shop.verificationStatus) {
                        VerificationStatus.NOT_VERIFIED -> tr("not_verified", lang)
                        VerificationStatus.UNDER_REVIEW -> tr("under_review", lang)
                        VerificationStatus.REJECTED -> tr("rejected", lang)
                        else -> ""
                    }
                    Surface(color = when(shop.verificationStatus){ VerificationStatus.REJECTED -> ErrorRed.copy(0.12f); VerificationStatus.UNDER_REVIEW -> WarningOrange.copy(0.12f); else -> MaterialTheme.colorScheme.surfaceVariant }, shape = RoundedCornerShape(8.dp)) {
                        Text("${tr("verification_status", lang)}: $statusText", modifier = Modifier.padding(8.dp), fontSize = 11.sp)
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text(tr("description", lang), fontWeight = FontWeight.Bold)
                Text(shopDescForLang(shop, lang), fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(12.dp))
                Card(shape = RoundedCornerShape(12.dp)) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row { Icon(Icons.Filled.LocationOn, null, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(6.dp)); Text("${shop.location.address} - ${shop.location.wilaya}", fontSize = 12.sp) }
                        Row { Icon(Icons.Filled.Phone, null, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(6.dp)); Text(shop.phone, fontSize = 12.sp, color = PrimaryDZ, fontWeight = FontWeight.Bold) }
                        Row { Icon(Icons.Filled.Schedule, null, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(6.dp)); Text("${shop.hours.open} - ${shop.hours.close}", fontSize = 12.sp) }
                    }
                }
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = onContact, modifier = Modifier.weight(1f)) { Icon(Icons.Filled.Chat, null, modifier = Modifier.size(18.dp)); Spacer(Modifier.width(6.dp)); Text(tr("contact_shop", lang)) }
                    FilledTonalButton(onClick = onMap) { Icon(Icons.Filled.Map, null); Spacer(Modifier.width(4.dp)); Text(tr("map", lang)) }
                }
                FilledTonalButton(onClick = onComplaint, modifier = Modifier.fillMaxWidth().padding(top=8.dp)) { Icon(Icons.Filled.Report, null, modifier = Modifier.size(16.dp)); Spacer(Modifier.width(6.dp)); Text(tr("complaint", lang)) }
            }
        }
        item {
            TabRow(selectedTabIndex = tab) {
                Tab(selected = tab==0, onClick = { tab=0 }, text = { Text(tr("products", lang), fontSize=12.sp) })
                Tab(selected = tab==1, onClick = { tab=1 }, text = { Text(tr("ads", lang), fontSize=12.sp) })
                Tab(selected = tab==2, onClick = { tab=2 }, text = { Text(tr("offers", lang), fontSize=12.sp) })
                Tab(selected = tab==3, onClick = { tab=3 }, text = { Text(tr("ratings", lang), fontSize=12.sp) })
            }
        }
        when(tab) {
            0 -> {
                item {
                    if (shop.products.isEmpty()) { Box(Modifier.padding(16.dp)) { Text("No products", fontSize=12.sp) } }
                    else LazyRow(contentPadding = PaddingValues(12.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(shop.products) { p ->
                            Card(shape = RoundedCornerShape(12.dp), modifier = Modifier.width(140.dp)) {
                                Column {
                                    AsyncImage(model = p.image, contentDescription = null, modifier = Modifier.fillMaxWidth().height(90.dp), contentScale = ContentScale.Crop)
                                    Column(Modifier.padding(8.dp)) { 
                                        Text(p.name, fontSize=11.sp, fontWeight = FontWeight.Bold, maxLines=1)
                                        Text("${p.price.toInt()} DZD", fontSize=11.sp, color=PrimaryDZ, fontWeight=FontWeight.Bold) 
                                    }
                                }
                            }
                        }
                    }
                    Column(Modifier.padding(horizontal=16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(tr("services", lang), fontWeight=FontWeight.Bold, fontSize=13.sp)
                        shop.services.forEach { s -> 
                            Row(verticalAlignment = Alignment.CenterVertically) { 
                                Icon(Icons.Filled.CheckCircle, null, tint=SuccessGreen, modifier=Modifier.size(14.dp))
                                Spacer(Modifier.width(6.dp))
                                Text(s, fontSize=12.sp) 
                            } 
                        }
                    }
                }
            }
            1 -> {
                val shopAds = FakeData.ads.filter { it.shopId==shop.id }
                if (shopAds.isEmpty()) item { Box(Modifier.padding(16.dp)) { Text("No ads for this shop") } }
                else items(shopAds.size) { idx ->
                    val ad = shopAds[idx]
                    Box(Modifier.padding(horizontal=12.dp, vertical=6.dp)) { AdCardFull(ad, lang) { onAdClick(ad.id) } }
                }
            }
            2 -> {
                val offs = FakeData.offers.filter { it.shopId==shop.id }
                if (offs.isEmpty()) item { Box(Modifier.padding(16.dp)) { Text("No offers") } }
                else item {
                    LazyRow(contentPadding = PaddingValues(12.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(offs) { o -> OfferCard(o, lang) {} }
                    }
                }
            }
            3 -> {
                val rs = FakeData.ratings.filter { it.shopId==shop.id }
                items(rs.size) { i ->
                    val r = rs[i]
                    Card(Modifier.padding(horizontal=12.dp, vertical=6.dp).fillMaxWidth()) {
                        Column(Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(r.customerName, fontWeight=FontWeight.Bold, fontSize=12.sp)
                                Spacer(Modifier.weight(1f))
                                Row { repeat(r.stars){ Icon(Icons.Filled.Star, null, tint=RatingGold, modifier=Modifier.size(14.dp)) } }
                            }
                            Text(r.comment, fontSize=12.sp, modifier=Modifier.padding(top=4.dp))
                            Text(r.date, fontSize=10.sp, color=MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
        }
        item { Spacer(Modifier.height(24.dp)) }
    }
}
