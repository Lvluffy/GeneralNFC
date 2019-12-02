package com.luffy.nfclib.model;

import java.io.Serializable;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name
 * @desc
 */
public class NfcBBean implements Serializable {
    private String cardId;//卡片ID
    private String protocolInfo;//协议信息

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getProtocolInfo() {
        return protocolInfo;
    }

    public void setProtocolInfo(String protocolInfo) {
        this.protocolInfo = protocolInfo;
    }
}
