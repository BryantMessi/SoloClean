package com.solo.soloclean.memory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.solo.soloclean.R;
import com.solo.soloclean.memory.bean.MemoryBean;

import java.util.List;

/**
 * Created by Messi on 16-10-19.
 */

public class MemoryAdapter extends BaseAdapter {

    private List<MemoryBean> mData;
    private Context mContext;

    public MemoryAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<MemoryBean> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mData == null) {
            return null;
        }
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_memory_info, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MemoryBean info = mData.get(position);
        holder.icon.setImageDrawable(info.getIcon());
        holder.label.setText(info.getLabel());
        holder.size.setText(info.getCacheSize() + "");
        return convertView;
    }

    private static class ViewHolder {
        ImageView icon;
        TextView label;
        TextView size;

        ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.img_icon);
            label = (TextView) view.findViewById(R.id.txt_label);
            size = (TextView) view.findViewById(R.id.txt_size);
        }
    }
}
