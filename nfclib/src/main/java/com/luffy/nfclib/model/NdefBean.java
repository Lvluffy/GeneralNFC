package com.luffy.nfclib.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name
 * @desc
 */
public class NdefBean implements Serializable {
    private String cardId;//卡片ID
    private String maxSize;//最大字节
    private String tagType;//卡片类型
    private List<String> payLoad;//支付负荷

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public List<String> getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(List<String> payLoad) {
        this.payLoad = payLoad;
    }
}
