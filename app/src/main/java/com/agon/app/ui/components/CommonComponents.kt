package com.agon.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.agon.app.data.*
import com.agon.app.ui.theme.*

@Composable
fun VerifiedBadge(lang: AppLanguage, small: Boolean = false) {
    Surface(
        color = VerifiedBg,
        shape = RoundedCornerShape(if (small) 6.dp else 8.dp),
        modifier = Modifier.wrapContentSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = if (small) 6.dp else 8.dp, vertical = if (small) 2.dp else 4.dp)
        ) {
            Icon(Icons.Filled.Verified, contentDescription = null, tint = VerifiedBlue, modifier = Modifier.size(if (small) 14.dp else 16.dp))
            Spacer(Modifier.width(3.dp))
            Text(tr("verified_badge", lang), color = VerifiedBlue, fontSize = if (small) 10.sp else 11.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LanguageSwitcher(current: AppLanguage, onSelect: (AppLanguage) -> Unit) {
    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(24.dp)) {
        Row(modifier = Modifier.padding(3.dp)) {
            AppLanguage.values().forEach { lang ->
                val selected = lang == current
                Surface(
                    color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.clickable { onSelect(lang) }
                ) {
                    Text(
                        when (lang) {
                            AppLanguage.AR -> "العربية"
                            AppLanguage.FR -> "Français"
                            AppLanguage.EN -> "English"
                        },
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun AppTopHeader(
    lang: AppLanguage,
    onLanguageChange: (AppLanguage) -> Unit,
    isLoggedIn: Boolean,
    userType: UserType,
    onLoginClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Surface(color = PrimaryDZ, shape = RoundedCornerShape(12.dp), modifier = Modifier.size(44.dp)) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text("د.م", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(tr("app_name", lang), fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("Algeria • ${FakeData.shops.size} ${tr("shops", lang)}", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (!isLoggedIn) {
                FilledTonalButton(onClick = onLoginClick, contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)) {
                    Icon(Icons.Filled.Person, null, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(tr("login_register", lang), fontSize = 11.sp)
                }
            } else {
                IconButton(onClick = onProfileClick) {
                    Surface(shape = CircleShape, color = PrimaryDZ, modifier = Modifier.size(36.dp)) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text("A", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(10.dp))
        LanguageSwitcher(current = lang, onSelect = onLanguageChange)
    }
}

@Composable
fun SearchBarCustom(query: String, onQueryChange: (String) -> Unit, lang: AppLanguage, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(tr("search", lang), fontSize = 13.sp) },
        leadingIcon = { Icon(Icons.Filled.Search, null) },
        trailingIcon = { if (query.isNotEmpty()) IconButton(onClick = { onQueryChange("") }) { Icon(Icons.Filled.Clear, null) } },
        shape = RoundedCornerShape(16.dp),
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant)
    )
}

@Composable
fun CategoryChipsRow(lang: AppLanguage, selected: ShopCategory?, onSelect: (ShopCategory?) -> Unit) {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState()).padding(horizontal = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        FilterChip(
            selected = selected == null,
            onClick = { onSelect(null) },
            label = { Text(tr("all_categories", lang)) },
            leadingIcon = { Text("🌐") }
        )
        ShopCategory.values().forEach { cat ->
            FilterChip(
                selected = selected == cat,
                onClick = { onSelect(if (selected == cat) null else cat) },
                label = { Text(CategoryInfo.getName(cat, lang)) },
                leadingIcon = { Text(cat.icon) },
                colors = FilterChipDefaults.filterChipColors(selectedContainerColor = CategoryInfo.getColor(cat).copy(alpha = 0.15f))
            )
        }
    }
}

@Composable
fun ShopCard(shop: ShopModel, lang: AppLanguage, onClick: () -> Unit, onFavorite: (() -> Unit)? = null, isFav: Boolean = false) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box {
                AsyncImage(model = shop.coverImage, contentDescription = null, modifier = Modifier.fillMaxWidth().height(130.dp), contentScale = ContentScale.Crop)
                if (shop.isVerified) {
                    Box(modifier = Modifier.padding(8.dp)) { VerifiedBadge(lang, small = true) }
                }
                Row(modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)) {
                    Surface(color = Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp)) {
                        Row(Modifier.padding(horizontal = 6.dp, vertical = 3.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Star, null, tint = RatingGold, modifier = Modifier.size(12.dp))
                            Spacer(Modifier.width(2.dp))
                            Text("${shop.rating}", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
            Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(model = shop.logo, contentDescription = null, modifier = Modifier.size(44.dp).clip(CircleShape), contentScale = ContentScale.Crop)
                Spacer(Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(shopNameForLang(shop, lang), fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(color = CategoryInfo.getColor(shop.category).copy(alpha = 0.12f), shape = RoundedCornerShape(6.dp)) {
                            Text(CategoryInfo.getName(shop.category, lang), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontSize = 10.sp, color = CategoryInfo.getColor(shop.category), fontWeight = FontWeight.Medium)
                        }
                        Spacer(Modifier.width(6.dp))
                        Icon(Icons.Filled.LocationOn, null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(shop.location.wilaya, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Schedule, null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.width(4.dp))
                        Text("${shop.hours.open}-${shop.hours.close}", fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                FilledButtonCompact(text = tr("view_shop", lang), onClick = onClick)
            }
        }
    }
}

@Composable
fun FilledButtonCompact(text: String, onClick: () -> Unit) {
    Button(onClick = onClick, contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp), shape = RoundedCornerShape(10.dp), modifier = Modifier.height(32.dp)) {
        Text(text, fontSize = 11.sp)
    }
}

fun shopNameForLang(shop: ShopModel, lang: AppLanguage): String = when (lang) {
    AppLanguage.AR -> shop.nameAr
    AppLanguage.FR -> shop.nameFr
    AppLanguage.EN -> shop.name
}
fun adTitleForLang(ad: AdModel, lang: AppLanguage): String = when (lang) {
    AppLanguage.AR -> ad.titleAr
    AppLanguage.FR -> ad.titleFr
    AppLanguage.EN -> ad.title
}
fun offerTitleForLang(off: OfferModel, lang: AppLanguage): String = when (lang) {
    AppLanguage.AR -> off.titleAr
    AppLanguage.FR -> off.titleFr
    AppLanguage.EN -> off.title
}
fun shopDescForLang(shop: ShopModel, lang: AppLanguage): String = when (lang) {
    AppLanguage.AR -> shop.descriptionAr
    AppLanguage.FR -> shop.descriptionFr
    AppLanguage.EN -> shop.description
}

@Composable
fun AdCard(ad: AdModel, lang: AppLanguage, onClick: () -> Unit) {
    Card(shape = RoundedCornerShape(14.dp), modifier = Modifier.width(200.dp).clickable { onClick() }, elevation = CardDefaults.cardElevation(2.dp)) {
        Column {
            Box {
                AsyncImage(model = ad.image, contentDescription = null, modifier = Modifier.fillMaxWidth().height(120.dp), contentScale = ContentScale.Crop)
                if (ad.isShopVerified) { Box(Modifier.padding(6.dp)) { VerifiedBadge(lang, true) } }
                Surface(color = Color.Black.copy(0.6f), shape = RoundedCornerShape(8.dp), modifier = Modifier.align(Alignment.BottomEnd).padding(6.dp)) {
                    Text("${ad.price.toInt()} DZD", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp))
                }
            }
            Column(Modifier.padding(8.dp)) {
                Text(adTitleForLang(ad, lang), fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Store, null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.width(3.dp))
                    Text(ad.shopName, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.LocationOn, null, modifier = Modifier.size(10.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(ad.location.wilaya, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.weight(1f))
                    Text("${ad.views} 👁", fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
fun AdCardFull(ad: AdModel, lang: AppLanguage, onClick: () -> Unit) {
    Card(shape = RoundedCornerShape(14.dp), modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
        Row(Modifier.padding(8.dp)) {
            AsyncImage(model = ad.image, contentDescription = null, modifier = Modifier.size(88.dp).clip(RoundedCornerShape(12.dp)), contentScale = ContentScale.Crop)
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(adTitleForLang(ad, lang), fontWeight = FontWeight.Bold, fontSize = 13.sp, modifier = Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                    if (ad.isShopVerified) Icon(Icons.Filled.Verified, null, tint = VerifiedBlue, modifier = Modifier.size(16.dp))
                }
                Text(ad.shopName, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(4.dp))
                Text("${ad.price.toInt()} DZD", fontWeight = FontWeight.ExtraBold, color = PrimaryDZ, fontSize = 14.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.LocationOn, null, modifier = Modifier.size(12.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(ad.location.city, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun OfferCard(offer: OfferModel, lang: AppLanguage, onClick: () -> Unit) {
    Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = OfferBg), modifier = Modifier.width(220.dp).clickable { onClick() }) {
        Column {
            Box {
                AsyncImage(model = offer.image, contentDescription = null, modifier = Modifier.fillMaxWidth().height(110.dp), contentScale = ContentScale.Crop)
                Surface(color = OfferRed, shape = RoundedCornerShape(bottomStart = 10.dp), modifier = Modifier.align(Alignment.TopEnd)) {
                    Text("-${offer.discountPercent}%", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                }
            }
            Column(Modifier.padding(10.dp)) {
                Text(offerTitleForLang(offer, lang), fontWeight = FontWeight.Bold, fontSize = 12.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${offer.newPrice.toInt()} DZD", fontWeight = FontWeight.Bold, color = OfferRed, fontSize = 13.sp)
                    Spacer(Modifier.width(6.dp))
                    Text(text = "${offer.oldPrice.toInt()} DZD", fontSize = 11.sp, color = Color.Gray, textDecoration = TextDecoration.LineThrough)
                }
                Spacer(Modifier.height(2.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Store, null, modifier = Modifier.size(12.dp))
                    Spacer(Modifier.width(3.dp))
                    Text(offer.shopName, fontSize = 10.sp)
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Filled.Timer, null, modifier = Modifier.size(12.dp), tint = OfferRed)
                    Text(tr("expiry", lang) + " ${offer.expiryDate}", fontSize = 9.sp, color = OfferRed)
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, onViewAll: (() -> Unit)? = null, lang: AppLanguage) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Text(title, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
        if (onViewAll != null) TextButton(onClick = onViewAll) { Text(tr("view_all", lang), fontSize = 12.sp) }
    }
}

@Composable
fun GuestBanner(lang: AppLanguage, onLogin: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer), shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Filled.Info, null, tint = MaterialTheme.colorScheme.onTertiaryContainer)
            Spacer(Modifier.width(8.dp))
            Text(tr("guest_mode", lang), modifier = Modifier.weight(1f), fontSize = 12.sp, color = MaterialTheme.colorScheme.onTertiaryContainer)
            TextButton(onClick = onLogin) { Text(tr("login_register", lang), fontSize = 11.sp) }
        }
    }
}

@Composable
fun StatChip(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Surface(shape = RoundedCornerShape(12.dp), color = MaterialTheme.colorScheme.surfaceVariant) {
        Row(Modifier.padding(horizontal = 10.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(4.dp))
            Column {
                Text(value, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Text(label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
