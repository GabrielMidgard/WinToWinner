package midgardsystem.com.wintowinner.fragments.interests;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.adapter.PreferenceTeamAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Team;
import midgardsystem.com.wintowinner.objects.User;
import midgardsystem.com.wintowinner.utils.AppConfig;

/**
 * Created by Gabriel on 19/12/2016.
 */
public class PreferenceTeamsFragment extends Fragment implements View.OnClickListener {
    View rootView;
    RecyclerView recyclerView;
    public List<Team> listTeams = new ArrayList<Team>();
    DatabaseHelper dbHelper;

    int idLeague;
    Context context;
    ProgressDialog progress;
    public PreferenceTeamAdapter adapter;

    RelativeLayout voidList;
    TextView lblMenuTitle;
    TextView lblVoidDescription;

    public PreferenceTeamsFragment(){}
    @SuppressLint("ValidFragment")
    public PreferenceTeamsFragment(int idLeague){
        this.idLeague=idLeague;
    }

    @Override
    public void onAttach(Activity activity) {
        this.context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        dbHelper = new DatabaseHelper(context);

        showProcessDialog();
        init();

        getTeamsByIdLeague(idLeague);

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
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void initializeAdapter() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setVisibility(View.VISIBLE);
        voidList.setVisibility(View.GONE);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);

        User user=((MainActivity)getActivity()).getUser();
        adapter = new PreferenceTeamAdapter(getContext(), dbHelper, listTeams, user.getIdUser());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);

        //recyclerView.setAdapter(new PreferenceTeamAdapter(getContext(), listTeams));

    }
    PreferenceTeamAdapter.OnItemClickListener onItemClickListener = new PreferenceTeamAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {

        }
    };


    public void getTeamsByIdLeague(int idLeague){
        RequestQueue rq = Volley.newRequestQueue(context.getApplicationContext());
        String url= AppConfig.URL_GET_TEAMS+idLeague;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response

                        parseDataTeam(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError ex) {
                        // Handle error
                        Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        int socketTimeout = 60000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        //rq.add(stringRequest);
        rq.add(stringRequest);

    }

    private void parseDataTeam(String response){

        progress.dismiss();
        try{

            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");
            //hidePDialog();
            if (success) {
                JSONArray valuesResult= jObj.getJSONArray("result");
                for ( int i=0; i< valuesResult.length(); i++) {
                    final Team objTeam = new Team();
                    JSONObject obj = valuesResult.getJSONObject(i);

                    //Get User
                    objTeam.setId(obj.getInt("id"));
                    objTeam.setName(obj.getString("name"));
                    objTeam.setImg(obj.getString("img"));
                    objTeam.setSelected(isTeamPreference(objTeam.getId()));


                    listTeams.add(objTeam);
                }

                initializeAdapter();
            }else{
                String errorMsg = jObj.getString("msg");
                //((MainActivity) getActivity()).showAlertDialog(getContext(), "", errorMsg, false);

                //encodeMessage(errorMsg);
            }

        }  catch (JSONException ex){
            //Toast.makeText(getContext(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            progress.dismiss();
        }
    }


    private void showProcessDialog() {
        progress = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        progress.setTitle("Cargando..");
        progress.setMessage("Esto podria tomar unos segundos...");
        progress.setCancelable(false);
        progress.show();
    }

    private int isTeamPreference(int idTeam){
        int isTeamPreference=0;

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        isTeamPreference= dbHelper.isTeamPreference(database, idTeam);

        return isTeamPreference;
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