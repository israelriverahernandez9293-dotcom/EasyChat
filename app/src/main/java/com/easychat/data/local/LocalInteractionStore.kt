package com.easychat.data.local

import android.content.Context

class LocalInteractionStore(context: Context) {
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun getChatCount(): Int = preferences.getInt(KEY_CHAT_COUNT, 0)

    fun getVideoCallCount(): Int = preferences.getInt(KEY_VIDEO_COUNT, 0)

    fun getLastAction(): String = preferences.getString(KEY_LAST_ACTION, "Aún no hay interacciones")
        ?: "Aún no hay interacciones"

    fun registerChatAction() {
        val current = getChatCount()
        preferences.edit()
            .putInt(KEY_CHAT_COUNT, current + 1)
            .putString(KEY_LAST_ACTION, "Última acción: iniciar chat")
            .apply()
    }

    fun registerVideoCallAction() {
        val current = getVideoCallCount()
        preferences.edit()
            .putInt(KEY_VIDEO_COUNT, current + 1)
            .putString(KEY_LAST_ACTION, "Última acción: videollamada")
            .apply()
    }

    fun clear() {
        preferences.edit().clear().apply()
    }

    private companion object {
        private const val PREFS_NAME = "easychat_local_store"
        private const val KEY_CHAT_COUNT = "chat_count"
        private const val KEY_VIDEO_COUNT = "video_count"
        private const val KEY_LAST_ACTION = "last_action"
    }
}
