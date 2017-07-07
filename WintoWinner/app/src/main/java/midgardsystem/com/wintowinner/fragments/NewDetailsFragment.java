package midgardsystem.com.wintowinner.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.objects.News;

/**
 * Created by Gabriel on 07/10/2016.
 */
public class NewDetailsFragment  extends Fragment {

    View rootView;
    News news;
    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();


    public NewDetailsFragment(){}
    @SuppressLint("ValidFragment")
    public NewDetailsFragment(News news){
        this.news=news;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_new_details, container, false);


        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.materialup_viewpager);

        /*
        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);*/
       // ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        //collapsingToolbar.setTitle("TESITNG TESITNG TESITNG TESITNGTESITNGTESITNG");

        loadBackdrop();
        init();

        return rootView;
    }


    private void loadBackdrop() {
        NetworkImageView thumbnailNews = (NetworkImageView) rootView.findViewById(R.id.thumbnailNews);
        thumbnailNews.setImageUrl(news.getImg(), imageLoader);
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
        inflater.inflate(R.menu.menu_bar, menu);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void init(){

        TextView txtHeader = (TextView) rootView.findViewById(R.id.txtHeader);
        TextView txtSubheader= (TextView)rootView.findViewById(R.id.txtSubHeader);
        TextView txtContent= (TextView)rootView.findViewById(R.id.txtContent);

        txtHeader.setText(news.getHeader());
        txtSubheader.setText(news.getSubeader());
        txtContent.setText(news.getContent());



    }





}