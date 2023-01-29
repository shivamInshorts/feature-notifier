package com.nis.app.featurenotifier.utils

import android.content.Context
import com.google.gson.Gson
import java.lang.reflect.Type

inline fun <reified T : Enum<T>> enumValueOfOrNull(value: String?): T? {
    return try {
        java.lang.Enum.valueOf(T::class.java, value.orEmpty())
    } catch (e: IllegalArgumentException) {
        null
    }
}

fun <T> readJsonAsset(fileName: String, type: Type, application: Context): T? {
    val fileInString: String =
        application.assets.open(fileName).bufferedReader()
            .use { it.readText() }
    return Gson().fromJson(fileInString, type)
}

//Accessible only from Kotlin code, prefer this in Kotlin for easier syntax
inline fun <reified T> readJsonAsset(fileName: String, context: Context): T? {
    return readJsonAsset(fileName, T::class.java, context)
}