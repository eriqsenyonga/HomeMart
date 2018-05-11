package com.plexosysconsult.homemart;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutHelloFreshFragment extends Fragment {


    View root;

    FrameLayout flAddress;

    public AboutHelloFreshFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        root = inflater.inflate(R.layout.fragment_about_hello_fresh, container, false);

        flAddress = (FrameLayout) root.findViewById(R.id.fl_address);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        String versionName = BuildConfig.VERSION_NAME;

        Element versionElement = new Element();
        versionElement.setTitle("Version 6.2");

        Element callElement = new Element();
        callElement.setTitle("Call Us");
        callElement.setValue("0701885127");
        callElement.setIconDrawable(R.drawable.ic_call_black_24dp);
        callElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0701885127"));
                startActivity(intent);
            }
        });


        View aboutPage = new AboutPage(getActivity())
                .isRTL(false).setDescription(getString(R.string.about_hellofresh))
                .setImage(R.drawable.logojpg)
                //       .addItem(versionElement)
                //      .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("com.plexosysconsult.homemart@gmail.com")
                .addWebsite("http://www.hellofreshuganda.com")
                .addItem(callElement)
                .addFacebook("com.plexosysconsult.homemart")
                //        .addTwitter("medyo80")
                //       .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                //       .addPlayStore("com.ideashower.readitlater.pro")
                //       .addGitHub("medyo")
                //       .addInstagram("medyo80")
                .addPlayStore("com.plexosysconsult.com.plexosysconsult.homemart")
                .create();

        flAddress.addView(aboutPage);


    }
}
