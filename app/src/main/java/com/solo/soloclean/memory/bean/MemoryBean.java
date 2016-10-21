package com.solo.soloclean.memory.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Messi on 16-10-12.
 */

public class MemoryBean {

    private Drawable mIcon;
    private String mLabel;
    private float mCacheSize;
    private int mProcessCount;
    private int mServiceCount;

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        this.mLabel = label;
    }

    public float getCacheSize() {
        return mCacheSize;
    }

    public void setCacheSize(float cacheSize) {
        this.mCacheSize = cacheSize;
    }

    public int getProcessCount() {
        return mProcessCount;
    }

    public void setProcessCount(int processCount) {
        this.mProcessCount = processCount;
    }

    public int getServiceCount() {
        return mServiceCount;
    }

    public void setServiceCount(int serviceCount) {
        this.mServiceCount = serviceCount;
    }
}
