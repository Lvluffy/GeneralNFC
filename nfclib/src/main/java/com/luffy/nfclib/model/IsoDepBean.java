package com.luffy.nfclib.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name
 * @desc
 */
public class IsoDepBean implements Serializable {
    private String cardId;//卡片ID
    private String balance;//余额
    private List<String> record;//记录

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public List<String> getRecord() {
        return record;
    }

    public void setRecord(List<String> record) {
        this.record = record;
    }
}
