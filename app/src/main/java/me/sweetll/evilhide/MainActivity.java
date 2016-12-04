package me.sweetll.evilhide;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.karumi.dexter.listener.single.SnackbarOnDeniedPermissionListener;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.sweetll.evilhide.adapter.AppListAdapter;
import me.sweetll.evilhide.model.MyAppInfo;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar mToolbar;
    @Bind(R.id.spinner_nav) Spinner mSpinner;
    @Bind(R.id.list_app) RecyclerView mRecyclerView;

    AppListAdapter mAppListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Logger.init();
        Dexter.initialize(this);
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        initPermissions();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                populateAppList(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mAppListAdapter = new AppListAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAppListAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent();
                intent.setClass(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void initPermissions() {
        PermissionListener snackBarPermissionListener =
            SnackbarOnDeniedPermissionListener.Builder
                .with(mToolbar, "需要电话权限以便从拨号盘启动")
                .withOpenSettingsButton("设置")
                .build();
        Dexter.checkPermission(snackBarPermissionListener, Manifest.permission.PROCESS_OUTGOING_CALLS);
    }

    void populateAppList(int flag) {
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        SharedPreferences sharedPreferences;

        mAppListAdapter.clearData();

        for (ApplicationInfo app : apps) {
            if (((app.flags & ApplicationInfo.FLAG_SYSTEM) != 1) && (!app.packageName.equals(getPackageName()))) {
                sharedPreferences = getSharedPreferences(app.packageName, 0);
                boolean hidden = !app.enabled;
                String password = sharedPreferences.getString(Settings.SHARED_PASSWORD, null);
                boolean star = sharedPreferences.getBoolean(Settings.SHARED_STAR, false);
                switch (flag) {
                    case Settings.SPINNER_STAR_APP:
                        if (star) mAppListAdapter.addData(new MyAppInfo(app, hidden, true, password));
                        break;
                    case Settings.SPINNER_HIDDEN_APP:
                        if (hidden) mAppListAdapter.addData(new MyAppInfo(app, true, star, password));
                        break;
                    case Settings.SPINNER_ALL_APP:
                        mAppListAdapter.addData(new MyAppInfo(app, hidden, star, password));
                        break;
                }
            }
        }
        mAppListAdapter.notifyDataSetChanged();
    }

}
