package com.stkj.freeshare.entitys;

import android.support.annotation.Nullable;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

public class ExpandPicAdapter extends FlexibleAdapter<AbstractFlexibleItem> {


    public ExpandPicAdapter(@Nullable List<AbstractFlexibleItem> items, @Nullable Object listeners) {
        super(items, listeners,true);
    }



    @Override
    public void updateDataSet(@Nullable List<AbstractFlexibleItem> items) {
        super.updateDataSet(items);
    }
}
