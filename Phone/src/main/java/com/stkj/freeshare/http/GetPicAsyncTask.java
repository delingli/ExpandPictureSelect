package com.stkj.freeshare.http;

import android.content.Context;
import android.os.AsyncTask;

import com.stkj.freeshare.entitys.ItemBeanImpl;
import com.stkj.freeshare.entitys.ExplandHead;
import com.stkj.freeshare.beans.ImgFolderBean;
import com.stkj.freeshare.beans.ImgItem;
import com.stkj.freeshare.utils.FileManager;
import com.stkj.freeshare.utils.FileSizeUtil;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * 执行查询本地文件操作
 */
public class GetPicAsyncTask extends AsyncTask<Void, Void, List<AbstractFlexibleItem>> {
    private Context context;
    private List<AbstractFlexibleItem> mDadaSource = new ArrayList<AbstractFlexibleItem>();
    public GetPicAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<AbstractFlexibleItem> doInBackground(Void... uris) {
        FileManager fileManager = FileManager.getInstance(context);

        final List<ImgFolderBean> imageFolders = fileManager.getImageFolders();
        for (int i = 0; i < imageFolders.size(); ++i) {
            mDadaSource.add(newExpandableSectionItem(i + 1, imageFolders.get(i)));
        }
        return mDadaSource;
    }
    @Override
    protected void onPostExecute(List<AbstractFlexibleItem> abstractFlexibleItems) {
        if(listener!=null){
            listener.taskSucessFul(abstractFlexibleItems);
        }
        super.onPostExecute(abstractFlexibleItems);
    }

    public ExplandHead newExpandableSectionItem(int i, ImgFolderBean folder) {
        ExplandHead expandableItem = new ExplandHead(i + "EH");
        String filesSize = FileSizeUtil.getAutoFileOrFilesSize(folder.getDir());
        folder.setFolderSize(filesSize);
        expandableItem.setImgFold(folder);
//        expandableItem.setTitle("Expandable Header " + i);
        List<String> picPath = FileManager.getInstance(context).getImgListByDir(folder.getDir());
        for (int j = 0; j < picPath.size(); j++) {
            ItemBeanImpl subItem = new ItemBeanImpl(expandableItem.getId() + "-" + j);
            ImgItem imgItem=new ImgItem(picPath.get(j),false);
            subItem.setImgItem(imgItem);
            expandableItem.addSubItems(subItem);
            subItem.setImgFolder(folder);
        }
        return expandableItem;
    }

    private OnTaskListener listener;

    public void setOnTaskListener(OnTaskListener listener) {
        this.listener = listener;
    }

    public interface OnTaskListener {
        void taskSucessFul(List<AbstractFlexibleItem> abstractFlexibleItems);
    }
}
