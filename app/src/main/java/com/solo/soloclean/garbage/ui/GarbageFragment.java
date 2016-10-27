package com.solo.soloclean.garbage.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solo.soloclean.R;
import com.solo.soloclean.garbage.beans.GarbageBean;
import com.solo.soloclean.garbage.presenter.GarbagePresenter;
import com.solo.soloclean.garbage.presenter.GarbagePresenterImpl;
import com.solo.soloclean.garbage.view.GarbageView;
import com.solo.soloclean.memory.bean.MemoryBean;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GarbageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GarbageFragment extends Fragment implements GarbageView, View.OnClickListener {
    private TextView mTxtInfo;
    private GarbagePresenter mPresenter;

    public GarbageFragment() {
        // Required empty public constructor
    }

    public static GarbageFragment newInstance(String param1, String param2) {
        GarbageFragment fragment = new GarbageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new GarbagePresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_garabge, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mTxtInfo = (TextView) view.findViewById(R.id.txt_info);
        view.findViewById(R.id.btn_cache_file).setOnClickListener(this);
        view.findViewById(R.id.btn_residual_file).setOnClickListener(this);
        view.findViewById(R.id.btn_temp_file).setOnClickListener(this);
        view.findViewById(R.id.btn_big_file).setOnClickListener(this);
        view.findViewById(R.id.btn_ad_file).setOnClickListener(this);
        view.findViewById(R.id.btn_installed_file).setOnClickListener(this);
        view.findViewById(R.id.btn_memory_file).setOnClickListener(this);
    }

    @Override
    public void setCacheFiles(List<GarbageBean> beanList) {

    }

    @Override
    public void setResidualFiles(List<GarbageBean> beanList) {

    }

    @Override
    public void setADFiles(List<GarbageBean> beanList) {

    }

    @Override
    public void setTempFiles(List<GarbageBean> beanList) {

    }

    @Override
    public void setInstalledPackages(List<GarbageBean> beanList) {

    }

    @Override
    public void setBigFiles(List<GarbageBean> beanList) {

    }

    @Override
    public void setMemoryGarbageInfo(List<MemoryBean> beanList) {

    }

    @Override
    public void setMemoryGarbageSize(float size) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_ad_file:
                break;
            case R.id.btn_cache_file:
                mPresenter.queryCacheFiles(getContext());
                break;
            case R.id.btn_installed_file:
                mPresenter.queryInstalledPackages(getContext());
                break;
            case R.id.btn_big_file:
                mPresenter.queryBigFiles(getContext());
                break;
            case R.id.btn_memory_file:
                mPresenter.queryMemoryGarbage(getContext());
                break;
            case R.id.btn_residual_file:
                break;
            case R.id.btn_temp_file:
                mPresenter.queryTempFiles(getContext());
                break;
        }
    }
}
