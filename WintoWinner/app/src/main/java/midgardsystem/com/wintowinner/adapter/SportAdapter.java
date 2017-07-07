package midgardsystem.com.wintowinner.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.fragments.interests.PreferencesLeaguesFragment;
import midgardsystem.com.wintowinner.objects.Sport;

/**
 * Created by Gabriel on 30/11/2016.
 */
public class SportAdapter  extends RecyclerView.Adapter<SportAdapter.ViewHolder>{
    Context context;
    public static List<Sport> SportItems;
    OnItemClickListener onItemClickListener;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();
    static DatabaseHelper dbHelper;

    public SportAdapter(Context context) {
        this.context = context;
    }


    public SportAdapter(Context context, List<Sport> SportItems) {
        this.context = context;
        this.SportItems = SportItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport, parent, false);
        dbHelper = new DatabaseHelper(context);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        int resourceId = context.getResources().getIdentifier("mipmap/" + SportItems.get(position).getSportImg(), null, context.getPackageName());
        holder.sportImg.setImageResource(resourceId);

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // カードをクリックした時の処理

                Fragment fragment = new PreferencesLeaguesFragment(SportItems.get(position).getIdSport());

                String TAG = "TEST_A";
                FragmentTransaction fragmentManager = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentManager.setCustomAnimations(R.anim.slide_zoom_back_in, R.anim.slide_zoom_back_out);


                fragmentManager.replace(R.id.main_content, fragment, TAG);
                fragmentManager.addToBackStack(TAG);
                fragmentManager.commit();
                //Toast.makeText(context, SportItems.get(position).getSportName() + " clicked", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        //return (bettingItems.size()!= 0 ? bettingItems.size() : 0);
        return SportItems.size();
    }


    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public CardView cv;
        public ImageView sportImg;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            sportImg = (ImageView) itemView.findViewById(R.id.sportImg);
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

