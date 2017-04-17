package com.iaihussein.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iaihussein.popularmovies.api.ReviewResult;

import java.util.List;

public class ReviewsAdapter extends BaseAdapter {


    List<ReviewResult> mResultList;
    private Context mContext;

    public ReviewsAdapter(Context c, List<ReviewResult> list) {
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
            view = inflater.inflate(R.layout.cellview_reviews, null);
        } else {
            view = convertView;
        }

        TextView mAuthorTextView = (TextView) view.findViewById(R.id.txt_author);
        TextView mContentTextView = (TextView) view.findViewById(R.id.txt_content);
        mAuthorTextView.setText(mResultList.get(position).getAuthor());
        mContentTextView.setText(mResultList.get(position).getContent());
        return view;
    }

}
