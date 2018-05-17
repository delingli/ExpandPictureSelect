package com.stkj.freeshare.fragments;

import android.Manifest;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stkj.freeshare.R;
import com.stkj.freeshare.entitys.ItemBeanImpl;
import com.stkj.freeshare.entitys.ExplandHead;
import com.stkj.freeshare.entitys.ExpandPicAdapter;
import com.stkj.freeshare.viewmodel.PicViewModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.FlexibleAdapter.OnItemClickListener;
import eu.davidea.flexibleadapter.common.FlexibleItemDecoration;
import eu.davidea.flexibleadapter.common.SmoothScrollGridLayoutManager;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SelectionPictureFragment extends Fragment implements OnItemClickListener, FlexibleAdapter.OnItemLongClickListener, EasyPermissions.PermissionCallbacks {
    private Map<String, AbstractFlexibleItem> mSelectedData;
    private RecyclerView mRecyclerView;
    private int mColumnCount = 3;
    private ExpandPicAdapter mAdapter;
    private Button btn_select;
    private LinearLayout ll_botton;
    private PicViewModel picViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycleview, container, false);
    }

    public static SelectionPictureFragment newInstance() {
        SelectionPictureFragment fragment = new SelectionPictureFragment();
        return fragment;
    }

    String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView = getView().findViewById(R.id.recycler_view);
        ll_botton = getView().findViewById(R.id.ll_botton);
        btn_select = getView().findViewById(R.id.btn_select);
        Button btn_send = getView().findViewById(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"发送数据",Toast.LENGTH_LONG).show();
                ll_botton.setVisibility(View.GONE);
            }
        });
        init();
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            initData();
        } else {
            EasyPermissions.requestPermissions(this, "申请必要权限",
                    1, perms);
        }

    }

    private void initData() {
        picViewModel = ViewModelProviders.of(SelectionPictureFragment.this).get(PicViewModel.class);
        picViewModel.getmPicDataSource().observe(this, new Observer<List<AbstractFlexibleItem>>() {
            @Override
            public void onChanged(@Nullable List<AbstractFlexibleItem> abstractFlexibleItems) {
                mAdapter.updateDataSet(abstractFlexibleItems);
            }
        });
        picViewModel.getDiskPicture();
    }




    private void init() {
        FlexibleAdapter.useTag("ExpandableSectionAdapter");
        // OnItemAdd and OnItemRemove listeners
        mSelectedData = new HashMap<>();
        mAdapter = new ExpandPicAdapter(null, this);
        mAdapter.addListener(this);
        mAdapter.expandItemsAtStartUp()
                .setAutoCollapseOnExpand(false)
                .setAutoScrollOnExpand(true)
                .setAnimateToLimit(Integer.MAX_VALUE) //Size limit = MAX_VALUE will always animate the changes
                .setNotifyMoveOfFilteredItems(true) //When true, filtering on big list is very slow!
                .setAnimationOnForwardScrolling(false)
                .setAnimationOnReverseScrolling(false);
        mRecyclerView = getView().findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(createNewGridLayoutManager());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new FlexibleItemDecoration(getActivity())
                .addItemViewType(R.layout.recycler_expandable_header_item)
                .withOffset(2));
//        mRecyclerView.setHasFixedSize(true); //Size of RV will not change
        // NOTE: Use default item animator 'canReuseUpdatedViewHolder()' will return true if
        // a Payload is provided. FlexibleAdapter is actually sending Payloads onItemChange.
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        // Custom divider item decorator
//        mRecyclerView.addItemDecoration(new FlexibleItemDecoration(getActivity())
//                .addItemViewType(R.layout.recycler_expandable_header_item)
//                .withOffset(4));
//        // New empty views handling, to set after FastScroller
//        mAdapter.setLongPressDragEnabled(true) //Enable long press to drag items
//                .setHandleDragEnabled(true) //Enable handle drag
//                //.setDisplayHeadersAtStartUp(true); //Show Headers at startUp: (not necessary if Headers are also Expandable AND expanded at startup)
//                .setStickyHeaders(true);
    }

    /**
     * 创建自定义布局管理器
     */
    protected GridLayoutManager createNewGridLayoutManager() {
        GridLayoutManager gridLayoutManager = new SmoothScrollGridLayoutManager(getActivity(), mColumnCount);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // NOTE: If you use simple integers to identify the ViewType,
                // here, you should use them and not Layout integers
                switch (mAdapter.getItemViewType(position)) {
                    case R.layout.layout_recycle_header:
                        return mColumnCount;
                    default:
                        return 1;
                }
            }
        });
        return gridLayoutManager;
    }


    @Override
    public boolean onItemClick(View view, int position) {
        IFlexible item = mAdapter.getItem(position);
        int itemCount = mAdapter.getItemCount();
        if (item != null && item instanceof ItemBeanImpl) {
            ItemBeanImpl it = (ItemBeanImpl) item;
            if(it.getImgItem().isSelect()){
                it.getImgFolder().reduceSelectCount();
            }else{
                it.getImgFolder().addSelectCount();
            }
            it.getImgItem().setSelect(!it.getImgItem().isSelect());

            if(it.getImgFolder().getCount()==it.getImgFolder().getSelectCount()){
                it.getImgFolder().setAllSelect(!it.getImgFolder().isAllSelect());
            }else {
               it.getImgFolder().setAllSelect(false);
            }
            checkVisibility();
            mAdapter.notifyDataSetChanged();
        }
        return false;
    }

    @Override
    public void onItemLongClick(int position) {
        AbstractFlexibleItem item = mAdapter.getItem(position);
       if (item instanceof ExplandHead) {
            ExplandHead selectAllItem = (ExplandHead) item;
            List<ItemBeanImpl> itemBeans = selectAllItem.getmSubItems();
            for (ItemBeanImpl it : itemBeans) {
                if(selectAllItem.getImgFold().isAllSelect()){
                    it.getImgFolder().resetSelectCount();
                }else{
                    it.getImgFolder().selectAllSelectCount();
                }
                it.getImgItem().setSelect(!selectAllItem.getImgFold().isAllSelect());
            }
           checkVisibility();
            selectAllItem.getImgFold().setAllSelect(!selectAllItem.getImgFold().isAllSelect());
            mAdapter.notifyDataSetChanged();
        }
    }
private void checkVisibility(){
    int selectedCount = picViewModel.getSelectedCount();
    if(selectedCount>0){
        if(ll_botton.getVisibility()== View.GONE){
            ll_botton.setVisibility(View.VISIBLE);
        }
        btn_select.setText("已选"+selectedCount);
    }else if(selectedCount<=0){
        ll_botton.setVisibility(View.GONE);
    }
}
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
//        Toa。.showToast(getApplicationContext(), "用户授权成功");
        Toast.makeText(getActivity(), "用户授权成功", Toast.LENGTH_LONG).show();
        initData();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(getActivity(), "用户授权失败", Toast.LENGTH_LONG).show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
