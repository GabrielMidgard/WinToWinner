package midgardsystem.com.wintowinner.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.adapter.MyMatchAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Match;

/**
 * Created by Gabriel on 22/12/2016.
 */
public class MyMatchFragment  extends Fragment {
    View rootView;
    public static DatabaseHelper dbHelper;
    public static SQLiteDatabase database;

    private RecyclerView recyclerView;
    Context context;
    List<Match> matchItem;
    MyMatchAdapter adapter;

    RelativeLayout voidList;
    TextView lblMenuTitle;
    TextView lblVoidDescription;

    private StaggeredGridLayoutManager staggeredLayoutManager;



    public MyMatchFragment() {
    }

    @SuppressLint("ValidFragment")
    public MyMatchFragment(List<Match> matchItem){
        this.matchItem=matchItem;

    }

    @Override
    public void onAttach(Activity activity) {
        this.context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_my_match));
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        staggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        init();

        initializeAdapter();

        return rootView;
    }

    private void initializeAdapter() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        RelativeLayout voidList = (RelativeLayout) rootView.findViewById(R.id.voidList);

        ImageView imgIconMenu = (ImageView) rootView.findViewById(R.id.imgIconMenu);
        TextView lblMenuTitle = (TextView) rootView.findViewById(R.id.lblMenuTitle);
        TextView lblVoidDescription = (TextView) rootView.findViewById(R.id.lblVoidDescription);

        if(matchItem.size()>0) {
            voidList.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setLayoutManager(staggeredLayoutManager);
            recyclerView.setHasFixedSize(true); //Data size is fixed - improves performance

            adapter = new MyMatchAdapter(getActivity(), matchItem);
            recyclerView.setAdapter(adapter);
        }else{

            voidList.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            imgIconMenu.setImageResource(R.drawable.ic_my_match);
            lblMenuTitle.setText(R.string.menu_my_interests);
            lblVoidDescription.setText(R.string.void_my_interests);

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
        //  ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title_fragment_contacts));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public static List<Match> getAllMatchItems(int idLeague){
        List<Match> listChildItem = new ArrayList<>();
        try {
            listChildItem=dbHelper.getMatchByLeagueId(database, idLeague);
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

        return listChildItem;
    }

    public void init() {
        voidList = (RelativeLayout) rootView.findViewById(R.id.voidList);
        lblMenuTitle = (TextView) rootView.findViewById(R.id.lblMenuTitle);
        lblVoidDescription = (TextView) rootView.findViewById(R.id.lblVoidDescription);

        lblMenuTitle.setText("Mis Partidos");
        //lblVoidDescription.setText("");
    }
}
