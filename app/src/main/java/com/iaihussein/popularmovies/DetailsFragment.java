package com.iaihussein.popularmovies;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iaihussein.popularmovies.api.InternetConnection;
import com.iaihussein.popularmovies.api.Result;
import com.iaihussein.popularmovies.api.ReviewResult;
import com.iaihussein.popularmovies.api.VideosResult;
import com.iaihussein.popularmovies.database.DBDataSource;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class DetailsFragment extends Fragment {

    static List<ReviewResult> mReviewResultList;
    static List<VideosResult> mVideosResultList;
    Result mResult;
    ImageView mImageView;
    TextView mTitleTextView, mRateTextView, mYearTextView, mOverViewTextView;
    Button mFavoriteButton;
    ListView mReviewListView, mVideosListView;
    ReviewsAdapter mReviewsAdapter;
    VideosAdapter mVideosAdapter;
    DBDataSource mDBDataSource;
    String mReview, mTrailer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mResult = MainActivity.mResultList.get(getArguments().getInt(Var.ARG_EXTRA));
        View mView = inflater.inflate(R.layout.fragment_details, container, false);
        mImageView = (ImageView) mView.findViewById(R.id.img_movie);
        mTitleTextView = (TextView) mView.findViewById(R.id.txt_title);
        mRateTextView = (TextView) mView.findViewById(R.id.txt_rate);
        mYearTextView = (TextView) mView.findViewById(R.id.txt_year);
        mOverViewTextView = (TextView) mView.findViewById(R.id.txt_overview);
        mFavoriteButton = (Button) mView.findViewById(R.id.btn_fav);
        mReviewListView = (ListView) mView.findViewById(R.id.lst_review);
        mVideosListView = (ListView) mView.findViewById(R.id.lst_videos);

        Picasso.with(getContext()).load(Var.URL_IMAGE + mResult.getPosterPath()).into(mImageView);
        mTitleTextView.setText(mResult.getTitle());
        mRateTextView.setText(mResult.getVoteAverage() + "/10");
        mYearTextView.setText(mResult.getReleaseDate());
        mOverViewTextView.setText(mResult.getOverview());

        mDBDataSource = new DBDataSource(getContext());
        if (mDBDataSource.isExist("" + mResult.getId())) {
            mFavoriteButton.setText("Remove Favorite");
            mReviewResultList = new Gson().fromJson(
                    mDBDataSource.getReview("" + mResult.getId()),
                    new TypeToken<List<ReviewResult>>() {
                    }.getType());
            mReviewsAdapter = new ReviewsAdapter(getContext(), mReviewResultList);
            mReviewListView.setAdapter(mReviewsAdapter);

            mVideosResultList = new Gson().fromJson(
                    mDBDataSource.getTrailer("" + mResult.getId()),
                    new TypeToken<List<VideosResult>>() {
                    }.getType());
            mVideosAdapter = new VideosAdapter(getContext(), mVideosResultList);
            mVideosListView.setAdapter(mVideosAdapter);
            mVideosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Var.URL_YOUTUBE + mVideosAdapter.mResultList.get(i).getKey())));
                }
            });
        } else {
            mFavoriteButton.setText("Add Favorite");
            getAdapterData(mResult.getId().toString());
        }
        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDBDataSource.isExist("" + mResult.getId())) {
                    mDBDataSource.deleteFavoriteById("" + mResult.getId());
                    mFavoriteButton.setText("Add Favorite");
                } else {
                    mFavoriteButton.setText("Remove Favorite");
                    mDBDataSource.createMovie(new Gson().toJson(mResult), "" + mResult.getId());
                    if (!mReview.isEmpty())
                        mDBDataSource.createReview(mReview, "" + mResult.getId());
                    if (!mTrailer.isEmpty())
                        mDBDataSource.createTrailer(mTrailer, "" + mResult.getId());
                }
            }
        });

        return mView;
    }

    void getAdapterData(String id) {
        new InternetConnection(getContext(), Var.URL_BASE + id + Var.REVEIW + Var.KEY) {
            @Override
            public void handleError(VolleyError volleyError) {
                Toast.makeText(getContext(), R.string.try_again, Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleSuccess(JSONObject jsonObject) {

                try {
                    mReview = jsonObject.getString(Var.RESULTS).toString();

                    mReviewResultList = new Gson().fromJson(
                            mReview,
                            new TypeToken<List<ReviewResult>>() {
                            }.getType());

                    if (mReviewsAdapter == null) {
                        mReviewsAdapter = new ReviewsAdapter(getContext(), mReviewResultList);
                        mReviewListView.setAdapter(mReviewsAdapter);
                    } else {
                        mReviewsAdapter.mResultList = mReviewResultList;
                        mReviewsAdapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), R.string.try_again, Toast.LENGTH_LONG).show();
                }
            }
        };
        new InternetConnection(getContext(), Var.URL_BASE + id + Var.VIDOES + Var.KEY) {
            @Override
            public void handleError(VolleyError volleyError) {
                Toast.makeText(getContext(), R.string.try_again, Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleSuccess(JSONObject jsonObject) {

                try {
                    mTrailer = jsonObject.getString(Var.RESULTS).toString();
                    mVideosResultList = new Gson().fromJson(
                            mTrailer,
                            new TypeToken<List<VideosResult>>() {
                            }.getType());

                    if (mVideosAdapter == null) {
                        mVideosAdapter = new VideosAdapter(getContext(), mVideosResultList);
                        mVideosListView.setAdapter(mVideosAdapter);
                    } else {
                        mVideosAdapter.mResultList = mVideosResultList;
                        mVideosAdapter.notifyDataSetChanged();
                    }
                    mVideosListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Var.URL_YOUTUBE + mVideosAdapter.mResultList.get(i).getKey())));
                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(getContext(), R.string.try_again, Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share&&mVideosResultList!=null&&!mVideosResultList.isEmpty()) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,Var.URL_YOUTUBE + mVideosResultList.get(0).getKey() );
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
