package com.agon.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agon.app.data.*

@Composable
fun AddAdScreen(lang: AppLanguage, onBack: ()->Unit, onSave: ()->Unit) {
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var cat by remember { mutableStateOf(ShopCategory.CLOTHING) }
    LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Row { IconButton(onClick=onBack){ Icon(Icons.Filled.ArrowBack, null) }; Text(tr("add_ad", lang), fontWeight=FontWeight.Bold, fontSize=18.sp) } }
        item { OutlinedTextField(value=title, onValueChange={ title=it }, label={Text("عنوان الإعلان")}, modifier=Modifier.fillMaxWidth(), shape=RoundedCornerShape(12.dp)) }
        item { OutlinedTextField(value=price, onValueChange={ price=it }, label={Text("السعر DZD")}, leadingIcon={ Text("DZD") }, modifier=Modifier.fillMaxWidth(), shape=RoundedCornerShape(12.dp)) }
        item {
            Text("التصنيف", fontWeight=FontWeight.Bold, fontSize=13.sp)
            Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) { ShopCategory.values().take(4).forEach { c -> FilterChip(selected=cat==c, onClick={ cat=c }, label={ Text(CategoryInfo.getName(c, lang), fontSize=10.sp) }) } }
        }
        item { OutlinedTextField(value=desc, onValueChange={ desc=it }, label={Text("الوصف")}, modifier=Modifier.fillMaxWidth().height(100.dp), shape=RoundedCornerShape(12.dp)) }
        item { OutlinedButton(onClick={}, modifier=Modifier.fillMaxWidth()){ Icon(Icons.Filled.Image, null); Spacer(Modifier.width(6.dp)); Text("إضافة صور الإعلان") } }
        item { Button(onClick=onSave, modifier=Modifier.fillMaxWidth()){ Text(tr("save", lang)) } }
        item { Text("ملاحظة: الحد الأقصى 50 إعلان لكل محل", fontSize=11.sp, color=MaterialTheme.colorScheme.onSurfaceVariant) }
    }
}
