package com.luffy.generalnfc.base;

import android.app.Activity;

import com.luffy.componentlib.activity.BaseLayerActivity;
import com.luffy.generalnfc.R;

import butterknife.ButterKnife;


/**
 * Created by lvlufei on 2019/7/18
 *
 * @name 公共-Activity
 * @desc
 */
public abstract class BaseActivity extends BaseLayerActivity {

    @Override
    public void bindButterKnife(Activity target) {
        ButterKnife.bind(target);
    }

    @Override
    public int setTitleColor() {
        return R.color.color_333333;
    }

    @Override
    public int setTitleSize() {
        return 17;
    }

}
