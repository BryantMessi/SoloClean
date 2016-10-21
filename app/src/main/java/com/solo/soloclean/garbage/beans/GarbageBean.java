package com.solo.soloclean.garbage.beans;

import android.graphics.drawable.Drawable;

/**
 * Created by Messi on 16-10-20.
 */

public class GarbageBean {
    private String mLabel;
    private Drawable mIcon;
    private String mSize;

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        this.mLabel = label;
    }

    public Drawable getIcon() {
        return mIcon;
    }

    public void setIcon(Drawable icon) {
        this.mIcon = icon;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        this.mSize = size;
    }
}
