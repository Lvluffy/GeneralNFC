package com.luffy.nfclib.reader.unNdef;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.util.Log;

import com.luffy.nfclib.model.NfcVBean;
import com.luffy.nfclib.reader.base.BaseNfcReader;
import com.luffy.nfclib.utils.NfcUtils;

import java.io.IOException;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name 非NDEF数据读取——NfcV
 * @desc
 */
public class NfcVReader extends BaseNfcReader<NfcVBean> {
    @Override
    public NfcVBean reader(Intent intent) {
        //数据
        NfcVBean mNfcVBean = new NfcVBean();
        //标签
        Tag mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //卡片ID
        String cardId = NfcUtils.bytesToHexString(mTag.getId());
        mNfcVBean.setCardId(cardId);
        //读取TAG
        NfcV mNfcV = NfcV.get(mTag);
        try {
            //链接NFC
            mNfcV.connect();
            //链接成功
            if (mNfcV.isConnected()) {
                //最大字节数
                mNfcVBean.setMaxTransceiveLength(String.valueOf(mNfcV.getMaxTransceiveLength()));
                //dsf
                mNfcVBean.setDsfId(NfcUtils.bytesToHexString(new byte[]{mNfcV.getResponseFlags()}));
                //响应标记
                mNfcVBean.setResponseFlags(NfcUtils.bytesToHexString(new byte[]{mNfcV.getResponseFlags()}));
            }
        } catch (IOException e) {
            Log.e("content", "NfcV-读取失败！");
        } finally {
            if (mNfcV != null) {
                try {
                    mNfcV.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /*打印数据*/
            Log.i("content", "卡片ID：" + mNfcVBean.getCardId() + "\n");
            Log.i("content", "最大字节数：" + mNfcVBean.getMaxTransceiveLength() + "\n");
            Log.i("content", "dsf：" + mNfcVBean.getDsfId() + "\n");
            Log.i("content", "响应标记：" + mNfcVBean.getResponseFlags() + "\n");
        }
        return mNfcVBean;
    }
}
