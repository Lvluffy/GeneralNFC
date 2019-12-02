package com.luffy.nfclib.model;

import java.io.Serializable;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name
 * @desc
 */
public class NfcFBean implements Serializable {
    private String cardId;//卡片ID
    private String timeout;//超时
    private String maxTransceiveLength;//最大字节数
    private String manufacturer;//制造商
    private String systemCode;//系统代码

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getMaxTransceiveLength() {
        return maxTransceiveLength;
    }

    public void setMaxTransceiveLength(String maxTransceiveLength) {
        this.maxTransceiveLength = maxTransceiveLength;
    }
}
