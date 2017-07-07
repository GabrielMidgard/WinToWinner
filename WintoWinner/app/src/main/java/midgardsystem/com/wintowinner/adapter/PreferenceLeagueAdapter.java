package midgardsystem.com.wintowinner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.objects.League;

/**
 * Created by Gabriel on 19/12/2016.
 */
public class PreferenceLeagueAdapter extends RecyclerView.Adapter<PreferenceLeagueAdapter.ViewHolder> {
    Context context;
    List<League> listLeagues;
    OnItemClickListener onItemClickListener;

    public PreferenceLeagueAdapter(Context context) {
        this.context = context;
    }


    public PreferenceLeagueAdapter(Context context, List<League> listLeagues) {
        this.context = context;
        this.listLeagues = listLeagues;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_league, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Picasso.with(context).load(listLeagues.get(position).getImageResourceId(context)).into(holder.placeImage);
        holder.cardArticleTitle.setText(listLeagues.get(position).getNameLeague());
    }

    @Override
    public int getItemCount() {
        return listLeagues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout placeHolder;
        public TextView cardArticleTitle;
        public ImageView placeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            placeHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            cardArticleTitle = (TextView) itemView.findViewById(R.id.cardArticleTitle);
            placeImage = (ImageView) itemView.findViewById(R.id.placeImage);
            placeHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(itemView, getPosition(), listLeagues.get(getPosition()));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, League objStandard);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }
}