package com.solo.soloclean.garbage.model;

import android.content.Context;

import com.solo.soloclean.memory.model.MemoryModelImpl;

/**
 * Created by Messi on 16-10-20.
 */

public interface GarbageModel {
    void queryCacheFiles(Context context, GarbageModelImpl.OnGarbageQueryListener listener);

    void queryResidualFiles(Context context, GarbageModelImpl.OnGarbageQueryListener listener);

    void queryADFiles(Context context, GarbageModelImpl.OnGarbageQueryListener listener);

    void queryTempFiles(Context context, GarbageModelImpl.OnGarbageQueryListener listener);

    void queryInstalledPackages(Context context, GarbageModelImpl.OnGarbageQueryListener listener);

    void queryBigFiles(Context context, GarbageModelImpl.OnGarbageQueryListener listener);

    void queryMemoryGarbage(Context context, MemoryModelImpl.OnGetRunningProcessListener listener);
}
