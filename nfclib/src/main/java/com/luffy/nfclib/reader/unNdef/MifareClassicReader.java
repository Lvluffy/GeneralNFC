package com.luffy.nfclib.reader.unNdef;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.util.Log;

import com.luffy.nfclib.model.MifareClassicBean;
import com.luffy.nfclib.reader.base.BaseNfcReader;
import com.luffy.nfclib.utils.NfcUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvlufei on 2019/7/26
 *
 * @name 非NDEF数据读取——MifareClassic
 * @desc
 */
public class MifareClassicReader extends BaseNfcReader<MifareClassicBean> {

    @Override
    public MifareClassicBean reader(Intent intent) {
        //数据
        MifareClassicBean mMifareClassicBean = new MifareClassicBean();
        List<MifareClassicBean.InfoBean> infoBeanList = new ArrayList<>();
        //标签
        Tag mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //卡片ID
        String cardId = NfcUtils.bytesToHexString(mTag.getId());
        mMifareClassicBean.setCardId(cardId);
        //读取TAG
        MifareClassic mifareClassic = MifareClassic.get(mTag);
        try {
            //链接NFC
            mifareClassic.connect();
            //链接成功
            if (mifareClassic.isConnected()) {
                //TAG的类型
                mMifareClassicBean.setTagType(getMifareClassicType(mifareClassic.getType()));
                //TAG中包含的"扇区数"
                int sectorCount = mifareClassic.getSectorCount();
                mMifareClassicBean.setSector(String.valueOf(sectorCount));
                //TAG中包含的"块数"
                mMifareClassicBean.setBlock(String.valueOf(mifareClassic.getBlockCount()));
                //TAG的"存储空间"
                mMifareClassicBean.setSize(mifareClassic.getSize() + "B");
                for (int sectorPosition = 0; sectorPosition < sectorCount; sectorPosition++) {
                    //这里验证的是密码A，如果想验证密码B也行，将方法中的A换成B就行
                    boolean auth = mifareClassic.authenticateSectorWithKeyA(sectorPosition, MifareClassic.KEY_DEFAULT);
                    if (auth) {
                        //读取扇区中的块
                        int bCount = mifareClassic.getBlockCountInSector(sectorPosition);
                        //读取扇区中块对应芯片存储器的位置
                        int bIndex = mifareClassic.sectorToBlock(sectorPosition);
                        for (int blockPosition = 0; blockPosition < bCount; blockPosition++) {
                            MifareClassicBean.InfoBean mInfoBean = new MifareClassicBean.InfoBean();
                            //循环读取全部数据
                            byte[] data = mifareClassic.readBlock(bIndex + blockPosition);
                            //16进制的字符串
                            String contant16 = NfcUtils.bytesToHexString(data);
                            //10进制的字符串
                            String contant10 = NfcUtils.hex2ToDecimal(contant16);
                            //赋值
                            mInfoBean.setContentHex(contant16);
                            mInfoBean.setContentDecimal(contant10);
                            mInfoBean.setSectorPosition(String.valueOf(sectorPosition));
                            mInfoBean.setBlockPosition(String.valueOf(blockPosition));
                            infoBeanList.add(mInfoBean);
                        }
                    }
                }
            }
        } catch (IOException e) {
            Log.e("content", "MifareClassic-读取失败！");
        } finally {
            if (mifareClassic != null) {
                try {
                    mifareClassic.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    mMifareClassicBean.setInfo(infoBeanList);
                }
            }
            /*打印数据*/
            Log.i("content", "卡片ID：" + mMifareClassicBean.getCardId() + "\n");
            Log.i("content", "卡片类型：" + mMifareClassicBean.getTagType() + "\n");
            Log.i("content", "扇区数：" + mMifareClassicBean.getSector() + "\n");
            Log.i("content", "块数：" + mMifareClassicBean.getBlock() + "\n");
            Log.i("content", "存储空间：" + mMifareClassicBean.getSize() + "\n");
            for (MifareClassicBean.InfoBean infoBean : mMifareClassicBean.getInfo()) {
                Log.i("content", "内容：" + "\n");
                Log.i("content", "Sector：" + infoBean.getSectorPosition() + "\n");
                Log.i("content", "Block：" + infoBean.getBlockPosition() + "\n");
                Log.i("content", "contentHex：" + infoBean.getContentHex() + "\n");
                Log.i("content", "contentDecimal：" + infoBean.getContentDecimal() + "\n");
            }
        }
        return mMifareClassicBean;
    }


    private String getMifareClassicType(int type) {
        if (type == MifareClassic.TYPE_UNKNOWN) {
            return "TYPE_UNKNOWN";
        } else if (type == MifareClassic.TYPE_CLASSIC) {
            return "TYPE_CLASSIC";
        } else if (type == MifareClassic.TYPE_PLUS) {
            return "TYPE_PLUS";
        } else if (type == MifareClassic.TYPE_PRO) {
            return "TYPE_PRO";
        }
        return "TYPE_UNKNOWN";
    }

}
