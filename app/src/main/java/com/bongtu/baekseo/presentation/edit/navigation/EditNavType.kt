package com.bongtu.baekseo.presentation.edit.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.bongtu.baekseo.data.model.event.EditEvent
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

object EditNavType {

    private val EditEventType = object : NavType<EditEvent?>(isNullableAllowed = true) {
        override fun get(bundle: Bundle, key: String): EditEvent? {
            return Json.decodeFromString<EditEvent>(bundle.getString(key) ?: return null)
        }

        override fun put(bundle: Bundle, key: String, value: EditEvent?) {
            bundle.putString(key, value?.let { Json.encodeToString(it) })
        }

        override fun parseValue(value: String): EditEvent? {
            if (value == "null") return null
            return Json.decodeFromString<EditEvent>(Uri.decode(value))
        }

        override fun serializeAsValue(value: EditEvent?): String {
            if (value == null) return "null"
            return Uri.encode(Json.encodeToString(value))
        }
    }

    val TYPE_MAP = mapOf(
        typeOf<EditEvent?>() to EditEventType
    )
}