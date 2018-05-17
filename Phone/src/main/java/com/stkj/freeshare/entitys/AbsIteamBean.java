package com.stkj.freeshare.entitys;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 每个Iteｍ数据实体
 * @param <VH>
 */
public abstract class AbsIteamBean<VH extends FlexibleViewHolder> extends AbstractFlexibleItem<VH> {
    protected String id;
    public AbsIteamBean(String id) {
        this.id = id;
    }
    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof AbsIteamBean) {
            AbsIteamBean inItem = (AbsIteamBean) inObject;
            return this.id.equals(inItem.id);
        }
        return false;
    }






}
