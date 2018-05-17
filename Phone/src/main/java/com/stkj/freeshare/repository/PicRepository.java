package com.stkj.freeshare.repository;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import com.stkj.freeshare.http.GetPicAsyncTask;

import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

public class PicRepository {
    private MutableLiveData<List<AbstractFlexibleItem>> mPicDataSource;
    private Context context;

    public PicRepository(Context context) {
        this.mPicDataSource = new MutableLiveData<>();
        this.context = context;
    }

    public MutableLiveData<List<AbstractFlexibleItem>> getPicDataSource() {
        return mPicDataSource;
    }

    /**
     * 获取图片异步执行
     */
    public void getDiskPicture() {
        GetPicAsyncTask task = new GetPicAsyncTask(context);
        task.setOnTaskListener(new GetPicAsyncTask.OnTaskListener() {
            @Override
            public void taskSucessFul(List<AbstractFlexibleItem> abstractFlexibleItems) {
                mPicDataSource.setValue(abstractFlexibleItems);

            }
        });
        task.execute();
    }
}
