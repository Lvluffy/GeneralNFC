package com.luffy.generalnfc.base;

import com.luffy.generalandroidlib.android.application.BaseLayerApplication;
import com.luffy.generalutilslib.utils.LogUtils;
import com.luffy.generalutilslib.utils.SharedPreferencesUtils;
import com.luffy.generalutilslib.utils.SystemUtils;

/**
 * Created by lvlufei on 2019/7/18
 *
 * @name 公共-Application
 * @desc
 */
public class BaseApplication extends BaseLayerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化日志
        LogUtils.getInstance().init(true);
        //初始化SP
        SharedPreferencesUtils.getInstance().init("share_data");
        //解决7.0拍照问题
        SystemUtils.getInstance().settingCamera();
    }

}
