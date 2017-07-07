package midgardsystem.com.wintowinner.fragments.interests;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.adapter.PreferenceLeagueAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.League;

/**
 * Created by Gabriel on 19/12/2016.
 */
public class PreferencesLeaguesFragment extends Fragment implements View.OnClickListener {
    View rootView;
    RecyclerView recyclerView;
    private List<League> listLeagues = new ArrayList<League>();

    int idSport;
    private PreferenceLeagueAdapter adapter;
    DatabaseHelper dbHelper;
    Context context;

    RelativeLayout voidList;
    TextView lblMenuTitle;
    TextView lblVoidDescription;

    public PreferencesLeaguesFragment(){}
    @SuppressLint("ValidFragment")
    public PreferencesLeaguesFragment(int idSport){
        this.idSport=idSport;
    }
    @Override
    public void onAttach(Activity activity) {
        this.context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        dbHelper = new DatabaseHelper(context);

        init();
        initializeData();
        if(listLeagues.size()>0){
            initializeAdapter();
        }

        return rootView;
    }
    PreferenceLeagueAdapter.OnItemClickListener onItemClickListener = new PreferenceLeagueAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position, League objLeague) {
            // カードをクリックした時の処理

            Fragment fragment = new PreferenceTeamsFragment(listLeagues.get(position).getId());

            String TAG = "TEST_A";
            FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentManager.setCustomAnimations(R.anim.slide_zoom_back_in, R.anim.slide_zoom_back_out);


            fragmentManager.replace(R.id.main_content, fragment, TAG);
            fragmentManager.addToBackStack(TAG);
            fragmentManager.commit();
            //Toast.makeText(getContext(), listLeagues.get(position).getNameLeague() + " clicked", Toast.LENGTH_SHORT).show();

        }
    };
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
    public void onDestroyView() {
        super.onDestroyView();
    }



    public void initializeAdapter() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setVisibility(View.VISIBLE);
        voidList.setVisibility(View.GONE);

        setLinearRecyclerView();
        recyclerView.setHasFixedSize(true);

        adapter = new PreferenceLeagueAdapter(getContext(), listLeagues);
        recyclerView.setAdapter(adapter);

        //recyclerView.setAdapter(new InterestLeagueAdapter(getContext(), listLeagues));
        adapter.setOnItemClickListener(onItemClickListener);

    }


    public void setLinearRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    private void initializeData(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            listLeagues = dbHelper.getLeaguesBySportId(database, idSport);
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
    }

    public void init() {
        voidList = (RelativeLayout) rootView.findViewById(R.id.voidList);
        lblMenuTitle = (TextView) rootView.findViewById(R.id.lblMenuTitle);
        lblVoidDescription = (TextView) rootView.findViewById(R.id.lblVoidDescription);

        lblMenuTitle.setText("Ligas de Preferencias");
        //lblVoidDescription.setText("");
    }

    @Override
    public void onClick(View v) {

    }
}
