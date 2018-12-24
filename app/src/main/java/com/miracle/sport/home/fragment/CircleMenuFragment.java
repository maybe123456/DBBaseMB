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
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.FragmentCircleMeunBinding;
import com.miracle.sport.common.BannerLayout;
import com.miracle.sport.common.adapter.WebBannerAdapter;
import com.miracle.sport.onetwo.act.OneFragActivity;
import com.miracle.sport.onetwo.frag.FragCpItemList;
import com.miracle.sport.onetwo.frag.FragmentLotteryMain;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.miracle.sport.home.view.CircleLayout;
import com.miracle.sport.home.view.CircleLayout.OnItemClickListener;
import com.miracle.sport.home.view.CircleLayout.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class CircleMenuFragment extends BaseFragment<FragmentCircleMeunBinding> implements
        OnItemSelectedListener, OnItemClickListener, OnClickListener {
    private List<String> images;

    @Override
    public int getLayout() {
        return R.layout.fragment_circle_meun;
    }

    @Override
    public void initView() {
        initBanner();
        initRecyclerViewBanner();
    }

    private void initRecyclerViewBanner() {

        List<String> list = new ArrayList<>();
//        list.add("http://img0.imgtn.bdimg.com/it/u=1352823040,1166166164&fm=27&gp=0.jpg");
        list.add("file:///android_asset/lottery/banner05.png");
        list.add("file:///android_asset/lottery/banner02.png");
        list.add("file:///android_asset/lottery/banner04.png");
        WebBannerAdapter webBannerAdapter=new WebBannerAdapter(mContext,list);
        webBannerAdapter.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                ToastUtil.toast("点击了第  " + position+"  项");
            }
        });
        binding.recycler.setAdapter(webBannerAdapter);
    }

    private void initBanner() {
        images = new ArrayList<>();
        images.add("file:///android_asset/lottery/banner05.png");
        images.add("file:///android_asset/lottery/banner02.png");
        images.add("file:///android_asset/lottery/banner04.png");
//        images.add("file:///android_asset/lottery/18.jpg");
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
        binding.mainCircleLayout.setOnItemSelectedListener(this);
        binding.mainCircleLayout.setOnItemClickListener(this);

        binding.relbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relbtn:
//                ToastUtil.toast("中国银行");
                //打开分类文章列表
                Intent i = new Intent(getActivity(), OneFragActivity.class);
                i.putExtra(OneFragActivity.EXTRA_KEY_FRAG_CLASS, HomeFragment.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position, long id, String name) {
//        ToastUtil.toast(" " + name);
        //打开分类文章列表
        Intent i = new Intent(getActivity(), OneFragActivity.class);
        i.putExtra(OneFragActivity.EXTRA_KEY_FRAG_CLASS, FragCpItemList.class);
        Message msg = new Message();
        msg.what = FragCpItemList.MSG_WHAT_KEY_REQKEY;
        msg.arg1 = position+1;
        i.putExtra(OneFragActivity.EXTRA_KEY_MSG, msg);
        i.putExtra(OneFragActivity.EXTRA_KEY_ACT_TITLE, ""+name);
        startActivity(i);
    }

    @Override
    public void onItemSelected(View view, int position, long id, String name) {

    }
}
