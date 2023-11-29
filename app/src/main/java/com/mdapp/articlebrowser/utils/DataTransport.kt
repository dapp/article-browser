package com.mdapp.articlebrowser.utils

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataTransport @Inject constructor() {
    private val map = mutableMapOf<String, Any>()
    fun put(key: String, value: Any) {
        map[key] = value
    }

    fun get(key: String): Any? {
        return map[key]
    }
}