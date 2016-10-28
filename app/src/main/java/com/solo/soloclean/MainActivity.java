package com.solo.soloclean;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.solo.soloclean.garbage.ui.GarbageFragment;
import com.solo.soloclean.memory.ui.MemoryFragment;
import com.solo.soloclean.safe.ui.SafeFragment;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private LinearLayout mLytControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onContentChanged() {
        findViewById(R.id.btn_garbage).setOnClickListener(this);
        findViewById(R.id.btn_memory).setOnClickListener(this);
        findViewById(R.id.btn_safe).setOnClickListener(this);
        mLytControl = (LinearLayout) findViewById(R.id.lyt_control);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        mLytControl.setVisibility(View.GONE);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (id) {
            case R.id.btn_garbage:
                ft.replace(R.id.flyt_container, GarbageFragment.newInstance(null, null));
                break;
            case R.id.btn_memory:
                ft.replace(R.id.flyt_container, MemoryFragment.newInstance(null, null));
                break;
            case R.id.btn_safe:
                ft.replace(R.id.flyt_container, SafeFragment.newInstance(null, null));
                break;
        }
        ft.commit();
    }
}
