package com.luffy.generalnfc.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.luffy.generallib.AppUtils;
import com.luffy.generallib.DoubleClickExitUtils;
import com.luffy.generalnfc.R;
import com.luffy.generalnfc.base.BaseActivity;
import com.luffy.nfclib.invoker.ReaderInvoler;
import com.luffy.toastlib.ToastBuilder;

import butterknife.BindView;

/**
 * Created by lvlufei on 2019/7/18
 *
 * @name 主界面
 * @desc
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.txt_content)
    TextView txtContent;

    private NfcAdapter mNfcAdapter;

    @Override
    public boolean visibilityTitleLayout() {
        return false;
    }

    @Override
    public int setLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    public void initReceiveData() {

    }

    @Override
    public void initView() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        if (mNfcAdapter == null) {
            new ToastBuilder(mContext).setTitle("该设备不支持NFC").show();
            return;
        }
        if (!mNfcAdapter.isEnabled()) {
            new ToastBuilder(mContext).setTitle("设备未开启NFC").show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
            }
        }
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void detachViewForPresenter() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null) {
            mNfcAdapter.disableForegroundDispatch(mContext);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mNfcAdapter != null) {
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, new Intent(mContext, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
            IntentFilter[] intentFilters = new IntentFilter[]{};
            mNfcAdapter.enableForegroundDispatch(mContext, pendingIntent, intentFilters, null);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction()) ||
                NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag mTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            txtContent.setText(ReaderInvoler.readTag(mTag, intent));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return DoubleClickExitUtils.getInstance().exit(this, new DoubleClickExitUtils.IDoubleClickExitCallBack() {
                @Override
                public void hanlderToastShow() {
                    new ToastBuilder(mContext)
                            .setTitle(String.format(mContext.getString(R.string.utils_double_click_exit_hint), AppUtils.getInstance().getAppName(mContext)))
                            .setGravity(Gravity.BOTTOM)
                            .show();
                }
            });
        }
        return super.onKeyDown(keyCode, event);
    }

}
