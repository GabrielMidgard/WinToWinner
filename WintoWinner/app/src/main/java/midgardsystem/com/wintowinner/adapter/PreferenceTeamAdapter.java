package midgardsystem.com.wintowinner.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Preference;
import midgardsystem.com.wintowinner.objects.Team;
import midgardsystem.com.wintowinner.utils.AppConfig;

/**
 * Created by Gabriel on 19/12/2016.
 */
public class PreferenceTeamAdapter  extends RecyclerView.Adapter<PreferenceTeamAdapter.ViewHolder> {
    private List<Team> listTeams;
    private Context context;
    private DatabaseHelper dbHelper;
    OnItemClickListener onItemClickListener;
    private int idUser;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();


    public PreferenceTeamAdapter(Context context, DatabaseHelper dbHelper, List<Team> listTeams, int idUser) {
        this.context = context;
        this.dbHelper=dbHelper;
        this.listTeams = listTeams;
        this.idUser=idUser;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = mLayoutInflater.inflate(R.layout.recycler_list, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_p_team, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //holder.txtName.setText(listSports.get(position).getSportName());

        if (imageLoader == null) {
            imageLoader = WintoWinnerApp.getInstance().getImageLoader();
        }

        if(listTeams.get(position).isSelected()==1) {
            holder.cardLayout.setBackgroundColor(context.getResources().getColor(R.color.wintowin));
        }else{
            holder.cardLayout.setBackgroundColor(context.getResources().getColor(R.color.md_white_1000));
        }

        holder.thumbnailTeam.setImageUrl(listTeams.get(position).getImg(), imageLoader);
        holder.txtTeamName.setText(listTeams.get(position).getName());



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (listTeams.get(position).isSelected()){
                    case 1:
                        holder.cardLayout.setBackgroundColor(context.getResources().getColor(R.color.md_white_1000));
                        listTeams.get(position).setSelected(0);
                        deleteTeamPreference(listTeams.get(position).getId());
                    break;

                    case 0:
                        holder.cardLayout.setBackgroundColor(context.getResources().getColor(R.color.wintowin));
                        listTeams.get(position).setSelected(1);
                        addTeamPreference(listTeams.get(position).getId());
                    break;
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        return listTeams.size();
    }

    private void addTeamPreference(int idTeam){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Preference objPreference = new Preference(idTeam);
        dbHelper.insertRowPreferences(database, objPreference);
        sendServerAddTeamPreference(idTeam);
    }
    private void deleteTeamPreference(int idTeam){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        dbHelper.detelePreferencesByIdTeam(database, idTeam);
        sendServerDeleteTeamPreference(idTeam);
    }

    public void sendServerAddTeamPreference(int idTeam){
        RequestQueue rq = Volley.newRequestQueue(context);
        String url= AppConfig.URL_ADD_PREFERENCES+idUser+"&idTeam="+idTeam;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        //parseData(response, user);
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
    public void sendServerDeleteTeamPreference(int idTeam){
        RequestQueue rq = Volley.newRequestQueue(context);
        String url= AppConfig.URL_DELETE_PREFERENCES+idUser+"&idTeam="+idTeam;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        //parseData(response, user);
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

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        //TextView txtTitleTeam;
        NetworkImageView thumbnailTeam;
        TextView txtTeamName;
        CardView cardView;
        RelativeLayout cardLayout;

        public ViewHolder(View v) {
            super(v);
            txtTeamName = (TextView) v.findViewById(R.id.txtTeamName);
            thumbnailTeam = (NetworkImageView) v.findViewById(R.id.thumbnailTeam);
            cardLayout = (RelativeLayout) v.findViewById(R.id.cardLayout);
            cardView = (CardView) v.findViewById(R.id.cardView);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

}



