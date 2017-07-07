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

import java.util.ArrayList;
import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.adapter.LeaguesAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Countries;
import midgardsystem.com.wintowinner.objects.League;
import midgardsystem.com.wintowinner.objects.Match;
import midgardsystem.com.wintowinner.objects.Sport;
import midgardsystem.com.wintowinner.utils.AnimatedExpandableListView;

/**
 * Created by Gabriel on 20/08/2016.
 */
public class MatchFragment extends Fragment {
    View rootView;
    private AnimatedExpandableListView listView;
    private LeaguesAdapter adapter;
    public static Context context;
    public static Sport sport;
    public static Countries countrie;
    static DatabaseHelper dbHelper;



    public MatchFragment(){}
    @Override
    public void onAttach(Activity activity) {
        this.context=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_animated_expandable, container, false);
        dbHelper = new DatabaseHelper(context);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title_match ));
        sport=new Sport();
        countrie= new Countries();
        Bundle args = getArguments();
        if (args  != null) {
            sport.setIdSport(args.getInt("idSport"));
            sport.setSportName(args.getString("nameSport"));

            countrie.setId(args.getInt("idCountrie"));
            countrie.setNameCountries(args.getString("nameCountrie"));

        }

        // Populate our list with groups and it's children
        List<League> groupLeagueItem =  getAllGroupLeagueItems();
        for(int i = 0; i < groupLeagueItem.size(); i++) {
            List<Match> matchItem =  getAllMatchItems(groupLeagueItem.get(i).getId());
            groupLeagueItem.get(i).setMatchItem(matchItem);
        }

        adapter = new LeaguesAdapter(getActivity(), groupLeagueItem);
        //adapter.setData(groupLeagueItem);

        listView = (AnimatedExpandableListView) rootView.findViewById(R.id.listView);
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
        inflater.inflate(R.menu.menu_main, menu);
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title_match ));
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    public static List<League> getAllGroupLeagueItems(){
        List<League> listGroupItem = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            listGroupItem=dbHelper.getLeaguesByCountriesId(database, countrie.getId(), sport.getIdSport());
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return listGroupItem;

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
