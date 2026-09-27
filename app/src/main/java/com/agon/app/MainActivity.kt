package com.agon.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.agon.app.data.*
import com.agon.app.ui.screens.*
import com.agon.app.ui.theme.AgonAppTheme
import com.agon.app.viewmodel.AppViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            AgonAppTheme {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    val navController = rememberNavController()
    val vm: AppViewModel = viewModel()
    val lang = vm.currentLanguage
    val layoutDir = if (lang.isRtl()) LayoutDirection.Rtl else LayoutDirection.Ltr
    var showRatingDialog by remember { mutableStateOf<DealModel?>(null) }

    CompositionLocalProvider(LocalLayoutDirection provides layoutDir) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                val backStack by navController.currentBackStackEntryAsState()
                val route = backStack?.destination?.route ?: ""
                val mainRoutes = listOf("home", "explore", "offers", "chats", "profile")
                if (mainRoutes.any { route.startsWith(it) }) {
                    BottomNavBar(navController, lang)
                }
            },
            snackbarHost = { SnackbarHost(SnackbarHostState()) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") {
                    HomeScreen(
                        vm = vm,
                        onNavigateShop = { id -> vm.selectedShopId = id; navController.navigate("shop/$id") },
                        onNavigateAd = { id -> vm.selectedAdId = id; navController.navigate("ad/$id") },
                        onNavigateOffer = { navController.navigate("offers") },
                        onNavigateExplore = { navController.navigate("explore") },
                        onLogin = { navController.navigate("login") },
                        onProfile = { navController.navigate("profile") }
                    )
                }
                composable("explore") {
                    ExploreScreen(
                        vm = vm,
                        onShopClick = { id -> vm.selectedShopId = id; navController.navigate("shop/$id") },
                        onAdClick = { id -> vm.selectedAdId = id; navController.navigate("ad/$id") }
                    )
                }
                composable("offers") {
                    OffersScreen(
                        vm = vm,
                        onOfferClick = { offer -> vm.selectedOfferId = offer.id; navController.navigate("offerDetail/${offer.id}") },
                        onAdClick = { id -> navController.navigate("ad/$id") }
                    )
                }
                composable("chats") {
                    ChatListScreen(
                        lang = lang,
                        conversations = FakeData.conversations,
                        onConversationClick = { id -> vm.selectedConversationId = id; navController.navigate("chat/$id") },
                        isLoggedIn = vm.isLoggedIn,
                        onLogin = { navController.navigate("login") }
                    )
                }
                composable("profile") {
                    ProfileScreen(
                        vm = vm,
                        lang = lang,
                        onLogin = { navController.navigate("login") },
                        onNavigateDeals = { navController.navigate("deals") },
                        onNavigateChats = { navController.navigate("chats") },
                        onNavigateOwner = { navController.navigate("dashboard") },
                        onNavigateAdmin = { navController.navigate("admin") },
                        onNavigateVerification = { navController.navigate("verification") },
                        onLogout = { vm.logout(); navController.navigate("home") { popUpTo("home") { inclusive = true } } }
                    )
                }
                composable("shop/{shopId}") { backStackEntry ->
                    val shopId = backStackEntry.arguments?.getString("shopId") ?: vm.selectedShopId ?: "1"
                    val shop = FakeData.shops.find { it.id == shopId } ?: FakeData.shops[0]
                    ShopDetailScreen(
                        shop = shop,
                        lang = lang,
                        isLoggedIn = vm.isLoggedIn,
                        onBack = { navController.popBackStack() },
                        onContact = {
                            if (!vm.isLoggedIn) navController.navigate("login")
                            else {
                                val conv = FakeData.conversations.find { it.shopId == shop.id } ?: FakeData.conversations[0]
                                navController.navigate("chat/${conv.id}")
                            }
                        },
                        onAdClick = { adId -> navController.navigate("ad/$adId") },
                        onComplaint = { vm.complaintTargetName = shop.nameAr; navController.navigate("complaint_tab") },
                        onMap = {}
                    )
                }
                composable("ad/{adId}") { entry ->
                    val adId = entry.arguments?.getString("adId") ?: vm.selectedAdId ?: "ad1"
                    val ad = FakeData.ads.find { it.id == adId } ?: FakeData.ads[0]
                    AdDetailScreen(
                        ad = ad,
                        lang = lang,
                        isLoggedIn = vm.isLoggedIn,
                        onBack = { navController.popBackStack() },
                        onShopClick = { shopId -> navController.navigate("shop/$shopId") },
                        onContact = {
                            if (!vm.isLoggedIn) navController.navigate("login")
                            else navController.navigate("chat/c1")
                        },
                        onComplaint = { vm.complaintTargetName = ad.shopName; navController.navigate("complaint_tab") }
                    )
                }
                composable("offerDetail/{offerId}") { entry ->
                    val oid = entry.arguments?.getString("offerId") ?: vm.selectedOfferId ?: "off1"
                    val offer = FakeData.offers.find { it.id == oid } ?: FakeData.offers[0]
                    OfferDetailScreen(
                        offer = offer,
                        lang = lang,
                        onBack = { navController.popBackStack() },
                        onContact = {
                            if (!vm.isLoggedIn) navController.navigate("login")
                            else navController.navigate("chat/c1")
                        }
                    )
                }
                composable("chat/{convId}") { entry ->
                    val cid = entry.arguments?.getString("convId") ?: vm.selectedConversationId ?: "c1"
                    val conv = FakeData.conversations.find { it.id == cid } ?: FakeData.conversations[0]
                    ChatDetailScreen(
                        conversation = conv,
                        lang = lang,
                        onBack = { navController.popBackStack() }
                    )
                }
                composable("login") {
                    LoginScreen(
                        lang = lang,
                        vm = vm,
                        onBack = { navController.popBackStack() },
                        onLogged = { type ->
                            navController.popBackStack()
                            if (type == UserType.SHOP_OWNER) navController.navigate("dashboard")
                        }
                    )
                }
                composable("deals") {
                    DealsScreen(
                        lang = lang,
                        onBack = { navController.popBackStack() },
                        onRate = { deal -> showRatingDialog = deal }
                    )
                }
                composable("dashboard") {
                    ShopOwnerDashboardScreen(
                        lang = lang,
                        onBack = { navController.popBackStack() },
                        onAddAd = { navController.navigate("addAd") },
                        onVerification = { navController.navigate("verification") }
                    )
                }
                composable("verification") {
                    val shop = FakeData.shops[0]
                    Column {
                        TopAppBar(title = { Text(tr("verification", lang), fontWeight = FontWeight.Bold) }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.Filled.ArrowBack, null) } })
                        VerificationScreenContent(lang = lang, shop = shop)
                    }
                }
                composable("admin") {
                    AdminDashboardScreen(lang = lang, onBack = { navController.popBackStack() })
                }
                composable("addAd") {
                    AddAdScreen(
                        lang = lang,
                        onBack = { navController.popBackStack() },
                        onSave = { navController.popBackStack() }
                    )
                }
                composable("complaint_tab") {
                    val name = vm.complaintTargetName
                    ComplaintScreen(lang = lang, targetName = name.ifEmpty { "محل" }, onBack = { navController.popBackStack() })
                }
            }

            showRatingDialog?.let { deal ->
                RatingDialog(
                    deal = deal,
                    lang = lang,
                    onDismiss = { showRatingDialog = null },
                    onSubmit = { _, _ -> showRatingDialog = null }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(navController: NavHostController, lang: AppLanguage) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val current = backStackEntry?.destination?.route ?: "home"
    NavigationBar {
        NavigationBarItem(
            selected = current.startsWith("home"),
            onClick = { navController.navigate("home") { popUpTo("home") { inclusive = false } } },
            icon = { Icon(Icons.Filled.Home, null) },
            label = { Text(tr("home", lang)) }
        )
        NavigationBarItem(
            selected = current.startsWith("explore"),
            onClick = { navController.navigate("explore") },
            icon = { Icon(Icons.Filled.Search, null) },
            label = { Text(tr("explore", lang)) }
        )
        NavigationBarItem(
            selected = current.startsWith("offers"),
            onClick = { navController.navigate("offers") },
            icon = { Icon(Icons.Filled.LocalOffer, null) },
            label = { Text(tr("offers", lang)) }
        )
        NavigationBarItem(
            selected = current.startsWith("chats"),
            onClick = { navController.navigate("chats") },
            icon = {
                BadgedBox(badge = { if (FakeData.conversations.any { it.unreadCount > 0 }) Badge { Text("2") } }) {
                    Icon(Icons.Filled.Chat, null)
                }
            },
            label = { Text(tr("chats", lang)) }
        )
        NavigationBarItem(
            selected = current.startsWith("profile"),
            onClick = { navController.navigate("profile") },
            icon = { Icon(Icons.Filled.Person, null) },
            label = { Text(tr("profile", lang)) }
        )
    }
}
