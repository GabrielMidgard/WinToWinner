package midgardsystem.com.wintowinner.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.fragments.profile.MyPrizesFragment;
import midgardsystem.com.wintowinner.fragments.profile.PersonalDataFragment;
import midgardsystem.com.wintowinner.objects.User;
import midgardsystem.com.wintowinner.utils.CirculaireNetworkImageView;

/**
 * Created by Gabriel on 02/10/2016.
 */
public class ProfileFragment extends Fragment implements AppBarLayout.OnOffsetChangedListener {

    View rootView;
    int noTabs=3;
    User user;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();

    private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
    private boolean isAvatarShown = true;

    private CirculaireNetworkImageView thumbnailProfileUser;
    private int maxScrollSize;


    public ProfileFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_profile));

        user =((MainActivity) getActivity()).getUser();

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.materialup_tabs);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.materialup_viewpager);
        AppBarLayout appbarLayout = (AppBarLayout) rootView.findViewById(R.id.materialup_appbar);
        thumbnailProfileUser = (CirculaireNetworkImageView) rootView.findViewById(R.id.thumbnailProfileUser);


        appbarLayout.addOnOffsetChangedListener(this);
        maxScrollSize = appbarLayout.getTotalScrollRange();

        viewPager.setAdapter(new TabsAdapter(getActivity().getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        init();
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Make sure you have this line of code.
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_main, menu);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void init(){
        TextView txtHeaderName = (TextView) rootView.findViewById(R.id.txtHeaderName);
        TextView txtCoins= (TextView)rootView.findViewById(R.id.txtCoins);
        CirculaireNetworkImageView thumbnailProfileUser= (CirculaireNetworkImageView)rootView.findViewById(R.id.thumbnailProfileUser);
        if(user.getImgUser()!=null) {
            thumbnailProfileUser.setImageUrl(user.getImgUser(), imageLoader);
        }
        txtHeaderName.setText(user.getName());
        txtCoins.setText(user.getCoins()+"");
    }

    public static void start(Context c) {

        //c.startActivity(new Intent(c, ProfileActivity.class));
    }
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (maxScrollSize == 0)
            maxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / maxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && isAvatarShown) {
            isAvatarShown = false;
            thumbnailProfileUser.animate().scaleY(0).scaleX(0).setDuration(200).start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !isAvatarShown) {
            isAvatarShown = true;

            thumbnailProfileUser.animate()
                    .scaleY(1).scaleX(1)
                    .start();
        }
    }

    class TabsAdapter extends FragmentStatePagerAdapter {
        public TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return noTabs;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                /*
                case 0:
                    return PersonalDataFragment.newInstance(user);
                case 1:
                    return PersonalDataFragment.newInstance(user);
                    */

                case 0 : return new PersonalDataFragment(user);
                case 1 : return new MovementFragment();
                case 2 : return new MyPrizesFragment();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Datos Personales";
                case 1:
                    return "Movimientos";
                case 2:
                    return "Premios";
            }
            return "";
        }
    }

}