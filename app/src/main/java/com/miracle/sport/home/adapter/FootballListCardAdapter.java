package com.miracle.sport.home.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.network.GlideApp;
import com.miracle.base.util.CommonUtils;
import com.miracle.sport.home.bean.Football;

public class FootballListCardAdapter extends RecyclerViewAdapter<Football> {
    private Context context;

    public FootballListCardAdapter(Context context) {
        super(R.layout.item_football_card);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Football item) {
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.setText(R.id.tvTime, item.getTime());
        helper.setText(R.id.tvAuthor,CommonUtils.getAppName(mContext));
//        Glide.with(context)
//                .load(item.getThumb())
//                .into((ImageView) helper.getView(R.id.iv));
        String thumb = item.getThumb();
        if (TextUtils.isEmpty(thumb)) {
            helper.setGone(R.id.iv, false);
        } else {
            GlideApp.with(context).load(thumb)
                    .placeholder(R.mipmap.defaule_img)
                    .error(R.mipmap.empty)
                    .into((ImageView) helper.getView(R.id.iv));
            helper.setGone(R.id.iv, true);
        }

    }
}
