package com.luffy.nfclib.model;

import java.io.Serializable;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name
 * @desc
 */
public class NfcVBean implements Serializable {
    private String cardId;//卡片ID
    private String maxTransceiveLength;//最大字节数
    private String dsfId;//dsf
    private String responseFlags;//响应标记

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getMaxTransceiveLength() {
        return maxTransceiveLength;
    }

    public void setMaxTransceiveLength(String maxTransceiveLength) {
        this.maxTransceiveLength = maxTransceiveLength;
    }

    public String getDsfId() {
        return dsfId;
    }

    public void setDsfId(String dsfId) {
        this.dsfId = dsfId;
    }

    public String getResponseFlags() {
        return responseFlags;
    }

    public void setResponseFlags(String responseFlags) {
        this.responseFlags = responseFlags;
    }
}
