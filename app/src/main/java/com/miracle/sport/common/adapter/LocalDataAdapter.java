package com.miracle.sport.common.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.miracle.R;


public class LocalDataAdapter extends RecyclerView.Adapter<LocalDataAdapter.ViewHolder> {
    private int[] images = {R.drawable.no_banner, R.drawable.no_banner, R.drawable.no_banner,
            R.drawable.no_banner, R.drawable.no_banner, R.drawable.no_banner, R.drawable.no_banner,
            R.drawable.no_banner, R.drawable.no_banner, R.drawable.no_banner};

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_over, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imageView.setImageResource(images[position]);
        holder.imageView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "点击了" + v.getTag(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
