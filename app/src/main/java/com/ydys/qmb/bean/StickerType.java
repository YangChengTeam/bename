package com.ydys.qmb.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by myflying on 2019/4/15.
 */
public class StickerType {

    @SerializedName("type_id")
    private String typeId;

    @SerializedName("type_name")
    private String typeName;

    @SerializedName("is_show")
    private int isShow; //0显示，1不显示 (默认0)

    private boolean isSelected;

    public StickerType(String name){
        this.typeName = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
