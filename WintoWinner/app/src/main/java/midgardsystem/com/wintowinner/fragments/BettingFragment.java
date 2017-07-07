package midgardsystem.com.wintowinner.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.adapter.BettingtAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.dialog.LoginDialog;
import midgardsystem.com.wintowinner.dialog.NewBettingDialog;
import midgardsystem.com.wintowinner.dialog.TeamSelectionDialog;
import midgardsystem.com.wintowinner.objects.Betting;
import midgardsystem.com.wintowinner.objects.Match;
import midgardsystem.com.wintowinner.objects.Team;
import midgardsystem.com.wintowinner.objects.User;
import midgardsystem.com.wintowinner.utils.AppConfig;

/**
 * Created by Gabriel on 27/08/2016.
 */
public class BettingFragment extends Fragment {
    View rootView;
    DatabaseHelper dbHelper;
    private RecyclerView recyclerView;

    public List<Betting> bettingList = new ArrayList<Betting>();


    private StaggeredGridLayoutManager staggeredLayoutManager;
    public BettingtAdapter adapter;
    private Handler mHandler = new Handler();
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();
    public Match match;
    Context context;

    private FloatingActionMenu menuRed;
    private com.github.clans.fab.FloatingActionButton btnFabNew;
    private com.github.clans.fab.FloatingActionButton btnFabSearch;
    private com.github.clans.fab.FloatingActionButton fab3;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();


    private ProgressDialog loading;
    public static final int DIALOG_FRAGMENT = 1;

    public BettingFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        this.context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_betting, container, false);
        dbHelper = new DatabaseHelper(context);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title_betting));
        match = new Match();
        Bundle args = getArguments();
        if (args != null) {
            match.setId(args.getInt("idMatch"));
        }

        menuRed = (FloatingActionMenu) rootView.findViewById(R.id.menu_red);
        btnFabNew = (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.btnFabNew);
        btnFabSearch = (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.btnFabSearch);

        menuRed.setClosedOnTouchOutside(true);
        menuRed.hideMenuButton(false);


        getMatch();
        init();
        staggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        loading = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        loading.setMessage("Cargando apuestas...");
        loading.show();

        getBetting();

        return rootView;
    }

    private void initializeAdapter() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(staggeredLayoutManager);
        recyclerView.setHasFixedSize(true); //Data size is fixed - improves performance

        adapter = new BettingtAdapter(getActivity(), bettingList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
    }


    BettingtAdapter.OnItemClickListener onItemClickListener = new BettingtAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            confirmAcceptBets(position);
        }
    };

    private void confirmAcceptBets(int position) {
        User user =((MainActivity) getActivity()).getUser();
        if( user != null){
            if(user.getId()!= bettingList.get(position).getUser().getId()){
                int betting = bettingList.get(position).getBetting();
                if (betting <= user.getCoins()) {
                    // seleccionar al equipo contrario o empate
                    showTeamSelection(position);
                }else{
                    Snackbar.make(rootView, R.string.no_coins_betting, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }else{
                Snackbar.make(rootView, "No puedes aceptar una apuesta lanzada por ti", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        }else{
            showLoginDialog();
        }
//Snackbar.make(rootView, R.string.no_coins_betting, Snackbar.LENGTH_LONG).setAction("Action", null).show();


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
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title_betting));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        /*
        mHandler.postDelayed(new Runnable() {
            public void run() {
                //materialDesignFAM.showMenu(true);
            }
        }, 1000);
        */
    }


    public void getMatch() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            match = dbHelper.getMatchById(database, match.getId());
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
    }

    public void init() {
        Team teamHome = null;
        Team teamVisit = null;

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            teamHome = dbHelper.getTeamById(database, match.getFkTeamHome());
            teamVisit = dbHelper.getTeamById(database, match.getFkTeamVisit());
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

        NetworkImageView thumbnailTeamLocal = (NetworkImageView) rootView.findViewById(R.id.thumbnailTeamLocal);
        NetworkImageView thumbnailTeamVisit = (NetworkImageView) rootView.findViewById(R.id.thumbnailTeamVisit);
        TextView lblDate = (TextView) rootView.findViewById(R.id.lblDate);

        thumbnailTeamLocal.setImageUrl(teamHome.getImg(), imageLoader);
        thumbnailTeamVisit.setImageUrl(teamVisit.getImg(), imageLoader);
        lblDate.setText(match.getDate());


    }


    public void showLoginDialog() {
        // Create the fragment and show it as a dialog.
       /*
       FragmentManager fragmentManager = getFragmentManager();
       new LoginDialog().show(fragmentManager, "UpdateAccountGlobal");
        */
        LoginDialog eDialog = new LoginDialog(match);
        //NewBettingDialog eDialog = new NewBettingDialog();
        // eDialog.listEvidences=listEvidences;
        eDialog.show(getFragmentManager(), "tag_somepopup");
    }
    public void showNewBettingDialog() {
        // Create the fragment and show it as a dialog.
        NewBettingDialog eDialog = new NewBettingDialog( ((MainActivity) getActivity()).getUser(), match, bettingList, adapter);
        eDialog.show(getFragmentManager(), "tag_somepopup");
    }
    public void showTeamSelection(int position) {
        // Create the fragment and show it as a dialog.
        TeamSelectionDialog eDialog = new TeamSelectionDialog( ((MainActivity) getActivity()).getUser(), match, bettingList.get(position),  position, adapter, bettingList);
        eDialog.show(getFragmentManager(), "tag_somepopup");
    }

    public void getBetting(){
        RequestQueue rq = Volley.newRequestQueue(context.getApplicationContext());
        String url= AppConfig.URL_GET_BETTING_BY_MATCH+match.getId();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        loading.dismiss();
                        parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError ex) {
                        // Handle error
                        Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(stringRequest);
    }

    private void parseData(String response){
        try{

            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");
            hidePDialog();
            if (success) {
                JSONArray valuesBetting= jObj.getJSONArray("result");
                for ( int i=0; i< valuesBetting.length(); i++) {
                    final Betting objBetting = new Betting();

                    JSONObject obj = valuesBetting.getJSONObject(i);
                    User objUser = new User();
                    Team objTeam = new Team();

                    //Get User
                    JSONObject valuesUser= obj.getJSONObject("user");
                    objUser.setId(valuesUser.getInt("id"));
                    objUser.setName(valuesUser.getString("name"));
                    objUser.setLastNames(valuesUser.getString("lastname"));
                    objUser.setEmail(valuesUser.getString("email"));
                    //objUser.setPsw(valuesUser.getString("password"));
                    objUser.setImgUser(valuesUser.getString("img"));
                    // userList.add(objUser);

                    JSONObject valuesTeam= obj.getJSONObject("team");
                    objTeam.setId(valuesTeam.getInt("id"));
                    objTeam.setName(valuesTeam.getString("name"));
                    if(objTeam.getId()!= -3) {
                        objTeam.setImg(valuesTeam.getString("img"));
                    }

                    objBetting.setId(obj.getInt("id"));
                    objBetting.setBetting(obj.getInt("betting"));
                    objBetting.setFkMatch(obj.getInt("idMatch"));

                    objBetting.setUser(objUser);
                    objBetting.setTeam(objTeam);

                    // adding speaker to speakers array
                    bettingList.add(objBetting);
                   // adapter.notifyDataSetChanged();

                }

            }else{
                String errorMsg = jObj.getString("msg");
                //encodeMessage(errorMsg);
            }

        }  catch (JSONException ex){
            Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }


        initializeAdapter();

    }

    private void hidePDialog() {
        if (loading != null) {
            loading.dismiss();
            loading = null;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        menus.add(menuRed);

        btnFabNew.setOnClickListener(clickListener);
        btnFabSearch.setOnClickListener(clickListener);

        int delay = 400;
        for (final FloatingActionMenu menu : menus) {
            mUiHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    menu.showMenuButton(true);
                }
            }, delay);
            delay += 150;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //fabEdit.show(true);
            }
        }, delay + 150);

        menuRed.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuRed.isOpened()) {
                    //Toast.makeText(getActivity(), menuRed.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
                }

                menuRed.toggle(true);
            }
        });

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnFabNew:
                    User user = ((MainActivity) getActivity()).getUser();
                    menuRed.close(true);
                    if(user==null){
                        showLoginDialog();

                    }else{
                        showNewBettingDialog();
                    }

                    break;
                case R.id.btnFabSearch:
                    //fab2.setVisibility(View.GONE);
                    break;

            }
        }
    };


}

