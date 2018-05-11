package com.plexosysconsult.homemart;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {


    FrameLayout frameLayout;
    View rootView;
    TabLayout tabs;
    ViewPager viewPager;


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_about_us, container, false);
        //  frameLayout = (FrameLayout) rootView.findViewById(R.id.fl_about_us);
        tabs = (TabLayout) rootView.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PagerAdapterShop adapterShop = new PagerAdapterShop(getChildFragmentManager(), getActivity(), PagerAdapterShop.ABOUTADAPTER);
        viewPager.setAdapter(adapterShop);
        viewPager.setOffscreenPageLimit(2);

        tabs.setupWithViewPager(viewPager);

/*


        View aboutPage = new AboutPage(getActivity())
                .isRTL(false)
                .setImage(R.drawable.hellofresh_trans)
         //       .addItem(versionElement)
          //      .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("com.plexosysconsult.homemart@gmail.com")
                .addWebsite("http://www.hellofreshuganda.com")
        //        .addFacebook("t")
        //        .addTwitter("medyo80")
         //       .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
         //       .addPlayStore("com.ideashower.readitlater.pro")
         //       .addGitHub("medyo")
         //       .addInstagram("medyo80")
                .create();

        frameLayout.addView(aboutPage);
        */
    }
}
