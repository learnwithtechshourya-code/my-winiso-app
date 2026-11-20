package com.example.data.model

data class LanguageOption(
    val code: String,
    val name: String,
    val nativeName: String
)

val OfficialLanguages = listOf(
    LanguageOption("en-us", "English (United States)", "English (US)"),
    LanguageOption("en-gb", "English (International)", "English (UK)"),
    LanguageOption("es-es", "Spanish (Spain)", "Español"),
    LanguageOption("es-mx", "Spanish (Mexico)", "Español (México)"),
    LanguageOption("fr-fr", "French", "Français"),
    LanguageOption("de-de", "German", "Deutsch"),
    LanguageOption("ja-jp", "Japanese", "日本語"),
    LanguageOption("pt-br", "Portuguese (Brazil)", "Português (Brasil)"),
    LanguageOption("zh-cn", "Chinese (Simplified)", "简体中文"),
    LanguageOption("zh-tw", "Chinese (Traditional)", "繁體中文"),
    LanguageOption("it-it", "Italian", "Italiano"),
    LanguageOption("ko-kr", "Korean", "한국어"),
    LanguageOption("ar-sa", "Arabic", "العربية"),
    LanguageOption("hi-in", "Hindi", "हिन्दी"),
    LanguageOption("ru-ru", "Russian", "Русский"),
    LanguageOption("tr-tr", "Turkish", "Türkçe"),
    LanguageOption("nl-nl", "Dutch", "Nederlands"),
    LanguageOption("pl-pl", "Polish", "Polski")
)
