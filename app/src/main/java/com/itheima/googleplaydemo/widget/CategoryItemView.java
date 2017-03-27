package com.itheima.googleplaydemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.itheima.googleplaydemo.R;
import com.itheima.googleplaydemo.bean.CategoryBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建者: Leon
 * 创建时间: 2016/9/25 22:28
 * 描述： TODO
 */
public class CategoryItemView extends RelativeLayout {

    private static final String TAG = "CategoryItemView";

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.table_layout)
    TableLayout mTableLayout;

    public CategoryItemView(Context context) {
        this(context, null);
    }

    public CategoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.list_category_item, this);
        ButterKnife.bind(this, this);
    }

    //这里只负责设置小模块的宽度,整体布局有xml布局参数完成
    public void bindView(CategoryBean item) {
        mTitle.setText(item.getTitle());
        //清空每个lIstView的Itemview
        mTableLayout.removeAllViews();
        //屏幕的宽度减去两边的padding值
        int widthPixels = getResources().getDisplayMetrics().widthPixels - mTableLayout.getPaddingLeft() - mTableLayout.getPaddingRight() ;
        int itemWidth = widthPixels / 3;
        //设置小模块的布局参数
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
        layoutParams.width = itemWidth;//每个子条目的宽度

        //遍历数据有几行数据,获取对应位置的信息
        List<CategoryBean.InfosBean> infos = item.getInfos();
        for (int i = 0; i < infos.size(); i++) {

            TableRow tableRow = new TableRow(getContext());

            //小模块1:图片标题
            CategoryInfoItemView infoItemView1 = new CategoryInfoItemView(getContext());
            //设置模块布局参数
            infoItemView1.setLayoutParams(layoutParams);

            infoItemView1.bindView(infos.get(i).getName1(), infos.get(i).getUrl1());
            tableRow.addView(infoItemView1);

            //小模块1:图片标题
            CategoryInfoItemView infoItemView2 = new CategoryInfoItemView(getContext());
            infoItemView2.setLayoutParams(layoutParams);
            infoItemView2.bindView(infos.get(i).getName2(), infos.get(i).getUrl2());
            tableRow.addView(infoItemView2);

            String name3 = infos.get(i).getName3();
            //name3为空时表示没有数据,不用添加小模块了
            if ( name3 != null && name3.length() > 0) {
                //小模块1:图片标题
                CategoryInfoItemView infoItemView3 = new CategoryInfoItemView(getContext());
                infoItemView3.setLayoutParams(layoutParams);
                infoItemView3.bindView(infos.get(i).getName3(), infos.get(i).getUrl3());
                tableRow.addView(infoItemView3);
            }
            //添加3个一行
            mTableLayout.addView(tableRow);
        }
    }
}
