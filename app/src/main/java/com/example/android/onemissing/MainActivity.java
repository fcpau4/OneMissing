package com.example.android.onemissing;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.example.android.onemissing.fragments.MapFragment;
import com.example.android.onemissing.fragments.UserEventsFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    private ImageButton btEvent;
    private ViewPagerAdapter adapter;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public MainActivity() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        viewPager = (ViewPager) findViewById(R.id.vpPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        tabLayout.setupWithViewPager(viewPager);
        setupViewPager(viewPager);

        btEvent = (ImageButton) findViewById(R.id.btEvent);
        btEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewEventActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {

        adapter.addFragment(new MapFragment(), "MAP");
        adapter.addFragment(new UserEventsFragment(), "EVENTS");
        viewPager.setAdapter(adapter);

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
