package com.moxun.tagcloud;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moxun.tagcloudlib.view.TagsAdapter;

import org.w3c.dom.Text;

/**
 * Created by moxun on 16/3/4.
 */
public class ViewTagsAdapter extends TagsAdapter {
    @Override
    public int getCount() {
        return 20;
    }

    @Override
    public View getView(Context context, int position, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.tag_item_view, parent, false);
        return view;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public int getPopularity(int position) {
        return position % 5;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor, float alpha) {
        View androidEye = view.findViewById(R.id.android_eye);
        if (androidEye!=null)androidEye.setBackgroundColor(themeColor);

        int color = Color.argb((int) ((1 - alpha) * 255), 255, 255, 255);
        ((ImageView) view.findViewById(R.id.iv)).setColorFilter(color);
    }
}
