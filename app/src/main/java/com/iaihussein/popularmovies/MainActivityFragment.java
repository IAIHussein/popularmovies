package com.iaihussein.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iaihussein.popularmovies.api.InternetConnection;
import com.iaihussein.popularmovies.api.Result;
import com.iaihussein.popularmovies.database.DBDataSource;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    GridView mGridView;
    ImageAdapter mImageAdapter;

    public MainActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_main, container, false);
        mGridView = (GridView) mView.findViewById(R.id.movie_grid);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DetailsFragment mDetailsFragment = new DetailsFragment();
                Bundle mBundle = new Bundle();
                mBundle.putInt(Var.ARG_EXTRA, i);
                mDetailsFragment.setArguments(mBundle);
                if (getActivity().findViewById(R.id.detail_fragment) == null)
                    getFragmentManager().beginTransaction().addToBackStack("").replace(R.id.fragment, mDetailsFragment).commit();
                else
                    getFragmentManager().beginTransaction().replace(R.id.detail_fragment, mDetailsFragment).commit();
            }
        });
        getAdapterData(Var.URL_POPULAR);

        return mView;
    }

    void getAdapterData(String url) {
        new InternetConnection(getContext(), url) {
            @Override
            public void handleError(VolleyError volleyError) {
                Toast.makeText(getContext(), R.string.try_again, Toast.LENGTH_LONG).show();
            }

            @Override
            public void handleSuccess(JSONObject jsonObject) {


                try {
                    MainActivity.mResultList = new Gson().fromJson(
                            jsonObject.getString(Var.RESULTS),
                            new TypeToken<List<Result>>() {
                            }.getType());

                    if (mImageAdapter == null) {
                        mImageAdapter = new ImageAdapter(getContext(), MainActivity.mResultList);
                        mGridView.setAdapter(mImageAdapter);
                    } else {
                        mImageAdapter.mResultList = MainActivity.mResultList;
                        mImageAdapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), R.string.try_again, Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_popular) {
            getAdapterData(Var.URL_POPULAR);
            return true;
        }
        if (id == R.id.action_top) {
            getAdapterData(Var.URL_TOP);

            return true;
        }
        if (id == R.id.action_fav) {

            if (new DBDataSource(getContext()).getAll() != null) {
                MainActivity.mResultList = new DBDataSource(getContext()).getAll();

                if (mImageAdapter == null) {
                    mImageAdapter = new ImageAdapter(getContext(), MainActivity.mResultList);
                    mGridView.setAdapter(mImageAdapter);
                } else {
                    mImageAdapter.mResultList = MainActivity.mResultList;
                    mImageAdapter.notifyDataSetChanged();
                }
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
