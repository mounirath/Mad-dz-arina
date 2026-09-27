package com.agon.app.data

import androidx.compose.ui.graphics.Color

enum class ShopCategory(val icon: String) {
    CLOTHING("👕"),
    TOOLS("🔧"),
    GROCERY("🛒"),
    PHARMACY("💊"),
    CLINIC("🏥"),
    LAB("🔬"),
    SERVICES("⚙️"),
    OTHER("📦")
}

enum class VerificationStatus {
    NOT_VERIFIED, UNDER_REVIEW, VERIFIED, REJECTED
}

enum class UserType {
    VISITOR, CUSTOMER, SHOP_OWNER, ADMIN
}

enum class DealStatus {
    ACTIVE, COMPLETED, PENDING_RATING
}

enum class CustomerRatingType {
    GOOD, UNSATISFIED, ANGRY
}

data class ShopLocation(
    val city: String,
    val wilaya: String,
    val address: String,
    val lat: Double = 36.7525,
    val lng: Double = 3.0420
)

data class WorkingHours(
    val open: String,
    val close: String,
    val closedDays: List<String> = emptyList()
)

data class ShopModel(
    val id: String,
    val name: String,
    val nameAr: String,
    val nameFr: String,
    val category: ShopCategory,
    val logo: String,
    val coverImage: String,
    val images: List<String>,
    val description: String,
    val descriptionAr: String,
    val descriptionFr: String,
    val location: ShopLocation,
    val phone: String,
    val hours: WorkingHours,
    val isVerified: Boolean,
    val verificationStatus: VerificationStatus,
    val rating: Float,
    val ratingCount: Int,
    val products: List<ProductModel>,
    val services: List<String>,
    val ownerId: String,
    val createdAt: String
)

data class ProductModel(
    val id: String,
    val name: String,
    val price: Double,
    val image: String
)

data class AdModel(
    val id: String,
    val title: String,
    val titleAr: String,
    val titleFr: String,
    val description: String,
    val price: Double,
    val image: String,
    val shopId: String,
    val shopName: String,
    val category: ShopCategory,
    val location: ShopLocation,
    val isShopVerified: Boolean,
    val createdAt: String,
    val views: Int
)

data class OfferModel(
    val id: String,
    val title: String,
    val titleAr: String,
    val titleFr: String,
    val image: String,
    val oldPrice: Double,
    val newPrice: Double,
    val discountPercent: Int,
    val shopId: String,
    val shopName: String,
    val expiryDate: String,
    val isActive: Boolean
)

data class UserModel(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val avatar: String,
    val type: UserType,
    val city: String,
    val ratingAsCustomer: Float = 0f,
    val dealsCount: Int = 0
)

data class ChatMessage(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val text: String,
    val time: String,
    val isRead: Boolean,
    val isMe: Boolean
)

data class ConversationModel(
    val id: String,
    val shopId: String,
    val shopName: String,
    val shopLogo: String,
    val customerId: String,
    val customerName: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int,
    val messages: List<ChatMessage>
)

data class DealModel(
    val id: String,
    val shopId: String,
    val shopName: String,
    val customerId: String,
    val customerName: String,
    val adId: String?,
    val status: DealStatus,
    val createdAt: String,
    val ratingDeadline: String,
    val isRatedByCustomer: Boolean = false,
    val isRatedByShop: Boolean = false
)

data class ShopRating(
    val id: String,
    val shopId: String,
    val dealId: String,
    val customerId: String,
    val customerName: String,
    val stars: Int,
    val comment: String,
    val date: String
)

data class CustomerRating(
    val id: String,
    val customerId: String,
    val shopId: String,
    val dealId: String,
    val type: CustomerRatingType,
    val reason: String,
    val date: String
)

data class ComplaintModel(
    val id: String,
    val fromUserId: String,
    val fromUserName: String,
    val targetId: String,
    val targetName: String,
    val targetType: String,
    val reason: String,
    val description: String,
    val status: String,
    val date: String
)

data class VerificationRequest(
    val id: String,
    val shopId: String,
    val shopName: String,
    val ownerName: String,
    val commercialRegister: String,
    val status: VerificationStatus,
    val submittedAt: String,
    val documents: List<String>
)

object CategoryInfo {
    fun getName(category: ShopCategory, lang: AppLanguage): String {
        return when (lang) {
            AppLanguage.AR -> when (category) {
                ShopCategory.CLOTHING -> "محلات الملابس"
                ShopCategory.TOOLS -> "محلات الأدوات"
                ShopCategory.GROCERY -> "المواد الغذائية"
                ShopCategory.PHARMACY -> "الصيدليات"
                ShopCategory.CLINIC -> "العيادات"
                ShopCategory.LAB -> "مراكز التحاليل"
                ShopCategory.SERVICES -> "الخدمات"
                ShopCategory.OTHER -> "تصنيفات أخرى"
            }
            AppLanguage.FR -> when (category) {
                ShopCategory.CLOTHING -> "Vêtements"
                ShopCategory.TOOLS -> "Outils"
                ShopCategory.GROCERY -> "Alimentation"
                ShopCategory.PHARMACY -> "Pharmacies"
                ShopCategory.CLINIC -> "Cliniques"
                ShopCategory.LAB -> "Laboratoires"
                ShopCategory.SERVICES -> "Services"
                ShopCategory.OTHER -> "Autres"
            }
            AppLanguage.EN -> when (category) {
                ShopCategory.CLOTHING -> "Clothing"
                ShopCategory.TOOLS -> "Tools"
                ShopCategory.GROCERY -> "Groceries"
                ShopCategory.PHARMACY -> "Pharmacies"
                ShopCategory.CLINIC -> "Clinics"
                ShopCategory.LAB -> "Labs"
                ShopCategory.SERVICES -> "Services"
                ShopCategory.OTHER -> "Others"
            }
        }
    }
    
    fun getColor(category: ShopCategory): Color {
        return when (category) {
            ShopCategory.CLOTHING -> Color(0xFF7C4DFF)
            ShopCategory.TOOLS -> Color(0xFFFF6D00)
            ShopCategory.GROCERY -> Color(0xFF00C853)
            ShopCategory.PHARMACY -> Color(0xFF00B0FF)
            ShopCategory.CLINIC -> Color(0xFFD50000)
            ShopCategory.LAB -> Color(0xFF0091EA)
            ShopCategory.SERVICES -> Color(0xFF6200EA)
            ShopCategory.OTHER -> Color(0xFF37474F)
        }
    }
}
