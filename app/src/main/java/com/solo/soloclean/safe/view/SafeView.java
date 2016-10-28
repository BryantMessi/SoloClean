package com.solo.soloclean.safe.view;

import com.solo.soloclean.safe.bean.SafeBean;

import java.util.List;

/**
 * Created by Messi on 16-10-27.
 */

public interface SafeView {
    void scanResultSafety();

    void scanResultUnSafety(List<SafeBean> beanList);
}
