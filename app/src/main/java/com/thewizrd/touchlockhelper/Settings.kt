package com.thewizrd.touchlockhelper

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit

object Settings {
    const val KEY_AUTOLAUNCH = "key_autolaunchtouchlock"

    fun isAutoLaunchTouchLockEnabled(context: Context): Boolean {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean(KEY_AUTOLAUNCH, false)
    }

    fun setAutoLaunchTouchLockEnabled(context: Context, enabled: Boolean) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit {
            putBoolean(KEY_AUTOLAUNCH, enabled)
        }
    }
}