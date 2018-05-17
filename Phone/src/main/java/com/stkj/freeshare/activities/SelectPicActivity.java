package com.stkj.freeshare.activities;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.stkj.freeshare.R;
import com.stkj.freeshare.fragments.SelectionPictureFragment;

public class SelectPicActivity extends AppCompatActivity    {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
        FrameLayout fl_continer = findViewById(R.id.fl_continer);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_continer, SelectionPictureFragment.newInstance());
        fragmentTransaction.commit();
    }
}



