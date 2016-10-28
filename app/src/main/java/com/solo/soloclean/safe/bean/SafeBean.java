package com.solo.soloclean.safe.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Messi on 16-10-27.
 */

public class SafeBean {
    private String mLabel;
    private Drawable mIcon;
    private String mSafeType;

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        this.mLabel = label;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setmIcon(Drawable icon) {
        this.mIcon = icon;
    }

    public String getSafeType() {
        return mSafeType;
    }

    public void setmSafeType(String safeType) {
        this.mSafeType = safeType;
    }
}
