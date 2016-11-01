package com.solo.soloclean.memory.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.solo.soloclean.memory.bean.ProcessInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Messi on 16-10-13.
 */

public class ProcessManager {

    public static List<ProcessInfo> getRunningProcessInfo() {
        ArrayList<ProcessInfo> processes = new ArrayList<>();
        File[] files = (new File("/proc")).listFiles();
        File[] temp = files;
        int length = files.length;

        for (int i = 0; i < length; ++i) {
            File file = temp[i];
            if (file.isDirectory()) {
                int pid;
                try {
                    pid = Integer.parseInt(file.getName());
                } catch (NumberFormatException var9) {
                    continue;
                }

                try {
                    processes.add(new ProcessInfo(pid));
                } catch (IOException var8) {
                }
            }
        }
        return processes;
    }

    public static long getTotalMemorySize(){
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
        return total;
    }

    public static long getAvailableMemorySize(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }
}
