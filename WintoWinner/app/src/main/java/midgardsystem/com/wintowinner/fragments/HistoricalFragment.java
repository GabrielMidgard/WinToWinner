package midgardsystem.com.wintowinner.fragments;

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
import midgardsystem.com.wintowinner.adapter.HistoricalAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Historical;

/**
 * Created by Gabriel on 10/10/2016.
 */
public class HistoricalFragment   extends Fragment {
    View rootView;
    DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    Context context;

    RelativeLayout voidList;
    TextView lblMenuTitle;
    TextView lblVoidDescription;

    public List<Historical> historicalList = new ArrayList<Historical>();

    private StaggeredGridLayoutManager staggeredLayoutManager;
    public HistoricalAdapter adapter;


    public HistoricalFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        this.context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_my_bets));
        dbHelper = new DatabaseHelper(context);

        init();
        staggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        getHistoricals();
        if(historicalList.size()>0) {
            initializeAdapter();
        }

        return rootView;
    }

    private void initializeAdapter() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setVisibility(View.VISIBLE);
        voidList.setVisibility(View.GONE);

        recyclerView.setLayoutManager(staggeredLayoutManager);
        recyclerView.setHasFixedSize(true); //Data size is fixed - improves performance
        adapter = new HistoricalAdapter(getActivity(), historicalList);
        recyclerView.setAdapter(adapter);
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
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_my_bets));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void getHistoricals() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            historicalList = dbHelper.getHistoricals(database);
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

    }

    public void init() {
        voidList = (RelativeLayout) rootView.findViewById(R.id.voidList);
        lblMenuTitle = (TextView) rootView.findViewById(R.id.lblMenuTitle);
        lblVoidDescription = (TextView) rootView.findViewById(R.id.lblVoidDescription);

        lblMenuTitle.setText("Mis apuestas");
        //lblVoidDescription.setText("");
    }


}
