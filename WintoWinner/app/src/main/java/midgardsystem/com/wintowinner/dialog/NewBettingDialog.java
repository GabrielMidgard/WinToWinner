package midgardsystem.com.wintowinner.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystems.lib.numericUpDown.NumericUpDown;
import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.adapter.BettingtAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Betting;
import midgardsystem.com.wintowinner.objects.Historical;
import midgardsystem.com.wintowinner.objects.Match;
import midgardsystem.com.wintowinner.objects.Movement;
import midgardsystem.com.wintowinner.objects.Team;
import midgardsystem.com.wintowinner.objects.User;
import midgardsystem.com.wintowinner.utils.AppConfig;

/**
 * Created by Gabriel on 22/08/2016.
 */
public class NewBettingDialog  extends DialogFragment implements NumericUpDown.OnQuantityChangeListener{
    View rootView;
    private int pricePerProduct = 180;
    private NumericUpDown quantityView;
    private User user;
    private Match match;

    public List<Betting> bettingList;
    public BettingtAdapter adapter;


    RadioButton bid_50;
    RadioButton bid_100;
    RadioButton bid_200;
    RadioButton bid_500;

    String selected="";
    static int teamSelection;

    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();
    DatabaseHelper dbHelper;

    public NewBettingDialog() {}

    @SuppressLint("ValidFragment")
    public NewBettingDialog(User user, Match match) {
        this.user=user;
        this.match=match;
    }

    @SuppressLint("ValidFragment")
    public NewBettingDialog(User user, Match match, List<Betting> bettingList, BettingtAdapter adapter) {
        this.user=user;
        this.match=match;
        this.bettingList=bettingList;
        this.adapter=adapter;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        return createDialog();
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onViewCreated(view, savedInstanceState);
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
        rootView = inflater.inflate(R.layout.dialog_new_betting, null);
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
        NetworkImageView thumbnailTeamLocal= (NetworkImageView)rootView.findViewById(R.id.thumbnailTeamLocal);
        NetworkImageView thumbnailTeamVisit= (NetworkImageView)rootView.findViewById(R.id.thumbnailTeamVisit);

        TextView txtCoins= (TextView)rootView.findViewById(R.id.txtCoins);
        txtCoins.setText(user.getCoins()+"");

        final RelativeLayout btnLocal= (RelativeLayout)rootView.findViewById(R.id.btnLocal);
        final RelativeLayout btndraw= (RelativeLayout)rootView.findViewById(R.id.btndraw);
        final RelativeLayout btnVisit= (RelativeLayout)rootView.findViewById(R.id.btnVisit);
        final TextView lblDraw= (TextView)rootView.findViewById(R.id.lblDraw);
        final RelativeLayout btnAdd = (RelativeLayout)rootView.findViewById(R.id.btnAdd);


        btnLocal.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnLocal.setBackgroundColor(getResources().getColor(R.color.wintowin));
                        btndraw.setBackgroundColor(getResources().getColor(R.color.md_gray_bg_stadistics));
                        btnVisit.setBackgroundColor(getResources().getColor(R.color.md_gray_bg_stadistics));
                        lblDraw.setTextColor(getResources().getColor(R.color.md_black_1000));
                        selected="local";
                    }
                }
        );
        btndraw.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnLocal.setBackgroundColor(getResources().getColor(R.color.md_gray_bg_stadistics));
                        btndraw.setBackgroundColor(getResources().getColor(R.color.wintowin));
                        btnVisit.setBackgroundColor(getResources().getColor(R.color.md_gray_bg_stadistics));
                        lblDraw.setTextColor(getResources().getColor(R.color.md_white_1000));
                        selected="draw";
                    }
                }
        );
        btnVisit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnLocal.setBackgroundColor(getResources().getColor(R.color.md_gray_bg_stadistics));
                        btndraw.setBackgroundColor(getResources().getColor(R.color.md_gray_bg_stadistics));
                        btnVisit.setBackgroundColor(getResources().getColor(R.color.wintowin));
                        lblDraw.setTextColor(getResources().getColor(R.color.md_black_1000));
                        selected="visit";
                    }
                }
        );

        btnAdd.setOnClickListener(
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

                        int betting = quantityView.getQuantity();
                        if (betting <= user.getCoins()) {

                            //restar de la base de datos de usuario
                            int totalCoints = user.getCoins() - betting;

                            if(createBet(match.getId(), teamSelection, betting, totalCoints)){
                                updateCointsUser(totalCoints);
                                Snackbar.make(rootView, R.string.create_betting, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                                Movement movement = new Movement(1,1,betting);
                                insertMovement(movement);
                            }

                            //add list
                            /*
                            Betting objBetting = new Betting();
                            objBetting.setTeam(objTeam);
                            holder.thumbnailUser.setImageUrl(bettingItems.get(position).getUser().getImgUser(), imageLoader);
                            holder.txtTeam.setText(bettingItems.get(position).getTeam().getName());
                            holder.txtBetting.setText("$ " + bettingItems.get(position).getBetting());


                            objBetting.setMatch(match);
                            objBetting.setBetting(totalCoints);

                            bettingFragment3.bettingList.add(objBetting);
                            bettingFragment3.adapter.notifyDataSetChanged();
                             */

                            //enviar al web service
                            dismiss();

                        } else {
                            Snackbar.make(rootView, R.string.no_coins_betting, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                        }


                    }
                }
        );

        thumbnailTeamLocal.setImageUrl(match.getTeamHome().getImg(), imageLoader);
        thumbnailTeamVisit.setImageUrl(match.getTeamVisit().getImg(), imageLoader);
        //ImageView btnBack = (ImageView) rootView.findViewById(R.id.btnBack);         // Obtiene el btn de cerrar de menu bar

        quantityView= (NumericUpDown)rootView.findViewById(R.id.quantityView);

        bid_50= (RadioButton)rootView.findViewById(R.id.bid_50);
        bid_100= (RadioButton)rootView.findViewById(R.id.bid_100);
        bid_200= (RadioButton)rootView.findViewById(R.id.bid_200);
        bid_500= (RadioButton)rootView.findViewById(R.id.bid_500);

        bid_50.setChecked(true);
        bid_50.setTextColor(ContextCompat.getColor(getContext(), R.color.md_white_1000));


        bid_50.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chengeBid(50);
                    bid_50.setTextColor(ContextCompat.getColor(getContext(), R.color.md_white_1000));
                }
                else{
                    bid_50.setTextColor(ContextCompat.getColor(getContext(), R.color.md_black_1000));
                }

            }
        });
        bid_100.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chengeBid(100);
                    bid_100.setTextColor(ContextCompat.getColor(getContext(), R.color.md_white_1000));
                }
                else{
                    bid_100.setTextColor(ContextCompat.getColor(getContext(), R.color.md_black_1000));
                }

            }
        });
        bid_200.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chengeBid(200);
                    bid_200.setTextColor(ContextCompat.getColor(getContext(), R.color.md_white_1000));
                }
                else{
                    bid_200.setTextColor(ContextCompat.getColor(getContext(), R.color.md_black_1000));
                }

            }
        });
        bid_500.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chengeBid(500);
                    bid_500.setTextColor(ContextCompat.getColor(getContext(), R.color.md_white_1000));
                } else {
                    bid_500.setTextColor(ContextCompat.getColor(getContext(), R.color.md_black_1000));
                }

            }
        });



        //quantityView.setOnQuantityChangeListener(this);
        quantityView.setQuantityClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(byIdName(getContext(), "change_quantity"));

                View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_change_quantity, null, false);
                final EditText txtQuantity = (EditText) inflate.findViewById(R.id.txtQuantity);
                final TextView txtPrice = (TextView) inflate.findViewById(R.id.txtPrice);
                final TextView txtTotal = (TextView) inflate.findViewById(R.id.txtTotal);

                txtQuantity.setText(String.valueOf(quantityView.getQuantity()));
                txtPrice.setText("$ " + pricePerProduct);
                txtTotal.setText("$ " + quantityView.getQuantity() * pricePerProduct);


                txtQuantity.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (TextUtils.isEmpty(s)) return;

                        int intNewQuantity = Integer.parseInt(s.toString());
                        txtTotal.setText("$ " + intNewQuantity * pricePerProduct);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                builder.setView(inflate);
                builder.setPositiveButton(byIdName(getContext(), "change"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newQuantity = txtQuantity.getText().toString();
                        if (TextUtils.isEmpty(newQuantity)) return;

                        int intNewQuantity = Integer.parseInt(newQuantity);

                        quantityView.setQuantity(intNewQuantity);
                    }
                }).setNegativeButton(byIdName(getContext(), "cancel"), null);
                builder.show();
            }
        });
        //RippleView btnUpdate = (RippleView) rootView.findViewById(R.id.btnUpdate); // Obtiene el btn UpdateAccountGlobal de cerrar de footer

        /*
        btnBack.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }
        );
        */


    }

    public static String byIdName(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }


    @Override
    public void onQuantityChanged(int newQuantity, boolean programmatically) {
        //quantityView.setQuantity(intNewQuantity);
        Toast.makeText(getActivity(), byIdName(getContext(), "quantity") + ": " + newQuantity, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLimitReached() {
        Log.d(getClass().getSimpleName(), byIdName(getContext(), "limit_reached"));
    }


/*
    @Override
    public void onStart() {
        super.onStart();

        // safety check
        if (getDialog() == null) {
            return;
        }
        int dialogWidth = RelativeLayout.LayoutParams.WRAP_CONTENT;
        int dialogHeight = 750; // specify a value here

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);

        // ... other stuff you want to do in your onStart() method
    }
    // getDialog().getWindow().setLayout(300,300);
*/
    private void chengeBid(int bid){
        quantityView.setBidQuantity(bid);
    }


    private void updateCointsUser(int coins){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            dbHelper.updateCointsUser(database, coins);
            dbHelper.updateWallet(database, coins);
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

    }

    private void insertMovement(Movement movement){

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            dbHelper.insertMovement(database, movement);

        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }

    }


    private boolean createBet(int idMatch, final int teamSelection, final int betting, int totalCoints){
        final boolean[] res = {false};
        RequestQueue rq = Volley.newRequestQueue(getActivity());
        String url= AppConfig.URL_CREATE_BETTING+user.getIdUser()+"&idMatch="+idMatch+"&teamSelection="+teamSelection+"&betting="+betting+"&active=1&totalBalanceCoins="+totalCoints;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        if(parseData(response)){
                            res[0] =true;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError ex) {
                        // Handle error
                        Toast.makeText(getActivity(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(stringRequest);
        return res[0];
    }

    private boolean parseData(String response){
        boolean res=false;
        final Betting objBetting = new Betting();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try{

            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");

            //hidePDialog();
            if (success) {
                JSONArray valuesResult= jObj.getJSONArray("historical");
                for ( int i=0; i< valuesResult.length(); i++) {
                    JSONObject obj = valuesResult.getJSONObject(i);

                    Historical objHistorical = new Historical();
                    objHistorical.setIdBetting(obj.getInt("idBetting"));
                    objHistorical.setBetting(obj.getInt("betting"));
                    objHistorical.setActive(obj.getInt("active"));
                    objHistorical.setIdMatch(obj.getInt("idMatch"));
                    objHistorical.setDate(obj.getString("date"));
                    objHistorical.setHour(obj.getString("hour"));
                    objHistorical.setTypeMatchName(obj.getString("typeMatchName"));

                    objHistorical.setStatus(obj.getString("status"));
                    objHistorical.setFkTeamHome(obj.getInt("fk_team_home"));
                    objHistorical.setFkTeamVisit(obj.getInt("fk_team_visit"));
                    objHistorical.setFkTeamWin(obj.getInt("fk_team_win"));

                    objHistorical.setFkUserTeam(obj.getInt("fk_user_team"));
                    objHistorical.setFkUserChallengingTeam(obj.getInt("fk_user_challenging_team"));
                    objHistorical.setRound(obj.getString("round"));

                    //INSERT
                    dbHelper.insertBettingHistory(database, objHistorical);

                }

                Team team = null;
                if(teamSelection!=-3) {
                    try {
                        team = dbHelper.getTeamById(database, teamSelection);

                    } catch (Exception ex) {
                        Log.e("ERROR", ex.getMessage());
                    }
                }else{
                    team = new Team();
                    team.setName("Empate");
                }

                objBetting.setBetting(quantityView.getQuantity());
                user.setImgUser(AppConfig.URL_IMG_USER + user.getImgUser());
                objBetting.setUser(user);
                objBetting.setTeam(team);

                bettingList.add(objBetting);
                adapter.notifyDataSetChanged();
                dismiss();
                res = true;
            }
            else{
                String errorMsg = jObj.getString("msg");
                //encodeMessage(errorMsg);
                res = false;
            }

        }  catch (JSONException ex){
            Toast.makeText(getContext(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return res;
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
