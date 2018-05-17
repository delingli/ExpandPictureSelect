package com.stkj.freeshare.beans;

/**
 * 文件夹
 */
public class ImageFloder {
    private String floderPath;
    private String firstImgPath;
    /**
     * 文件夹名
     */
    private String name;
    /**
     * 图数量
     */
    private int picCount;

    public String getFloderPath() {
        return floderPath;
    }

    public void setFloderPath(String floderPath) {
        this.floderPath = floderPath;
        int lastIndexOf = this.floderPath.lastIndexOf("/");
        this.name = this.floderPath.substring(lastIndexOf);
    }

    public String getFirstImgPath() {
        return firstImgPath;
    }

    public void setFirstImgPath(String firstImgPath) {
        this.firstImgPath = firstImgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicCount() {
        return picCount;
    }

    public void setPicCount(int picCount) {
        this.picCount = picCount;
    }
}
