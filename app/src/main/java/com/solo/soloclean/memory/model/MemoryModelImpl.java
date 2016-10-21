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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
    public void getTotalMemorySize(Context context, OnGetRunningProcessListener listener) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long total = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            total = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        //return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
        Log.d(TAG, "总内存大小为：" + total / (1024 * 1024));
        listener.onGetTotalMemorySize(total / (1024 * 1024));
    }

    @Override
    public void getAvailableMemorySize(Context context, OnGetRunningProcessListener listener) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        Log.d(TAG, "可用内存---->>>" + mi.availMem / (1024 * 1024));
        listener.onGetAvailableMemorySize(mi.availMem / (1024 * 1024));
    }

    @Override
    public void clearRunningProcess(Context context, List<String> packageNames, OnGetRunningProcessListener listener) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }


    public interface OnGetRunningProcessListener {
        void onGetRunningProcessInfo(List<MemoryBean> memoryBeanList);

        void onGetRunningProcessSize(float size);

        void onGetTotalMemorySize(float size);

        void onGetAvailableMemorySize(float size);

        void onFinishClearRunningProcess();
    }
}
