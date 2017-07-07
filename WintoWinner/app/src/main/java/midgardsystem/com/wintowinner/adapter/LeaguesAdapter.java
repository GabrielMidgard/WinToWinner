package midgardsystem.com.wintowinner.adapter;

import android.content.Context;
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
import midgardsystem.com.wintowinner.objects.League;
import midgardsystem.com.wintowinner.objects.Match;
import midgardsystem.com.wintowinner.utils.AnimatedExpandableListView;

/**
 * Created by Gabriel on 20/08/2016.
 */
public class LeaguesAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;
    Context context;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();
    private List<League> items;

    public LeaguesAdapter(Context context, List<League> items) {
        inflater = LayoutInflater.from(context);
        this.context=context;
        this.items = items;
    }

    @Override
    public Match getChild(int leaguePosition, int matchPosition) {
        return items.get(leaguePosition).getMatchItem().get(matchPosition);
    }

    @Override
    public long getChildId(int leaguePosition, int matchPosition) {
        return matchPosition;
    }

    @Override
    public View getRealChildView(final int leagueGroupPosition, final int matchPosition, boolean isLastMatch, View convertView, ViewGroup parent) {
        MatchHolder holder;
        final Match item = getChild(leagueGroupPosition, matchPosition);
        if (convertView == null) {
            holder = new MatchHolder();
            convertView = inflater.inflate(R.layout.item_match, parent, false);
            convertView.setBackgroundColor(context.getResources().getColor(R.color.rippelColor));

            holder.match = (LinearLayout) convertView.findViewById(R.id.match);
            holder.thumbnailTeamLocal = (NetworkImageView) convertView.findViewById(R.id.thumbnailTeamLocal);
            holder.thumbnailTeamVisit = (NetworkImageView) convertView.findViewById(R.id.thumbnailTeamVisit);
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            holder.txtHour = (TextView) convertView.findViewById(R.id.txtHour);

            holder.txtTeamLocal = (TextView) convertView.findViewById(R.id.txtTeamLocal);
            holder.txtTeamVisit = (TextView) convertView.findViewById(R.id.txtTeamVisit);

            convertView.setTag(holder);
        } else {
            holder = (MatchHolder) convertView.getTag();
        }

        if (imageLoader == null) {
            imageLoader = WintoWinnerApp.getInstance().getImageLoader();
        }


        holder.thumbnailTeamLocal.setImageUrl(item.getLocalImg(), imageLoader);
        holder.thumbnailTeamVisit.setImageUrl(item.getVisitImg(), imageLoader);


        holder.txtDate.setText(item.getDate());
        holder.txtHour.setText(item.getHour());

        holder.txtTeamLocal.setText(item.getLocalName());
        holder.txtTeamVisit.setText(item.getVisitName());

        holder.match.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) view.getContext()).showBettings(item.getId());
                    }
                }
        );
        return convertView;
    }

    @Override
    public int getRealChildrenCount(int leaguePosition) {
        return items.get(leaguePosition).getMatchItem().size();
    }

    @Override
    public League getGroup(int leaguePosition) {
        return items.get(leaguePosition);
    }
    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public long getGroupId(int leaguePosition) {
        return leaguePosition;
    }

    @Override
    public View getGroupView(int leaguePosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupLeagueHolder holder;
        League item = getGroup(leaguePosition);
        if (convertView == null) {
            holder = new GroupLeagueHolder();
            convertView = inflater.inflate(R.layout.item_group_league, parent, false);
            convertView.setBackgroundColor(context.getResources().getColor(R.color.wintowin_dark));
            holder.txtNameLeague = (TextView) convertView.findViewById(R.id.txtNameLeague);
            convertView.setTag(holder);
        } else {
            holder = (GroupLeagueHolder) convertView.getTag();
        }
        holder.txtNameLeague.setText(item.getNameLeague());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}

class MatchHolder {
    LinearLayout match;
    NetworkImageView thumbnailTeamLocal;
    NetworkImageView thumbnailTeamVisit;
    TextView txtDate;
    TextView txtHour;
    TextView txtTeamLocal;
    TextView txtTeamVisit;
}

class GroupLeagueHolder {
    TextView txtNameLeague;
}
