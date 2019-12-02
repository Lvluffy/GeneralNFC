package com.luffy.nfclib.reader.base;

import android.content.Intent;

/**
 * Created by lvlufei on 2019/7/26
 *
 * @name 通用的nfc读取器
 * @desc
 */
public abstract class BaseNfcReader<D> {

    /**
     * 读取
     */
    public abstract D reader(Intent intent);
}
