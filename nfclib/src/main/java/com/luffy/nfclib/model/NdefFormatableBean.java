package com.luffy.nfclib.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name
 * @desc
 */
public class NdefFormatableBean implements Serializable {
    private String cardId;//卡片ID
    private List<String> payLoad;//支付负荷

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public List<String> getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(List<String> payLoad) {
        this.payLoad = payLoad;
    }
}
