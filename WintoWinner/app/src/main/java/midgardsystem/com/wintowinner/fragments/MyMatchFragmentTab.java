package midgardsystem.com.wintowinner.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Match;

/**
 * Created by Gabriel on 22/12/2016.
 */
public class MyMatchFragmentTab extends Fragment {
    View rootView;
    public static Context context;
    List<Match> matchTodayItem;
    List<Match> matchTomorrowItem;
    List<Match> matchComingSoonItem;



    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;
    int selectedTab=0;
    static DatabaseHelper dbHelper;

    public MyMatchFragmentTab(){}

    @Override
    public void onAttach(Activity activity) {
        context=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_my_match));
        dbHelper = new DatabaseHelper(context);

        Bundle args = getArguments();
        if (args  != null) {
            selectedTab=args.getInt("selectedTab");
        }

        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewPager);


        matchTodayItem =  getMatchTodayPreference();
        matchTomorrowItem =  getMatchTomorrowPreference();
        matchComingSoonItem =  getMatchComingSoonPreference();
        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);

                tabLayout.getTabAt(selectedTab).select();

            }
        });




        return rootView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Make sure you have this line of code.
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        //inflater.inflate(R.menu.home_menu, menu);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_my_match));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0 : return new MyMatchFragment(matchTodayItem);
                case 1 : return new MyMatchFragment(matchTomorrowItem);
                case 2 : return new MyMatchFragment(matchComingSoonItem);
            }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Hoy";
                case 1 :
                    return "Mañana";
                case 2 :
                    return "Esperando";
            }

            return null;
        }
    }



    public static List<Match> getMatchTodayPreference(){
        List<Match> listMatchTodayItem = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {

            listMatchTodayItem=dbHelper.getMatchDatePreference(database, 1);
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

        return listMatchTodayItem;
    }

    public static List<Match> getMatchTomorrowPreference(){
        List<Match> listMatchTomorrowItem = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            listMatchTomorrowItem=dbHelper.getMatchDatePreference(database, 2);
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

        return listMatchTomorrowItem;
    }

    public static List<Match> getMatchComingSoonPreference(){
        List<Match> listMatchComingSoonItem = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            listMatchComingSoonItem=dbHelper.getMatchDatePreference(database, 4);
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

        return listMatchComingSoonItem;
    }

    public static List<Match> getAllMatchItems(int idLeague){
        List<Match> listChildItem = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            listChildItem=dbHelper.getMatchByLeagueId(database, idLeague);
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

        return listChildItem;
    }
}

