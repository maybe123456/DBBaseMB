package com.miracle.sport.me.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.GOTO;
import com.miracle.base.bean.UserInfoBean;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.FragmentMe2Binding;
import com.miracle.sport.me.activity.DDZMyCircleActivity;
import com.miracle.sport.me.activity.DDZMyPostActivity;
import com.miracle.sport.me.activity.MyCollectionsActivity;

import java.lang.ref.WeakReference;

/**
 * Created by Michael on 2018/12/19 11:12 (星期三)
 */
public class MeFragment2 extends BaseFragment<FragmentMe2Binding> {
    private ZHandler handler;
    private ProgressDialog dialogProgress;
    private UserInfoBean userInfo;

    @Override
    public int getLayout() {
        return R.layout.fragment_me2;
    }

    @Override
    public void initView() {
        dialogProgress = new ProgressDialog(mContext);
        handler = new ZHandler(this);
    }

    @Override
    public void initListener() {
        binding.llMyPost.setOnClickListener(this);
        binding.llMyCircle.setOnClickListener(this);
        binding.llMyCollection.setOnClickListener(this);
        binding.llAboutUs.setOnClickListener(this);
        binding.llSettings.setOnClickListener(this);
        binding.llMeInfo.setOnClickListener(this);
        binding.ibContactService.setOnClickListener(this);
        binding.ibClearCache.setOnClickListener(this);
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reqData();
            }
        });
    }

    private void reqData() {
        if (CommonUtils.getUser() != null) {
            ZClient.getService(ZService.class).getUserInfo().enqueue(new ZCallback<ZResponse<UserInfoBean>>(binding.swipeRefreshLayout) {
                @Override
                public void onSuccess(ZResponse<UserInfoBean> data) {
                    userInfo = data.getData();
                    binding.tvName.setText(userInfo.getNickname());
                    binding.tvSign.setText(userInfo.getUsername());
                    GlideApp.with(mContext).load(userInfo.getImg()).placeholder(R.mipmap.default_head).into(binding.ivHead);
                }
            });
        } else {
            if (binding.swipeRefreshLayout.isRefreshing()) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMyPost:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    startActivity(new Intent(mContext, DDZMyPostActivity.class));
                }
                break;
            case R.id.llMyCircle:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    startActivity(new Intent(mContext, DDZMyCircleActivity.class));
                }
                break;
            case R.id.llMyCollection:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    startActivity(new Intent(mContext, MyCollectionsActivity.class));
                }
                break;
            case R.id.llAboutUs:
                GOTO.AboutUsActivity(mContext);
                break;
            case R.id.llSettings:
                GOTO.SettingActivity(mContext);
                break;
            case R.id.llMeInfo:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    GOTO.MeInfoActivity(mContext, userInfo);
                }
                break;
            case R.id.ibContactService:
                GOTO.CustomerServiceActivity(mContext);
                break;
            case R.id.ibClearCache:
                dialogProgress.setMessage("清理中...");
                dialogProgress.show();
                handler.sendEmptyMessageDelayed(0, 1000);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        reqData();
    }

    private static final class ZHandler extends Handler {
        private WeakReference<MeFragment2> weakReference;

        public ZHandler(MeFragment2 meFragment) {
            weakReference = new WeakReference<>(meFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            weakReference.get().dialogProgress.dismiss();
            ToastUtil.toast(msg.what == 0 ? "缓存清理完成!" : "当前已是最新版本");
        }
    }
}
