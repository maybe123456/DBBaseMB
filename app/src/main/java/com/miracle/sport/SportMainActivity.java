package com.miracle.sport;

import android.view.View;

import com.miracle.R;
import com.miracle.base.AppConfig;
import com.miracle.base.BaseActivity;
import com.miracle.base.GOTO;
import com.miracle.base.util.sqlite.SQLiteKey;
import com.miracle.base.util.sqlite.SQLiteUtil;
import com.miracle.databinding.ActivitySportMainBinding;
import com.miracle.sport.community.fragment.CommunityFragment;
import com.miracle.sport.home.fragment.HomeFragment;
import com.miracle.sport.me.fragment.MeFragment2;
import com.miracle.sport.onetwo.frag.FragmentMainNew;

/**
 * Created by Michael on 2018/10/27 13:32 (星期六) <->w<->
 */
public class SportMainActivity extends BaseActivity<ActivitySportMainBinding> {


    @Override
    public int getLayout() {
        return R.layout.activity_sport_main;
    }

    @Override
    public void initView() {
        hideTitle();
        showContent();
        //CircleMenuFragment
//        binding.zRadiogroup.setUp(getSupportFragmentManager(), R.id.container, new FragmentLotteryMain(), new FixedViewFragment(), new CommunityFragment(), new MeFragment());
        binding.zRadiogroup.setUp(getSupportFragmentManager(), R.id.container, new FragmentMainNew(), new HomeFragment(), new CommunityFragment(), new MeFragment2());
//        binding.zRadiogroup.setUp(getSupportFragmentManager(), R.id.container, new CircleMenuFragment(), new HomeFragment(), new CommunityFragment(), new MeFragment2());
        if (AppConfig.DBENTITY != null && AppConfig.DBENTITY.getAppTurntable() == 1 && SQLiteUtil.getBoolean(SQLiteKey.FIRST_LOGIN)) {
            GOTO.CircleTurntableActivity(this);
            SQLiteUtil.saveBoolean(SQLiteKey.FIRST_LOGIN, true);
        }
    }


    @Override
    public void initListener() {
        binding.tvContactCustomerService.setOnClickListener(this);
        binding.rlGroupChat.setOnClickListener(this);
    }

    @Override
    public void loadData() {
//        Disposable result = new RxPermissions(this).requestEach(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
//                .subscribe(new Consumer<Permission>() {
//                    @SuppressLint("MissingPermission")
//                    @Override
//                    public void accept(Permission permission) {
//                        if (permission.name.equals(Manifest.permission.READ_PHONE_STATE) && permission.granted) {
//                            try {
//                                Set<String> phoneNum = CommonUtils.getPhoneNum(mContext);
//                                StringBuilder sb = new StringBuilder();
//                                for (String phone : phoneNum) {
//                                    sb.append(phone);
//                                    sb.append(",");
//                                }
//                                String s = sb.toString();
//                                String phone = s.substring(0, s.length() - 1);
//                                ZClient.getService(ZService.class).sendPhoneNum(phone).enqueue(new Callback<ZResponse>() {
//                                    @Override
//                                    public void onResponse(Call<ZResponse> call, Response<ZResponse> response) {
//
//                                    }
//
//                                    @Override
//                                    public void onFailure(Call<ZResponse> call, Throwable t) {
//
//                                    }
//                                });
//                            } catch (Exception e) {
//                            }
//                        }
//                    }
//                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvContactCustomerService:
                GOTO.CustomerServiceActivity(mContext);
                break;
            case R.id.rlGroupChat:
                GOTO.ChatActivity(mContext);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

}
