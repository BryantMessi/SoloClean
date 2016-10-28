package com.solo.soloclean.safe.presenter;

import android.content.Context;

import com.solo.soloclean.safe.model.SafeModel;
import com.solo.soloclean.safe.model.SafeModelImpl;
import com.solo.soloclean.safe.view.SafeView;

/**
 * Created by Messi on 16-10-27.
 */

public class SafePresenterImpl implements SafePresenter {

    private SafeModel mModel;
    private SafeView mSafeView;

    public SafePresenterImpl(SafeView safeView) {
        this.mSafeView = safeView;
        mModel = new SafeModelImpl();
    }

    @Override
    public void safeCloudScan(Context context) {
        mModel.cloudScan(context);
    }
}
