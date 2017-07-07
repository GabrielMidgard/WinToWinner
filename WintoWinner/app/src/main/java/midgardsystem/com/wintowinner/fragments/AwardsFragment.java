package midgardsystem.com.wintowinner.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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
import midgardsystem.com.wintowinner.adapter.AwardAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Award;
import midgardsystem.com.wintowinner.utils.AppConfig;

/**
 * Created by Gabriel on 10/10/2016.
 */
public class AwardsFragment   extends Fragment implements View.OnClickListener {
    View rootView;
    RecyclerView recyclerView;
    Context context;
    DatabaseHelper dbHelper;

    public List<Award> AwardsaList = new ArrayList<Award>();
    private ProgressDialog loading;


    public AwardsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        this.context = (FragmentActivity) activity;
        super.onAttach(activity);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_store));
        dbHelper = new DatabaseHelper(context);

        loading = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        loading.setMessage("Cargando apuestas...");
        loading.show();

        getAwards();

        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_store));
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

    public void getAwards(){
        RequestQueue rq = Volley.newRequestQueue(context.getApplicationContext());
        String url= AppConfig.URL_GET_PRIZE;

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

        rq.add(stringRequest);
    }

    private void parseData(String response){
        try{

            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");

            if (success) {
                JSONArray valuesBetting= jObj.getJSONArray("result");

                for ( int i=0; i< valuesBetting.length(); i++) {
                    JSONObject obj = valuesBetting.getJSONObject(i);
                    final Award objAward = new Award();

                    //Get Award
                    objAward.setId(obj.getInt("id"));
                    objAward.setName(obj.getString("prizesName"));
                    objAward.setImg(obj.getString("prizesImg"));
                    objAward.setPriceCoins(obj.getInt("priceCoins"));

                    AwardsaList.add(objAward);
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
    public void initializeAdapter() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        setGridRecyclerView();
        recyclerView.setHasFixedSize(true);

        RelativeLayout voidList = (RelativeLayout) rootView.findViewById(R.id.voidList);
        ImageView imgIconMenu = (ImageView) rootView.findViewById(R.id.imgIconMenu);
        TextView lblMenuTitle = (TextView) rootView.findViewById(R.id.lblMenuTitle);
        TextView lblVoidDescription = (TextView) rootView.findViewById(R.id.lblVoidDescription);

        if(AwardsaList.size()>0) {
            voidList.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            recyclerView.setAdapter(new AwardAdapter(getContext(), AwardsaList, ((MainActivity) getActivity()).getUser(), dbHelper));

        }else {
            voidList.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            imgIconMenu.setImageResource(R.drawable.ic_notice);
            lblMenuTitle.setText(R.string.menu_news);
            lblVoidDescription.setText(R.string.void_new);
        }


    }

    public void setGridRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }


    @Override
    public void onClick(View v) {

    }
}