package me.sweetll.evilhide.model


import android.content.pm.ApplicationInfo
import me.sweetll.evilhide.extension.*

class AppInfo(var applicationInfo: ApplicationInfo) {
    val packageName: String
    var favorite: Boolean
        get() = packageName.getFavorite()
        set(value) {
            packageName.saveFavorite(value)
        }
    var hidden: Boolean
        get() = packageName.getHidden()
        set(value) {
            packageName.saveHidden(value)
        }
    var password: String
        get() = packageName.getPassword()
        set(value) {
            packageName.savePassword(value)
        }

    init {
        packageName = applicationInfo.packageName
    }
}