package com.agon.app.data

object FakeData {
    val shops = listOf(
        ShopModel(
            id = "1", name = "Ansar Fashion", nameAr = "أنصار للموضة", nameFr = "Ansar Mode",
            category = ShopCategory.CLOTHING,
            logo = "https://images.unsplash.com/photo-1556905055-8f358a7a47b2?w=200",
            coverImage = "https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=800",
            images = listOf(
                "https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=800",
                "https://images.unsplash.com/photo-1489987707025-afc232f7ea0f?w=800"
            ),
            description = "Best clothing store in Algiers", descriptionAr = "أفضل محل ملابس في الجزائر العاصمة، تشكيلة واسعة من الملابس العصرية",
            descriptionFr = "Meilleur magasin de vêtements à Alger",
            location = ShopLocation("الجزائر", "Alger", "شارع ديدوش مراد، الجزائر العاصمة", 36.7525, 3.0420),
            phone = "+213 550 12 34 56", hours = WorkingHours("08:00", "20:00", listOf("Vendredi")),
            isVerified = true, verificationStatus = VerificationStatus.VERIFIED,
            rating = 4.7f, ratingCount = 124,
            products = listOf(
                ProductModel("p1", "Chemise Homme", 4500.0, "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=300"),
                ProductModel("p2", "Robe Femme", 7800.0, "https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=300")
            ),
            services = listOf("توصيل مجاني", "تفصيل حسب الطلب", "إرجاع خلال 7 أيام"),
            ownerId = "owner1", createdAt = "2024-01-15"
        ),
        ShopModel(
            id = "2", name = "Bricostore DZ", nameAr = "بريكوستور", nameFr = "Bricostore DZ",
            category = ShopCategory.TOOLS,
            logo = "https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?w=200",
            coverImage = "https://images.unsplash.com/photo-1504148455328-c376907d081c?w=800",
            images = listOf("https://images.unsplash.com/photo-1504148455328-c376907d081c?w=800"),
            description = "All tools you need", descriptionAr = "جميع الأدوات والمعدات المنزلية والمهنية",
            descriptionFr = "Tous les outils dont vous avez besoin",
            location = ShopLocation("وهران", "Oran", "حي الياسمين، وهران", 35.6969, -0.6331),
            phone = "+213 661 22 33 44", hours = WorkingHours("07:30", "18:30"),
            isVerified = true, verificationStatus = VerificationStatus.VERIFIED,
            rating = 4.5f, ratingCount = 89,
            products = listOf(ProductModel("p3", "Perceuse BOSCH", 18500.0, "https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?w=300")),
            services = listOf("توصيل", "ضمان"), ownerId = "owner2", createdAt = "2024-02-10"
        ),
        ShopModel(
            id = "3", name = "Pharmacie El Amel", nameAr = "صيدلية الأمل", nameFr = "Pharmacie El Amel",
            category = ShopCategory.PHARMACY,
            logo = "https://images.unsplash.com/photo-1579154204601-01588f351e67?w=200",
            coverImage = "https://images.unsplash.com/photo-1587854692152-cbe660dbde88?w=800",
            images = listOf("https://images.unsplash.com/photo-1587854692152-cbe660dbde88?w=800"),
            description = "Pharmacie 24/7", descriptionAr = "صيدلية تعمل 24 ساعة، جميع الأدوية متوفرة",
            descriptionFr = "Pharmacie ouverte 24h/24",
            location = ShopLocation("قسنطينة", "Constantine", "وسط المدينة، قسنطينة", 36.3650, 6.6147),
            phone = "+213 770 11 22 33", hours = WorkingHours("00:00", "23:59"),
            isVerified = true, verificationStatus = VerificationStatus.VERIFIED,
            rating = 4.9f, ratingCount = 210,
            products = emptyList(), services = listOf("استشارة صيدلانية", "توصيل أدوية"),
            ownerId = "owner3", createdAt = "2024-01-05"
        ),
        ShopModel(
            id = "4", name = "Marché Central", nameAr = "السوق المركزي", nameFr = "Marché Central",
            category = ShopCategory.GROCERY,
            logo = "https://images.unsplash.com/photo-1542838132-92c53300491e?w=200",
            coverImage = "https://images.unsplash.com/photo-1542838132-92c53300491e?w=800",
            images = listOf("https://images.unsplash.com/photo-1542838132-92c53300491e?w=800"),
            description = "Fresh groceries", descriptionAr = "مواد غذائية طازجة يوميا من المزرعة",
            descriptionFr = "Produits frais quotidiennement",
            location = ShopLocation("عنابة", "Annaba", "سوق الحطاب، عنابة", 36.9000, 7.7667),
            phone = "+213 550 99 88 77", hours = WorkingHours("06:00", "22:00"),
            isVerified = false, verificationStatus = VerificationStatus.UNDER_REVIEW,
            rating = 4.2f, ratingCount = 56,
            products = emptyList(), services = listOf("توصيل للمنازل"), ownerId = "owner4", createdAt = "2024-03-01"
        ),
        ShopModel(
            id = "5", name = "Clinique Salam", nameAr = "عيادة السلام", nameFr = "Clinique Salam",
            category = ShopCategory.CLINIC,
            logo = "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=200",
            coverImage = "https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=800",
            images = listOf("https://images.unsplash.com/photo-1519494026892-80bbd2d6fd0d?w=800"),
            description = "Clinique générale", descriptionAr = "عيادة متعددة التخصصات، أطباء ذوو خبرة",
            descriptionFr = "Clinique pluridisciplinaire",
            location = ShopLocation("سطيف", "Sétif", "حي 1000 مسكن، سطيف", 36.1917, 5.4133),
            phone = "+213 36 12 34 56", hours = WorkingHours("08:00", "17:00", listOf("Vendredi")),
            isVerified = true, verificationStatus = VerificationStatus.VERIFIED,
            rating = 4.8f, ratingCount = 342,
            products = emptyList(), services = listOf("فحص عام", "أشعة", "تحاليل"), ownerId = "owner5", createdAt = "2023-12-20"
        ),
        ShopModel(
            id = "6", name = "Labo Analyse DZ", nameAr = "مخبر التحاليل الجزائر", nameFr = "Labo Analyse DZ",
            category = ShopCategory.LAB,
            logo = "https://images.unsplash.com/photo-1579154204601-01588f351e67?w=200",
            coverImage = "https://images.unsplash.com/photo-1582719471384-894fbb16e074?w=800",
            images = listOf("https://images.unsplash.com/photo-1582719471384-894fbb16e074?w=800"),
            description = "Laboratoire d'analyses", descriptionAr = "مخبر تحاليل طبية معتمد بأحدث الأجهزة",
            descriptionFr = "Laboratoire agréé équipements modernes",
            location = ShopLocation("البليدة", "Blida", "شارع الاستقلال، البليدة", 36.4203, 2.8277),
            phone = "+213 550 44 55 66", hours = WorkingHours("07:00", "19:00"),
            isVerified = false, verificationStatus = VerificationStatus.NOT_VERIFIED,
            rating = 4.0f, ratingCount = 23,
            products = emptyList(), services = listOf("تحاليل دم", "PCR", "تحاليل هرمونية"), ownerId = "owner6", createdAt = "2024-04-10"
        ),
        ShopModel(
            id = "7", name = "TechFix Alger", nameAr = "تك فكس الجزائر", nameFr = "TechFix Alger",
            category = ShopCategory.SERVICES,
            logo = "https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?w=200",
            coverImage = "https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?w=800",
            images = listOf("https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?w=800"),
            description = "Réparation téléphones", descriptionAr = "تصليح الهواتف والحواسيب باحترافية",
            descriptionFr = "Réparation pro téléphones et PC",
            location = ShopLocation("الجزائر", "Alger", "باب الزوار، الجزائر", 36.7525, 3.0420),
            phone = "+213 661 77 88 99", hours = WorkingHours("09:00", "19:00"),
            isVerified = true, verificationStatus = VerificationStatus.VERIFIED,
            rating = 4.6f, ratingCount = 178,
            products = emptyList(), services = listOf("تصليح شاشات", "سوفت وير", "اكسسوارات"), ownerId = "owner7", createdAt = "2024-02-28"
        ),
        ShopModel(
            id = "8", name = "Déco Home", nameAr = "ديكو هوم", nameFr = "Déco Home",
            category = ShopCategory.OTHER,
            logo = "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=200",
            coverImage = "https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=800",
            images = listOf("https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=800"),
            description = "Décoration maison", descriptionAr = "ديكور وأثاث منزلي عصري",
            descriptionFr = "Décoration et mobilier moderne",
            location = ShopLocation("تلمسان", "Tlemcen", "وسط المدينة، تلمسان", 34.8833, -1.3167),
            phone = "+213 770 55 44 33", hours = WorkingHours("09:00", "18:00", listOf("Vendredi")),
            isVerified = false, verificationStatus = VerificationStatus.REJECTED,
            rating = 3.8f, ratingCount = 12,
            products = emptyList(), services = listOf("تصميم داخلي", "توصيل"), ownerId = "owner8", createdAt = "2024-05-01"
        )
    )

    val ads = listOf(
        AdModel("ad1", "iPhone 15 Pro 256GB", "آيفون 15 برو 256 جيجا", "iPhone 15 Pro 256Go", "Neuf sous emballage", 185000.0, "https://images.unsplash.com/photo-1592750475338-74b7b21085ab?w=600", "1", "Ansar Fashion", ShopCategory.CLINIC, ShopLocation("الجزائر", "Alger", "", 36.7525, 3.0420), true, "2024-06-10", 234),
        AdModel("ad2", "Costume Homme Élégant", "بدلة رجالية أنيقة", "Costume Homme Élégant", "Taille 48-56 disponible", 12500.0, "https://images.unsplash.com/photo-1594938298603-c8148c4dae35?w=600", "1", "Ansar Fashion", ShopCategory.CLOTHING, ShopLocation("الجزائر", "Alger", "", 36.7525, 3.0420), true, "2024-06-09", 189),
        AdModel("ad3", "Perceuse Makita Pro", "مثقاب ماكيتا احترافي", "Perceuse Makita Pro", "750W avec coffret", 22000.0, "https://images.unsplash.com/photo-1504148455328-c376907d081c?w=600", "2", "Bricostore DZ", ShopCategory.TOOLS, ShopLocation("وهران", "Oran", "", 35.6969, -0.6331), true, "2024-06-08", 98),
        AdModel("ad4", "Miel Naturel Montagne", "عسل طبيعي جبلي", "Miel Naturel Montagne", "1kg pur 100%", 4500.0, "https://images.unsplash.com/photo-1471943311424-646960669fbc?w=600", "4", "Marché Central", ShopCategory.GROCERY, ShopLocation("عنابة", "Annaba", "", 36.9, 7.7667), false, "2024-06-07", 156),
        AdModel("ad5", "Réparation iPhone écran", "تصليح شاشة آيفون", "Réparation écran iPhone", "Tous modèles", 8000.0, "https://images.unsplash.com/photo-1592899677977-9bb10ba128a5?w=600", "7", "TechFix Alger", ShopCategory.SERVICES, ShopLocation("الجزائر", "Alger", "", 36.7525, 3.0420), true, "2024-06-06", 76),
        AdModel("ad6", "Canapé 3 places velours", "أريكة 3 مقاعد مخمل", "Canapé 3 places velours", "Couleur beige", 85000.0, "https://images.unsplash.com/photo-1555041469-a586c61ea9bc?w=600", "8", "Déco Home", ShopCategory.OTHER, ShopLocation("تلمسان", "Tlemcen", "", 34.8833, -1.3167), false, "2024-06-05", 45)
    )

    val offers = listOf(
        OfferModel("off1", "Robe d'été -50%", "فستان صيفي - خصم 50%", "Robe d'été -50%", "https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=600", 12000.0, 6000.0, 50, "1", "Ansar Fashion", "2024-07-15", true),
        OfferModel("off2", "Pack Outils Complet", "طقم أدوات كامل", "Pack Outils Complet", "https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?w=600", 35000.0, 28000.0, 20, "2", "Bricostore DZ", "2024-06-30", true),
        OfferModel("off3", "Huile d'olive extra", "زيت زيتون بكر ممتاز", "Huile d'olive extra", "https://images.unsplash.com/photo-1474979266404-7eaacbcd87c5?w=600", 2000.0, 1500.0, 25, "4", "Marché Central", "2024-06-25", true),
        OfferModel("off4", "Changement batterie iPhone", "تغيير بطارية آيفون", "Batterie iPhone", "https://images.unsplash.com/photo-1605236453806-6ff36851218e?w=600", 12000.0, 7500.0, 38, "7", "TechFix Alger", "2024-07-01", true)
    )

    val users = listOf(
        UserModel("u1", "أحمد بن علي", "ahmed@mail.dz", "+213 550 00 11 22", "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200", UserType.CUSTOMER, "الجزائر", 4.5f, 12),
        UserModel("u2", "Karim DZ", "karim@mail.dz", "+213 660 33 44 55", "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200", UserType.SHOP_OWNER, "وهران", 4.8f, 45),
        UserModel("admin", "إدارة المنصة", "admin@dalil.dz", "+213 21 00 00 00", "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=200", UserType.ADMIN, "الجزائر")
    )

    val conversations = listOf(
        ConversationModel(
            "c1", "1", "أنصار للموضة", "https://images.unsplash.com/photo-1556905055-8f358a7a47b2?w=200",
            "u1", "أحمد بن علي", "مرحبا، هل المقاس 42 متوفر؟", "14:32", 2,
            listOf(
                ChatMessage("m1", "c1", "u1", "السلام عليكم، هل المقاس 42 متوفر في البدلة الزرقاء؟", "14:30", true, true),
                ChatMessage("m2", "c1", "owner1", "وعليكم السلام، نعم متوفر أخي أحمد", "14:31", true, false),
                ChatMessage("m3", "c1", "u1", "كم السعر النهائي؟", "14:32", false, true)
            )
        ),
        ConversationModel(
            "c2", "7", "تك فكس الجزائر", "https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?w=200",
            "u1", "أحمد بن علي", "سأمر غدا إن شاء الله", "09:15", 0,
            listOf(
                ChatMessage("m4", "c2", "u1", "عندي آيفون 12 شاشته مكسورة", "09:10", true, true),
                ChatMessage("m5", "c2", "owner7", "نستطيع إصلاحه في ساعة، التكلفة 8000 دج", "09:12", true, false),
                ChatMessage("m6", "c2", "u1", "سأمر غدا إن شاء الله، شكرا", "09:15", true, true)
            )
        )
    )

    val deals = listOf(
        DealModel("d1", "1", "أنصار للموضة", "u1", "أحمد بن علي", "ad2", DealStatus.PENDING_RATING, "2024-06-01", "2024-07-01", false, false),
        DealModel("d2", "7", "تك فكس الجزائر", "u1", "أحمد بن علي", "ad5", DealStatus.COMPLETED, "2024-05-20", "2024-06-20", true, true),
        DealModel("d3", "2", "بريكوستور", "u1", "أحمد بن علي", "ad3", DealStatus.ACTIVE, "2024-06-10", "2024-07-10", false, false)
    )

    val ratings = listOf(
        ShopRating("r1", "1", "d1", "u1", "أحمد بن علي", 5, "محل رائع وتعامل ممتاز، أنصح به بشدة", "2024-06-02"),
        ShopRating("r2", "1", "d2", "u2", "محمد", 4, "جودة جيدة لكن التوصيل تأخر قليلا", "2024-05-28"),
        ShopRating("r3", "7", "d2", "u1", "أحمد بن علي", 5, "خدمة سريعة واحترافية", "2024-05-22")
    )

    val customerRatings = listOf(
        CustomerRating("cr1", "u1", "1", "d2", CustomerRatingType.GOOD, "", "2024-05-22"),
        CustomerRating("cr2", "u1", "7", "d2", CustomerRatingType.GOOD, "", "2024-05-22")
    )

    val complaints = listOf(
        ComplaintModel("comp1", "u1", "أحمد بن علي", "4", "السوق المركزي", "shop", "تأخر التوصيل", "طلبت منتج منذ 3 أيام ولم يصل بعد", "قيد المراجعة", "2024-06-05"),
        ComplaintModel("comp2", "owner4", "بريكوستور", "u1", "أحمد بن علي", "user", "عدم الجدية", "الزبون طلب ولم يستلم", "تم الحل", "2024-06-01")
    )

    val verificationRequests = listOf(
        VerificationRequest("v1", "4", "السوق المركزي", "عمر خالد", "RC-2024-123456", VerificationStatus.UNDER_REVIEW, "2024-06-08", listOf("سجل تجاري", "بطاقة هوية")),
        VerificationRequest("v2", "6", "مخبر التحاليل", "سارة أحمد", "RC-2024-789012", VerificationStatus.NOT_VERIFIED, "2024-06-09", listOf("اعتماد وزارة الصحة")),
        VerificationRequest("v3", "8", "ديكو هوم", "ليلى", "RC-2024-345678", VerificationStatus.REJECTED, "2024-06-02", listOf("سجل تجاري منتهي"))
    )
}
