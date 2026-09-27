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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopOwnerDashboardScreen(lang: AppLanguage, onBack: ()->Unit, onAddAd: ()->Unit, onVerification: ()->Unit) {
    val shop = FakeData.shops[0]
    var tab by remember { mutableStateOf(0) }
    var ads by remember { mutableStateOf(FakeData.ads.filter { it.shopId==shop.id } + FakeData.ads.take(3)) }
    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title={
                Column{
                    Text(tr("dashboard", lang), fontSize=16.sp, fontWeight=FontWeight.Bold)
                    Text("${tr("max_ads", lang)}: ${ads.size}/50", fontSize=11.sp)
                }
            },
            navigationIcon={ IconButton(onClick=onBack){ Icon(Icons.Filled.ArrowBack, null)} },
            actions={ IconButton(onClick=onAddAd){ Icon(Icons.Filled.Add, null)} }
        )
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            DashboardStat("${ads.size}", tr("ads", lang), Icons.Filled.Campaign, PrimaryDZ, Modifier.weight(1f))
            DashboardStat("${shop.rating}", tr("ratings", lang), Icons.Filled.Star, RatingGold, Modifier.weight(1f))
            DashboardStat("${FakeData.conversations.size}", tr("chats", lang), Icons.Filled.Chat, VerifiedBlue, Modifier.weight(1f))
            DashboardStat("${FakeData.offers.size}", tr("offers", lang), Icons.Filled.LocalOffer, OfferRed, Modifier.weight(1f))
        }
        TabRow(selectedTabIndex=tab) {
            Tab(selected=tab==0, onClick={tab=0}, text={Text(tr("ads", lang), fontSize=11.sp)})
            Tab(selected=tab==1, onClick={tab=1}, text={Text(tr("products", lang), fontSize=11.sp)})
            Tab(selected=tab==2, onClick={tab=2}, text={Text(tr("verification", lang), fontSize=11.sp)})
            Tab(selected=tab==3, onClick={tab=3}, text={Text("الزبائن", fontSize=11.sp)})
        }
        when(tab) {
            0 -> {
                Column(Modifier.fillMaxSize()) {
                    LinearProgressIndicator(progress={ ads.size/50f }, modifier=Modifier.fillMaxWidth())
                    LazyColumn(modifier=Modifier.weight(1f), contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        item {
                            Button(onClick=onAddAd, modifier=Modifier.fillMaxWidth()) { Icon(Icons.Filled.Add, null); Spacer(Modifier.width(6.dp)); Text(tr("add_ad", lang)) }
                        }
                        items(ads) { ad ->
                            Card(shape=RoundedCornerShape(12.dp)) {
                                Row(Modifier.padding(10.dp), verticalAlignment=Alignment.CenterVertically) {
                                    AsyncImage(model=ad.image, contentDescription=null, modifier=Modifier.size(56.dp).clip(RoundedCornerShape(8.dp)))
                                    Spacer(Modifier.width(10.dp))
                                    Column(Modifier.weight(1f)) {
                                        Text(adTitleForLang(ad, lang), fontWeight=FontWeight.Bold, fontSize=12.sp, maxLines=1)
                                        Text("${ad.price.toInt()} DZD • ${ad.views} views", fontSize=10.sp)
                                    }
                                    IconButton(onClick={}){ Icon(Icons.Filled.Edit, null, modifier=Modifier.size(18.dp)) }
                                    IconButton(onClick={ ads=ads.filter { it.id!=ad.id } }){ Icon(Icons.Filled.Delete, null, tint=ErrorRed, modifier=Modifier.size(18.dp)) }
                                }
                            }
                        }
                    }
                }
            }
            1 -> {
                LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(shop.products) { p ->
                        Card(shape=RoundedCornerShape(10.dp)) {
                            Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically){
                                Text(p.name, modifier=Modifier.weight(1f), fontSize=12.sp)
                                Text("${p.price.toInt()} DZD", fontWeight=FontWeight.Bold, fontSize=12.sp, color=PrimaryDZ)
                            }
                        }
                    }
                    item { OutlinedButton(onClick={}, modifier=Modifier.fillMaxWidth()){ Text("إضافة منتج") } }
                }
            }
            2 -> VerificationScreenContent(lang, shop)
            3 -> CustomerRatingsList(lang)
        }
    }
}

@Composable
fun DashboardStat(value: String, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(shape=RoundedCornerShape(12.dp), modifier=modifier, colors=CardDefaults.cardColors(containerColor=color.copy(0.12f))) {
        Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint=color, modifier=Modifier.size(20.dp))
            Text(value, fontWeight=FontWeight.Bold, fontSize=14.sp, color=color)
            Text(label, fontSize=10.sp)
        }
    }
}

@Composable
fun VerificationScreenContent(lang: AppLanguage, shop: ShopModel) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text(tr("verification", lang), fontWeight=FontWeight.Bold, fontSize=18.sp)
            Spacer(Modifier.height(8.dp))
            val status = shop.verificationStatus
            Card(shape=RoundedCornerShape(12.dp), colors=CardDefaults.cardColors(containerColor=when(status){VerificationStatus.VERIFIED->SuccessGreen.copy(0.12f); VerificationStatus.UNDER_REVIEW->WarningOrange.copy(0.12f); VerificationStatus.REJECTED->ErrorRed.copy(0.12f); else->MaterialTheme.colorScheme.surfaceVariant})) {
                Row(Modifier.padding(12.dp), verticalAlignment=Alignment.CenterVertically) {
                    Icon(when(status){VerificationStatus.VERIFIED->Icons.Filled.Verified; VerificationStatus.UNDER_REVIEW->Icons.Filled.HourglassTop; VerificationStatus.REJECTED->Icons.Filled.Block; else->Icons.Filled.Info}, null, modifier=Modifier.size(28.dp))
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text(when(status){VerificationStatus.VERIFIED->tr("verified_badge", lang); VerificationStatus.UNDER_REVIEW->tr("under_review", lang); VerificationStatus.REJECTED->tr("rejected", lang); else->tr("not_verified", lang)}, fontWeight=FontWeight.Bold, fontSize=13.sp)
                        Text("سيتم مراجعة طلبك خلال 48 ساعة", fontSize=11.sp)
                    }
                }
            }
        }
        item {
            Text("المستندات المطلوبة:", fontWeight=FontWeight.Bold, fontSize=13.sp)
            OutlinedTextField(value="RC-2024-123456", onValueChange={}, label={Text("رقم السجل التجاري")}, modifier=Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick={}, modifier=Modifier.fillMaxWidth()) { Icon(Icons.Filled.UploadFile, null); Spacer(Modifier.width(6.dp)); Text("رفع السجل التجاري") }
            OutlinedButton(onClick={}, modifier=Modifier.fillMaxWidth()) { Icon(Icons.Filled.UploadFile, null); Spacer(Modifier.width(6.dp)); Text("رفع وثيقة إثبات النشاط") }
            OutlinedButton(onClick={}, modifier=Modifier.fillMaxWidth()) { Icon(Icons.Filled.UploadFile, null); Spacer(Modifier.width(6.dp)); Text("رفع بطاقة الهوية (عند الحاجة)") }
            Spacer(Modifier.height(12.dp))
            Button(onClick={}, modifier=Modifier.fillMaxWidth()) { Text("إرسال طلب التوثيق") }
        }
    }
}

@Composable
fun CustomerRatingsList(lang: AppLanguage) {
    LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text("تقييم الزبائن", fontWeight=FontWeight.Bold, fontSize=16.sp) }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                RatingEmojiCard("😊 ${tr("good", lang)}", 45, Color(0xFF4CAF50), Modifier.weight(1f))
                RatingEmojiCard("😐 ${tr("unsatisfied", lang)}", 8, Color(0xFFFFC107), Modifier.weight(1f))
                RatingEmojiCard("😠 ${tr("angry", lang)}", 2, Color(0xFFF44336), Modifier.weight(1f))
            }
        }
        items(FakeData.customerRatings) { cr ->
            Card(shape=RoundedCornerShape(10.dp)) {
                Row(Modifier.padding(10.dp), verticalAlignment=Alignment.CenterVertically) {
                    Text(when(cr.type){CustomerRatingType.GOOD->"😊"; CustomerRatingType.UNSATISFIED->"😐"; CustomerRatingType.ANGRY->"😠"}, fontSize=20.sp)
                    Spacer(Modifier.width(8.dp))
                    Column(Modifier.weight(1f)) {
                        Text(cr.customerId, fontWeight=FontWeight.Bold, fontSize=12.sp)
                        Text(when(cr.type){CustomerRatingType.GOOD->tr("good", lang); CustomerRatingType.UNSATISFIED->tr("unsatisfied", lang); CustomerRatingType.ANGRY->tr("angry", lang)}, fontSize=11.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun RatingEmojiCard(label: String, count: Int, color: Color, modifier: Modifier = Modifier) {
    Card(colors=CardDefaults.cardColors(containerColor=color.copy(0.12f)), shape=RoundedCornerShape(12.dp), modifier=modifier) {
        Column(Modifier.padding(12.dp), horizontalAlignment=Alignment.CenterHorizontally) {
            Text("$count", fontWeight=FontWeight.Bold, fontSize=20.sp, color=color)
            Text(label, fontSize=10.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(lang: AppLanguage, onBack: ()->Unit) {
    var adminTab by remember { mutableStateOf(0) }
    Column(Modifier.fillMaxSize()) {
        TopAppBar(title={Text(tr("admin", lang), fontWeight=FontWeight.Bold)}, navigationIcon={ IconButton(onClick=onBack){ Icon(Icons.Filled.ArrowBack, null)} })
        ScrollableTabRow(selectedTabIndex=adminTab) {
            Tab(selected=adminTab==0, onClick={adminTab=0}, text={Text(tr("users", lang))})
            Tab(selected=adminTab==1, onClick={adminTab=1}, text={Text(tr("shops", lang))})
            Tab(selected=adminTab==2, onClick={adminTab=2}, text={Text(tr("ads", lang))})
            Tab(selected=adminTab==3, onClick={adminTab=3}, text={Text(tr("requests", lang))})
            Tab(selected=adminTab==4, onClick={adminTab=4}, text={Text(tr("complaints", lang))})
            Tab(selected=adminTab==5, onClick={adminTab=5}, text={Text(tr("ratings", lang))})
        }
        when(adminTab) {
            0 -> AdminUsersList(lang)
            1 -> AdminShopsList(lang)
            2 -> AdminAdsList(lang)
            3 -> AdminVerificationList(lang)
            4 -> AdminComplaintsList(lang)
            5 -> AdminRatingsList(lang)
        }
    }
}

@Composable
fun AdminUsersList(lang: AppLanguage) {
    LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(FakeData.users) { u ->
            Card(shape=RoundedCornerShape(10.dp)) {
                Row(Modifier.padding(12.dp), verticalAlignment=Alignment.CenterVertically){
                    AsyncImage(model=u.avatar, contentDescription=null, modifier=Modifier.size(40.dp).clip(CircleShape))
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)){
                        Text(u.name, fontWeight=FontWeight.Bold, fontSize=12.sp)
                        Text(u.email, fontSize=10.sp)
                    }
                    Text(u.type.name, fontSize=10.sp, modifier=Modifier.padding(4.dp))
                }
            }
        }
    }
}
@Composable
fun AdminShopsList(lang: AppLanguage) {
    LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(FakeData.shops) { s ->
            Card(shape=RoundedCornerShape(10.dp)) {
                Row(Modifier.padding(12.dp), verticalAlignment=Alignment.CenterVertically){
                    Text(s.category.icon, fontSize=20.sp)
                    Spacer(Modifier.width(8.dp))
                    Column(Modifier.weight(1f)){
                        Text(shopNameForLang(s, lang), fontWeight=FontWeight.Bold, fontSize=12.sp)
                        Text(s.location.wilaya, fontSize=10.sp)
                    }
                    if(s.isVerified) Icon(Icons.Filled.Verified, null, tint=VerifiedBlue, modifier=Modifier.size(18.dp))
                }
            }
        }
    }
}
@Composable
fun AdminAdsList(lang: AppLanguage) {
    LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(FakeData.ads) { ad ->
            Card(shape=RoundedCornerShape(10.dp)) {
                Row(Modifier.padding(10.dp), verticalAlignment=Alignment.CenterVertically){
                    AsyncImage(model=ad.image, contentDescription=null, modifier=Modifier.size(48.dp).clip(RoundedCornerShape(8.dp)))
                    Spacer(Modifier.width(8.dp))
                    Column(Modifier.weight(1f)){
                        Text(adTitleForLang(ad, lang), fontSize=12.sp, fontWeight=FontWeight.Bold)
                        Text("${ad.price.toInt()} DZD • ${ad.shopName}", fontSize=10.sp)
                    }
                    IconButton(onClick={}){ Icon(Icons.Filled.Delete, null, tint=ErrorRed, modifier=Modifier.size(18.dp)) }
                }
            }
        }
    }
}
@Composable
fun AdminVerificationList(lang: AppLanguage) {
    LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(FakeData.verificationRequests) { req ->
            Card(shape=RoundedCornerShape(12.dp)) {
                Column(Modifier.padding(12.dp), verticalArrangement=Arrangement.spacedBy(6.dp)) {
                    Row(verticalAlignment=Alignment.CenterVertically){
                        Text(req.shopName, fontWeight=FontWeight.Bold, fontSize=13.sp, modifier=Modifier.weight(1f))
                        Surface(color=when(req.status){VerificationStatus.VERIFIED->SuccessGreen.copy(0.12f); VerificationStatus.UNDER_REVIEW->WarningOrange.copy(0.12f); else->ErrorRed.copy(0.12f)}, shape=RoundedCornerShape(6.dp)){ Text(req.status.name, modifier=Modifier.padding(horizontal=6.dp, vertical=2.dp), fontSize=10.sp) }
                    }
                    Text("المالك: ${req.ownerName} • ${req.commercialRegister}", fontSize=11.sp)
                    Text("المستندات: ${req.documents.joinToString(", ")}", fontSize=10.sp, color=MaterialTheme.colorScheme.onSurfaceVariant)
                    Row(horizontalArrangement=Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()){
                        Button(onClick={}, modifier=Modifier.weight(1f)){ Text("قبول", fontSize=11.sp) }
                        OutlinedButton(onClick={}, modifier=Modifier.weight(1f)){ Text("رفض", fontSize=11.sp) }
                    }
                }
            }
        }
    }
}
@Composable
fun AdminComplaintsList(lang: AppLanguage) {
    LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(FakeData.complaints) { comp ->
            Card(shape=RoundedCornerShape(10.dp)) {
                Column(Modifier.padding(12.dp)) {
                    Row{
                        Text(comp.fromUserName, fontWeight=FontWeight.Bold, fontSize=12.sp, modifier=Modifier.weight(1f))
                        Text(comp.status, fontSize=10.sp, color=WarningOrange)
                    }
                    Text("ضد: ${comp.targetName} (${comp.targetType})", fontSize=11.sp)
                    Text("السبب: ${comp.reason}", fontSize=11.sp, fontWeight=FontWeight.Medium)
                    Text(comp.description, fontSize=10.sp, color=MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}
@Composable
fun AdminRatingsList(lang: AppLanguage) {
    LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(FakeData.ratings) { r ->
            Card(shape=RoundedCornerShape(10.dp)) {
                Column(Modifier.padding(10.dp)){
                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text(r.customerName, fontWeight=FontWeight.Bold, fontSize=12.sp, modifier=Modifier.weight(1f))
                        Row{ repeat(r.stars){ Icon(Icons.Filled.Star, null, modifier=Modifier.size(12.dp), tint=RatingGold) } }
                    }
                    Text(r.comment, fontSize=11.sp)
                    Text("المحل: ${r.shopId} • ${r.date}", fontSize=10.sp, color=MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun ComplaintScreen(lang: AppLanguage, targetName: String, onBack: ()->Unit) {
    var reason by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(verticalAlignment=Alignment.CenterVertically){ IconButton(onClick=onBack){ Icon(Icons.Filled.ArrowBack, null) }; Text("${tr("complaint", lang)} - $targetName", fontWeight=FontWeight.Bold) }
        Text("سبب الشكوى:", fontWeight=FontWeight.Bold, fontSize=13.sp)
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            val reasons = listOf("عدم الجدية", "رفض استقبال الطرد", "عدم التواصل لاستلام الطرد", "تخريب الطرد", "سبب آخر")
            reasons.forEach { r -> FilterChip(selected=reason==r, onClick={ reason=r }, label={ Text(r, fontSize=11.sp) }) }
        }
        OutlinedTextField(value=desc, onValueChange={ desc=it }, label={ Text("وصف الشكوى") }, modifier=Modifier.fillMaxWidth().height(120.dp))
        Button(onClick=onBack, modifier=Modifier.fillMaxWidth()){ Text(tr("send", lang)) }
    }
}
