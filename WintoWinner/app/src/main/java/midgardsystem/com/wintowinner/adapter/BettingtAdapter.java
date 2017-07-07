package midgardsystem.com.wintowinner.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Betting;
import midgardsystem.com.wintowinner.utils.CirculaireNetworkImageView;

/**
 * Created by Gabriel on 27/08/2016.
 */
public class BettingtAdapter extends RecyclerView.Adapter<BettingtAdapter.ViewHolder>{
    Context context;
    public static List<Betting> bettingItems;
    OnItemClickListener onItemClickListener;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();
    static DatabaseHelper dbHelper;

    public BettingtAdapter(Context context) {
        this.context = context;
    }


    public BettingtAdapter(Context context, List<Betting> bettingItems) {
        this.context = context;
        this.bettingItems = bettingItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_betting, parent, false);
        dbHelper = new DatabaseHelper(context);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (imageLoader == null) {
            imageLoader = WintoWinnerApp.getInstance().getImageLoader();
        }

        holder.thumbnailUser.setImageUrl(bettingItems.get(position).getUser().getImgUser(), imageLoader);

        holder.txtTeam.setText(bettingItems.get(position).getTeam().getName());
        holder.txtBetting.setText(bettingItems.get(position).getBetting()+"");

    }

    @Override
    public int getItemCount() {
        //return (bettingItems.size()!= 0 ? bettingItems.size() : 0);
        return bettingItems.size();
    }


    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cv;
        public CirculaireNetworkImageView thumbnailUser;
        public TextView txtTeam;
        public TextView txtBetting;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);

            txtTeam = (TextView) itemView.findViewById(R.id.txtTeam);
            txtBetting = (TextView) itemView.findViewById(R.id.txtBetting);
            //thumbnailUser = (NetworkImageView) ite mView.findViewById(R.id.thumbnailUser);
            thumbnailUser = (CirculaireNetworkImageView) itemView.findViewById(R.id.thumbnailUser);
            cv.setOnClickListener(this);
            cv.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));
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


