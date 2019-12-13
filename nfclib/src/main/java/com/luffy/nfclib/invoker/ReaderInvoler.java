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
                for (String aTagTechList : tagTechList) {
                    stringBuilder.append("*").append(aTagTechList).append("*").append("\n");
                    stringBuilder.append(readTech(aTagTechList, intent));
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
            stringBuilder.append("卡片ID：").append(mNfcABean.getCardId()).append("\n");
            stringBuilder.append("超时：").append(mNfcABean.getTimeout()).append("\n");
            stringBuilder.append("响应：").append(mNfcABean.getAtqa()).append("\n");
            return stringBuilder.toString();
        } else if (TagTech.TECHS[1].equals(curTagTech)) {
            /*获取数据*/
            NfcBBean mNfcBBean = new NfcBReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：").append(mNfcBBean.getCardId()).append("\n");
            stringBuilder.append("协议信息：").append(mNfcBBean.getProtocolInfo()).append("\n");
            return stringBuilder.toString();
        } else if (TagTech.TECHS[2].equals(curTagTech)) {
            /*获取数据*/
            NfcFBean mNfcFBean = new NfcFReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：").append(mNfcFBean.getCardId()).append("\n");
            stringBuilder.append("超时：").append(mNfcFBean.getTimeout()).append("\n");
            stringBuilder.append("最大字节数：").append(mNfcFBean.getMaxTransceiveLength()).append("\n");
            stringBuilder.append("制造商：").append(mNfcFBean.getManufacturer()).append("\n");
            stringBuilder.append("系统代码：").append(mNfcFBean.getSystemCode()).append("\n");
            return stringBuilder.toString();
        } else if (TagTech.TECHS[3].equals(curTagTech)) {
            /*获取数据*/
            NfcVBean mNfcVBean = new NfcVReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：").append(mNfcVBean.getCardId()).append("\n");
            stringBuilder.append("最大字节数：").append(mNfcVBean.getMaxTransceiveLength()).append("\n");
            stringBuilder.append("dsf：").append(mNfcVBean.getDsfId()).append("\n");
            stringBuilder.append("响应标记：").append(mNfcVBean.getResponseFlags()).append("\n");
            return stringBuilder.toString();
        } else if (TagTech.TECHS[4].equals(curTagTech)) {
            /*获取数据*/
            IsoDepBean mIsoDepBean = new IsoDepReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：").append(mIsoDepBean.getCardId()).append("\n");
            stringBuilder.append("余额：").append(mIsoDepBean.getBalance()).append("\n");
            for (String record : mIsoDepBean.getRecord()) {
                stringBuilder.append("记录：").append(record).append("\n");
            }
            return stringBuilder.toString();
        } else if (TagTech.TECHS[5].equals(curTagTech)) {
            /*获取数据*/
            NdefBean mNdefBean = new NdefReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：").append(mNdefBean.getCardId()).append("\n");
            stringBuilder.append("卡片类型：").append(mNdefBean.getTagType()).append("\n");
            stringBuilder.append("最大字节：").append(mNdefBean.getMaxSize()).append("\n");
            for (String payload : mNdefBean.getPayLoad()) {
                stringBuilder.append("payload：").append(payload).append("\n");
            }
            return stringBuilder.toString();
        } else if (TagTech.TECHS[6].equals(curTagTech)) {
            /*获取数据*/
            NdefFormatableBean mNdefFormatableBean = new NdefFormatableReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：").append(mNdefFormatableBean.getCardId()).append("\n");
            for (String payload : mNdefFormatableBean.getPayLoad()) {
                stringBuilder.append("payload：").append(payload).append("\n");
            }
            return stringBuilder.toString();
        } else if (TagTech.TECHS[7].equals(curTagTech)) {
            /*获取数据*/
            MifareClassicBean mBaseNfcBean = new MifareClassicReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：").append(mBaseNfcBean.getCardId()).append("\n");
            stringBuilder.append("卡片类型：").append(mBaseNfcBean.getTagType()).append("\n");
            stringBuilder.append("扇区数：").append(mBaseNfcBean.getSector()).append("\n");
            stringBuilder.append("块数：").append(mBaseNfcBean.getBlock()).append("\n");
            stringBuilder.append("存储空间：").append(mBaseNfcBean.getSize()).append("\n");
            for (MifareClassicBean.InfoBean infoBean : mBaseNfcBean.getInfo()) {
                stringBuilder.append("内容：" + "\n");
                stringBuilder.append("Sector：").append(infoBean.getSectorPosition()).append("\n");
                stringBuilder.append("Block：").append(infoBean.getBlockPosition()).append("\n");
                stringBuilder.append("contentHex：").append(infoBean.getContentHex()).append("\n");
                stringBuilder.append("contentDecimal：").append(infoBean.getContentDecimal()).append("\n");
            }
            return stringBuilder.toString();
        } else if (TagTech.TECHS[8].equals(curTagTech)) {
            /*获取数据*/
            MifareUltralightBean mMifareUltralightBean = new MifareUltralightReader().reader(intent);
            /*绑定数据*/
            stringBuilder.append("卡片ID：").append(mMifareUltralightBean.getCardId()).append("\n");
            stringBuilder.append("卡片类型：").append(mMifareUltralightBean.getTagType()).append("\n");
            stringBuilder.append("总容量：").append(mMifareUltralightBean.getMifareUltralightPageSize()).append("\n");
            stringBuilder.append("page0：").append(mMifareUltralightBean.getPage0()).append("\n");
            stringBuilder.append("page4：").append(mMifareUltralightBean.getPage4()).append("\n");
            stringBuilder.append("page8：").append(mMifareUltralightBean.getPage8()).append("\n");
            stringBuilder.append("page12：").append(mMifareUltralightBean.getPage12()).append("\n");
            return stringBuilder.toString();
        }
        return stringBuilder.toString();
    }

}
