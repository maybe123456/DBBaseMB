package com.miracle.sport.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.miracle.R;
import com.miracle.base.AppConfig;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.GlideApp;
import com.miracle.base.switcher.GameActivity;
import com.miracle.base.util.ContextHolder;
import com.miracle.databinding.FixedViewFragmentBinding;
import com.miracle.sport.onetwo.act.OneFragActivity;
import com.miracle.sport.onetwo.frag.FragCpItemList;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

public class FixedViewFragment extends BaseFragment<FixedViewFragmentBinding> {
    private List<String> images;

    @Override
    public int getLayout() {
        return R.layout.fixed_view_fragment;
    }

    @Override
    public void initView() {
        initBanner();
    }

    private void initBanner() {
        images = new ArrayList<>();
        images.add("file:///android_asset/lottery/banner05.png");
        images.add("file:///android_asset/lottery/banner02.png");
        images.add("file:///android_asset/lottery/banner04.png");
        binding.banner.setImages(images).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                GlideApp.with(ContextHolder.getContext()).load(path).placeholder(R.mipmap.defaule_img).into(imageView);
            }
        }).start();

        binding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (AppConfig.DBENTITY != null && AppConfig.DBENTITY.getAppBanner() == 1) {
                    startActivity(new Intent(mContext, GameActivity.class).putExtra("url", AppConfig.DBENTITY.getAppUrl()));
                }
            }
        });
    }

    @Override
    public void initListener() {
        binding.homeLl1Ssq.setOnClickListener(this);
        binding.homeLl2.setOnClickListener(this);
        binding.homeLl3.setOnClickListener(this);
        binding.homeLl4.setOnClickListener(this);
        binding.homeLl5.setOnClickListener(this);
        binding.homeLl6.setOnClickListener(this);
        binding.homeLl7.setOnClickListener(this);
        binding.homeLl8.setOnClickListener(this);
        binding.homeLl9.setOnClickListener(this);
        binding.homeLl10.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_ll1_ssq:
                GotoOneFragActivity(1 , "推荐");
                break;
            case R.id.home_ll2:
                GotoOneFragActivity(2 , "国际围棋");
                break;
            case R.id.home_ll3:
                GotoOneFragActivity(3 , "中国围棋");
                break;
            case R.id.home_ll4:
                GotoOneFragActivity(4 , "国际象棋");
                break;
            case R.id.home_ll5:
                GotoOneFragActivity(5 , "中国象棋");

                break;
            case R.id.home_ll6:
                GotoOneFragActivity(6 , "棋牌资讯");

                break;
            case R.id.home_ll7:
                GotoOneFragActivity(7 , "玩法大全");

                break;
            case R.id.home_ll8:
                GotoOneFragActivity(8 , "麻将技巧");
                break;
            case R.id.home_ll9:
                GotoOneFragActivity(9 , "斗地主攻略");
                break;
            case R.id.home_ll10:
                GotoOneFragActivity(4 , "国际象棋");
                break;
        }
    }

    private void GotoOneFragActivity(int position, String name) {

        //打开分类文章列表
        Intent i = new Intent(getActivity(), OneFragActivity.class);
        i.putExtra(OneFragActivity.EXTRA_KEY_FRAG_CLASS, FragCpItemList.class);
        Message msg = new Message();
        msg.what = FragCpItemList.MSG_WHAT_KEY_REQKEY;
        msg.arg1 = position;
        i.putExtra(OneFragActivity.EXTRA_KEY_MSG, msg);
        i.putExtra(OneFragActivity.EXTRA_KEY_ACT_TITLE, ""+name);
        startActivity(i);

    }


}
