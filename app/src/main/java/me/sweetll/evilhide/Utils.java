package me.sweetll.evilhide;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * Created by sweet on 3/7/16.
 */
public class Utils {
    private static String getLauncherActivityFromPackageName(Context context, String packageName) {
        final PackageManager pm = context.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);

        List<ResolveInfo> appList = pm.queryIntentActivities(mainIntent, 0);

        for (ResolveInfo app : appList) {
            if (app.activityInfo.packageName.equals(packageName)) {
                return app.activityInfo.name;
            }
        }
        return null;
    }
}
