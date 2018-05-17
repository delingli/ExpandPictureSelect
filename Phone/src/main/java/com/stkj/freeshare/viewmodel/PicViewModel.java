package com.stkj.freeshare.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.stkj.freeshare.entitys.ExplandHead;
import com.stkj.freeshare.repository.PicRepository;

import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

public class PicViewModel extends AndroidViewModel {
    private MutableLiveData<List<AbstractFlexibleItem>> mPicDataSource;
    private PicRepository mPicrepository;

    public PicViewModel(@NonNull Application application) {
        super(application);
        mPicrepository = new PicRepository(application);
        mPicDataSource = mPicrepository.getPicDataSource();
    }

    public MutableLiveData<List<AbstractFlexibleItem>> getmPicDataSource() {
        return mPicDataSource;
    }
    public int getSelectedCount() {
        List<AbstractFlexibleItem> headitem = mPicDataSource.getValue();
        int totalSelect=0;
        for(int i=0;i<headitem.size();++i){
            if(headitem.get(i) instanceof ExplandHead){
                ExplandHead head= (ExplandHead) headitem.get(i);
                totalSelect+= head.getImgFold().getSelectCount();
            }
        }
        return totalSelect;
    }
    public void getDiskPicture() {
        this.mPicrepository.getDiskPicture();
    }
}
