package com.solo.soloclean.garbage.model;

import android.content.Context;

/**
 * Created by Messi on 16-10-20.
 */

public interface FastGarbageModel {
    void queryCacheFiles(Context context, FastGarbageModelImpl.OnGarbageQueryListener listener);

    void queryResidualFiles(Context context, FastGarbageModelImpl.OnGarbageQueryListener listener);

    void queryADFiles(Context context, FastGarbageModelImpl.OnGarbageQueryListener listener);

    void queryTempFiles(Context context, FastGarbageModelImpl.OnGarbageQueryListener listener);

    void queryInstalledPackages(Context context, FastGarbageModelImpl.OnGarbageQueryListener listener);

    void queryBigFiles(Context context, FastGarbageModelImpl.OnGarbageQueryListener listener);
}
