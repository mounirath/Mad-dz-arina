package com.agon.app.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.agon.app.data.*

class AppViewModel : ViewModel() {
    var currentLanguage by mutableStateOf(AppLanguage.AR)
        private set
    var currentUserType by mutableStateOf(UserType.VISITOR)
        private set
    var isLoggedIn by mutableStateOf(false)
        private set
    var selectedCategory by mutableStateOf<ShopCategory?>(null)
    var searchQuery by mutableStateOf("")
    var showVerifiedOnly by mutableStateOf(false)
    var favoritesShops by mutableStateOf(setOf<String>())
    var favoritesAds by mutableStateOf(setOf<String>())
    var selectedShopId by mutableStateOf<String?>(null)
    var selectedAdId by mutableStateOf<String?>(null)
    var selectedConversationId by mutableStateOf<String?>(null)
    var selectedOfferId by mutableStateOf<String?>(null)
    var complaintTargetName by mutableStateOf("")

    fun setLanguage(lang: AppLanguage) { currentLanguage = lang }
    fun setCategory(cat: ShopCategory?) { selectedCategory = cat }
    fun toggleVerifiedFilter() { showVerifiedOnly = !showVerifiedOnly }
    fun toggleFavoriteShop(id: String) { favoritesShops = if (favoritesShops.contains(id)) favoritesShops - id else favoritesShops + id }
    fun toggleFavoriteAd(id: String) { favoritesAds = if (favoritesAds.contains(id)) favoritesAds - id else favoritesAds + id }
    fun loginAs(type: UserType) { currentUserType = type; isLoggedIn = true }
    fun logout() { currentUserType = UserType.VISITOR; isLoggedIn = false }

    fun getFilteredShops(): List<ShopModel> {
        var list = FakeData.shops
        if (selectedCategory != null) list = list.filter { it.category == selectedCategory }
        if (showVerifiedOnly) list = list.filter { it.isVerified }
        if (searchQuery.isNotBlank()) {
            list = list.filter {
                it.name.contains(searchQuery, true) || it.nameAr.contains(searchQuery, true) ||
                it.location.city.contains(searchQuery, true) || it.phone.contains(searchQuery)
            }
        }
        return list
    }
    fun getFilteredAds(): List<AdModel> {
        var list = FakeData.ads
        if (selectedCategory != null) list = list.filter { it.category == selectedCategory }
        if (searchQuery.isNotBlank()) {
            list = list.filter {
                it.title.contains(searchQuery, true) || it.titleAr.contains(searchQuery, true) || it.shopName.contains(searchQuery, true)
            }
        }
        return list
    }
    fun getFilteredOffers(): List<OfferModel> = FakeData.offers
}
