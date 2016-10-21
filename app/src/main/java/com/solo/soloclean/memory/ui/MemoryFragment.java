package com.solo.soloclean.memory.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.solo.soloclean.R;
import com.solo.soloclean.memory.bean.MemoryBean;
import com.solo.soloclean.memory.MemoryAdapter;
import com.solo.soloclean.memory.presenter.MemoryPresenter;
import com.solo.soloclean.memory.presenter.MemoryPresenterImpl;
import com.solo.soloclean.memory.view.MemoryView;

import java.util.List;


public class MemoryFragment extends Fragment implements MemoryView, View.OnClickListener {

    private TextView mTxtTotal;
    private TextView mTxtAva;
    private TextView mTxtCleanSize;
    private ListView mLstMemoryInfo;
    private MemoryAdapter mAdapter;

    private MemoryPresenter mPresenter;

    public MemoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters..
     *
     * @return A new instance of fragment MemoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MemoryFragment newInstance(String param1, String param2) {
        MemoryFragment fragment = new MemoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MemoryPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_memory, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.btn_ava).setOnClickListener(this);
        view.findViewById(R.id.btn_clean_size).setOnClickListener(this);
        view.findViewById(R.id.btn_total).setOnClickListener(this);
        view.findViewById(R.id.btn_clean).setOnClickListener(this);
        view.findViewById(R.id.btn_info).setOnClickListener(this);

        mTxtAva = (TextView) view.findViewById(R.id.txt_available);
        mTxtCleanSize = (TextView) view.findViewById(R.id.txt_clean_size);
        mTxtTotal = (TextView) view.findViewById(R.id.txt_total);
        mLstMemoryInfo = (ListView) view.findViewById(R.id.lst_memory);
        mAdapter = new MemoryAdapter(getContext());
        mLstMemoryInfo.setAdapter(mAdapter);
    }

    @Override
    public void setRunningMemoryInfo(List<MemoryBean> memoryBeanList) {
        mAdapter.setData(memoryBeanList);
    }

    @Override
    public void setRunningMemorySize(float size) {
        mTxtCleanSize.setText("占用了：" + size);
    }

    @Override
    public void setTotalMemorySize(float size) {
        mTxtTotal.setText("总内存为：" + size);
    }

    @Override
    public void setAvailableMemorySize(float size) {
        mTxtAva.setText("可用内存为: " + size);
    }

    @Override
    public void clearRunningProcessFinished() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_ava:
                mPresenter.getAvailableMemorySize(getContext());
                break;
            case R.id.btn_clean:
                break;
            case R.id.btn_clean_size:
                mPresenter.getRunningProcessSize(getContext());
                break;
            case R.id.btn_total:
                mPresenter.getTotalMemorySize(getContext());
                break;
            case R.id.btn_info:
                mPresenter.getRunningProcessInfo(getContext());
                break;
        }
    }
}
