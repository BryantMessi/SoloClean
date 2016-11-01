package com.solo.soloclean.memory.model;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Debug;
import android.util.Log;

import com.solo.soloclean.memory.bean.MemoryBean;
import com.solo.soloclean.memory.bean.ProcessInfo;
import com.solo.soloclean.memory.utils.ProcessManager;
import com.solo.soloclean.common.utils.AppUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Messi on 16-10-12.
 */

public class MemoryModelImpl implements MemoryModel {

    private static final String TAG = MemoryModelImpl.class.getSimpleName();

    @Override
    public void getRunningProcessInfo(Context context, OnGetRunningProcessListener listener) {
        // 通过调用ActivityManager的getRunningAppProcesses()方法获得系统里所有正在运行的进程
        ArrayList<MemoryBean> memoryInfos = new ArrayList<>();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        PackageManager pm = context.getPackageManager();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.RunningAppProcessInfo> appProcessList = am.getRunningAppProcesses();
            int size = appProcessList.size();
            for (int i = 0; i < size; i++) {
                ActivityManager.RunningAppProcessInfo appProcessInfo = appProcessList.get(i);
                // 进程ID号
                int pid = appProcessInfo.pid;
                // 进程名，默认是包名或者由属性android：process=""指定
                String processName = appProcessInfo.processName;
                // 获得该进程占用的内存
                int[] myMempid = new int[]{pid};
                // 此MemoryInfo位于android.os.Debug.MemoryInfo包中，用来统计进程的内存信息
                Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(myMempid);
                // 获取进程占内存用信息 kb单位
                int memSize = memoryInfo[0].dalvikPrivateDirty;

                Log.i(TAG, "processName: " + processName + "  pid: " + pid
                        + " memorySize is -->" + memSize + "kb");
                // 获得每个进程里运行的应用程序(包),即每个应用程序的包名
                String[] packageList = appProcessInfo.pkgList;
                MemoryBean memoryBean = new MemoryBean();
                memoryBean.setIcon(AppUtils.getApplicationIcon(context, packageList[i]));
                memoryBean.setLabel((String) AppUtils.getApplicationLabel(context, packageList[i]));
                memoryBean.setCacheSize(memSize);

                memoryInfos.add(memoryBean);
            }
        } else {
            List<ProcessInfo> processInfos = ProcessManager.getRunningProcessInfo();
            for (ProcessInfo process : processInfos) {
                int pid = process.getPid();
                String packageName = process.getProcessName();

                MemoryBean memory = new MemoryBean();
                memory.setLabel((String) AppUtils.getApplicationLabel(context, packageName));
                memory.setIcon(AppUtils.getApplicationIcon(context, packageName));

                int[] myMempid = new int[]{pid};
                // 此MemoryInfo位于android.os.Debug.MemoryInfo包中，用来统计进程的内存信息
                Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(myMempid);
                // 获取进程占内存用信息 kb单位
                int memSize = memoryInfo[0].dalvikPrivateDirty;//单位是kb

                memory.setCacheSize(memSize);
                memoryInfos.add(memory);
            }
        }
        listener.onGetRunningProcessInfo(memoryInfos);
    }

    @Override
    public void getRunningProcessSize(Context context, OnGetRunningProcessListener listener) {
        float size = 0;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            List<ActivityManager.RunningAppProcessInfo> appProcessList = am.getRunningAppProcesses();

            for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
                // 进程ID号
                int pid = appProcessInfo.pid;
                // 获得该进程占用的内存
                int[] myMempid = new int[]{pid};
                // 此MemoryInfo位于android.os.Debug.MemoryInfo包中，用来统计进程的内存信息
                Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(myMempid);
                // 获取进程占内存用信息 kb单位
                int memSize = memoryInfo[0].dalvikPrivateDirty;
                size += memSize;
                Log.i(TAG, "pid: " + pid
                        + " memorySize is -->" + memSize + "kb");
            }
        } else {
            List<ProcessInfo> processInfos = ProcessManager.getRunningProcessInfo();
            for (ProcessInfo process : processInfos) {
                int pid = process.getPid();
                int[] myMempid = new int[]{pid};
                // 此MemoryInfo位于android.os.Debug.MemoryInfo包中，用来统计进程的内存信息
                Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(myMempid);
                // 获取进程占内存用信息 kb单位
                int memSize = memoryInfo[0].dalvikPrivateDirty;//单位是kb
                Log.d(TAG, "process name :" + process.getProcessName() + " pid :" + pid + " size:" + memSize / 1024);
                size += memSize;
            }
        }
        listener.onGetRunningProcessSize(size);
    }

    @Override
    public void getRunningProcessPercent(Context context, OnGetRunningProcessListener listener) {
        long total = ProcessManager.getTotalMemorySize();
        long ava = ProcessManager.getAvailableMemorySize(context);
        double temp = (double)(total-ava);
        double percent =(temp/total)*100;
        DecimalFormat df = new DecimalFormat("###");
        listener.onGetRunningProcessPercent(Integer.parseInt(df.format(percent)));
    }

    @Override
    public void getTotalMemorySize(Context context, OnGetRunningProcessListener listener) {
        listener.onGetTotalMemorySize(ProcessManager.getTotalMemorySize() / (1024 * 1024));
    }

    @Override
    public void getAvailableMemorySize(Context context, OnGetRunningProcessListener listener) {
        listener.onGetAvailableMemorySize(ProcessManager.getAvailableMemorySize(context) / (1024 * 1024));
    }

    @Override
    public void clearRunningProcess(Context context, List<String> packageNames, OnGetRunningProcessListener listener) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }


    public interface OnGetRunningProcessListener {
        void onGetRunningProcessInfo(List<MemoryBean> memoryBeanList);

        void onGetRunningProcessSize(float size);

        void onGetRunningProcessPercent(int percent);

        void onGetTotalMemorySize(float size);

        void onGetAvailableMemorySize(float size);

        void onFinishClearRunningProcess();
    }
}
