package com.luffy.nfclib.reader.unNdef;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcB;
import android.util.Log;

import com.luffy.nfclib.model.NfcBBean;
import com.luffy.nfclib.reader.base.BaseNfcReader;
import com.luffy.nfclib.utils.NfcUtils;

import java.io.IOException;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name 非NDEF数据读取——NfcB
 * @desc
 */
public class NfcBReader extends BaseNfcReader<NfcBBean> {
    @Override
    public NfcBBean reader(Intent intent) {
        //数据
        NfcBBean mNfcBBean = new NfcBBean();
        //标签
        Tag mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //卡片ID
        String cardId = NfcUtils.bytesToHexString(mTag.getId());
        mNfcBBean.setCardId(cardId);
        //读取TAG
        NfcB mNfcB = NfcB.get(mTag);
        try {
            //链接NFC
            mNfcB.connect();
            //链接成功
            if (mNfcB.isConnected()) {
                //协议信息
                mNfcBBean.setProtocolInfo(NfcUtils.bytesToHexString(mNfcB.getProtocolInfo()));
            }
        } catch (IOException e) {
            Log.e("content","NfcB-读取失败！");
        } finally {
            if (mNfcB != null) {
                try {
                    mNfcB.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /*打印数据*/
            Log.i("content", "卡片ID：" + mNfcBBean.getCardId() + "\n");
            Log.i("content", "协议信息：" + mNfcBBean.getProtocolInfo() + "\n");
        }
        return mNfcBBean;
    }
}
