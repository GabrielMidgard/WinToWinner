package midgardsystem.com.wintowinner.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.adapter.NewsAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.News;
import midgardsystem.com.wintowinner.utils.AppConfig;

/**
 * Created by Gabriel on 05/10/2016.
 */
public class NewsFragment  extends Fragment {
    View rootView;
    DatabaseHelper dbHelper;
    private RecyclerView recyclerView;

    public List<News> NewsList = new ArrayList<News>();
    private StaggeredGridLayoutManager staggeredLayoutManager;
    public NewsAdapter adapter;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();
    Context context;



    private ProgressDialog loading;
    public static final int DIALOG_FRAGMENT = 1;

    public NewsFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        this.context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_recycler, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_news));
        dbHelper = new DatabaseHelper(context);

        staggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        loading = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        loading.setMessage( getResources().getString(R.string.load_news));
        loading.show();

        getNews();

        return rootView;
    }

    private void initializeAdapter() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(staggeredLayoutManager);
        recyclerView.setHasFixedSize(true); //Data size is fixed - improves performance

        RelativeLayout voidList = (RelativeLayout) rootView.findViewById(R.id.voidList);
        ImageView imgIconMenu = (ImageView) rootView.findViewById(R.id.imgIconMenu);
        TextView lblMenuTitle = (TextView) rootView.findViewById(R.id.lblMenuTitle);
        TextView lblVoidDescription = (TextView) rootView.findViewById(R.id.lblVoidDescription);
        if(NewsList.size()>0) {
            voidList.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

            adapter = new NewsAdapter(getActivity(), NewsList);
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(onItemClickListener);
        }else {
            voidList.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            imgIconMenu.setImageResource(R.drawable.ic_notice);
            lblMenuTitle.setText(R.string.menu_news);
            lblVoidDescription.setText(R.string.void_new);
        }
    }


    NewsAdapter.OnItemClickListener onItemClickListener = new NewsAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            showNewsDetails(position);
        }
    };

    private void showNewsDetails(int position) {
        Fragment fragment = null;
        fragment = new NewDetailsFragment(NewsList.get(position));
        String TAG_HOME="";

        if (fragment != null) {

            FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();

            fragmentManager.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentManager.replace(R.id.main_content, fragment, TAG_HOME);
            fragmentManager.addToBackStack(null);
            fragmentManager.commit();

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

    @Override
    public void onStart() {
        super.onStart();
    }



    public void getNews(){
        RequestQueue rq = Volley.newRequestQueue(context.getApplicationContext());
        String url= AppConfig.URL_GET_NEWS;

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
            hidePDialog();
            if (success) {
                JSONArray valuesBetting= jObj.getJSONArray("result");
                for ( int i=0; i< valuesBetting.length(); i++) {
                    final News objNews = new News();

                    JSONObject obj = valuesBetting.getJSONObject(i);

                    //Get News
                    objNews.setId(obj.getInt("id"));
                    objNews.setHeader(obj.getString("header"));
                    objNews.setSubeader(obj.getString("subheader"));
                    objNews.setContent(obj.getString("content"));
                    objNews.setImg(obj.getString("img"));
                    objNews.setCreated(obj.getString("created"));
                    objNews.setIdSport(obj.getInt("fk_sport"));

                    // adding speaker to speakers array
                    NewsList.add(objNews);
                    //adapter.notifyDataSetChanged();
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


}


