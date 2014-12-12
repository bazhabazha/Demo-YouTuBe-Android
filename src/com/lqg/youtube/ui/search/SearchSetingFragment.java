package com.lqg.youtube.ui.search;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.lqg.youtube.R;
import com.lqg.youtube.support.util.SearchSetting;

import org.androidannotations.annotations.EFragment;

/**
 * Created by LQG on 2014/12/8.
 */
@EFragment
public class SearchSetingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private ActionBarDrawerToggle mDrawerToggle;

    public static final String MAXRESULTS = "maxResults";
    public static final String ORDER = "order";
    public static final String SAFESEARCH = "safeSearch";
    public static final String VIDEODEFINITION = "videoDefinition";
    public static final String VIDEODURATION = "videoDuration";
    public static final String VIDEOTYPE = "videoType";

    private View mFragmentContainerView;
    private DrawerLayout mDrawerLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        setRetainInstance(false);
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setBackgroudColor();
    }

    void setBackgroudColor() {
        getView().setBackgroundColor(0xfff5f5f5);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
    }

    public void setup(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded())
                    return;

                getActionBar().setTitle(getParentActivity().getQuery());
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!isAdded())
                    return;

                getActionBar().setTitle("Setting");
                getActivity().invalidateOptionsMenu();
            }
        };
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    private ActionBar getActionBar() {
        return ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    private MainActivity_ getParentActivity() {
        return (MainActivity_) getActivity();
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(MAXRESULTS)) {
            String value = sharedPreferences.getString(key, "20");
            getPreferenceScreen().findPreference(MAXRESULTS).setSummary(value);
            SearchSetting.getInstance().setMaxResults(value);
        } else if (key.equals(ORDER)) {
            String value = sharedPreferences.getString(key, "relevance");
            getPreferenceScreen().findPreference(ORDER).setSummary(value);
            SearchSetting.getInstance().setOrder(value);
        } else if (key.equals(SAFESEARCH)) {
            String value = sharedPreferences.getString(key, "none");
            getPreferenceScreen().findPreference(SAFESEARCH).setSummary(value);
            SearchSetting.getInstance().setSafeSearch(value);
        } else if (key.equals(VIDEODEFINITION)) {
            String value = sharedPreferences.getString(key, "any");
            getPreferenceScreen().findPreference(VIDEODEFINITION).setSummary(value);
            SearchSetting.getInstance().setVideoDefinition(value);
        } else if (key.equals(VIDEODURATION)) {
            String value = sharedPreferences.getString(key, "any");
            getPreferenceScreen().findPreference(VIDEODURATION).setSummary(value);
            SearchSetting.getInstance().setVideoDuration(value);
        } else if (key.equals(VIDEOTYPE)) {
            String value = sharedPreferences.getString(key, "any");
            getPreferenceScreen().findPreference(VIDEOTYPE).setSummary(value);
            SearchSetting.getInstance().setVideoType(value);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            menu.clear();
            return;
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
