package com.stkj.freeshare.entitys;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stkj.freeshare.R;
import com.stkj.freeshare.beans.ImgFolderBean;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IExpandable;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.items.IHeader;
import eu.davidea.viewholders.ExpandableViewHolder;

public class ExplandHead extends AbstractFlexibleItem<ExplandHead.MyExplandHeadViewHolder> implements IExpandable<ExplandHead.MyExplandHeadViewHolder, ItemBeanImpl>, IHeader<ExplandHead.MyExplandHeadViewHolder> {
    private List<ItemBeanImpl> mSubItems;
    private ImgFolderBean imgFold;
    public ImgFolderBean getImgFold() {
        return imgFold;
    }

    public void setImgFold(ImgFolderBean imgFold) {
        this.imgFold = imgFold;
    }

    protected String id;
    private boolean mExpanded = false;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public ExplandHead(String id) {
        this.id = id;
        setDraggable(false);
        //We start with header shown and expanded
        setHidden(false);
        setExpanded(true);
        //NOT selectable (otherwise ActionMode will be activated on long click)!
        setSelectable(false);
    }

    public List<ItemBeanImpl> getmSubItems() {
        return mSubItems;
    }

    public void addSubItems(ItemBeanImpl subItems) {
        if (mSubItems == null)
            mSubItems = new ArrayList<>();
        mSubItems.add(subItems);
    }

    @Override
    public boolean equals(Object inObject) {
        if (inObject instanceof ExplandHead) {
            ExplandHead inItem = (ExplandHead) inObject;
            return this.id.equals(inItem.id);
        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_recycle_header;
    }

    @Override
    public MyExplandHeadViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new MyExplandHeadViewHolder(view, adapter);
    }


    @Override
    public void bindViewHolder(final FlexibleAdapter<IFlexible> adapter, final MyExplandHeadViewHolder holder, final int position, List<Object> payloads) {
        if (adapter.getItem(position) != null) {
            holder.tv_folderName.setText(getImgFold().getName());
            holder.tv_folderDesc.setText("共" + getImgFold().getCount() + "张");
            holder.tv_size.setText(getImgFold().getFolderSize()+"");
            holder.mHandleView.setImageResource(getImgFold().isAllSelect() ? R.mipmap.album_ic_done_white : R.mipmap.ic_drag_handle_white_24dp);
//            holder.mHandleView.setImageResource(isAllSelect() ? R.drawable.album_ic_done_white : R.drawable.ic_drag_handle_white_24dp);
        }
    }


    @Override
    public boolean isExpanded() {
        return mExpanded;
    }

    @Override
    public void setExpanded(boolean expanded) {
        mExpanded = expanded;
    }

    @Override
    public int getExpansionLevel() {
        return 0;
    }

    public boolean removeSubItem(ItemBeanImpl item) {
        return item != null && mSubItems.remove(item);
    }

    @Override
    public List<ItemBeanImpl> getSubItems() {
        return mSubItems;
    }

    public final boolean hasSubItems() {
        return mSubItems != null && mSubItems.size() > 0;
    }

    class MyExplandHeadViewHolder extends ExpandableViewHolder {
        TextView tv_folderName;
        TextView tv_folderDesc;
        TextView  tv_size;
        ImageView mHandleView;

        public MyExplandHeadViewHolder(View view, final FlexibleAdapter adapter) {
            super(view, adapter);
            tv_folderName = view.findViewById(R.id.tv_folderName);
            tv_folderDesc = view.findViewById(R.id.tv_folderDesc);
            tv_size = view.findViewById(R.id.tv_size);

            this.mHandleView = view.findViewById(R.id.row_handle);
            this.mHandleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (adapter.mItemLongClickListener != null) {
                        adapter.mItemLongClickListener.onItemLongClick(getAdapterPosition());
                        mHandleView.setImageResource(getImgFold().isAllSelect() ? R.mipmap.album_ic_done_white : R.mipmap.ic_drag_handle_white_24dp);
                    }
                }
            });
            // Support for StaggeredGridLayoutManager
            setFullSpan(true);
        }

        @Override
        protected boolean isViewExpandableOnClick() {
            return true;//default=true
        }

        @Override
        protected boolean isViewCollapsibleOnClick() {
            return true;//default=true
        }

        @Override
        protected boolean isViewCollapsibleOnLongClick() {
            return true;//default=true
        }

        @Override
        protected boolean shouldNotifyParentOnClick() {
            return true;//default=false
        }

        @Override
        protected void toggleExpansion() {
            super.toggleExpansion(); //If overridden, you must call the super method
        }

        @Override
        protected void expandView(int position) {
            super.expandView(position); //If overridden, you must call the super method
            // Let's notify the item has been expanded. Note: from 5.0.0-rc1 the next line becomes
            // obsolete, override the new method shouldNotifyParentOnClick() as showcased here
            //if (mAdapter.isExpanded(position)) mAdapter.notifyItemChanged(position, true);
        }

        @Override
        protected void collapseView(int position) {
            super.collapseView(position); //If overridden, you must call the super method
        }
    }
}
