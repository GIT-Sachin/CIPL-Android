package com.cipl.calc.main;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        setFontBodoni();
        setFontGothicTabs(findViewById(R.id.tabs));
    }

    private void setFontGothicTabs(View viewById) {
        if (viewById instanceof ViewGroup) {
            TabLayout tabs = (TabLayout) viewById;
            TabLayout.Tab tab1 = tabs.getTabAt(0);
            TabLayout.Tab tab2 = tabs.getTabAt(1);
            tab1.setCustomView(createTextView("LA & EMI"));
            tab2.setCustomView(createTextView("Min. DP & EMI"));
        }
    }

    private View createTextView(String s) {
        Typeface gothic = Typeface.createFromAsset(getAssets(), "fonts/centurygothic.TTF");
        TextView tv = new AppCompatTextView(getBaseContext());
        tv.setText(s);
        tv.setTextColor(getResources().getColor(R.color.textColor));
        tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv.setTypeface(gothic);
        return tv;
    }

    private void setFontBodoni() {
        TextView tv = (TextView) findViewById(R.id.logoText);
        Typeface bodoni = Typeface.createFromAsset(getAssets(), "fonts/bodoni-mt.TTF");
        tv.setTypeface(bodoni);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragmemt(new LoanAmountEMI(), null);
        adapter.addFragmemt(new MinDPEMI(), null);
        viewPager.setAdapter(adapter);
    }


}
