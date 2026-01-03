package com.example.taskmanager_balanandreimariancirlanalexandrudamian

import android.content.Context

class AppPrefs(context: Context) {
    private val sp = context.getSharedPreferences("task_prefs", Context.MODE_PRIVATE)

    var username: String
        get() = sp.getString("username", "Student") ?: "Student"
        set(value) = sp.edit().putString("username", value).apply()

    var sortMode: String
        get() = sp.getString("sortMode", "newest") ?: "newest" // newest | title
        set(value) = sp.edit().putString("sortMode", value).apply()
}
