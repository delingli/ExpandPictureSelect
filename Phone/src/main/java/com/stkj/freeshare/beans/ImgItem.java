package com.stkj.freeshare.beans;

public class ImgItem {
    /**
     * 是否被选中
     */
    private boolean isSelect;
    /**
     * 图片路径
     */
    private String imgPath;

    public ImgItem(String imgPath,boolean isSelect) {
        this.imgPath = imgPath;
        this.isSelect=isSelect;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
