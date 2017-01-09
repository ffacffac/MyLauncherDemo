package com.mylauncherdemo;

import java.util.List;
import java.util.zip.Inflater;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GVAppAdapter extends BaseAdapter {
    Context mContext;
    List<ResolveInfo> mAllApps;
    LayoutInflater mInflater;
    PackageManager mPm;

    public GVAppAdapter(Context context, List<ResolveInfo> allApps, PackageManager pm) {
        this.mContext = context;
        this.mAllApps = allApps;
        this.mPm = pm;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mAllApps != null ? mAllApps.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.application_layout, parent, false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tvAppName.setText(mAllApps.get(position).loadLabel(mPm).toString());
        vh.ivAppIcon.setImageDrawable(mAllApps.get(position).loadIcon(mPm));
        return convertView;
    }

    private class ViewHolder {
        TextView tvAppName;
        ImageView ivAppIcon;

        public ViewHolder(View convertView) {
            tvAppName = (TextView) convertView.findViewById(R.id.app_title);
            ivAppIcon = (ImageView) convertView.findViewById(R.id.app_icon);
        }
    }
}
