package com.lqg.youtube.ui.search;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.lqg.youtube.R;
import com.lqg.youtube.support.http.Search;
import com.lqg.youtube.ui.play.PlayVideoUsingVideoViewActivity_;
import com.lqg.youtube.ui.play.PlayVideoUsingYouTuBeActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by LQG on 2014/12/4.
 */
@EFragment(R.layout.fragment_main)
public class SearchFragment extends Fragment implements SearchView.OnQueryTextListener {

    @ViewById(R.id.search_pb)
    ProgressBar pb;

    private List<SearchResult> searchResultList = new ArrayList<>();
    private SearchResultAdapter adapter;
    private QueryTask task;

    @ViewById(R.id.listview)
    ListView listView;

    @AfterViews
    void init() {
        adapter = new SearchResultAdapter(this, searchResultList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onQueryTextSubmit(final String query) {
        if (task != null)
            task.cancel(true);

        pb.setVisibility(View.VISIBLE);
        task = new QueryTask();
        task.execute(query);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @ItemClick(R.id.listview)
    void onItemClick(SearchResult item) {
        Intent lVideoIntent;
        YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(getActivity());
        if (result == YouTubeInitializationResult.SUCCESS) {
            lVideoIntent = new Intent(getActivity(), PlayVideoUsingYouTuBeActivity_.class);
        } else {
            lVideoIntent = new Intent(getActivity(), PlayVideoUsingVideoViewActivity_.class);
        }
        lVideoIntent.putExtra("videoId", item.getId().getVideoId());
        startActivity(lVideoIntent);
    }

    private class QueryTask extends AsyncTask<String, Void, SearchListResponse> {

        @Override
        protected SearchListResponse doInBackground(String... params) {
            return Search.search(params[0]);
        }

        @Override
        protected void onPostExecute(SearchListResponse searchListResponse) {
            pb.setVisibility(View.INVISIBLE);
            if (searchListResponse == null)
                return;

            onQuery(searchListResponse);
        }
    }

    private boolean onQuery(SearchListResponse result) {
        if (result == null)
            return false;

        searchResultList.clear();
        searchResultList.addAll(result.getItems());
        adapter.notifyDataSetChanged();
        return true;
    }

}
