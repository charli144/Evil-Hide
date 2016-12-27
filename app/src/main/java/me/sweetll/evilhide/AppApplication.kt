package me.sweetll.evilhide

import android.app.Application
import android.preference.PreferenceManager
import com.karumi.dexter.Dexter
import com.orhanobut.logger.Logger

class AppApplication : Application() {
    companion object {
        private lateinit var INSTANCE: AppApplication

        fun get(): AppApplication = INSTANCE
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Logger.init()
        Dexter.initialize(this)
        PreferenceManager.setDefaultValues(this, R.xml.settings, false)
    }
}