package midgardsystem.com.wintowinner.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.adapter.TabAdapter;

/**
 * Created by Gabriel on 18/08/2016.
 */
public class HomeFragment extends Fragment {
    View rootView;
    public static Context context;

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static boolean login = false ;

    private static final String TAG_LIST_MATCH= "tag_list_match";

    public HomeFragment(){}
    @Override
    public void onAttach(Activity activity) {
        context=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title_home));

        init();
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);

        setupTabIcons(viewPager);

        return rootView;
    }

    private void setupTabIcons(ViewPager viewPager) {

        if(login==false){
            TabAdapter adapter = new TabAdapter(getChildFragmentManager());
            adapter.addFrag(new ListContinentCountriesFragment(), 1, "Soccer", login);
            //adapter.addFrag(new ListContinentCountriesFragment(), 2,  "Básquetbol", login);
            adapter.addFrag(new ListContinentCountriesFragment(), 3,  "Béisbol", login);
            adapter.addFrag(new ListContinentCountriesFragment(), 4,  "Fútbol Americano", login);
           // adapter.addFrag(new ListContinentCountriesFragment(), 5, "Box", login);
            //adapter.addFrag(new ListContinentCountriesFragment(), 6, "Tenis", login);
            //adapter.addFrag(new ListContinentCountriesFragment(), 7, "Hockey", login);
            //adapter.addFrag(new ListContinentCountriesFragment(), 8, "Voleibol", login);
            /**/
            adapter.addFrag(new ListContinentCountriesFragment(), 10,  "UFC", login);
            adapter.addFrag(new ListContinentCountriesFragment(), 9,  "CPMX8", login);

            /*
            adapter.addFrag(new MatchFragment(), 2,  "Básquetbol", login);
            adapter.addFrag(new MatchFragment(), 3,  "Béisbol", login);
            adapter.addFrag(new MatchFragment(), 4,  "Fútbol Americano", login);
            adapter.addFrag(new MatchFragment(), 5, "Box", login);
            adapter.addFrag(new MatchFragment(), 6, "Tenis", login);
            adapter.addFrag(new MatchFragment(), 7, "Hockey", login);
            adapter.addFrag(new MatchFragment(), 8, "Voleibol", login);
            */
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);

            TextView tabSoccer = (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabSoccer.setText("Soccer");
            tabSoccer.setGravity(Gravity.CENTER_VERTICAL);
            tabSoccer.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_soccer, 0, 0, 0);
            tabSoccer.setCompoundDrawablePadding(15);
            tabLayout.getTabAt(0).setCustomView(tabSoccer);

            /*
            TextView tabBasquetbol = (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabBasquetbol.setText("Básquetbol");
            tabBasquetbol.setGravity(Gravity.CENTER_VERTICAL);
            tabBasquetbol.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_basketball, 0, 0, 0);
            tabBasquetbol.setCompoundDrawablePadding(15);
            tabLayout.getTabAt(1).setCustomView(tabBasquetbol);
            */

            TextView tabBeisbol= (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabBeisbol.setText("Béisbol");
            tabBeisbol.setGravity(Gravity.CENTER_VERTICAL);
            tabBeisbol.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_baseball, 0, 0, 0);
            tabBeisbol.setCompoundDrawablePadding(15);
            tabLayout.getTabAt(1).setCustomView(tabBeisbol);

            TextView tabFutbol= (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabFutbol.setText("Fútbol Americano");
            tabFutbol.setGravity(Gravity.CENTER_VERTICAL);
            tabFutbol.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_football, 0, 0, 0);
            tabFutbol.setCompoundDrawablePadding(15);
            tabLayout.getTabAt(2).setCustomView(tabFutbol);

            /*
            TextView tabBox = (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabBox.setText("Box");
            tabBox.setGravity(Gravity.CENTER_VERTICAL);
            tabBox.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_box, 0, 0, 0);
            tabBox.setCompoundDrawablePadding(15);
            tabLayout.getTabAt(4).setCustomView(tabBox);

            TextView tabTenis= (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabTenis.setText("Tenis");
            tabTenis.setGravity(Gravity.CENTER_VERTICAL);
            tabTenis.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_tennis, 0, 0, 0);
            tabTenis.setCompoundDrawablePadding(15);
            tabLayout.getTabAt(5).setCustomView(tabTenis);

            TextView tabHockey= (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabHockey.setText("Hockey");
            tabHockey.setGravity(Gravity.CENTER_VERTICAL);
            tabHockey.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_hockey, 0, 0, 0);
            tabHockey.setCompoundDrawablePadding(15);
            tabLayout.getTabAt(6).setCustomView(tabHockey);

            TextView tabVoleibol= (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabVoleibol.setText("Voleibol");
            tabVoleibol.setGravity(Gravity.CENTER_VERTICAL);
            tabVoleibol.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_boleyball, 0, 0, 0);
            tabVoleibol.setCompoundDrawablePadding(15);
            tabLayout.getTabAt(7).setCustomView(tabVoleibol);
            */

            TextView tabUFC= (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            tabUFC.setText("UFC");
            tabUFC.setGravity(Gravity.CENTER_VERTICAL);
            tabUFC.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_ufc, 0, 0, 0);
            tabUFC.setCompoundDrawablePadding(15);
            tabLayout.getTabAt(3).setCustomView(tabUFC);

            TextView CPMX8= (TextView) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
            CPMX8.setText("CPMX8");
            CPMX8.setGravity(Gravity.CENTER_VERTICAL);
            CPMX8.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_cpmx, 0, 0, 0);
            CPMX8.setCompoundDrawablePadding(15);
            tabLayout.getTabAt(4).setCustomView(CPMX8);

 /**/
        }

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
        inflater.inflate(R.menu.menu_main, menu);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title_home));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void init(){    }
}
