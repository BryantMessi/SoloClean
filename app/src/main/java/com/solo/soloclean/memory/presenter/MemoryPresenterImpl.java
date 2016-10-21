package com.solo.soloclean.memory.presenter;

import android.content.Context;

import com.solo.soloclean.memory.bean.MemoryBean;
import com.solo.soloclean.memory.model.MemoryModel;
import com.solo.soloclean.memory.model.MemoryModelImpl;
import com.solo.soloclean.memory.view.MemoryView;

import java.util.List;

/**
 * Created by Messi on 16-10-13.
 */

public class MemoryPresenterImpl implements MemoryPresenter, MemoryModelImpl.OnGetRunningProcessListener {

    private MemoryModel mMemoryModel;
    private MemoryView mMemoryView;

    public MemoryPresenterImpl(MemoryView memoryView) {
        mMemoryModel = new MemoryModelImpl();
        mMemoryView = memoryView;
    }

    @Override
    public void getRunningProcessInfo(Context context) {
        mMemoryModel.getRunningProcessInfo(context, this);
    }

    @Override
    public void getRunningProcessSize(Context context) {
        mMemoryModel.getRunningProcessSize(context, this);
    }

    @Override
    public void getTotalMemorySize(Context context) {
        mMemoryModel.getTotalMemorySize(context, this);
    }

    @Override
    public void getAvailableMemorySize(Context context) {
        mMemoryModel.getAvailableMemorySize(context, this);
    }

    @Override
    public void clearRunningProcess(Context context, List<String> packageNames) {
        mMemoryModel.clearRunningProcess(context, packageNames, this);
    }

    @Override
    public void onGetRunningProcessInfo(List<MemoryBean> memoryBeanList) {
        mMemoryView.setRunningMemoryInfo(memoryBeanList);
    }

    @Override
    public void onGetRunningProcessSize(float size) {
        mMemoryView.setRunningMemorySize(size);
    }

    @Override
    public void onGetTotalMemorySize(float size) {
        mMemoryView.setTotalMemorySize(size);
    }

    @Override
    public void onGetAvailableMemorySize(float size) {
        mMemoryView.setAvailableMemorySize(size);
    }

    @Override
    public void onFinishClearRunningProcess() {
        mMemoryView.clearRunningProcessFinished();
    }
}
