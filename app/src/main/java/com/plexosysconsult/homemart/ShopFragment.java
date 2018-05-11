package com.plexosysconsult.homemart;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {

    TabLayout tabs;
    ViewPager viewPager;
    View rootView;
    FloatingActionButton fab;



    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_shop, container, false);
        tabs = (TabLayout) rootView.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PagerAdapterShop adapterShop = new PagerAdapterShop(getChildFragmentManager(), getActivity(), PagerAdapterShop.SHOPADAPTER);
        viewPager.setAdapter(adapterShop);
        viewPager.setOffscreenPageLimit(2);

        tabs.setupWithViewPager(viewPager);

        MainActivity mainActivity = (MainActivity) getActivity();

        mainActivity.fab.hide();
     //   mainActivity.checkCartForItems();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

     //   menu.clear();
/*
       getActivity().getMenuInflater().inflate(R.menu.shop_menu, menu);

        // Associate searchable configuration with the SearchView
        //      SearchManager searchManager =
        //            (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Toast.makeText(getActivity(), "Search coming soon", Toast.LENGTH_LONG).show();

             //   ((PapListLocalAdapter) localPapList.getAdapter()).setFilter(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if (newText.isEmpty()) {
              //      ((PapListLocalAdapter) localPapList.getAdapter()).flushFilter();
                } else {

                //    ((PapListLocalAdapter) localPapList.getAdapter()).setFilter(newText);

                }

                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
             //   ((PapListLocalAdapter) localPapList.getAdapter()).flushFilter();

                return true;
            }
        });

        //    searchView.setSearchableInfo(
        //          searchManager.getSearchableInfo(getActivity().getComponentName()));
*/

    }

    @Override
    public void onResume() {
        super.onResume();

        getActivity().invalidateOptionsMenu();
    }
}
