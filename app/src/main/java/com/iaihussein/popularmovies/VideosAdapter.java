package com.iaihussein.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iaihussein.popularmovies.api.VideosResult;

import java.util.List;

public class VideosAdapter extends BaseAdapter {


    List<VideosResult> mResultList;
    private Context mContext;

    public VideosAdapter(Context c, List<VideosResult> list) {
        mContext = c;
        mResultList = list;
    }

    @Override
    public int getCount() {
        return mResultList != null ? mResultList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mResultList != null ? mResultList.get(position) : position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.cellview_videos, null);
        } else {
            view = convertView;
        }

        TextView mTitleTextView = (TextView) view.findViewById(R.id.txt_video_title);
        TextView mTypeTextView = (TextView) view.findViewById(R.id.txt_video_type);
        mTitleTextView.setText(mResultList.get(position).getName());
        mTypeTextView.setText(mResultList.get(position).getType() + "\n" + mResultList.get(position).getSite());
        return view;
    }

}
