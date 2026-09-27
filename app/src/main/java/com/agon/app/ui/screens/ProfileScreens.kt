package com.agon.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.agon.app.data.*
import com.agon.app.ui.components.*
import com.agon.app.ui.theme.*
import com.agon.app.viewmodel.AppViewModel

@Composable
fun ProfileScreen(vm: AppViewModel, lang: AppLanguage, onLogin: ()->Unit, onNavigateDeals: ()->Unit, onNavigateChats: ()->Unit, onNavigateOwner: ()->Unit, onNavigateAdmin: ()->Unit, onNavigateVerification: ()->Unit, onLogout: ()->Unit) {
    if (!vm.isLoggedIn) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier=Modifier.padding(24.dp)) {
                Icon(Icons.Filled.Person, null, modifier=Modifier.size(72.dp), tint=MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(12.dp))
                Text(tr("guest_mode", lang), fontWeight=FontWeight.Bold)
                Spacer(Modifier.height(12.dp))
                Button(onClick = onLogin) { Text(tr("login_register", lang)) }
            }
        }
        return
    }
    val user = FakeData.users.firstOrNull() ?: FakeData.users[0]
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Card(shape=RoundedCornerShape(16.dp)) {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(model=user.avatar, contentDescription=null, modifier=Modifier.size(84.dp).clip(CircleShape))
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(user.name, fontWeight=FontWeight.Bold, fontSize=18.sp)
                        Text(user.email, fontSize=12.sp, color=MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(Modifier.height(8.dp))
                        Surface(color=PrimaryDZ.copy(0.12f), shape=RoundedCornerShape(8.dp)) { 
                            val label = when(vm.currentUserType){UserType.CUSTOMER->"زبون"; UserType.SHOP_OWNER->"صاحب محل"; UserType.ADMIN->"إدارة"; else->"زائر"}
                            Text(label, modifier=Modifier.padding(horizontal=10.dp, vertical=4.dp), fontSize=11.sp, color=PrimaryDZ, fontWeight=FontWeight.Bold) 
                        }
                    }
                }
            }
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier=Modifier.fillMaxWidth()) {
                StatChip(Icons.Filled.Handshake, tr("deals", lang), "${user.dealsCount}")
                StatChip(Icons.Filled.Star, tr("ratings", lang), "${user.ratingAsCustomer}")
                StatChip(Icons.Filled.Chat, tr("chats", lang), "${FakeData.conversations.size}")
            }
        }
        item { HorizontalDivider() }
        item { Text(tr("my_account", lang), fontWeight=FontWeight.Bold) }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ProfileMenuItem(Icons.Filled.Handshake, tr("deals", lang), "3 تعاملات") { onNavigateDeals() }
                ProfileMenuItem(Icons.Filled.Chat, tr("chats", lang), "${FakeData.conversations.size}") { onNavigateChats() }
                if (vm.currentUserType==UserType.SHOP_OWNER || vm.currentUserType==UserType.ADMIN) {
                    ProfileMenuItem(Icons.Filled.Dashboard, tr("dashboard", lang), tr("shops", lang)) { onNavigateOwner() }
                    ProfileMenuItem(Icons.Filled.Verified, tr("verification", lang), tr("not_verified", lang)) { onNavigateVerification() }
                }
                if (vm.currentUserType==UserType.ADMIN) {
                    ProfileMenuItem(Icons.Filled.AdminPanelSettings, tr("admin", lang), "لوحة الإدارة") { onNavigateAdmin() }
                }
                ProfileMenuItem(Icons.Filled.Report, tr("complaints", lang), "${FakeData.complaints.size}") {}
                ProfileMenuItem(Icons.Filled.Settings, "الإعدادات", "") {}
            }
        }
        item {
            Spacer(Modifier.height(12.dp))
            OutlinedButton(onClick = onLogout, modifier=Modifier.fillMaxWidth()) { Icon(Icons.Filled.Logout, null, modifier=Modifier.size(18.dp)); Spacer(Modifier.width(8.dp)); Text("تسجيل خروج") }
        }
    }
}

@Composable
fun ProfileMenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String, onClick: ()->Unit) {
    Card(onClick=onClick, shape=RoundedCornerShape(12.dp)) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(color=MaterialTheme.colorScheme.surfaceVariant, shape=RoundedCornerShape(10.dp), modifier=Modifier.size(40.dp)) { 
                Box(Modifier.fillMaxSize(), contentAlignment=Alignment.Center){ Icon(icon, null, modifier=Modifier.size(20.dp)) } 
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) { 
                Text(title, fontWeight=FontWeight.Medium, fontSize=13.sp)
                if (subtitle.isNotEmpty()) Text(subtitle, fontSize=11.sp, color=MaterialTheme.colorScheme.onSurfaceVariant) 
            }
            Icon(Icons.Filled.ChevronRight, null)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealsScreen(lang: AppLanguage, onBack: ()->Unit, onRate: (DealModel)->Unit) {
    val deals = FakeData.deals
    Column(Modifier.fillMaxSize()) {
        TopAppBar(title={Text(tr("deals", lang))}, navigationIcon={ IconButton(onClick=onBack){ Icon(Icons.Filled.ArrowBack, null)} })
        LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(deals) { deal ->
                Card(shape=RoundedCornerShape(14.dp)) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(verticalAlignment=Alignment.CenterVertically) {
                            Text(deal.shopName, fontWeight=FontWeight.Bold, fontSize=13.sp, modifier=Modifier.weight(1f))
                            val bg = when(deal.status){DealStatus.ACTIVE->PrimaryDZ.copy(0.12f); DealStatus.COMPLETED->SuccessGreen.copy(0.12f); DealStatus.PENDING_RATING->WarningOrange.copy(0.12f)}
                            Surface(color=bg, shape=RoundedCornerShape(6.dp)) { Text(deal.status.name, modifier=Modifier.padding(horizontal=8.dp, vertical=3.dp), fontSize=10.sp) }
                        }
                        Text("العميل: ${deal.customerName} • ${deal.createdAt}", fontSize=11.sp, color=MaterialTheme.colorScheme.onSurfaceVariant)
                        if (deal.status==DealStatus.PENDING_RATING && !deal.isRatedByCustomer) {
                            Button(onClick={ onRate(deal) }, modifier=Modifier.fillMaxWidth()) { 
                                Icon(Icons.Filled.Star, null, modifier=Modifier.size(16.dp))
                                Spacer(Modifier.width(6.dp))
                                Text(tr("rate_deal", lang)) 
                            }
                        }
                        if (deal.isRatedByCustomer) Text("✓ تم التقييم", fontSize=11.sp, color=SuccessGreen)
                    }
                }
            }
        }
    }
}

@Composable
fun RatingDialog(deal: DealModel, lang: AppLanguage, onDismiss: ()->Unit, onSubmit: (Int, String)->Unit) {
    var stars by remember { mutableStateOf(5) }
    var comment by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest=onDismiss, 
        title={Text(tr("rate_deal", lang))}, 
        text={
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("تقييم: ${deal.shopName}", fontWeight=FontWeight.Bold)
                Row(horizontalArrangement=Arrangement.spacedBy(4.dp)) { 
                    (1..5).forEach { i -> 
                        IconButton(onClick={ stars=i }){ 
                            Icon(Icons.Filled.Star, null, tint=if(i<=stars) RatingGold else Color.Gray) 
                        } 
                    } 
                }
                OutlinedTextField(value=comment, onValueChange={ comment=it }, label={ Text("تعليق (اختياري)") }, modifier=Modifier.fillMaxWidth())
            }
        }, 
        confirmButton={ Button(onClick={ onSubmit(stars, comment) }){ Text(tr("send", lang)) } }, 
        dismissButton={ TextButton(onClick=onDismiss){ Text(tr("cancel", lang)) } }
    )
}
