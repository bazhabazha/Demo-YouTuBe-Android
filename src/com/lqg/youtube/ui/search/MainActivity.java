package com.lqg.youtube.ui.search;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lqg.youtube.R;
import com.lqg.youtube.support.util.LogUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity implements SearchView.OnQueryTextListener {
    @ViewById
    DrawerLayout drawerLayout;

    @ViewById(R.id.tl_custom)
    Toolbar toolbar;

    SearchSetingFragment_ searchSetingFragment;

    @AfterViews
    void afterViews() {
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        searchSetingFragment = findSearchSetingFragment();
        searchSetingFragment.setup(R.id.drawcontainer, drawerLayout, toolbar);
    }

    SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!searchSetingFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.menu_search, menu);
            searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setOnQueryTextListener(this);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private SearchFragment_ findSearchFragment() {
        return (SearchFragment_) getSupportFragmentManager().findFragmentById(R.id.maincontainer);
    }

    private SearchSetingFragment_ findSearchSetingFragment() {
        return (SearchSetingFragment_) getFragmentManager().findFragmentById(R.id.drawcontainer);
    }

    boolean duplicate;
    String query;

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!duplicate) {
            this.query = query;
            LogUtil.d("query===" + query);
            duplicate = true;
            setTitle(query);
            SearchFragment_ searchFragment = findSearchFragment();
            hideSoftKeyboard(searchView);
            searchView.clearFocus();
            return searchFragment.onQueryTextSubmit(query);
        } else
            duplicate = false;

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        SearchFragment_ searchFragment = findSearchFragment();
        return searchFragment.onQueryTextChange(newText);
    }

    public String getQuery() {
        return query == null ? "Search" : query;
    }

    public void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
