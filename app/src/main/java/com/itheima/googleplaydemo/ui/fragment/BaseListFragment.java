package com.itheima.googleplaydemo.ui.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * 创建者: Leon
 * 创建时间: 2016/9/15 13:54
 * 描述： TODO
 */
public abstract class BaseListFragment extends BaseFragment{

    private ListView mListView;

    private BaseAdapter mBaseAdapter;
    //返回一个ListView
    @Override
    protected final View onCreateContentView() {
        //创建一个listView
        mListView = new ListView(getContext());
        //创建适配器
        mBaseAdapter = onCreateAdapter();
        //创建头部,可以添加带ListView上面
        View header = onCreateHeaderView();
        if (header != null) {
            mListView.addHeaderView(header);
        }
        //设置适配器
        mListView.setAdapter(mBaseAdapter);
        //设置监听
        mListView.setOnItemClickListener(mOnItemClickListener);
        mListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        initListView();
        return mListView;
    }
    //设置监听
    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            onListItemClick(i);
        }
    };

    protected abstract BaseAdapter onCreateAdapter();

    protected View onCreateHeaderView() {
        return null;
    }

    protected void onListItemClick(int i) {};

    protected void initListView(){};

    protected void setListDivider(int height) {
        mListView.setDivider(new ColorDrawable(Color.TRANSPARENT));
        mListView.setDividerHeight(height);
    }

    protected BaseAdapter getAdapter() {
        return mBaseAdapter;
    }


    public ListView getListView() {
            return mListView;
    }

}
