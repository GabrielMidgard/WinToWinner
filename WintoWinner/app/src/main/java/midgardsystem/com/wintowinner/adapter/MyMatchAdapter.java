package midgardsystem.com.wintowinner.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.objects.Match;

/**
 * Created by Gabriel on 29/12/2016.
 */
public class MyMatchAdapter extends RecyclerView.Adapter<MyMatchAdapter.ViewHolder>{
    Context context;
    public static List<Match> matchItems;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();

    public MyMatchAdapter(Context context) {
        this.context = context;
    }


    public MyMatchAdapter(Context context, List<Match> matchItems) {
        this.context = context;
        this.matchItems = matchItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.thumbnailTeamLocal.setImageUrl(matchItems.get(position).getLocalImg(), imageLoader);
        holder.thumbnailTeamVisit.setImageUrl(matchItems.get(position).getVisitImg(), imageLoader);

        holder.txtDate.setText(matchItems.get(position).getDate());
        holder.txtHour.setText(matchItems.get(position).getHour());

        holder.txtTeamLocal.setText(matchItems.get(position).getLocalName());
        holder.txtTeamVisit.setText(matchItems.get(position).getVisitName());

        holder.match.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) view.getContext()).showBettings(matchItems.get(position).getId());
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        //return (bettingItems.size()!= 0 ? bettingItems.size() : 0);
        return matchItems.size();
    }


    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout match;
        NetworkImageView thumbnailTeamLocal;
        NetworkImageView thumbnailTeamVisit;
        TextView txtDate;
        TextView txtHour;
        TextView txtTeamLocal;
        TextView txtTeamVisit;

        public ViewHolder(View itemView) {
            super(itemView);
            match = (LinearLayout) itemView.findViewById(R.id.match);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            txtHour = (TextView) itemView.findViewById(R.id.txtHour);
            txtTeamLocal = (TextView) itemView.findViewById(R.id.txtTeamLocal);
            txtTeamVisit = (TextView) itemView.findViewById(R.id.txtTeamVisit);

            thumbnailTeamLocal= (NetworkImageView)itemView.findViewById(R.id.thumbnailTeamLocal);
            thumbnailTeamVisit= (NetworkImageView)itemView.findViewById(R.id.thumbnailTeamVisit);

            if (imageLoader == null) {
                imageLoader = WintoWinnerApp.getInstance().getImageLoader();
            }
        }
    }
}
