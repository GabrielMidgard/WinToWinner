package midgardsystem.com.wintowinner.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.adapter.ContinentCountriesAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Continent;
import midgardsystem.com.wintowinner.objects.Countries;
import midgardsystem.com.wintowinner.objects.Sport;
import midgardsystem.com.wintowinner.utils.AnimatedExpandableListView;

/**
 * Created by Gabriel on 18/08/2016.
 */
public class ListContinentCountriesFragment extends Fragment {
    View rootView;
    public static DatabaseHelper dbHelper;
    private AnimatedExpandableListView listView;
    private RelativeLayout voidList;

    private ContinentCountriesAdapter adapter;
    public static Context context;
    public static Sport sport;

    public ListContinentCountriesFragment(){}
    @Override
    public void onAttach(Activity activity) {
        this.context=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_animated_expandable, container, false);
        dbHelper = new DatabaseHelper(context);
        sport=new Sport();
        Bundle args = getArguments();
        if (args  != null) {
            sport.setIdSport(args.getInt("idSport"));
            sport.setSportName(args.getString("nameSport"));
        }

        // Populate our list with groups and it's children ( Continentes )
        List<Continent> groupContinentItem =  getAllGroupContinentItems();
        for(int i = 0; i < groupContinentItem.size(); i++) {
            List<Countries> CountrieItem =  getAllCountriesItems(groupContinentItem.get(i).getId());
            if(CountrieItem.size()>0) {
                groupContinentItem.get(i).setCountrieItem(CountrieItem);
            }
        }

        listView = (AnimatedExpandableListView) rootView.findViewById(R.id.listView);
        voidList = (RelativeLayout) rootView.findViewById(R.id.voidList);

        if(groupContinentItem.size()>0) {
            voidList.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            initializeAdapter(groupContinentItem);
        }else{
            voidList.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            ImageView imgIconMenu = (ImageView) rootView.findViewById(R.id.imgIconMenu);
            TextView lblMenuTitle = (TextView) rootView.findViewById(R.id.lblMenuTitle);
            TextView lblVoidDescription = (TextView) rootView.findViewById(R.id.lblVoidDescription);

            imgIconMenu.setImageResource(R.drawable.ic_match);
            lblMenuTitle.setText(R.string.menu_meetings);
            lblVoidDescription.setText(R.string.void_meetings);
        }

        return rootView;
    }


    private void initializeAdapter(List<Continent> groupContinentItem){
        adapter = new ContinentCountriesAdapter(getActivity(), groupContinentItem, sport);
        //adapter.setData(groupLeagueItem);


        listView.setAdapter(adapter);

        // In order to show animations, we need to use a custom click handler
        // for our ExpandableListView.
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (listView.isGroupExpanded(groupPosition)) {
                    listView.collapseGroupWithAnimation(groupPosition);
                } else {
                    listView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });
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
        //  ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title_fragment_contacts));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    public static List<Continent> getAllGroupContinentItems(){
        List<Continent> listGroupItem = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        try {
            listGroupItem = dbHelper.getAllContinent(database, sport.getIdSport());
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return listGroupItem;


    }

    public static List<Countries> getAllCountriesItems(int idContinent){
        List<Countries> listChildItem = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        try {
            listChildItem = dbHelper.getAllCountriesByIdContinent(database, idContinent, sport.getIdSport());
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return listChildItem;
    }
}

