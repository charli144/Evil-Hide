package me.sweetll.evilhide.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
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
    public void onBindViewHolder(AppListViewHolder holder, int position) {
        final PackageManager pm = mContext.getPackageManager();
        final MyAppInfo app = mAppInfos.get(position);

        holder.mAppLabel.setText(pm.getApplicationLabel(app.applicationInfo));
        holder.mIconImage.setImageDrawable(pm.getApplicationIcon(app.applicationInfo));

        holder.mSwitchButton.setChecked(app.hidden);
    }

    @Override
    public int getItemCount() {
        return mAppInfos.size();
    }

    public ArrayList<MyAppInfo> getData() {
        return mAppInfos;
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

    public class AppListViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.icon_app) ImageView mIconImage;
        @Bind(R.id.label_app) TextView mAppLabel;
        @Bind(R.id.btn_add) Button mAddButton;
        @Bind(R.id.switch_btn) Switch mSwitchButton;

        public AppListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final MyAppInfo app = mAppInfos.get(position);
                    final EditText editPassword = new EditText(mContext);
                    if (!TextUtils.isEmpty(app.password)) {
                        editPassword.setText(app.password);
                    }
                    new AlertDialog.Builder(mContext)
                        .setTitle("请输入启动该应用的密码")
                        .setView(editPassword)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                app.password = editPassword.getText().toString();
                                SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getPackageName() + app.applicationInfo.packageName, 0);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Settings.SHARED_PASSWORD, app.password);
                                editor.apply();
                            }
                        })
                        .show();
                }
            });

            mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    MyAppInfo selectedApp = mAppInfos.get(position);
                    //如果一致就不要管了
                    if (selectedApp.hidden == isChecked)
                        return;
                    selectedApp.hidden = isChecked;
                    String packageName = selectedApp.applicationInfo.packageName;
                    SharedPreferences sharedPreferences = mContext.getSharedPreferences(mContext.getPackageName() + packageName, 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (isChecked) {
                        editor.putBoolean(Settings.SHARED_HIDDEN, true);
                        editor.apply();
                        String cmd = "pm disable " + packageName;
                        HiddenService.performAction(mContext, cmd);
                    } else {
                        editor.putBoolean(Settings.SHARED_HIDDEN, false);
                        editor.apply();
                        String cmd = "pm enable " + packageName;
                        HiddenService.performAction(mContext, cmd);
                    }
                }
            });

        }
    }
}
