package com.agon.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.agon.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(lang: AppLanguage, conversations: List<ConversationModel>, onConversationClick: (String)->Unit, isLoggedIn: Boolean, onLogin: ()->Unit) {
    if (!isLoggedIn) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                Icon(Icons.Filled.Lock, null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(12.dp))
                Text(tr("login_required", lang), fontWeight=FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Button(onClick = onLogin) { Text(tr("login_register", lang)) }
            }
        }
        return
    }
    LazyColumn(contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item { Text(tr("chats", lang), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, modifier=Modifier.padding(4.dp)) }
        items(conversations) { conv ->
            Card(modifier = Modifier.fillMaxWidth().clickable { onConversationClick(conv.id) }, shape=RoundedCornerShape(14.dp)) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(model = conv.shopLogo, contentDescription = null, modifier = Modifier.size(48.dp).clip(CircleShape))
                    Spacer(Modifier.width(12.dp))
                    Column(Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) { 
                            Text(conv.shopName, fontWeight=FontWeight.Bold, fontSize=13.sp, modifier=Modifier.weight(1f))
                            if (conv.unreadCount>0) Badge { Text("${conv.unreadCount}") } 
                        }
                        Text(conv.lastMessage, fontSize=12.sp, color=MaterialTheme.colorScheme.onSurfaceVariant, maxLines=1)
                    }
                    Column(horizontalAlignment = Alignment.End) { 
                        Text(conv.lastMessageTime, fontSize=10.sp, color=MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(conversation: ConversationModel, lang: AppLanguage, onBack: ()->Unit) {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(conversation.messages) }
    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { 
                Row(verticalAlignment = Alignment.CenterVertically) { 
                    AsyncImage(model=conversation.shopLogo, contentDescription = null, modifier=Modifier.size(36.dp).clip(CircleShape))
                    Spacer(Modifier.width(8.dp))
                    Column { 
                        Text(conversation.shopName, fontSize=14.sp, fontWeight=FontWeight.Bold)
                        Text("Online", fontSize=10.sp, color=SuccessGreen) 
                    } 
                } 
            }, 
            navigationIcon = { IconButton(onClick=onBack){ Icon(Icons.Filled.ArrowBack, null)} }, 
            actions = { IconButton(onClick={}){ Icon(Icons.Filled.MoreVert, null)} }
        )
        LazyColumn(modifier=Modifier.weight(1f).fillMaxWidth().background(MaterialTheme.colorScheme.surfaceVariant.copy(0.3f)), contentPadding = PaddingValues(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(messages) { msg ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = if (msg.isMe) Arrangement.End else Arrangement.Start) {
                    if (!msg.isMe) { 
                        Box(Modifier.size(28.dp).clip(CircleShape).background(PrimaryDZ), contentAlignment = Alignment.Center) { 
                            val ini = conversation.shopName.firstOrNull()?.toString() ?: "S"
                            Text(ini, color=Color.White, fontSize=10.sp, fontWeight=FontWeight.Bold) 
                        }
                        Spacer(Modifier.width(6.dp)) 
                    }
                    Column {
                        Surface(color = if (msg.isMe) PrimaryDZ else MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = if (msg.isMe) 16.dp else 4.dp, bottomEnd = if (msg.isMe) 4.dp else 16.dp), shadowElevation = 1.dp) {
                            Text(msg.text, modifier=Modifier.padding(12.dp), fontSize=13.sp, color = if (msg.isMe) Color.White else MaterialTheme.colorScheme.onSurface)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top=2.dp, start=4.dp)) {
                            Text(msg.time, fontSize=10.sp, color=MaterialTheme.colorScheme.onSurfaceVariant)
                            if (msg.isMe) { 
                                Spacer(Modifier.width(4.dp))
                                Icon(if (msg.isRead) Icons.Filled.DoneAll else Icons.Filled.Done, null, modifier=Modifier.size(12.dp), tint = if (msg.isRead) VerifiedBlue else MaterialTheme.colorScheme.onSurfaceVariant) 
                            }
                        }
                    }
                }
            }
        }
        Row(Modifier.fillMaxWidth().padding(8.dp).navigationBarsPadding(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value=messageText, onValueChange={ messageText=it }, placeholder={ Text(tr("type_message", lang), fontSize=13.sp)}, modifier=Modifier.weight(1f), shape=RoundedCornerShape(24.dp))
            Spacer(Modifier.width(8.dp))
            FilledIconButton(onClick={
                if (messageText.isNotBlank()) {
                    messages = messages + ChatMessage("m${messages.size+10}", conversation.id, "u1", messageText, "الآن", false, true)
                    messageText=""
                }
            }) { Icon(Icons.Filled.Send, null) }
        }
    }
}
