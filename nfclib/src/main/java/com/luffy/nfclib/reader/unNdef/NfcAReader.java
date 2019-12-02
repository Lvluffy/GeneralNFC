package com.luffy.nfclib.reader.unNdef;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.util.Log;

import com.luffy.nfclib.model.NfcABean;
import com.luffy.nfclib.reader.base.BaseNfcReader;
import com.luffy.nfclib.utils.NfcUtils;

import java.io.IOException;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name 非NDEF数据读取——NfcA
 * @desc
 */
public class NfcAReader extends BaseNfcReader<NfcABean> {
    @Override
    public NfcABean reader(Intent intent) {
        //数据
        NfcABean mNfcABean = new NfcABean();
        //标签
        Tag mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //卡片ID
        String cardId = NfcUtils.bytesToHexString(mTag.getId());
        mNfcABean.setCardId(cardId);
        //读取TAG
        NfcA mNfcA = NfcA.get(mTag);
        try {
            //链接NFC
            mNfcA.connect();
            //链接成功
            if (mNfcA.isConnected()) {
                //超时
                mNfcABean.setTimeout(String.valueOf(mNfcA.getTimeout()));
                //响应
                mNfcABean.setAtqa(NfcUtils.bytesToHexString(mNfcA.getAtqa()));
            }
        } catch (IOException e) {
            Log.e("content", "NfcA-读取失败！");
        } finally {
            if (mNfcA != null) {
                try {
                    mNfcA.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /*打印数据*/
            Log.i("content", "卡片ID：" + mNfcABean.getCardId() + "\n");
            Log.i("content", "超时：" + mNfcABean.getTimeout() + "\n");
            Log.i("content", "响应：" + mNfcABean.getAtqa() + "\n");
        }
        return mNfcABean;
    }
}
