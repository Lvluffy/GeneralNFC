package com.luffy.nfclib.reader.ndef;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.util.Log;

import com.luffy.nfclib.model.NdefFormatableBean;
import com.luffy.nfclib.reader.base.BaseNfcReader;
import com.luffy.nfclib.utils.NfcUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name NDEF数据读取——NdefFormatable
 * @desc
 */
public class NdefFormatableReader extends BaseNfcReader<NdefFormatableBean> {
    @Override
    public NdefFormatableBean reader(Intent intent) {
        //数据
        NdefFormatableBean mNdefFormatableBean = new NdefFormatableBean();
        List<String> payLoadList = new ArrayList<>();
        //标签
        Tag mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //卡片ID
        String cardId = NfcUtils.bytesToHexString(mTag.getId());
        mNdefFormatableBean.setCardId(cardId);
        //读取TAG
        NdefFormatable mNdefFormatable = NdefFormatable.get(mTag);
        try {
            //链接NFC
            mNdefFormatable.connect();
            //链接成功
            if (mNdefFormatable.isConnected()) {
                //NdefFormatable消息数组
                Parcelable[] ndefMessageArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                if (ndefMessageArray != null && ndefMessageArray.length > 0) {
                    for (int i = 0; i < ndefMessageArray.length; i++) {
                        //Ndef消息
                        NdefMessage mNdefMessage = (NdefMessage) ndefMessageArray[i];
                        //Ndef记录数组
                        NdefRecord[] mNdefRecordArray = mNdefMessage.getRecords();
                        for (int j = 0; j < mNdefRecordArray.length; j++) {
                            NdefRecord mNdefRecord = mNdefRecordArray[j];
                            if (mNdefRecord != null) {
                                payLoadList.add(NfcUtils.bytesToHexString(mNdefRecord.getPayload()));
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("content", "NdefFormatable-读取失败！");
        } finally {
            if (mNdefFormatable != null) {
                try {
                    mNdefFormatable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    mNdefFormatableBean.setPayLoad(payLoadList);
                }
            }
            /*打印数据*/
            Log.i("content", "卡片ID：" + mNdefFormatableBean.getCardId() + "\n");
            for (String payload : mNdefFormatableBean.getPayLoad()) {
                Log.i("content", "payload：" + payload + "\n");
            }
        }
        return mNdefFormatableBean;
    }
}
