package com.solo.soloclean.garbage.model;

import android.content.Context;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.RemoteException;
import android.text.format.Formatter;

import com.solo.soloclean.common.utils.AppUtils;
import com.solo.soloclean.garbage.beans.GarbageBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Messi on 16-10-20.
 */

public class FastGarbageModelImpl implements FastGarbageModel {

    @Override
    public void queryCacheFiles(final Context context, OnGarbageQueryListener listener) {
        try {
            List<String> installedPackages = AppUtils.getInstalledPackages(context);
            if (!installedPackages.isEmpty()) {
                final List<GarbageBean> garbageBeans = new ArrayList<>();
                PackageManager pm = context.getPackageManager();
                Method getPackageSizeInfo = pm.getClass().getMethod(
                        "getPackageSizeInfo", String.class, IPackageStatsObserver.class);

                for (final String pkg : installedPackages) {
                    getPackageSizeInfo.invoke(pm, pkg, new IPackageStatsObserver.Stub() {
                        @Override
                        public void onGetStatsCompleted(final PackageStats pStats, boolean succeeded)
                                throws RemoteException {
                            GarbageBean bean = new GarbageBean();
                            bean.setIcon(AppUtils.getApplicationIcon(context, pkg));
                            bean.setLabel((String) AppUtils.getApplicationLabel(context, pkg));
                            bean.setSize(Formatter.formatFileSize(context, pStats.cacheSize));
                            garbageBeans.add(bean);
                        }
                    });
                }
                listener.onCacheFilesQuery(garbageBeans);
            }
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void queryResidualFiles(Context context, OnGarbageQueryListener listener) {

    }

    @Override
    public void queryADFiles(Context context, OnGarbageQueryListener listener) {

    }

    @Override
    public void queryTempFiles(Context context, OnGarbageQueryListener listener) {

    }

    @Override
    public void queryInstalledPackages(Context context, OnGarbageQueryListener listener) {

    }

    @Override
    public void queryBigFiles(Context context, OnGarbageQueryListener listener) {

    }


    public interface OnGarbageQueryListener {
        void onCacheFilesQuery(List<GarbageBean> cacheFiles);

        void onResidualFilesQuery(List<GarbageBean> cacheFiles);

        void onTempFilesQuery(List<GarbageBean> cacheFiles);

        void onInstalledPackagesQuery(List<GarbageBean> cacheFiles);

        void onBigFilesQuery(List<GarbageBean> cacheFiles);
    }
}
