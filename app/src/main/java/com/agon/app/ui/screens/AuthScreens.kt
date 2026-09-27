package com.agon.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.agon.app.data.*
import com.agon.app.ui.theme.*
import com.agon.app.viewmodel.AppViewModel

@Composable
fun LoginScreen(lang: AppLanguage, vm: AppViewModel, onBack: ()->Unit, onLogged: (UserType)->Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(UserType.CUSTOMER) }
    Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, null) }
            Text(tr("login_register", lang), fontWeight=FontWeight.Bold, fontSize=18.sp)
        }
        Surface(color=PrimaryDZ, shape=RoundedCornerShape(20.dp), modifier=Modifier.size(80.dp).align(Alignment.CenterHorizontally)) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("د.م", color=Color.White, fontSize=28.sp, fontWeight=FontWeight.ExtraBold) }
        }
        Text(tr("app_name", lang), modifier=Modifier.align(Alignment.CenterHorizontally), fontWeight=FontWeight.Bold, fontSize=16.sp)
        OutlinedTextField(value=email, onValueChange={ email=it }, label={ Text("Email") }, leadingIcon={ Icon(Icons.Filled.Email, null) }, modifier=Modifier.fillMaxWidth(), shape=RoundedCornerShape(12.dp))
        OutlinedTextField(value=password, onValueChange={ password=it }, label={ Text("Password") }, leadingIcon={ Icon(Icons.Filled.Lock, null) }, modifier=Modifier.fillMaxWidth(), shape=RoundedCornerShape(12.dp))
        Text("نوع الحساب / Type compte", fontWeight=FontWeight.Bold, fontSize=13.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier=Modifier.fillMaxWidth()) {
            UserType.values().forEach { type ->
                if (type!=UserType.VISITOR) {
                    FilterChip(selected=selectedType==type, onClick={ selectedType=type }, label={ Text(when(type){UserType.CUSTOMER->"زبون"; UserType.SHOP_OWNER->"صاحب محل"; UserType.ADMIN->"إدارة"; else->""}, fontSize=11.sp) })
                }
            }
        }
        Button(onClick={ vm.loginAs(selectedType); onLogged(selectedType) }, modifier=Modifier.fillMaxWidth(), shape=RoundedCornerShape(12.dp)) { Text(tr("login_register", lang)); Spacer(Modifier.width(8.dp)); Icon(Icons.Filled.Login, null, modifier=Modifier.size(18.dp)) }
        OutlinedButton(onClick={ vm.logout(); onBack() }, modifier=Modifier.fillMaxWidth()) { Text(if (lang==AppLanguage.AR) "المتابعة كزائر" else "Continue as guest") }
        Divider()
        Text("بيانات تجريبية: أي بريد وكلمة مرور تعمل. اختر نوع الحساب للتجربة.", fontSize=11.sp, color=MaterialTheme.colorScheme.onSurfaceVariant, modifier=Modifier.align(Alignment.CenterHorizontally))
    }
}
