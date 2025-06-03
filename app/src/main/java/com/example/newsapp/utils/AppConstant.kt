package com.example.newsapp.utils

import com.example.newsapp.data.model.Code


object AppConstant {

    const val API_KEY = "0d5e2f9b968043fab5f8cf2f232ed03b"
    const val USER_AGENT = "Robin hood"
    const val COUNTRY = "us"
    const val PAGE_SIZE = 10
    const val INITIAL_PAGE = 1

    val COUNTRIES = listOf(
        Code("ae", "United Arab Emirates"),
        Code("ar", "Argentina"),
        Code("at", "Austria"),
        Code("au", "Australia"),
        Code("be", "Belgium"),
        Code("bg", "Bulgaria"),
        Code("br", "Brazil"),
        Code("ca", "Canada"),
        Code("ch", "Switzerland"),
        Code("cn", "China"),
        Code("co", "Colombia"),
        Code("cu", "Cuba"),
        Code("cz", "Czech Republic"),
        Code("de", "Germany"),
        Code("eg", "Egypt"),
        Code("fr", "France"),
        Code("gb", "United Kingdom"),
        Code("gr", "Greece"),
        Code("hk", "Hong Kong"),
        Code("hu", "Hungary"),
        Code("id", "Indonesia"),
        Code("ie", "Ireland"),
        Code("il", "Israel"),
        Code("in", "India"),
        Code("it", "Italy"),
        Code("jp", "Japan"),
        Code("kr", "South Korea"),
        Code("lt", "Lithuania"),
        Code("lv", "Latvia"),
        Code("ma", "Morocco"),
        Code("mx", "Mexico"),
        Code("my", "Malaysia"),
        Code("ng", "Nigeria"),
        Code("nl", "Netherlands"),
        Code("no", "Norway"),
        Code("nz", "New Zealand"),
        Code("ph", "Philippines"),
        Code("pl", "Poland"),
        Code("pt", "Portugal"),
        Code("ro", "Romania"),
        Code("rs", "Serbia"),
        Code("ru", "Russia"),
        Code("sa", "Saudi Arabia"),
        Code("se", "Sweden"),
        Code("sg", "Singapore"),
        Code("si", "Slovenia"),
        Code("sk", "Slovakia"),
        Code("th", "Thailand"),
        Code("tr", "Turkey"),
        Code("tw", "Taiwan"),
        Code("ua", "Ukraine"),
        Code("us", "United States"),
        Code("ve", "Venezuela"),
        Code("za", "South Africa")
    )
    val LANGUAGES = listOf(
        Code("ar", "Arabic"),
        Code("de", "German"),
        Code("en", "English"),
        Code("es", "Spanish"),
        Code("fr", "French"),
        Code("he", "Hebrew"),
        Code("it", "Italian"),
        Code("nl", "Dutch"),
        Code("no", "Norwegian"),
        Code("pt", "Portuguese"),
        Code("ru", "Russian"),
        Code("sv", "Swedish"),
        Code("ud", "Urdu"),
        Code("zh", "Chinese")
    )

}