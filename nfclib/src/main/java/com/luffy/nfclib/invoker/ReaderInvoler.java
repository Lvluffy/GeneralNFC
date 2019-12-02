package com.luffy.nfclib.invoker;

import android.content.Intent;
import android.nfc.Tag;


import com.luffy.nfclib.model.IsoDepBean;
import com.luffy.nfclib.model.MifareClassicBean;
import com.luffy.nfclib.model.MifareUltralightBean;
import com.luffy.nfclib.model.NdefBean;
import com.luffy.nfclib.model.NdefFormatableBean;
import com.luffy.nfclib.model.NfcABean;
import com.luffy.nfclib.model.NfcBBean;
import com.luffy.nfclib.model.NfcFBean;
import com.luffy.nfclib.model.NfcVBean;
import com.luffy.nfclib.reader.ndef.NdefFormatableReader;
import com.luffy.nfclib.reader.ndef.NdefReader;
import com.luffy.nfclib.reader.unNdef.IsoDepReader;
import com.luffy.nfclib.reader.unNdef.MifareClassicReader;
import com.luffy.nfclib.reader.unNdef.MifareUltralightReader;
import com.luffy.nfclib.reader.unNdef.NfcAReader;
import com.luffy.nfclib.reader.unNdef.NfcBReader;
import com.luffy.nfclib.reader.unNdef.NfcFReader;
import com.luffy.nfclib.reader.unNdef.NfcVReader;
import com.luffy.nfclib.tech.TagTech;
import com.luffy.nfclib.utils.NfcUtils;

import java.text.SimpleDateFormat;

/**
 * Created by lvlufei on 2019/7/26
 *
 * @name 读取器调用者
 * @desc
 */
public class ReaderInvoler {

    public static String readTag(Tag tag, Intent intent) {
        if (tag != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ReadTime:").append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis())).append("\n")
                    .append("ID-Hex:").append(NfcUtils.bytesToHexString(tag.getId())).append("\n")
                    .append("Technologies:").append(tag.toString()).append("\n");
            String[] tagTechList = tag.getTechList();
            if (tagTechList != null) {
                for (int i = 0; i < tagTechList.length; i++) {
                    stringBuilder.append("*").append(tagTechList[i]).append("*").append("\n");
                    stringBuilder.append(readTech(tagTechList[i], intent));
                }
            }
            return stringBuilder.toString();
        }
        return null;
    }

    private static String readTech(String curTagTech, Intent intent) {
        StringBuilder stringBuilder = new StringBuilder();
        if (TagTech.TECHS[0].equals(curTagTech)) {
            /*获取数据*/
            NfcABean mNfcABean = new NfcAReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：" + mNfcABean.getCardId() + "\n");
            stringBuilder.append("超时：" + mNfcABean.getTimeout() + "\n");
            stringBuilder.append("响应：" + mNfcABean.getAtqa() + "\n");
            return stringBuilder.toString();
        } else if (TagTech.TECHS[1].equals(curTagTech)) {
            /*获取数据*/
            NfcBBean mNfcBBean = new NfcBReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：" + mNfcBBean.getCardId() + "\n");
            stringBuilder.append("协议信息：" + mNfcBBean.getProtocolInfo() + "\n");
            return stringBuilder.toString();
        } else if (TagTech.TECHS[2].equals(curTagTech)) {
            /*获取数据*/
            NfcFBean mNfcFBean = new NfcFReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：" + mNfcFBean.getCardId() + "\n");
            stringBuilder.append("超时：" + mNfcFBean.getTimeout() + "\n");
            stringBuilder.append("最大字节数：" + mNfcFBean.getMaxTransceiveLength() + "\n");
            stringBuilder.append("制造商：" + mNfcFBean.getManufacturer() + "\n");
            stringBuilder.append("系统代码：" + mNfcFBean.getSystemCode() + "\n");
            return stringBuilder.toString();
        } else if (TagTech.TECHS[3].equals(curTagTech)) {
            /*获取数据*/
            NfcVBean mNfcVBean = new NfcVReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：" + mNfcVBean.getCardId() + "\n");
            stringBuilder.append("最大字节数：" + mNfcVBean.getMaxTransceiveLength() + "\n");
            stringBuilder.append("dsf：" + mNfcVBean.getDsfId() + "\n");
            stringBuilder.append("响应标记：" + mNfcVBean.getResponseFlags() + "\n");
            return stringBuilder.toString();
        } else if (TagTech.TECHS[4].equals(curTagTech)) {
            /*获取数据*/
            IsoDepBean mIsoDepBean = new IsoDepReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：" + mIsoDepBean.getCardId() + "\n");
            stringBuilder.append("余额：" + mIsoDepBean.getBalance() + "\n");
            for (String record : mIsoDepBean.getRecord()) {
                stringBuilder.append("记录：" + record + "\n");
            }
            return stringBuilder.toString();
        } else if (TagTech.TECHS[5].equals(curTagTech)) {
            /*获取数据*/
            NdefBean mNdefBean = new NdefReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：" + mNdefBean.getCardId() + "\n");
            stringBuilder.append("卡片类型：" + mNdefBean.getTagType() + "\n");
            stringBuilder.append("最大字节：" + mNdefBean.getMaxSize() + "\n");
            for (String payload : mNdefBean.getPayLoad()) {
                stringBuilder.append("payload：" + payload + "\n");
            }
            return stringBuilder.toString();
        } else if (TagTech.TECHS[6].equals(curTagTech)) {
            /*获取数据*/
            NdefFormatableBean mNdefFormatableBean = new NdefFormatableReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：" + mNdefFormatableBean.getCardId() + "\n");
            for (String payload : mNdefFormatableBean.getPayLoad()) {
                stringBuilder.append("payload：" + payload + "\n");
            }
            return stringBuilder.toString();
        } else if (TagTech.TECHS[7].equals(curTagTech)) {
            /*获取数据*/
            MifareClassicBean mBaseNfcBean = new MifareClassicReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：" + mBaseNfcBean.getCardId() + "\n");
            stringBuilder.append("卡片类型：" + mBaseNfcBean.getTagType() + "\n");
            stringBuilder.append("扇区数：" + mBaseNfcBean.getSector() + "\n");
            stringBuilder.append("块数：" + mBaseNfcBean.getBlock() + "\n");
            stringBuilder.append("存储空间：" + mBaseNfcBean.getSize() + "\n");
            for (MifareClassicBean.InfoBean infoBean : mBaseNfcBean.getInfo()) {
                stringBuilder.append("内容：" + "\n");
                stringBuilder.append("Sector：" + infoBean.getSectorPosition() + "\n");
                stringBuilder.append("Block：" + infoBean.getBlockPosition() + "\n");
                stringBuilder.append("contentHex：" + infoBean.getContentHex() + "\n");
                stringBuilder.append("contentDecimal：" + infoBean.getContentDecimal() + "\n");
            }
            return stringBuilder.toString();
        } else if (TagTech.TECHS[8].equals(curTagTech)) {
            /*获取数据*/
            MifareUltralightBean mMifareUltralightBean = new MifareUltralightReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：" + mMifareUltralightBean.getCardId() + "\n");
            stringBuilder.append("卡片类型：" + mMifareUltralightBean.getTagType() + "\n");
            stringBuilder.append("总容量：" + mMifareUltralightBean.getMifareUltralightPageSize() + "\n");
            stringBuilder.append("page0：" + mMifareUltralightBean.getPage0() + "\n");
            stringBuilder.append("page4：" + mMifareUltralightBean.getPage4() + "\n");
            stringBuilder.append("page8：" + mMifareUltralightBean.getPage8() + "\n");
            stringBuilder.append("page12：" + mMifareUltralightBean.getPage12() + "\n");
            return stringBuilder.toString();
        }
        return stringBuilder.toString();
    }

}
