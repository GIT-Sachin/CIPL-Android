package com.cipl.calc.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.cipl.calc.main.fragments.LoanAmountEMI;
import com.cipl.calc.main.fragments.MinDPEMI;

public class MainActivity extends AppCompatActivity {


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private SectionsPageAdapter sectionsPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragmemt(new LoanAmountEMI(), "LA & EMI");
        adapter.addFragmemt(new MinDPEMI(), "Min. DP & EMI");
        viewPager.setAdapter(adapter);
    }


}
