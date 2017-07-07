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

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Movement;

/**
 * Created by Gabriel on 08/10/2016.
 */
public class MovementAdapter  extends RecyclerView.Adapter<MovementAdapter.ViewHolder>{
    Context context;
    public static List<Movement> movementItems;
    static DatabaseHelper dbHelper;

    public MovementAdapter(Context context) {
        this.context = context;
    }


    public MovementAdapter(Context context, List<Movement> movementItems) {
        this.context = context;
        this.movementItems = movementItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movements, parent, false);
        dbHelper = new DatabaseHelper(context);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.txtTypeMoviment.setText(movementItems.get(position).getSrtTypeMovement());
        holder.txtCoins.setText("$ " + movementItems.get(position).getBalance());

    }

    @Override
    public int getItemCount() {
        //return (bettingItems.size()!= 0 ? bettingItems.size() : 0);
        return movementItems.size();
    }


    public static String byIdString(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public TextView txtTypeMoviment;
        public TextView txtCoins;

        public ViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);

            txtTypeMoviment = (TextView) itemView.findViewById(R.id.txtTypeMoviment);
            txtCoins = (TextView) itemView.findViewById(R.id.txtCoins);
            cv.setCardBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }


    }

}


