package me.sweetll.evilhide.model


import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import me.sweetll.evilhide.AppApplication
import me.sweetll.evilhide.extension.*
import me.sweetll.evilhide.service.HiddenService

class AppInfo(val packageName: String) {
    val applicationInfo: ApplicationInfo
        get() = AppApplication.get().packageManager.getApplicationInfo(packageName, 0)

    var favorite: Boolean
        get() = packageName.getFavorite()
        set(value) {
            packageName.saveFavorite(value)
        }
    var hidden: Boolean
        get() = !applicationInfo.enabled
        set(value) {
            val cmd = "pm ${if (value) "disable" else "enable"} $packageName"
            HiddenService.performAction(cmd)
        }
    var password: String
        get() = packageName.getPassword()
        set(value) {
            packageName.savePassword(value)
        }
}