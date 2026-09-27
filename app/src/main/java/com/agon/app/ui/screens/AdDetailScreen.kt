package com.agon.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun AdDetailScreen(ad: AdModel, lang: AppLanguage, isLoggedIn: Boolean, onBack: ()->Unit, onShopClick: (String)->Unit, onContact: ()->Unit, onComplaint: ()->Unit) {
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Box {
                AsyncImage(model = ad.image, contentDescription = null, modifier = Modifier.fillMaxWidth().height(280.dp), contentScale = ContentScale.Crop)
                Surface(color = Color.Black.copy(0.5f), shape = CircleShape, modifier = Modifier.align(Alignment.TopStart).padding(12.dp)) { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, null, tint=Color.White) } }
                if (ad.isShopVerified) Box(Modifier.align(Alignment.TopEnd).padding(12.dp)) { VerifiedBadge(lang) }
            }
        }
        item {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(adTitleForLang(ad, lang), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${ad.price.toInt()} DZD", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = PrimaryDZ)
                    Spacer(Modifier.width(12.dp))
                    Surface(color = CategoryInfo.getColor(ad.category).copy(0.15f), shape = RoundedCornerShape(8.dp)) { Text(CategoryInfo.getName(ad.category, lang), modifier = Modifier.padding(horizontal=8.dp, vertical=4.dp), fontSize=11.sp, color=CategoryInfo.getColor(ad.category)) }
                }
                Text(ad.description, fontSize=13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                HorizontalDivider()
                Card(shape=RoundedCornerShape(12.dp)) {
                    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = PrimaryDZ, modifier = Modifier.size(40.dp)) { 
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { 
                                val initial = ad.shopName.firstOrNull()?.toString() ?: "M"
                                Text(initial, color=Color.White, fontWeight=FontWeight.Bold) 
                            } 
                        }
                        Spacer(Modifier.width(10.dp))
                        Column(Modifier.weight(1f)) { 
                            Text(ad.shopName, fontWeight=FontWeight.Bold, fontSize=13.sp)
                            Text("${ad.location.city} • ${ad.location.wilaya}", fontSize=11.sp, color=MaterialTheme.colorScheme.onSurfaceVariant) 
                        }
                        FilledTonalButton(onClick = { onShopClick(ad.shopId) }) { Text(tr("view_shop", lang), fontSize=11.sp) }
                    }
                }
                Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Filled.LocationOn, null, modifier=Modifier.size(16.dp)); Spacer(Modifier.width(6.dp)); Text("${ad.location.wilaya} - ${ad.location.city}", fontSize=12.sp) }
                Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Filled.Visibility, null, modifier=Modifier.size(16.dp)); Spacer(Modifier.width(6.dp)); Text("${ad.views} views", fontSize=12.sp) }
                Text("${tr("ads", lang)} ID: ${ad.id} • ${ad.createdAt}", fontSize=10.sp, color=MaterialTheme.colorScheme.onSurfaceVariant)
                Button(onClick = onContact, modifier=Modifier.fillMaxWidth()) { Icon(Icons.Filled.Chat, null); Spacer(Modifier.width(8.dp)); Text(tr("contact_shop", lang)) }
                OutlinedButton(onClick = onComplaint, modifier=Modifier.fillMaxWidth()) { Icon(Icons.Filled.Report, null, modifier=Modifier.size(16.dp)); Spacer(Modifier.width(6.dp)); Text(tr("complaint", lang)) }
                if (!isLoggedIn) {
                    Card(colors=CardDefaults.cardColors(containerColor=MaterialTheme.colorScheme.tertiaryContainer)) { 
                        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) { 
                            Icon(Icons.Filled.Lock, null, modifier=Modifier.size(16.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(tr("login_required", lang), fontSize=12.sp)
                        } 
                    }
                }
            }
        }
    }
}
