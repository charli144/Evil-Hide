package me.sweetll.evilhide

import android.app.Application
import android.preference.PreferenceManager

class AppApplication : Application() {
    companion object {
        private lateinit var INSTANCE: AppApplication

        fun get(): AppApplication = INSTANCE
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        PreferenceManager.setDefaultValues(this, R.xml.settings, false)
    }
}