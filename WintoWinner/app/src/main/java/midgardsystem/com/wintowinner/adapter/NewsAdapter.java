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
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.News;

/**
 * Created by Gabriel on 05/10/2016.
 */
public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    Context context;
    public static List<News> newsItems;
    OnItemClickListener onItemClickListener;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();
    static DatabaseHelper dbHelper;

    public NewsAdapter(Context context) {
        this.context = context;
    }


    public NewsAdapter(Context context, List<News> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        dbHelper = new DatabaseHelper(context);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (imageLoader == null) {
            imageLoader = WintoWinnerApp.getInstance().getImageLoader();
        }

        holder.thumbnailNews.setImageUrl(newsItems.get(position).getImg(), imageLoader);

        holder.txtHeader.setText(newsItems.get(position).getHeader());

    }

    @Override
    public int getItemCount() {
        //return (bettingItems.size()!= 0 ? bettingItems.size() : 0);
        return newsItems.size();
    }


    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cv;
        public NetworkImageView thumbnailNews;
        public TextView txtHeader;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);

            txtHeader = (TextView) itemView.findViewById(R.id.txtHeader);

            thumbnailNews = (NetworkImageView) itemView.findViewById(R.id.thumbnailNews);
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


