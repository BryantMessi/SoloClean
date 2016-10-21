package com.solo.soloclean.garbage.presenter;

import android.content.Context;

import com.solo.soloclean.garbage.beans.GarbageBean;
import com.solo.soloclean.garbage.model.FastGarbageModel;
import com.solo.soloclean.garbage.model.FastGarbageModelImpl;
import com.solo.soloclean.garbage.view.FastGarbageView;

import java.util.List;

/**
 * Created by Messi on 16-10-21.
 */

public class FastGarbagePresenterImpl implements FastGarbagePresenter, FastGarbageModelImpl.OnGarbageQueryListener {

    private FastGarbageModel mGarbageModel;
    private FastGarbageView mGarbageView;

    public FastGarbagePresenterImpl(FastGarbageView view) {
        this.mGarbageView = view;
        mGarbageModel = new FastGarbageModelImpl();
    }

    @Override
    public void queryCacheFiles(Context context) {
        mGarbageModel.queryCacheFiles(context, this);
    }

    @Override
    public void queryResidualFiles(Context context) {
        mGarbageModel.queryResidualFiles(context, this);
    }

    @Override
    public void queryTempFiles(Context context) {
        mGarbageModel.queryTempFiles(context, this);
    }

    @Override
    public void queryInstalledPackages(Context context) {
        mGarbageModel.queryInstalledPackages(context, this);
    }

    @Override
    public void queryBigFiles(Context context) {
        mGarbageModel.queryBigFiles(context, this);
    }

    @Override
    public void onCacheFilesQuery(List<GarbageBean> cacheFiles) {

    }

    @Override
    public void onResidualFilesQuery(List<GarbageBean> cacheFiles) {

    }

    @Override
    public void onTempFilesQuery(List<GarbageBean> cacheFiles) {

    }

    @Override
    public void onInstalledPackagesQuery(List<GarbageBean> cacheFiles) {

    }

    @Override
    public void onBigFilesQuery(List<GarbageBean> cacheFiles) {

    }
}
