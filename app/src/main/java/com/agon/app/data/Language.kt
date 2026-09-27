package com.agon.app.data

enum class AppLanguage(val code: String, val displayName: String, val flag: String) {
    AR("ar", "العربية", "🇩🇿"),
    FR("fr", "Français", "🇫🇷"),
    EN("en", "English", "🇬🇧");
    
    fun isRtl(): Boolean = this == AR
}
