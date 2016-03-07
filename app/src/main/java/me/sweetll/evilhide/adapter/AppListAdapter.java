package me.sweetll.evilhide.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.sweetll.evilhide.R;
import me.sweetll.evilhide.Settings;
import me.sweetll.evilhide.model.MyAppInfo;
import me.sweetll.evilhide.service.HiddenService;

/**
 * Created by sweet on 3/6/16.
 */
public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppListViewHolder> {
    private final Context mContext;
    private ArrayList<MyAppInfo> mAppInfos;

    public AppListAdapter(Context context) {
        super();
        mContext = context;
        mAppInfos = new ArrayList<>();
    }

    @Override
    public AppListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_app, parent, false);
        return new AppListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AppListViewHolder holder, final int position) {
        final PackageManager pm = mContext.getPackageManager();
        final MyAppInfo app = mAppInfos.get(position);

        holder.mAppLabel.setText(pm.getApplicationLabel(app.applicationInfo));
        holder.mIconImage.setImageDrawable(pm.getApplicationIcon(app.applicationInfo));

        holder.mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.mSwitchButton.setChecked(app.hidden);
        holder.mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MyAppInfo selectedApp = mAppInfos.get(position);
                final PackageManager pm = mContext.getPackageManager();
                String packageName = selectedApp.applicationInfo.packageName;
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("evil_hide" + packageName, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isChecked) {
                    editor.putBoolean(Settings.SHARED_HIDDEN, true);
                    editor.commit();
                    String cmd = "pm disable " + packageName;
                    HiddenService.performAction(mContext, cmd);
                } else {
                    editor.putBoolean(Settings.SHARED_HIDDEN, false);
                    editor.commit();
                    String cmd = "pm enable " + packageName;
                    HiddenService.performAction(mContext, cmd);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAppInfos.size();
    }

    private String getLauncherActivityFromPackageName(String packageName) {
        final PackageManager pm = mContext.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);

        List<ResolveInfo> appList = pm.queryIntentActivities(mainIntent, 0);

        for (ResolveInfo app : appList) {
            if (app.activityInfo.packageName.equals(packageName)) {
                return app.activityInfo.name;
            }
        }

        return null;
    }

    public void setData(ArrayList<MyAppInfo> infos) {
        mAppInfos = infos;
    }

    public void addData(MyAppInfo info) {
        mAppInfos.add(info);
    }

    public void clearData() {
        mAppInfos.clear();
    }

    public static class AppListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon_app) ImageView mIconImage;
        @Bind(R.id.label_app) TextView mAppLabel;
        @Bind(R.id.btn_add) Button mAddButton;
        @Bind(R.id.switch_btn) Switch mSwitchButton;

        public AppListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
