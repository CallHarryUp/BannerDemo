package com.wen_wen.bannerdemo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by WeLot on 2018/1/30.
 */

public class BannerAdapter extends PagerAdapter {
    private Context context;
    private View.OnClickListener onBannerClickListener;

    public BannerAdapter(Context context) {
        this.context = context;
    }

    private int[] banners = new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c};

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position %= banners.length;//去余 position的取值范围是length-1
        ImageView banner = new ImageView(context);
        banner.setImageResource(banners[position]);
        banner.setScaleType(ImageView.ScaleType.CENTER_CROP);
        banner.setTag(position);
        banner.setOnClickListener(onClickListener);
        container.addView(banner);
        return banner;

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onBannerClickListener != null) {
                onBannerClickListener.onClick(v);
            }
        }
    };

    public void setOnBannerClickListener(View.OnClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public   int[]  getBanners(){

        return   banners;
    }
}
