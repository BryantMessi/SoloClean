package com.solo.soloclean.garbage.model;

import android.content.Context;

/**
 * Created by Messi on 16-10-20.
 */

public interface DeepGarbageModel extends FastGarbageModel {
    void queryMemorySize(Context context);
}
