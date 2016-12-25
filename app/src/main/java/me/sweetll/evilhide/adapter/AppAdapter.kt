package me.sweetll.evilhide.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import me.sweetll.evilhide.R
import me.sweetll.evilhide.model.MyAppInfo

class AppAdapter(newData : MutableList<MyAppInfo>? = null) : BaseQuickAdapter<MyAppInfo, BaseViewHolder>(R.layout.list_app, newData) {

    override fun convert(helper: BaseViewHolder, myAppInfo: MyAppInfo) {

    }
}
