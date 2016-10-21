package com.solo.soloclean.memory.presenter;

import android.content.Context;

import java.util.List;

/**
 * Created by Messi on 16-10-13.
 */

public interface MemoryPresenter {
    void getRunningProcessInfo(Context context);

    void getRunningProcessSize(Context context);

    void getTotalMemorySize(Context context);

    void getAvailableMemorySize(Context context);

    void clearRunningProcess(Context context, List<String> packageNames);
}
