package me.sweetll.evilhide.viewmodel

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import com.github.ivbaranov.mfb.MaterialFavoriteButton
import me.sweetll.evilhide.model.AppInfo

class AppViewModel(val context: Context, val appInfo: AppInfo) {
    val appName: ObservableField<String> = ObservableField()
    val appIcon: ObservableField<Drawable> = ObservableField()
    val isStar: ObservableBoolean = ObservableBoolean()
    val isHidden: ObservableBoolean = ObservableBoolean()

    init {
        val pm = context.packageManager
        appName.set(pm.getApplicationLabel(appInfo.applicationInfo).toString())
        appIcon.set(pm.getApplicationIcon(appInfo.applicationInfo))
        isStar.set(appInfo.favorite)
        isHidden.set(appInfo.hidden)
    }

    fun onClickApp(view: View) {

    }

    fun onClickAdd(view: View) {

    }

    fun onFavoriteChange(favorite: Boolean) {

    }

    fun onCheckChange(check: Boolean) {

    }

    companion object {
        @JvmStatic
        @BindingAdapter("drawable")
        fun setImageSrc(imageView: ImageView, drawable: Drawable) {
            imageView.setImageDrawable(drawable)
        }

        @JvmStatic
        @BindingAdapter("favorite")
        fun setFavorite(materialFavoriteButton: MaterialFavoriteButton, favorite: Boolean) {
            materialFavoriteButton.setFavorite(favorite, true)
        }

        @JvmStatic
        @BindingAdapter("check")
        fun setCheck(switch: Switch, check: Boolean) {
            switch.isChecked = check
        }
    }
}
