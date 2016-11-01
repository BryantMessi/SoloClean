package com.solo.soloclean.memory.view;

import com.solo.soloclean.memory.bean.MemoryBean;

import java.util.List;

/**
 * Created by Messi on 16-10-13.
 */

public interface MemoryView {
    void setRunningMemoryInfo(List<MemoryBean> memoryBeanList);

    void setTotalMemorySize(float size);

    void setAvailableMemorySize(float size);
}
