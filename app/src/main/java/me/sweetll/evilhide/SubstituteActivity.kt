package me.sweetll.evilhide

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener

import butterknife.BindView
import butterknife.ButterKnife
import me.sweetll.evilhide.adapter.AppAdapter
import me.sweetll.evilhide.model.AppInfo


class SubstituteActivity : AppCompatActivity() {
    @BindView(R.id.toolbar)
    lateinit var mToolbar: Toolbar

    @BindView(R.id.spinner_nav)
    lateinit var mSpinner: Spinner

    @BindView(R.id.list_app)
    lateinit var mRecyclerView: RecyclerView

    val appListAdapter = AppAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        initPermissions()

        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        mSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                populateAppList(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = appListAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent()
                intent.setClass(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun initPermissions() {
        val snackBarPermissionListener = SnackbarOnDeniedPermissionListener.Builder
                .with(mToolbar, "需要电话权限以便从拨号盘启动")
                .withOpenSettingsButton("设置")
                .build()
        Dexter.checkPermission(snackBarPermissionListener, Manifest.permission.PROCESS_OUTGOING_CALLS)
    }

    fun populateAppList(flag: Int) {
        val pm = packageManager
        val apps = pm.getInstalledApplications(0)
        var sharedPreferences: SharedPreferences

        appListAdapter.clearData()

        for (app in apps) {
            if (app.flags and ApplicationInfo.FLAG_SYSTEM != 1 && app.packageName != packageName) {
                sharedPreferences = getSharedPreferences(app.packageName, 0)
                val hidden = !app.enabled
                val password = sharedPreferences.getString(Settings.SHARED_PASSWORD, "")
                val star = sharedPreferences.getBoolean(Settings.SHARED_STAR, false)
                when (flag) {
                    Settings.SPINNER_STAR_APP -> if (star) appListAdapter.addNewData(AppInfo(app, hidden, true, password!!))
                    Settings.SPINNER_HIDDEN_APP -> if (hidden) appListAdapter.addNewData(AppInfo(app, true, star, password!!))
                    Settings.SPINNER_ALL_APP -> appListAdapter.addNewData(AppInfo(app, hidden, star, password!!))
                }
            }
        }
        appListAdapter.notifyDataSetChanged()
    }
}
