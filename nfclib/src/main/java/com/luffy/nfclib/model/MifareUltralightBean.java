package com.luffy.nfclib.model;

import java.io.Serializable;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name
 * @desc
 */
public class MifareUltralightBean implements Serializable {
    private String cardId;//卡片ID
    private String tagType;//卡片类型
    private String mifareUltralightPageSize;//总容量
    private String page0;
    private String page4;
    private String page8;
    private String page12;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getMifareUltralightPageSize() {
        return mifareUltralightPageSize;
    }

    public void setMifareUltralightPageSize(String mifareUltralightPageSize) {
        this.mifareUltralightPageSize = mifareUltralightPageSize;
    }

    public String getPage0() {
        return page0;
    }

    public void setPage0(String page0) {
        this.page0 = page0;
    }

    public String getPage4() {
        return page4;
    }

    public void setPage4(String page4) {
        this.page4 = page4;
    }

    public String getPage8() {
        return page8;
    }

    public void setPage8(String page8) {
        this.page8 = page8;
    }

    public String getPage12() {
        return page12;
    }

    public void setPage12(String page12) {
        this.page12 = page12;
    }
}
