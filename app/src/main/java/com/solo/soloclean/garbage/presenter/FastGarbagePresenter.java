package com.solo.soloclean.garbage.presenter;

import android.content.Context;

/**
 * Created by Messi on 16-10-20.
 */

public interface FastGarbagePresenter {
    void queryCacheFiles(Context context);

    void queryResidualFiles(Context context);

    void queryTempFiles(Context context);

    void queryInstalledPackages(Context context);

    void queryBigFiles(Context context);
}
