package com.solo.soloclean.memory.utils;

import com.solo.soloclean.memory.bean.ProcessInfo;

import java.io.File;
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
}
