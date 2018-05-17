package com.stkj.freeshare.beans;

import java.io.Serializable;

/**
 * 文件夹
 */
public class ImgFolderBean implements Serializable{
    /**当前文件夹的路径*/
    private String dir;
    /**第一张图片的路径*/
    private String fistImgPath;
    /**文件夹名*/
    private String name;
    /**文件夹中图片的数量*/
    private int count;
    private int selectCount;

    private boolean isAllSelect;
    private String folderSize;

    public String getFolderSize() {
        return folderSize;
    }

    public void setFolderSize(String folderSize) {
        this.folderSize = folderSize;
    }

    public boolean isAllSelect() {
        return isAllSelect;
    }

    public void setAllSelect(boolean allSelect) {
        isAllSelect = allSelect;
    }

    public int getSelectCount() {
        return selectCount;
    }
    public void addSelectCount() {
        selectCount++;
        if (selectCount > getCount()) {
            selectCount = getCount();
        }
    }

    public void reduceSelectCount() {
        selectCount--;
        if (selectCount < 0) {
            selectCount = 0;
        }
    }
    public void resetSelectCount() {
        selectCount=0;
    }
    public void selectAllSelectCount() {
        selectCount=getCount();
    }
    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndex = dir.lastIndexOf("/");
        this.name = dir.substring(lastIndex + 1);
    }

    public String getFistImgPath() {
        return fistImgPath;
    }

    public void setFistImgPath(String fistImgPath) {
        this.fistImgPath = fistImgPath;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ImgFolderBean() {
    }

    public ImgFolderBean(String dir, String fistImgPath, String name, int count) {
        this.dir = dir;
        this.fistImgPath = fistImgPath;
        this.name = name;
        this.count = count;
    }

    @Override
    public String toString() {
        return "ImgFolderBean{" +
                "dir='" + dir + '\'' +
                ", fistImgPath='" + fistImgPath + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
