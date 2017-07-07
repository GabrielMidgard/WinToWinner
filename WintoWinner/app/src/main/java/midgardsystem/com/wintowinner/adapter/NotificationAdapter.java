package midgardsystem.com.wintowinner.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Notification;

/**
 * Created by Gabriel on 05/11/2016.
 */
public class NotificationAdapter  extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{
    Context context;
    public static List<Notification> notificationItems;
    DatabaseHelper dbHelper;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();

    public NotificationAdapter(Context context) {
        this.context = context;
    }


    public NotificationAdapter(Context context, List<Notification> notificationItems) {
        this.context = context;
        this.notificationItems=notificationItems;
        //this.onItemClickListener = onItemClickListener;
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /*
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            dbHelper.getNotifications(database);
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }*/

        if (imageLoader == null) {
            imageLoader = WintoWinnerApp.getInstance().getImageLoader();
        }

        int typeNotification=notificationItems.get(position).getTypeNotification().getId();

        switch (typeNotification){
            //Bonificacion
            case 1:
                holder.notificationMovement.setVisibility(View.VISIBLE);
                holder.notificationParticipantRaffle.setVisibility(View.GONE);
                holder.txtTypeMoviment.setText(notificationItems.get(position).getTypeNotification().getTypeNotification());
                holder.txtCoins.setText(notificationItems.get(position).getBalanceCoins()+"");

            break;

            // Rifa
            case 2:
                holder.notificationParticipantRaffle.setVisibility(View.VISIBLE);
                holder.notificationMovement.setVisibility(View.GONE);
                //holder.lblCongratulations;
                holder.thumbnailPrice.setImageUrl(notificationItems.get(position).getPrizeImg(), imageLoader);
                holder.txtPriceName.setText(notificationItems.get(position).getPrizeName());

                //holder.txtPriceName.setText("Bla bla lba");
                holder.txtDescripcionTermsRaffle.setText(context.getResources().getString(R.string.descripcionTermsRaffle_parcipant));
            break;

            // Info
            case 3:
            break;

            // Ganador Rifa
            case 4:
                holder.notificationParticipantRaffle.setVisibility(View.VISIBLE);
                holder.notificationMovement.setVisibility(View.GONE);
                //holder.lblCongratulations;
                holder.thumbnailPrice.setImageUrl(notificationItems.get(position).getPrize().getPrizesImg(), imageLoader);
                holder.txtPriceName.setText(notificationItems.get(position).getPrize().getPrizesName());
                holder.txtDescripcionTermsRaffle.setText(context.getResources().getString(R.string.descripcionTermsRaffle_winner));
            break;
        }


    }

    @Override
    public int getItemCount() {
        //return (bettingItems.size()!= 0 ? bettingItems.size() : 0);
        return notificationItems.size();
    }

    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //implements View.OnClickListener
        public CardView cv;

        RelativeLayout notificationMovement;
        TextView txtTypeMoviment;
        TextView txtCoins;

        RelativeLayout notificationParticipantRaffle;
        TextView lblCongratulations;
        NetworkImageView thumbnailPrice;
        TextView txtPriceName;
        TextView txtDescripcionTermsRaffle;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);

            notificationMovement = (RelativeLayout) itemView.findViewById(R.id.notificationMovement);
            txtTypeMoviment = (TextView) itemView.findViewById(R.id.txtTypeMoviment);
            txtCoins = (TextView) itemView.findViewById(R.id.txtCoins);


            notificationParticipantRaffle = (RelativeLayout) itemView.findViewById(R.id.notificationParticipantRaffle);
            lblCongratulations = (TextView) itemView.findViewById(R.id.lblCongratulations);
            thumbnailPrice = (NetworkImageView) itemView.findViewById(R.id.thumbnailPrice);
            txtPriceName = (TextView) itemView.findViewById(R.id.txtPriceName);
            txtDescripcionTermsRaffle = (TextView) itemView.findViewById(R.id.txtDescripcionTermsRaffle);

            //cv.setOnClickListener(this);
            cv.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));
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

