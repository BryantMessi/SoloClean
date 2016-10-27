package com.solo.soloclean.garbage.presenter;

import android.content.Context;

import com.solo.soloclean.garbage.beans.GarbageBean;
import com.solo.soloclean.garbage.model.GarbageModel;
import com.solo.soloclean.garbage.model.GarbageModelImpl;
import com.solo.soloclean.garbage.view.GarbageView;
import com.solo.soloclean.memory.bean.MemoryBean;
import com.solo.soloclean.memory.model.MemoryModelImpl;

import java.util.List;

/**
 * Created by Messi on 16-10-21.
 */

public class GarbagePresenterImpl implements GarbagePresenter, GarbageModelImpl.OnGarbageQueryListener, MemoryModelImpl.OnGetRunningProcessListener {

    private GarbageModel mGarbageModel;
    private GarbageView mGarbageView;

    public GarbagePresenterImpl(GarbageView view) {
        this.mGarbageView = view;
        mGarbageModel = new GarbageModelImpl();
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
    public void queryMemoryGarbage(Context context) {

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

    @Override
    public void onMemoryGarbageQuery(List<GarbageBean> cacheFiles) {

    }

    @Override
    public void onGetRunningProcessInfo(List<MemoryBean> memoryBeanList) {

    }

    @Override
    public void onGetRunningProcessSize(float size) {

    }

    @Override
    public void onGetTotalMemorySize(float size) {

    }

    @Override
    public void onGetAvailableMemorySize(float size) {

    }

    @Override
    public void onFinishClearRunningProcess() {

    }
}
