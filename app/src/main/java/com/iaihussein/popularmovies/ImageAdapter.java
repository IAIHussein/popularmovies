package com.iaihussein.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.iaihussein.popularmovies.api.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends BaseAdapter {


    List<Result> mResultList;
    private Context mContext;

    public ImageAdapter(Context c, List<Result> list) {
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
            view = inflater.inflate(R.layout.cellview, null);
        } else {
            view = convertView;
        }

        ImageView imageView = (ImageView) view.findViewById(R.id.imgViewId);
        Picasso.with(mContext).load(Var.URL_IMAGE + mResultList.get(position).getBackdropPath()).into(imageView);
        return view;
    }

}
