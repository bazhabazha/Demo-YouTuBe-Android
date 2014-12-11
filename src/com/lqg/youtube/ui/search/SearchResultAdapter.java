package com.lqg.youtube.ui.search;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.api.services.youtube.model.SearchResult;
import com.lqg.youtube.R;
import com.lqg.youtube.support.util.ImageLoaderUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

/**
 * Created by LQG on 2014/12/4.
 */
public class SearchResultAdapter extends BaseAdapter {
    private List<SearchResult> searchResultList;
    private static DisplayImageOptions options;

    Context context;

    public SearchResultAdapter(Fragment fragment, List<SearchResult> searchResultList) {
        context = fragment.getActivity();
        this.searchResultList = searchResultList;
        options = ImageLoaderUtil.createListPicDisplayImageOptions();
    }

    @Override
    public int getCount() {
        return searchResultList.size();
    }

    @Override
    public SearchResult getItem(int position) {
        return searchResultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search, null);
            holder = new Holder();
            holder.thumIv = (ImageView) convertView.findViewById(R.id.video_thm);
            holder.titleTv = (TextView) convertView.findViewById(R.id.video_title);
            holder.descriptionTv = (TextView) convertView.findViewById(R.id.video_description);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        SearchResult result = getItem(position);
        holder.titleTv.setText(result.getSnippet().getTitle());
        holder.descriptionTv.setText(result.getSnippet().getDescription());
        ImageLoaderUtil.getImageLoader().displayImage(result.getSnippet().getThumbnails().getDefault().getUrl(), holder.thumIv, options);
        return convertView;
    }

    public class Holder {
        ImageView thumIv;
        TextView titleTv;
        TextView descriptionTv;
    }
}
