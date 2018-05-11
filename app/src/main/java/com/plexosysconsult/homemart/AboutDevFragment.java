package com.plexosysconsult.homemart;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutDevFragment extends Fragment {


    View root;

    FrameLayout flAddress;

    public AboutDevFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_about_hello_fresh, container, false);

        flAddress = (FrameLayout) root.findViewById(R.id.fl_address);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String versionName = BuildConfig.VERSION_NAME;

        Element versionElement = new Element();
        versionElement.setTitle("Version " + versionName);

        Element callElement = new Element();
        callElement.setTitle("Call Us");
        callElement.setValue("0702157270");
        callElement.setIconDrawable(R.drawable.ic_call_black_24dp);
        callElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0702157270"));
                startActivity(intent);
            }
        });


        View aboutPage = new AboutPage(getActivity())
                .isRTL(false).setDescription(getString(R.string.about_dev))
                .setImage(R.drawable.plexo_logo)
                .addItem(versionElement)
                //       .addItem(versionElement)
                //      .addItem(adsElement)
              //  .addGroup("Connect with us")
                .addEmail("eric.senyonga@plexosysconsult.com")
                .addWebsite("http://plexosysconsult.com")
                .addItem(callElement)
                //  .addFacebook("com.plexosysconsult.homemart")
                //        .addTwitter("medyo80")
                //       .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                //       .addPlayStore("com.ideashower.readitlater.pro")
                //       .addGitHub("medyo")
                //       .addInstagram("medyo80")
                .create();

        flAddress.addView(aboutPage);
    }
}
