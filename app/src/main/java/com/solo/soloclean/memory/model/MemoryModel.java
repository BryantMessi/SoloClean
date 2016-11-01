package com.solo.soloclean.memory.model;

import android.content.Context;

import java.util.List;

/**
 * Created by Messi on 16-10-12.
 */

public interface MemoryModel {
    void getRunningProcessInfo(Context context, MemoryModelImpl.OnGetRunningProcessListener listener);

    void getRunningProcessSize(Context context, MemoryModelImpl.OnGetRunningProcessListener listener);

    void getRunningProcessPercent(Context context, MemoryModelImpl.OnGetRunningProcessListener listener);

    void getTotalMemorySize(Context context, MemoryModelImpl.OnGetRunningProcessListener listener);

    void getAvailableMemorySize(Context context, MemoryModelImpl.OnGetRunningProcessListener listener);

    void clearRunningProcess(Context context, List<String> packageNames, MemoryModelImpl.OnGetRunningProcessListener listener);
}
