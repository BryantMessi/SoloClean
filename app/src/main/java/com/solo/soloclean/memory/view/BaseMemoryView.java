package com.solo.soloclean.memory.view;

/**
 * Created by Messi on 16-10-31.
 */

public interface BaseMemoryView {
    void setRunningProcessSize(float size);

    void setRunningProcessPercent(int percent);

    void clearRunningProcess();
}
