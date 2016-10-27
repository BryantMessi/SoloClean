package com.solo.soloclean.garbage.view;

import com.solo.soloclean.garbage.beans.GarbageBean;
import com.solo.soloclean.memory.bean.MemoryBean;

import java.util.List;

/**
 * Created by Messi on 16-10-21.
 */

public interface GarbageView {
    void setCacheFiles(List<GarbageBean> beanList);

    void setResidualFiles(List<GarbageBean> beanList);

    void setADFiles(List<GarbageBean> beanList);

    void setTempFiles(List<GarbageBean> beanList);

    void setInstalledPackages(List<GarbageBean> beanList);

    void setBigFiles(List<GarbageBean> beanList);

    void setMemoryGarbageInfo(List<MemoryBean> beanList);

    void setMemoryGarbageSize(float size);
}
