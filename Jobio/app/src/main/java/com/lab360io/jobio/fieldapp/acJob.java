package com.lab360io.jobio.fieldapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.crashlytics.android.Crashlytics;
import com.xwray.fontbinding.FontCache;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import adapter.TabsPagerAdapter;
import fragments.frJobList;
import fragments.frJobMap;
import io.fabric.sdk.android.Fabric;
import utility.ConstantVal;
import utility.Logger;
import utility.MyFunction;
import utility.OptionMenu;

//http://www.theappguruz.com/blog/android-tab-layout-with-swipeable-views
//https://thepseudocoder.wordpress.com/2011/10/13/android-tabs-viewpager-swipe-able-tabs-ftw/
public class acJob extends ActionBarActivity implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
    ActionBarActivity ac;
    public static Context mContext;
    MyFunction objMyFunction = new MyFunction();
    String[] tabs;
    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
    private PagerAdapter mPagerAdapter;

    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;

        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }

    }

    class TabFactory implements TabHost.TabContentFactory {
        private final Context mContext;

        public TabFactory(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //v.setMinimumWidth(0);
            //v.setMinimumHeight(0);
            return v;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        //FontCache.getInstance(getApplicationContext()).addFont("Ubuntu", "Ubuntu-C.ttf");
        //DataBindingUtil.setContentView(this, R.layout.job);
        setContentView(R.layout.job);
        mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
        mContext = this;
        ac = this;
        tabs = getResources().getStringArray(R.array.arrJob);
        //mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        //vp.setAdapter(mAdapter);
        objMyFunction.setActionBar(ac, getString(R.string.strJob), getString(R.string.strJob));
        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
        // Intialise ViewPager
        this.intialiseViewPager();
        /*final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);

        for (String tab_name : tabs) {
            Logger.debug(tab_name);
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        objMyFunction.registerSessionTimeoutBroadcast(ac);
    }

    @Override
    protected void onStop() {
        super.onStop();
        objMyFunction.unRegisterSesionTimeOutBroadcast(ac);
    }

    private void intialiseViewPager() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(new frJobList());
        fragments.add(new frJobMap());
        this.mPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager(), fragments);
        //
        this.mViewPager.setAdapter(this.mPagerAdapter);
        this.mViewPager.setOnPageChangeListener(this);
    }

    /**
     * Initialise the Tab Host
     */
    private static void AddTab(acJob activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        acJob.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab1").setIndicator(tabs[0]), (tabInfo = new TabInfo("Tab1", frJobList.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        acJob.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab2").setIndicator(tabs[1]), (tabInfo = new TabInfo("Tab2", frJobMap.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        this.onTabChanged("Tab1");
        mTabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("tab", mTabHost.getCurrentTabTag());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Logger.debug("onPageSelected:" + position);
        this.mTabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int pos = this.mTabHost.getCurrentTab();
        this.mViewPager.setCurrentItem(pos);
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            mTabHost.getTabWidget().getChildAt(i).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tilt)));
            //.setBackgroundResource(R.drawable.tab_selected); // unselected
        }
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.lightGrey)));
    }

    OptionMenu objOptionMenu = new OptionMenu();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        objOptionMenu.getCommonMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        objOptionMenu.handleMenuItemClick(this, item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantVal.EXIT_REQUEST_CODE && resultCode == ConstantVal.EXIT_RESPONSE_CODE) {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        frJobList.arrCollapseHeaderList.clear();
    }
}
