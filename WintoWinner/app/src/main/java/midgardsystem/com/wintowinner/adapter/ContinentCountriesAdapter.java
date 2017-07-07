package midgardsystem.com.wintowinner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.objects.Continent;
import midgardsystem.com.wintowinner.objects.Countries;
import midgardsystem.com.wintowinner.objects.Sport;
import midgardsystem.com.wintowinner.utils.AnimatedExpandableListView;

/**
 * Created by Gabriel on 18/08/2016.
 */
public class ContinentCountriesAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private LayoutInflater inflater;
    Context context;

    private List<Continent> items;
    private Sport sport;

    public ContinentCountriesAdapter(Context context, List<Continent> items,Sport sport ) {
        inflater = LayoutInflater.from(context);
        this.context=context;
        this.items = items;
        this.sport = sport;
    }

    @Override
    public Countries getChild(int continentPosition, int countriesPosition) {
        return items.get(continentPosition).getCountrieItem().get(countriesPosition);
    }

    @Override
    public long getChildId(int continentPosition, int countriesPosition) {
        return countriesPosition;
    }

    @Override
    public View getRealChildView(final int continentGroupPosition, final int countriePosition, boolean isLastContinent, View view, ViewGroup viewGroup) {
        CountriesHolder holder;
        final Countries item = getChild(continentGroupPosition, countriePosition);
        if (view == null) {
            holder = new CountriesHolder();
            view = inflater.inflate(R.layout.item_countrie, viewGroup, false);
            view.setBackgroundColor(context.getResources().getColor(R.color.rippelColor));

            holder.countrie = (LinearLayout) view.findViewById(R.id.countrie);
            holder.txtCountrie = (TextView) view.findViewById(R.id.txtCountrie);
            holder.txtNoLeague = (TextView) view.findViewById(R.id.txtNoLeague);


            holder.countrie.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((MainActivity) view.getContext()).showCountries(item, sport);
                        }
                    }
            );
            view.setTag(holder);
        } else {
            holder = (CountriesHolder) view.getTag();
        }


        holder.txtCountrie.setText(item.getNameCountries());
        holder.txtNoLeague.setText(context.getResources().getString(R.string.leagues)+":"+item.getnLeagues()+"");
        /*
        if(item.getnLeagues()!=0) {
            holder.txtNoLeague.setText(item.getnLeagues()+"");
        }*/
        return view;
    }

    @Override
    public int getRealChildrenCount(int countriePosition) {
        return items.get(countriePosition).getCountrieItem().size();
    }

    @Override
    public Continent getGroup(int continentPosition) {
        return items.get(continentPosition);
    }
    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public long getGroupId(int continentPosition) {
        return continentPosition;
    }

    @Override
    public View getGroupView(int continentPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupContinentHolder holder;
        Continent item = getGroup(continentPosition);
        if (convertView == null) {
            holder = new GroupContinentHolder();
            convertView = inflater.inflate(R.layout.item_group_continent, parent, false);
            convertView.setBackgroundColor(context.getResources().getColor(R.color.wintowin_dark));
            holder.txtNameContinent = (TextView) convertView.findViewById(R.id.txtNameContinent);
            convertView.setTag(holder);
        } else {
            holder = (GroupContinentHolder) convertView.getTag();
        }
        if(item.getName()!=null) {
            holder.txtNameContinent.setText(item.getName());
        }
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

class CountriesHolder {
    TextView txtCountrie;
    TextView txtNoLeague;
    LinearLayout countrie;
}

class GroupContinentHolder {
    TextView txtNameContinent;
}
