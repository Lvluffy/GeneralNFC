package com.luffy.nfclib.reader.ndef;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.util.Log;

import com.luffy.nfclib.model.NdefBean;
import com.luffy.nfclib.reader.base.BaseNfcReader;
import com.luffy.nfclib.utils.NfcUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name NDEF数据读取——Ndef
 * @desc
 */
public class NdefReader extends BaseNfcReader<NdefBean> {
    @Override
    public NdefBean reader(Intent intent) {
        //数据
        NdefBean mNdefBean = new NdefBean();
        List<String> payLoadList = new ArrayList<>();
        //标签
        Tag mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //卡片ID
        String cardId = NfcUtils.bytesToHexString(mTag.getId());
        mNdefBean.setCardId(cardId);
        //读取TAG
        Ndef mNdef = Ndef.get(mTag);
        try {
            //链接NFC
            mNdef.connect();
            //链接成功
            if (mNdef.isConnected()) {
                //最大字节
                mNdefBean.setMaxSize(String.valueOf(mNdef.getMaxSize()));
                //卡片类型
                mNdefBean.setTagType(mNdef.getType());
                //Ndef消息数组
                Parcelable[] ndefMessageArray = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                if (ndefMessageArray != null && ndefMessageArray.length > 0) {
                    for (Parcelable aNdefMessageArray : ndefMessageArray) {
                        //Ndef消息
                        NdefMessage mNdefMessage = (NdefMessage) aNdefMessageArray;
                        //Ndef记录数组
                        NdefRecord[] mNdefRecordArray = mNdefMessage.getRecords();
                        for (NdefRecord mNdefRecord : mNdefRecordArray) {
                            if (mNdefRecord != null) {
                                payLoadList.add(NfcUtils.bytesToHexString(mNdefRecord.getPayload()));
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("content", "Ndef-读取失败！");
        } finally {
            if (mNdef != null) {
                try {
                    mNdef.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    mNdefBean.setPayLoad(payLoadList);
                }
            }
            /*打印数据*/
            Log.i("content", "卡片ID：" + mNdefBean.getCardId() + "\n");
            Log.i("content", "卡片类型：" + mNdefBean.getTagType() + "\n");
            Log.i("content", "最大字节：" + mNdefBean.getMaxSize() + "\n");
            for (String payload : mNdefBean.getPayLoad()) {
                Log.i("content", "payload：" + payload + "\n");
            }
        }
        return mNdefBean;
    }
}
