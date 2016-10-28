package com.solo.soloclean.safe.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.solo.soloclean.R;
import com.solo.soloclean.safe.bean.SafeBean;
import com.solo.soloclean.safe.presenter.SafePresenter;
import com.solo.soloclean.safe.presenter.SafePresenterImpl;
import com.solo.soloclean.safe.view.SafeView;

import java.util.List;

public class SafeFragment extends Fragment implements SafeView, View.OnClickListener {

    private SafePresenter mPresenter;

    public SafeFragment() {
        // Required empty public constructor
    }

    public static SafeFragment newInstance(String param1, String param2) {
        SafeFragment fragment = new SafeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mPresenter = new SafePresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_safe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btn_scan).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_scan:
                mPresenter.safeCloudScan(getContext());
                break;
        }
    }

    @Override
    public void scanResultSafety() {

    }

    @Override
    public void scanResultUnSafety(List<SafeBean> beanList) {

    }
}
