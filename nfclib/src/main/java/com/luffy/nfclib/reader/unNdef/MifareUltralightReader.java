package com.luffy.nfclib.reader.unNdef;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.util.Log;

import com.luffy.nfclib.model.MifareUltralightBean;
import com.luffy.nfclib.reader.base.BaseNfcReader;
import com.luffy.nfclib.utils.NfcUtils;

import java.io.IOException;

/**
 * Created by lvlufei on 2019/7/26
 *
 * @name 非NDEF数据读取——MifareUltralight
 * @desc
 */
public class MifareUltralightReader extends BaseNfcReader<MifareUltralightBean> {

    @Override
    public MifareUltralightBean reader(Intent intent) {
        //数据
        MifareUltralightBean mMifareUltralightBean = new MifareUltralightBean();
        //标签
        Tag mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //卡片ID
        String cardId = NfcUtils.bytesToHexString(mTag.getId());
        mMifareUltralightBean.setCardId(cardId);
        //读取TAG
        MifareUltralight mifareUltralight = MifareUltralight.get(mTag);
        try {
            //链接NFC
            mifareUltralight.connect();
            //链接成功
            if (mifareUltralight.isConnected()) {
                //TAG的类型
                mMifareUltralightBean.setTagType(getMifareUltralightType(mifareUltralight.getType()));
                //总容量
                mMifareUltralightBean.setMifareUltralightPageSize(String.valueOf(mifareUltralight.PAGE_SIZE));
                //这里只读取了其中几个page
                byte[] page0 = mifareUltralight.readPages(0);
                mMifareUltralightBean.setPage0(NfcUtils.bytesToHexString(page0));
                byte[] page4 = mifareUltralight.readPages(4);
                mMifareUltralightBean.setPage4(NfcUtils.bytesToHexString(page4));
                byte[] page8 = mifareUltralight.readPages(8);
                mMifareUltralightBean.setPage8(NfcUtils.bytesToHexString(page8));
                byte[] page12 = mifareUltralight.readPages(12);
                mMifareUltralightBean.setPage12(NfcUtils.bytesToHexString(page12));
            }
        } catch (IOException e) {
            Log.e("content", "MifareUltralight-读取失败！");
        } finally {
            if (mifareUltralight != null) {
                try {
                    mifareUltralight.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {

                }
            }
            /*打印数据*/
            Log.i("content", "卡片ID：" + mMifareUltralightBean.getCardId() + "\n");
            Log.i("content", "卡片类型：" + mMifareUltralightBean.getTagType() + "\n");
            Log.i("content", "总容量：" + mMifareUltralightBean.getMifareUltralightPageSize() + "\n");
            Log.i("content", "page0：" + mMifareUltralightBean.getPage0() + "\n");
            Log.i("content", "page4：" + mMifareUltralightBean.getPage4() + "\n");
            Log.i("content", "page8：" + mMifareUltralightBean.getPage8() + "\n");
            Log.i("content", "page12：" + mMifareUltralightBean.getPage12() + "\n");
        }
        return mMifareUltralightBean;
    }

    private static String getMifareUltralightType(int type) {
        if (type == MifareUltralight.TYPE_UNKNOWN) {
            return "TYPE_UNKNOWN";
        } else if (type == MifareUltralight.TYPE_ULTRALIGHT) {
            return "TYPE_ULTRALIGHT";
        } else if (type == MifareUltralight.TYPE_ULTRALIGHT_C) {
            return "TYPE_ULTRALIGHT_C";
        }
        return "TYPE_UNKNOWN";
    }
}
