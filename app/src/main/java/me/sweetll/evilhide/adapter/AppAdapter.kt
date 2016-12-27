package me.sweetll.evilhide.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.sweetll.evilhide.R
import me.sweetll.evilhide.databinding.ItemAppBinding
import me.sweetll.evilhide.model.AppInfo
import me.sweetll.evilhide.viewmodel.AppViewModel

class AppAdapter(var data: MutableList<AppInfo>) : RecyclerView.Adapter<AppAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAppBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_app,
                parent,
                false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appInfo = data[position]
        holder.bindAppInfo(appInfo)
    }

    override fun getItemCount(): Int = data.size

    fun setNewData(newData: MutableList<AppInfo>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemAppBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindAppInfo(appInfo: AppInfo) {
            binding.viewModel = AppViewModel(itemView.context, appInfo)
            binding.starBtn.setOnFavoriteChangeListener{
                button, favorite ->
                binding.viewModel.onFavoriteChange(favorite)
            }
            binding.switchBtn.setOnCheckedChangeListener {
                button, check ->
                binding.viewModel.onCheckChange(check)
            }
        }
    }

}