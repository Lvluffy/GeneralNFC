package com.luffy.nfclib.tech;

import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;

/**
 * Created by lvlufei on 2019/7/29
 *
 * @name 技术
 * @desc
 */
public class TagTech {
    public static final String[] TECHS = new String[]{
            NfcA.class.getName(),
            NfcB.class.getName(),
            NfcF.class.getName(),
            NfcV.class.getName(),
            IsoDep.class.getName(),
            Ndef.class.getName(),
            NdefFormatable.class.getName(),
            MifareClassic.class.getName(),
            MifareUltralight.class.getName()};
}
