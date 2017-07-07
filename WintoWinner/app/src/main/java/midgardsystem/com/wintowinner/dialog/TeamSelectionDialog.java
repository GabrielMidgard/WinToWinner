package midgardsystem.com.wintowinner.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.adapter.BettingtAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Betting;
import midgardsystem.com.wintowinner.objects.ConfirmDialog;
import midgardsystem.com.wintowinner.objects.Match;
import midgardsystem.com.wintowinner.objects.User;

/**
 * Created by Gabriel on 19/09/2016.
 */
public class TeamSelectionDialog   extends DialogFragment {
    View rootView;
    private int pricePerProduct = 180;
    private User user;
    private Match match;
    private Betting betting;
    //private BettingFragment bettingFragment3;


    String selected="";
    int teamSelection;

    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();
    DatabaseHelper dbHelper;

    int position; BettingtAdapter adapter; List<Betting> bettingList;

    public TeamSelectionDialog() {}

    @SuppressLint("ValidFragment")
    public TeamSelectionDialog(User user, Match match, Betting betting, int position, BettingtAdapter adapter, List<Betting> bettingList) {
        this.user=user;
        this.match=match;
        this.betting=betting;

        this.position=position;
        this.adapter=adapter;
        this.bettingList=bettingList;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        return createDialog();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    /**
     * Crea un PopUp con personalizado para comportarse
     * como formulario de PopUp
     *
     * @return PopUp
     */
    public AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_team_selection, null);
        builder.setView(rootView);
        dbHelper = new DatabaseHelper(getContext());
        getTeams();
        init();

        ImageView btnBack = (ImageView) rootView.findViewById(R.id.btnBack);         // Obtiene el btn de cerrar de menu bar
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //return builder.create();
        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        return builder.create();
    }

    public void init(){
        final RelativeLayout btnTeamHome= (RelativeLayout)rootView.findViewById(R.id.btnTeamHome);
        final RelativeLayout btnTeamVisit= (RelativeLayout)rootView.findViewById(R.id.btnTeamVisit);
        final RelativeLayout btndraw= (RelativeLayout)rootView.findViewById(R.id.btndraw);
        final TextView lblDraw= (TextView)rootView.findViewById(R.id.lblDraw);

        int idTeamBet=betting.getTeam().getId();

        if(idTeamBet==-3) {
            btndraw.setVisibility(View.GONE);

            NetworkImageView thumbnailTeamVisit= (NetworkImageView)rootView.findViewById(R.id.thumbnailTeamVisit);
            NetworkImageView thumbnailTeamHome= (NetworkImageView)rootView.findViewById(R.id.thumbnailTeamHome);

            thumbnailTeamHome.setImageUrl(match.getTeamHome().getImg(), imageLoader);
            thumbnailTeamVisit.setImageUrl(match.getTeamVisit().getImg(), imageLoader);

            btnTeamHome.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnTeamHome.setBackgroundColor(getResources().getColor(R.color.wintowin));
                            btnTeamVisit.setBackgroundColor(getResources().getColor(R.color.md_gray_bg_stadistics));
                            selected="local";
                        }
                    }
            );

            btnTeamVisit.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnTeamHome.setBackgroundColor(getResources().getColor(R.color.md_gray_bg_stadistics));
                            btnTeamVisit.setBackgroundColor(getResources().getColor(R.color.wintowin));
                            selected="visit";
                        }
                    }
            );


        }else if(idTeamBet == match.getFkTeamHome()){
            btnTeamHome.setVisibility(View.GONE);

            NetworkImageView thumbnailTeamVisit= (NetworkImageView)rootView.findViewById(R.id.thumbnailTeamVisit);
            thumbnailTeamVisit.setImageUrl(match.getTeamVisit().getImg(), imageLoader);

            btndraw.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btndraw.setBackgroundColor(getResources().getColor(R.color.wintowin));
                            btnTeamVisit.setBackgroundColor(getResources().getColor(R.color.md_gray_bg_stadistics));
                            lblDraw.setTextColor(getResources().getColor(R.color.md_white_1000));
                            selected = "draw";
                        }
                    }
            );
            btnTeamVisit.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btndraw.setBackgroundColor(getResources().getColor(R.color.md_gray_bg_stadistics));
                            btnTeamVisit.setBackgroundColor(getResources().getColor(R.color.wintowin));
                            lblDraw.setTextColor(getResources().getColor(R.color.md_black_1000));
                            selected = "visit";
                        }
                    }
            );

        }else{
            btnTeamVisit.setVisibility(View.GONE);

            NetworkImageView thumbnailTeamHome= (NetworkImageView)rootView.findViewById(R.id.thumbnailTeamHome);
            thumbnailTeamHome.setImageUrl(match.getTeamHome().getImg(), imageLoader);

            btndraw.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btndraw.setBackgroundColor(getResources().getColor(R.color.wintowin));
                            btnTeamHome.setBackgroundColor(getResources().getColor(R.color.md_gray_bg_stadistics));
                            lblDraw.setTextColor(getResources().getColor(R.color.md_white_1000));
                            selected = "draw";
                        }
                    }
            );
            btnTeamHome.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btndraw.setBackgroundColor(getResources().getColor(R.color.md_gray_bg_stadistics));
                            btnTeamHome.setBackgroundColor(getResources().getColor(R.color.wintowin));
                            lblDraw.setTextColor(getResources().getColor(R.color.md_black_1000));
                            selected = "local";
                        }
                    }
            );
        }



        TextView lblBetCoins = (TextView) rootView.findViewById(R.id.lblBetCoins);
        TextView txtBetCoins = (TextView) rootView.findViewById(R.id.txtBetCoins);
        TextView lblMyCoins = (TextView) rootView.findViewById(R.id.lblMyCoins);
        TextView txtCoins = (TextView) rootView.findViewById(R.id.txtCoins);

        txtBetCoins.setText(betting.getBetting()+"");
        txtCoins.setText(user.getCoins() + "");



        final RelativeLayout btnAccepted = (RelativeLayout)rootView.findViewById(R.id.btnAccepted);
        TextView lblAccepted = (TextView) rootView.findViewById(R.id.lblAccepted);

        btnAccepted.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (selected) {
                            case "local":
                                teamSelection = match.getFkTeamHome();
                                break;

                            case "draw":
                                teamSelection = -3;
                                break;

                            case "visit":
                                teamSelection = match.getFkTeamVisit();
                                break;

                            default:
                                teamSelection = -3;

                        }

                        dismiss();
                        ConfirmDialog objConfirm = new ConfirmDialog(getResources().getString(R.string.confirm), getResources().getString(R.string.message_ConfirmAcceptBets), getResources().getString(R.string.continues), getResources().getString(R.string.cancel));
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        //new Confirm(objConfirm).show(fragmentManager, "ConfirmAcceptBets");
                        //ICallConfirmBet listeners = (ICallConfirmBet) getActivity();
                        new ConfirmBet(objConfirm, position, adapter, bettingList, teamSelection).show(fragmentManager, "ConfirmBet");
                    }
                }
        );

    }

    public static String byIdName(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }



    private void updateCointsUser(int coins){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            dbHelper.updateCointsUser(database, coins);
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

    }

    private void getTeams(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            match.setTeamHome(dbHelper.getTeamById(database, match.getFkTeamHome()));
            match.setTeamVisit(dbHelper.getTeamById(database, match.getFkTeamVisit()));
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

