package com.itheima.googleplaydemo.ui.fragment;

/**
 * 创建者: Leon
 * 创建时间: 2016/9/15 13:13
 * 描述： TODO
 */
public class AppFragment extends BaseAppListFragment {
    private static final String TAG = "AppFragment";

    @Override
    protected void startLoadData() {
        onDataLoadedSuccess();
    }
}