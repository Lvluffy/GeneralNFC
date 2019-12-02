package com.luffy.generalnfc.base;

import android.graphics.Color;
import android.view.View;

import com.luffy.generalnfc.R;
import com.luffy.uilib.android.fragment.BaseLayerFragment;

import butterknife.ButterKnife;

/**
 * Created by lvlufei on 2019/7/18
 *
 * @name 公共-Fragment
 * @desc
 */
public abstract class BaseFragment extends BaseLayerFragment {

    @Override
    public void bindButterKnife(View target) {
        ButterKnife.bind(this, rootView);
    }

    @Override
    public int setTitleColor() {
        return R.color.color_333333;
    }

    @Override
    public int onFragmentBackgroundColor() {
        return Color.parseColor("#FAFAFA");
    }
}
