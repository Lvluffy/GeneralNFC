package com.luffy.nfclib.model;

import java.io.Serializable;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name
 * @desc
 */
public class NfcABean implements Serializable {
    private String cardId;//卡片ID
    private String timeout;//超时
    private String atqa;//响应

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getAtqa() {
        return atqa;
    }

    public void setAtqa(String atqa) {
        this.atqa = atqa;
    }
}
