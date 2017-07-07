package midgardsystem.com.wintowinner.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Award;
import midgardsystem.com.wintowinner.objects.User;
import midgardsystem.com.wintowinner.utils.AppConfig;

/**
 * Created by Gabriel on 10/10/2016.
 */
public class AwardAdapter  extends RecyclerView.Adapter<AwardAdapter.ViewHolder> {
    private List<Award> listAwards;
    private Context context;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();
    public Typeface fontCalibri;
    User user;
    DatabaseHelper dbHelper;

    public AwardAdapter(Context context, List<Award> listAwards, User user, DatabaseHelper dbHelper) {
        this.context = context;
        this.listAwards = listAwards;
        this.user=user;
        this.dbHelper=dbHelper;
        //fontCalibri = Typeface.createFromAsset(context.getAssets(), "fonts/Calibri.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = mLayoutInflater.inflate(R.layout.recycler_list, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_award, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtName.setText(listAwards.get(position).getName()+"");
        holder.txtCostCoins.setText(listAwards.get(position).getPriceCoins()+"");

        //holder.txtName.setText(listSports.get(position).getSportName());
        //int resourceId = context.getResources().getIdentifier("drawable/" + listAwards.get(position).getImg(), null, context.getPackageName());
        //holder.cardArticleImage.setImageResource(resourceId);

        holder.thumbnailArticle.setImageUrl(listAwards.get(position).getImg(), imageLoader);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getCoins()>=listAwards.get(position).getPriceCoins()){
                    AwardConfirm(listAwards.get(position));
                }
                else {
                    Toast.makeText(context, listAwards.get(position).getName() + " clicked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAwards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        TextView txtCostCoins;
        NetworkImageView thumbnailArticle;
        CardView cardView;
        RecyclerView recyclerView;

        public ViewHolder(View v) {
            super(v);
            txtName = (TextView) v.findViewById(R.id.txtArticleTitle);
            txtCostCoins = (TextView) v.findViewById(R.id.txtCostCoins);
            thumbnailArticle= (NetworkImageView)v.findViewById(R.id.thumbnailArticle);

            if (imageLoader == null) {
                imageLoader = WintoWinnerApp.getInstance().getImageLoader();
            }

            cardView = (CardView) v.findViewById(R.id.cardView);
            recyclerView = (RecyclerView) v.findViewById(R.id.list);

            txtName.setTypeface(fontCalibri);
            txtCostCoins.setTypeface(fontCalibri);
        }
    }


    public void AwardConfirm(final Award award) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.confirm));
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.message_ConfirmAcceptAward));
        alertDialogBuilder.setCancelable(true);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                sendMyWishPrize(award);
            }
        });

        alertDialogBuilder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void sendMyWishPrize(final Award award){
        RequestQueue rq = Volley.newRequestQueue(context);
        user.setEmail(user.getEmail().replace(" ", ""));
        user.setPsw(user.getPsw().replace(" ", ""));

        String url= AppConfig.URL_SET_PRIZE+"&idUser="+user.getIdUser()+"&idPrize="+award.getId();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        parseData(response, award);
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

    private void parseData(String response, Award award){
        try{

            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");

            if (success) {

                SQLiteDatabase database = dbHelper.getWritableDatabase();
                try {
                    dbHelper.insertPrize(database, award, 0);
                }
                catch(Exception ex) {
                    Log.e("ERROR", ex.getMessage());
                }

            }else{
                String errorMsg = jObj.getString("msg");
                //encodeMessage(errorMsg);

                //Snackbar.make(rootView, R.string.acepted_terms, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                //Snackbar.make(rootView, errorMsg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        }  catch (JSONException ex){
            Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}




