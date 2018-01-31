package com.wen_wen.bannerdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final long DELAY_TIME = 3000;
    private ViewPager pager;
    private LinearLayout point;
    private BannerAdapter bannerAdapter;
    private int currentItem = 0;
    private Handler handler = new Handler();
    private final Runnable mTicker = new Runnable() {
        public void run() {
            long now = SystemClock.uptimeMillis();
            long next = now + (DELAY_TIME - now % DELAY_TIME);

            handler.postAtTime(mTicker, next);//延迟5秒再次执行runnable,就跟计时器一样效果

            currentItem++;
            pager.setCurrentItem(currentItem);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = ((ViewPager) findViewById(R.id.banner_pager));
        point = ((LinearLayout) findViewById(R.id.point_linear));
        bannerAdapter = new BannerAdapter(this);
        //缓存页数
        //pager.setOffscreenPageLimit(2);
        bannerAdapter.setOnBannerClickListener(this);
        pager.addOnPageChangeListener(this);
        pager.setAdapter(bannerAdapter);

        initPoint();

        //设置当前页

        pager.setCurrentItem(currentItem);

        handler.postDelayed(mTicker, DELAY_TIME);

    }

    private void initPoint() {
        for (int i = 0; i < bannerAdapter.getBanners().length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            if (i == 0) {
                imageView.setImageResource(R.drawable.point_select);
            } else {
                imageView.setImageResource(R.drawable.point_unselect);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.
                    LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 10;
            layoutParams.rightMargin = 10;
            point.addView(imageView, layoutParams);

        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
        setPointSelect(currentItem);
    }

    private void setPointSelect(int selectItems) {

        Log.d("111","selectItems:"+selectItems%bannerAdapter.getBanners().length);
        for (int i = 0; i < bannerAdapter.getBanners().length; i++) {
            ImageView imageView = (ImageView) point.getChildAt(i);
            imageView.setBackgroundDrawable(null);//先把背景设置成无
            if (i == (selectItems%bannerAdapter.getBanners().length)) {
                imageView.setImageResource(R.drawable.point_select);
            } else {
                imageView.setImageResource(R.drawable.point_unselect);
            }
        }


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(mTicker);
    }
}
