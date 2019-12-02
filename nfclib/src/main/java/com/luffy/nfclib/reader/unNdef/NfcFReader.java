package com.luffy.nfclib.reader.unNdef;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcF;
import android.util.Log;

import com.luffy.nfclib.model.NfcFBean;
import com.luffy.nfclib.reader.base.BaseNfcReader;
import com.luffy.nfclib.utils.NfcUtils;

import java.io.IOException;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name 非NDEF数据读取——NfcF
 * @desc
 */
public class NfcFReader extends BaseNfcReader<NfcFBean> {
    @Override
    public NfcFBean reader(Intent intent) {
        //数据
        NfcFBean mNfcFBean = new NfcFBean();
        //标签
        Tag mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //卡片ID
        String cardId = NfcUtils.bytesToHexString(mTag.getId());
        mNfcFBean.setCardId(cardId);
        //读取TAG
        NfcF mNfcf = NfcF.get(mTag);
        try {
            //链接NFC
            mNfcf.connect();
            //链接成功
            if (mNfcf.isConnected()) {
                //超时
                mNfcFBean.setTimeout(String.valueOf(mNfcf.getTimeout()));
                //最大字节数
                mNfcFBean.setMaxTransceiveLength(String.valueOf(mNfcf.getMaxTransceiveLength()));
                //制造商
                mNfcFBean.setManufacturer(NfcUtils.bytesToHexString(mNfcf.getManufacturer()));
                //系统代码
                mNfcFBean.setSystemCode(NfcUtils.bytesToHexString(mNfcf.getSystemCode()));
            }
        } catch (IOException e) {
            Log.e("content","NfcF-读取失败！");
        } finally {
            if (mNfcf != null) {
                try {
                    mNfcf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /*打印数据*/
            Log.i("content", "卡片ID：" + mNfcFBean.getCardId() + "\n");
            Log.i("content", "超时：" + mNfcFBean.getTimeout() + "\n");
            Log.i("content", "最大字节数：" + mNfcFBean.getMaxTransceiveLength() + "\n");
            Log.i("content", "制造商：" + mNfcFBean.getManufacturer() + "\n");
            Log.i("content", "系统代码：" + mNfcFBean.getSystemCode() + "\n");
        }
        return mNfcFBean;
    }
}
