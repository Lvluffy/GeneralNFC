package com.luffy.nfclib.reader.unNdef;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.util.Log;

import com.luffy.nfclib.model.IsoDepBean;
import com.luffy.nfclib.reader.base.BaseNfcReader;
import com.luffy.nfclib.utils.NfcUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name 非NDEF数据读取——IsoDep
 * @desc
 */
public class IsoDepReader extends BaseNfcReader<IsoDepBean> {

    @Override
    public IsoDepBean reader(Intent intent) {
        //数据
        IsoDepBean mIsoDepBean = new IsoDepBean();
        //标签
        Tag mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        //卡片ID
        String cardId = NfcUtils.bytesToHexString(mTag.getId());
        mIsoDepBean.setCardId(cardId);
        //读取TAG
        IsoDep mIsoDep = IsoDep.get(mTag);
        try {
            //连接
            mIsoDep.connect();
            //链接成功
            if (mIsoDep.isConnected()) {
                // 1.select PSF (1PAY.SYS.DDF01)
                // 选择支付系统文件，它的名字是1PAY.SYS.DDF01。
                byte[] DFN_PSE = {(byte) '1', (byte) 'P', (byte) 'A', (byte) 'Y', (byte) '.', (byte) 'S', (byte) 'Y', (byte) 'S', (byte) '.', (byte) 'D', (byte) 'D', (byte) 'F', (byte) '0', (byte) '1',};
                mIsoDep.transceive(getSelectCommand(DFN_PSE));
                // 2.选择公交卡应用的名称
                byte[] DFN_SRV = {(byte) 0xA0, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x03, (byte) 0x86, (byte) 0x98, (byte) 0x07, (byte) 0x01,};
                mIsoDep.transceive(getSelectCommand(DFN_SRV));
                // 3.读取余额
                byte[] ReadMoney = {(byte) 0x80, // CLA Class
                        (byte) 0x5C, // INS Instruction
                        (byte) 0x00, // P1 Parameter 1
                        (byte) 0x02, // P2 Parameter 2
                        (byte) 0x04, // Le
                };
                byte[] Money = mIsoDep.transceive(ReadMoney);
                if (Money != null && Money.length > 4) {
                    int cash = byteToInt(Money, 4);
                    //余额
                    float balance = cash / 100.0f;
                    mIsoDepBean.setBalance(String.valueOf(balance));
                }
                // 4.读取所有交易记录
                byte[] ReadRecord = {(byte) 0x00, // CLA Class
                        (byte) 0xB2, // INS Instruction
                        (byte) 0x01, // P1 Parameter 1
                        (byte) 0xC5, // P2 Parameter 2
                        (byte) 0x00, // Le
                };
                // 处理Record-消费记录
                byte[] Records = mIsoDep.transceive(ReadRecord);
                ArrayList<byte[]> ret = parseRecords(Records);
                List<String> retList = parseRecordsToStrings(ret);
                mIsoDepBean.setRecord(retList);
            }
        } catch (IOException e) {
            Log.e("content", "IsoDep-读取失败！");
        } finally {
            if (mIsoDep != null) {
                try {
                    mIsoDep.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            /*打印数据*/
            Log.i("content", "卡片ID：" + mIsoDepBean.getCardId() + "\n");
            Log.i("content", "余额：" + mIsoDepBean.getBalance() + "\n");
            for (String record : mIsoDepBean.getRecord()) {
                Log.i("content", "记录：" + record + "\n");
            }
        }
        return mIsoDepBean;
    }

    private byte[] getSelectCommand(byte[] aid) {
        final ByteBuffer cmd_pse = ByteBuffer.allocate(aid.length + 6);
        cmd_pse.put((byte) 0x00) // CLA Class
                .put((byte) 0xA4) // INS Instruction
                .put((byte) 0x04) // P1 Parameter 1
                .put((byte) 0x00) // P2 Parameter 2
                .put((byte) aid.length) // Lc
                .put(aid).put((byte) 0x00); // Le
        return cmd_pse.array();
    }

    /**
     * 整条Records解析成ArrayList<byte[]>
     *
     * @param Records
     * @return
     */
    private ArrayList<byte[]> parseRecords(byte[] Records) {
        int max = Records.length / 23;
        ArrayList<byte[]> ret = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            byte[] aRecord = new byte[23];
            for (int j = 23 * i, k = 0; j < 23 * (i + 1); j++, k++) {
                aRecord[k] = Records[j];
            }
            ret.add(aRecord);
        }
        return ret;
    }

    /**
     * ArrayList<byte[]>记录分析List<String> 一条记录是23个字节byte[] data，对其解码如下
     * data[0]-data[1]:index data[2]-data[4]:over,金额溢出?？？ data[5]-data[8]:交易金额
     * ？？代码应该是（5，4） data[9]:如果等于0x06或者0x09，表示刷卡；否则是充值
     * data[10]-data[15]:刷卡机或充值机编号
     * data[16]-data[22]:日期String.format("%02X%02X.%02X.%02X %02X:%02X:%02X"
     * ,data[16], data[17], data[18], data[19], data[20], data[21], data[22]);
     *
     * @param Records
     * @return
     */
    private List<String> parseRecordsToStrings(ArrayList<byte[]>... Records) {
        final byte TRANS_CSU = 6; // 如果等于0x06或者0x09，表示刷卡；否则是充值
        final byte TRANS_CSU_CPX = 9; // 如果等于0x06或者0x09，表示刷卡；否则是充值
        List<String> recordsList = new ArrayList<>();
        for (ArrayList<byte[]> record : Records) {
            if (record == null)
                continue;
            for (byte[] v : record) {
                StringBuilder stringBuilder = new StringBuilder();
                int cash = byteToInt(v, 4);
                char t = (v[9] == TRANS_CSU || v[9] == TRANS_CSU_CPX) ? '-' : '+';
                stringBuilder.append(String.format("%02X%02X.%02X.%02X %02X:%02X ", v[16], v[17], v[18], v[19], v[20], v[21], v[22]));
                stringBuilder.append("   ").append(t).append(cash / 100.0f);
                recordsList.add(stringBuilder.toString());
            }
        }
        return recordsList;
    }

    /**
     * byteArray转化为int
     *
     * @param b
     * @param n
     * @return
     */
    private int byteToInt(byte[] b, int n) {
        int ret = 0;
        for (int i = 0; i < n; i++) {
            ret = ret << 8;
            ret |= b[i] & 0x00FF;
        }
        if (ret > 100000 || ret < -100000)
            ret -= 0x80000000;
        return ret;
    }


}
