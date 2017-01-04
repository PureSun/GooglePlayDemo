package com.itheima.googleplaydemo.ui.fragment;

import android.animation.ValueAnimator;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.itheima.googleplaydemo.R;
import com.itheima.googleplaydemo.app.Constant;
import com.itheima.googleplaydemo.bean.AppDetailBean;
import com.itheima.googleplaydemo.loader.AppDetailDataLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建者: Leon
 * 创建时间: 2016/9/18 12:04
 * 描述： TODO
 */
public class AppDetailFragment extends BaseFragment {
    private static final String TAG = "AppDetailFragment";

    @BindView(R.id.favorite)
    Button mFavorite;
    @BindView(R.id.download)
    Button mDownload;
    @BindView(R.id.share)
    Button mShare;
    @BindView(R.id.app_icon)
    ImageView mAppIcon;
    @BindView(R.id.app_name)
    TextView mAppName;
    @BindView(R.id.app_rating)
    RatingBar mAppRating;
    @BindView(R.id.download_count)
    TextView mDownloadCount;
    @BindView(R.id.version_code)
    TextView mVersionCode;
    @BindView(R.id.time)
    TextView mTime;
    @BindView(R.id.app_size)
    TextView mAppSize;
    @BindView(R.id.app_detail_security_tags)
    LinearLayout mAppDetailSecurityTags;
    @BindView(R.id.app_detail_security_arrow)
    ImageView mAppDetailSecurityArrow;
    @BindView(R.id.app_detail_security_des)
    LinearLayout mAppDetailSecurityDes;
    @BindView(R.id.app_detail_pic_container)
    LinearLayout mAppDetailPicContainer;

    private boolean securityInfoOpen = false;

    @Override
    protected void startLoadData() {
        String packageName = getActivity().getIntent().getStringExtra("package_name");
        AppDetailDataLoader.getInstance().loadData(packageName, this);
    }

    @Override
    protected View onCreateContentView() {
        View content = LayoutInflater.from(getContext()).inflate(R.layout.fragment_app_detail, null);
        ButterKnife.bind(this, content);
        initView();
        return content;
    }

    private void initView() {
        updateAppInfo();
        updateSafeInfo();
        updatePicsInfo();
    }

    private void updatePicsInfo() {
        AppDetailBean data = AppDetailDataLoader.getInstance().getData();
        for (int i = 0; i < data.getScreen().size(); i++) {
            String screen = data.getScreen().get(i);
            ImageView imageView = new ImageView(getContext());
            int padding = getResources().getDimensionPixelSize(R.dimen.app_detail_pic_padding);
            if (i != data.getScreen().size() - 1) {
                imageView.setPadding(0, 0, padding, 0);
            }
            Glide.with(getContext()).load(Constant.URL_IMAGE + screen).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(imageView);
            mAppDetailPicContainer.addView(imageView);
        }

    }

    private void updateSafeInfo() {
        AppDetailBean data = AppDetailDataLoader.getInstance().getData();
        for (int i = 0; i < data.getSafe().size(); i++) {
            AppDetailBean.SafeBean safeBean = data.getSafe().get(i);
            //Add tag
            ImageView tag = new ImageView(getContext());
            mAppDetailSecurityTags.addView(tag);
            Glide.with(getContext())
                    .load(Constant.URL_IMAGE + safeBean.getSafeUrl())
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(tag);

            //Add one line description
            LinearLayout line = new LinearLayout(getContext());
            ImageView ivDes = new ImageView(getContext());
            TextView tvDes = new TextView(getContext());
            tvDes.setText(safeBean.getSafeDes());
            if (safeBean.getSafeDesColor() == 0) {
                tvDes.setTextColor(getResources().getColor(R.color.app_detail_safe_normal));
            } else {
                tvDes.setTextColor(getResources().getColor(R.color.app_detail_safe_warning));
            }

            line.addView(ivDes);
            Glide.with(getContext())
                    .load(Constant.URL_IMAGE + safeBean.getSafeDesUrl())
                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .into(ivDes);
            line.addView(tvDes);

            mAppDetailSecurityDes.addView(line);
            collapseAppDetailSecurity();
        }
    }

    private void collapseAppDetailSecurity() {
        ViewGroup.LayoutParams layoutParams = mAppDetailSecurityDes.getLayoutParams();
        layoutParams.height = 0;
        mAppDetailSecurityDes.setLayoutParams(layoutParams);
    }

    private void updateAppInfo() {
        AppDetailBean data = AppDetailDataLoader.getInstance().getData();
        String iconUrl = Constant.URL_IMAGE + data.getIconUrl();
        Glide.with(getContext()).load(iconUrl).into(mAppIcon);
        mAppName.setText(data.getName());
        mAppRating.setRating(data.getStars());

        String downloadCount = String.format(getString(R.string.download_count), data.getDownloadNum());
        mDownloadCount.setText(downloadCount);

        String versionCode = String.format(getString(R.string.version_code), data.getVersion());
        mVersionCode.setText(versionCode);

        String timestamp = String.format(getString(R.string.time), data.getDate());
        mTime.setText(timestamp);

        String size = String.format(getString(R.string.app_size), Formatter.formatFileSize(getContext(), data.getSize()));
        mAppSize.setText(size);

    }

    @OnClick({R.id.favorite, R.id.download, R.id.share, R.id.app_detail_security_arrow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.favorite:
                break;
            case R.id.download:
                break;
            case R.id.share:
                break;
            case R.id.app_detail_security_arrow:
                toggleSecurityInfo();
                break;
        }
    }

    private void toggleSecurityInfo() {
        if (securityInfoOpen) {
            int measuredHeight = mAppDetailSecurityDes.getMeasuredHeight();
            animateSecurityInfo(measuredHeight, 0);

        } else {
            mAppDetailSecurityDes.measure(0, 0);
            int measuredHeight = mAppDetailSecurityDes.getMeasuredHeight();
            animateSecurityInfo(0, measuredHeight);
        }
        securityInfoOpen = !securityInfoOpen;

    }

    private void animateSecurityInfo(int start, int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mAppDetailSecurityDes.getLayoutParams();
                layoutParams.height = animatedValue;
                mAppDetailSecurityDes.setLayoutParams(layoutParams);
            }
        });
    }
}