package com.stkj.freeshare.entitys;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.stkj.freeshare.R;
import com.stkj.freeshare.utils.Utils;
import com.stkj.freeshare.beans.ImgFolderBean;
import com.stkj.freeshare.beans.ImgItem;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.helpers.AnimatorHelper;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * 具体item实体类
 */
public class ItemBeanImpl extends AbsIteamBean<ItemBeanImpl.ChildViewHolder> implements IFilterable<String> {
    private ImgFolderBean imgFolder;
    private ImgItem imgItem;

    public ImgFolderBean getImgFolder() {
        return imgFolder;
    }

    public ImgItem getImgItem() {
        return imgItem;
    }

    public void setImgItem(ImgItem imgItem) {
        this.imgItem = imgItem;
    }

    public void setImgFolder(ImgFolderBean imgFolder) {
        this.imgFolder = imgFolder;
    }

    public ItemBeanImpl(String id) {
        super(id);
        setDraggable(false);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.recycler_stub_item;
    }

    @Override
    public ChildViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {

        return new ChildViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, ChildViewHolder holder, int position, List<Object> payloads) {

//            String payload = (String) payloads.get(0);
//            Log.d("payload", payload);
        holder.iv_selected.setVisibility(getImgItem().isSelect() ? View.VISIBLE : View.GONE);
//        holder.img_item.setImageURI(Uri.parse(getImgItem().getImgPath()));
        Glide.with(holder.img_item.getContext()).load(getImgItem().getImgPath()).placeholder(R.drawable.ic_launcher_background).into(holder.img_item);


    }

    @Override
    public boolean filter(String constraint) {
        return false;
    }

    static class ChildViewHolder extends FlexibleViewHolder {
        public ImageView img_item, iv_selected;

        public ChildViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            this.img_item = view.findViewById(R.id.img_item);
            this.iv_selected = view.findViewById(R.id.iv_selected);
            int width = Utils.getDisplayMetrics(img_item.getContext()).widthPixels / 3-2;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,width);
            this.img_item .setLayoutParams(params);
        }

        @Override
        public float getActivationElevation() {
            return Utils.dpToPx(itemView.getContext(), 4f);
        }

        @Override
        public void scrollAnimators(@NonNull List<Animator> animators, int position, boolean isForward) {
            AnimatorHelper.scaleAnimator(animators, itemView, 0f);
        }
    }
}
