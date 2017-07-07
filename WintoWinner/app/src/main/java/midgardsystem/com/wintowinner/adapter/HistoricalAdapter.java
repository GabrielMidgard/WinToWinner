package midgardsystem.com.wintowinner.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.AccessToken;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Historical;
import midgardsystem.com.wintowinner.objects.Team;

/**
 * Created by Gabriel on 10/10/2016.
 */
public class HistoricalAdapter  extends RecyclerView.Adapter<HistoricalAdapter.ViewHolder>{
    public Context context;
    public static List<Historical> HistoricalItems;
    //OnItemClickListener onItemClickListener;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();
    DatabaseHelper dbHelper;
    ShareDialog shareDialog;

    public HistoricalAdapter(Context context) {
        this.context = context;
    }


    public HistoricalAdapter(Context context, List<Historical> HistoricalItems) {
        this.context = context;
        this.HistoricalItems=HistoricalItems;
        //this.onItemClickListener = onItemClickListener;
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historical_bet, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Team teamHome = null;
        Team teamVisit = null;

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            teamHome = dbHelper.getTeamById(database, HistoricalItems.get(position).getFkTeamHome());
            teamVisit = dbHelper.getTeamById(database, HistoricalItems.get(position).getFkTeamVisit());
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

        if (imageLoader == null) {
            imageLoader = WintoWinnerApp.getInstance().getImageLoader();
        }

        holder.thumbnailTeamLocal.setImageUrl(teamHome.getImg(), imageLoader);
        holder.thumbnailTeamVisit.setImageUrl(teamVisit.getImg(), imageLoader);

        holder.txtDate.setText(HistoricalItems.get(position).getDate()+"");
        holder.txtHour.setText(HistoricalItems.get(position).getHour());
        //

        if(AccessToken.getCurrentAccessToken()==null){
            holder.btnShare.setVisibility(View.GONE);
        }else{
            holder.btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(ShareDialog.canShow(ShareLinkContent.class)){
                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                .setContentTitle("Win To Winner")
                                .setImageUrl(Uri.parse("http://wintowin.com.mx/_img/_social/fb.png"))
                                .setContentDescription("Comparte, Diviertete y siempre gana porque ganas")
                                //.setContentUrl(Uri.parse("https://developers.facebook.com"))
                                .build();
                        shareDialog.show(linkContent); //Show facebook ShareDialog
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //return (bettingItems.size()!= 0 ? bettingItems.size() : 0);
        return HistoricalItems.size();
    }

    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //implements View.OnClickListener
        public CardView cv;
        RelativeLayout btnShare;
        ImageView imgShare;
        NetworkImageView thumbnailTeamLocal;
        NetworkImageView thumbnailTeamVisit;
        TextView txtDate;
        TextView txtHour;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            btnShare = (RelativeLayout) itemView.findViewById(R.id.btnShare);
            imgShare = (ImageView) itemView.findViewById(R.id.imgShare);

            thumbnailTeamLocal = (NetworkImageView) itemView.findViewById(R.id.thumbnailTeamLocal);
            thumbnailTeamVisit = (NetworkImageView) itemView.findViewById(R.id.thumbnailTeamVisit);

            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtHour = (TextView) itemView.findViewById(R.id.txtHour);

            //cv.setOnClickListener(this);
            cv.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));
            shareDialog= new ShareDialog((Activity) context);
        }

        /*
        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(itemView, getPosition());
            }
        }
        */
    }

    /*
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }
    */
}

