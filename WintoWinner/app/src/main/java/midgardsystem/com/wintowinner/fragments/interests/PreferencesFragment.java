package midgardsystem.com.wintowinner.fragments.interests;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import midgardsystem.com.wintowinner.adapter.SportAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Sport;

/**
 * Created by Gabriel on 30/11/2016.
 */
public class PreferencesFragment extends Fragment {
    View rootView;
    DatabaseHelper dbHelper;
    RecyclerView recyclerView;
    private List<Sport> listSports;
    Context context;

    public PreferencesFragment(){}

    @Override
    public void onAttach(Activity activity) {
        this.context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_preferences));
        dbHelper = new DatabaseHelper(context);
        init();
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

    public void init(){
        listSports = new ArrayList<>();
        initializeData();
        initializeAdapter();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void initializeAdapter() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        setGridRecyclerView();
        recyclerView.setHasFixedSize(true);

        RelativeLayout voidList = (RelativeLayout) rootView.findViewById(R.id.voidList);
        ImageView imgIconMenu = (ImageView) rootView.findViewById(R.id.imgIconMenu);
        TextView lblMenuTitle = (TextView) rootView.findViewById(R.id.lblMenuTitle);
        TextView lblVoidDescription = (TextView) rootView.findViewById(R.id.lblVoidDescription);

        if(listSports.size()>0) {
            voidList.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setAdapter(new SportAdapter(getContext(), listSports));

            /*
            SportAdapter.OnItemClickListener onItemClickListener = new SportAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    //confirmAcceptBets(position);
                }
            };*/
        }else {
            voidList.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            imgIconMenu.setImageResource(R.drawable.ic_preferences);
            lblMenuTitle.setText(R.string.menu_preferences);
            lblVoidDescription.setText(R.string.void_new);
        }


    }

    public void setGridRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }


    private void initializeData(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            listSports = dbHelper.getAllSports(database);
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
    }

}
