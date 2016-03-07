package me.sweetll.evilhide;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
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

import com.orhanobut.logger.Logger;

import java.io.DataOutputStream;
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void populateAppList(int flag) {
        final PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);
        SharedPreferences sharedPreferences;

        mAppListAdapter.clearData();
        mAppListAdapter.notifyDataSetChanged();

        for (ApplicationInfo app : apps) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 1) {
                sharedPreferences = getSharedPreferences(getPackageName() + app.packageName, 0);
                boolean hidden = !app.enabled;
                String password = sharedPreferences.getString(Settings.SHARED_PASSWORD, null);
                switch (flag) {
                    case Settings.SPINNER_HIDDEN_APP:
                        if (hidden) mAppListAdapter.addData(new MyAppInfo(app, true, password));
                        break;
                    case Settings.SPINNER_ALL_APP:
                        mAppListAdapter.addData(new MyAppInfo(app, hidden, password));
                        break;
                }
            }
        }
        mAppListAdapter.notifyDataSetChanged();
    }

}
