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
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.objects.Award;

/**
 * Created by Gabriel on 22/11/2016.
 */
public class MyPrizeAdapter  extends RecyclerView.Adapter<MyPrizeAdapter.ViewHolder>{
    Context context;
    public static List<Award> awardItems;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();

    public MyPrizeAdapter(Context context) {
        this.context = context;
    }


    public MyPrizeAdapter(Context context, List<Award> awardItems) {
        this.context = context;
        this.awardItems = awardItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_prize, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtStatus.setText(awardItems.get(position).getStatusPrize()+"");
        holder.txtPrize.setText(awardItems.get(position).getName()+"");

        holder.thumbnailPrize.setImageUrl(awardItems.get(position).getImg(), imageLoader);

    }

    @Override
    public int getItemCount() {
        //return (bettingItems.size()!= 0 ? bettingItems.size() : 0);
        return awardItems.size();
    }


    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView txtStatus;
        public TextView txtPrize;
        public NetworkImageView thumbnailPrize;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);

            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtPrize = (TextView) itemView.findViewById(R.id.txtPrize);
            thumbnailPrize= (NetworkImageView)itemView.findViewById(R.id.thumbnailPrize);

            if (imageLoader == null) {
                imageLoader = WintoWinnerApp.getInstance().getImageLoader();
            }
            cv.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }


    }

}


