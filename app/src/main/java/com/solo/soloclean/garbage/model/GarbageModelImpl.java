package com.solo.soloclean.garbage.model;

import android.content.Context;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Environment;
import android.os.RemoteException;
import android.text.format.Formatter;
import android.util.Log;

import com.solo.soloclean.common.constants.SystemConstants;
import com.solo.soloclean.common.utils.AppUtils;
import com.solo.soloclean.common.utils.FileUtils;
import com.solo.soloclean.garbage.beans.GarbageBean;
import com.solo.soloclean.memory.model.MemoryModel;
import com.solo.soloclean.memory.model.MemoryModelImpl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Messi on 16-10-20.
 */

public class GarbageModelImpl implements GarbageModel {

    /**
     * 获取应用缓存
     */
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
                            Log.d("messi", "cache file name :" + AppUtils.getApplicationLabel(context, pkg) + " size :" + Formatter.formatFileSize(context, pStats.cacheSize));

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

    /**
     * 获取卸载残留
     */
    @Override
    public void queryResidualFiles(Context context, OnGarbageQueryListener listener) {

    }

    /**
     * 获取广告文件
     */
    @Override
    public void queryADFiles(Context context, OnGarbageQueryListener listener) {

    }

    /**
     * 获取系统垃圾
     */
    @Override
    public void queryTempFiles(Context context, OnGarbageQueryListener listener) {
        List<GarbageBean> beanList = new ArrayList<>();
        if (FileUtils.isFileAvailable(SystemConstants.FILE_DATA_ANR)) {
            beanList.addAll(getGarbageFiles(context, SystemConstants.FILE_DATA_ANR));
        }

        if (FileUtils.isFileAvailable(SystemConstants.FILE_DATA_LOCAL_TMP)) {
            beanList.addAll(getGarbageFiles(context, SystemConstants.FILE_DATA_LOCAL_TMP));
        }

        if (FileUtils.isFileAvailable(SystemConstants.FILE_DATA_SYSTEM_APPUSAGESTATES)) {
            beanList.addAll(getGarbageFiles(context, SystemConstants.FILE_DATA_SYSTEM_APPUSAGESTATES));
        }

        if (FileUtils.isFileAvailable(SystemConstants.FILE_DATA_SYSTEM_DROPBOX)) {
            beanList.addAll(getGarbageFiles(context, SystemConstants.FILE_DATA_SYSTEM_DROPBOX));
        }

        if (FileUtils.isFileAvailable(SystemConstants.FILE_DATA_SYSTEM_USAGESTATS)) {
            beanList.addAll(getGarbageFiles(context, SystemConstants.FILE_DATA_SYSTEM_USAGESTATS));
        }

        if (FileUtils.isFileAvailable(SystemConstants.FILE_DATA_TMP)) {
            beanList.addAll(getGarbageFiles(context, SystemConstants.FILE_DATA_TMP));
        }

        if (FileUtils.isFileAvailable(SystemConstants.FILE_DATA_TOMBSTONES)) {
            beanList.addAll(getGarbageFiles(context, SystemConstants.FILE_DATA_TOMBSTONES));
        }

        if (FileUtils.isFileAvailable(SystemConstants.FILE_DEV_LOG_MAIN)) {
            beanList.addAll(getGarbageFiles(context, SystemConstants.FILE_DEV_LOG_MAIN));
        }

        if (FileUtils.isSDCardMounted()) {
            List<File> files = new ArrayList<>();
            files = FileUtils.getFiles(Environment.getExternalStorageDirectory(), files);
            if (!files.isEmpty()) {
                for (File file : files) {
                    if (file.length() == 0) {
                        GarbageBean bean = new GarbageBean();
                        bean.setLabel(file.getName());
                        bean.setSize(Formatter.formatFileSize(context, file.length()));
                        beanList.add(bean);
                    }
                }
            }
        }
        for (GarbageBean bean : beanList) {
            Log.d("messi", "temp file name :" + bean.getLabel() + " size :" + bean.getSize());
        }
        listener.onTempFilesQuery(beanList);
    }

    /**
     * 获取无用安装包
     */
    @Override
    public void queryInstalledPackages(Context context, OnGarbageQueryListener listener) {
        List<GarbageBean> beanList = new ArrayList<>();
        if (FileUtils.isSDCardMounted()) {
            List<File> files = new ArrayList<>();
            files = FileUtils.getFiles(Environment.getExternalStorageDirectory(), files);
            if (!files.isEmpty()) {
                for (File file : files) {
                    String name = file.getName();
                    if (name.endsWith(".apk")) {
                        //判断是否是无用安装包
                        if (!file.canExecute()) {
                            GarbageBean bean = new GarbageBean();
                            bean.setSize(Formatter.formatFileSize(context, file.length()));
                            bean.setLabel(name);
                            beanList.add(bean);
                        } else if (AppUtils.isAppInstalled(context, name.substring(0, name.lastIndexOf(".")))) {
                            GarbageBean garbageBean = new GarbageBean();
                            garbageBean.setLabel(name);
                            garbageBean.setSize(Formatter.formatFileSize(context, file.length()));
                            beanList.add(garbageBean);
                        }
                    }
                }
            }
        }

        for (GarbageBean bean : beanList) {
            Log.d("messi", "installed file name :" + bean.getLabel() + " size :" + bean.getSize());
        }
        listener.onInstalledPackagesQuery(beanList);
    }

    /**
     * 获取大文件
     */
    @Override
    public void queryBigFiles(Context context, OnGarbageQueryListener listener) {
        List<GarbageBean> beanList = new ArrayList<>();
        if (FileUtils.isSDCardMounted()) {
            List<File> files = new ArrayList<>();
            files = FileUtils.getFiles(Environment.getExternalStorageDirectory(), files);
            if (!files.isEmpty()) {
                for (File file : files) {
                    if (file.length() > SystemConstants.BIG_FILE_THRESHOLD) {
                        //大文件
                        GarbageBean bean = new GarbageBean();
                        bean.setLabel(file.getName());
                        bean.setSize(file.length() + "");
                        beanList.add(bean);
                    }
                }
            }
        }

        for (GarbageBean bean : beanList) {
            Log.d("messi", "big file name :" + bean.getLabel() + " size :" + bean.getSize());
        }
        listener.onBigFilesQuery(beanList);
    }

    /**
     * 获取占用内存
     */
    @Override
    public void queryMemoryGarbage(Context context, MemoryModelImpl.OnGetRunningProcessListener listener) {
        MemoryModel memoryModel = new MemoryModelImpl();
        memoryModel.getRunningProcessInfo(context, listener);
        memoryModel.getAvailableMemorySize(context, listener);
    }

    private List<GarbageBean> getGarbageFiles(Context context, File file) {
        List<GarbageBean> beanList = new ArrayList<>();
        List<File> files = new ArrayList<>();
        files = FileUtils.getFiles(file, files);
        if (!files.isEmpty()) {
            for (File f : files) {
                GarbageBean bean = new GarbageBean();
                bean.setLabel(f.getName());
                bean.setSize(Formatter.formatFileSize(context, f.length()));
                beanList.add(bean);
            }
        }
        return beanList;
    }

    public interface OnGarbageQueryListener {
        void onCacheFilesQuery(List<GarbageBean> cacheFiles);

        void onResidualFilesQuery(List<GarbageBean> cacheFiles);

        void onTempFilesQuery(List<GarbageBean> cacheFiles);

        void onInstalledPackagesQuery(List<GarbageBean> cacheFiles);

        void onBigFilesQuery(List<GarbageBean> cacheFiles);

        void onMemoryGarbageQuery(List<GarbageBean> cacheFiles);
    }
}
