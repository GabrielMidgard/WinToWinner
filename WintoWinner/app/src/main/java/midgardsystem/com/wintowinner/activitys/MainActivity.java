package midgardsystem.com.wintowinner.activitys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.adapter.BettingtAdapter;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.dialog.CoinSalesDialog;
import midgardsystem.com.wintowinner.dialog.ConfirmLogOut;
import midgardsystem.com.wintowinner.dialog.NotificationDialog;
import midgardsystem.com.wintowinner.fragments.AwardsFragment;
import midgardsystem.com.wintowinner.fragments.BettingFragment;
import midgardsystem.com.wintowinner.fragments.HistoricalFragment;
import midgardsystem.com.wintowinner.fragments.HomeFragment;
import midgardsystem.com.wintowinner.fragments.LoginFragment;
import midgardsystem.com.wintowinner.fragments.MatchFragment;
import midgardsystem.com.wintowinner.fragments.MyMatchFragmentTab;
import midgardsystem.com.wintowinner.fragments.NewsFragment;
import midgardsystem.com.wintowinner.fragments.ProfileFragment;
import midgardsystem.com.wintowinner.fragments.interests.PreferencesFragment;
import midgardsystem.com.wintowinner.objects.Betting;
import midgardsystem.com.wintowinner.objects.ConfirmDialog;
import midgardsystem.com.wintowinner.objects.Countries;
import midgardsystem.com.wintowinner.objects.Historical;
import midgardsystem.com.wintowinner.objects.Movement;
import midgardsystem.com.wintowinner.objects.Notification;
import midgardsystem.com.wintowinner.objects.Sport;
import midgardsystem.com.wintowinner.objects.User;
import midgardsystem.com.wintowinner.utils.AppConfig;
import midgardsystem.com.wintowinner.utils.ICallConfirm;
import midgardsystem.com.wintowinner.utils.ICallConfirmBet;
import midgardsystem.com.wintowinner.utils.ICallConfirmLogOut;

import midgardsystem.com.wintowinner.objects.PlayPalResult;
import midgardsystem.com.wintowinner.utils.ValidateConection;

/**
 * Created by Gabriel on 13/08/2016.
 */
public class MainActivity  extends AppCompatActivity implements ICallConfirm, ICallConfirmBet, ICallConfirmLogOut {

    private DrawerLayout drawerLayout; //Instancia del drawer
    private String drawerTitle; //Titulo inicial del drawer 1D1D1D
    private String ACTIONBAR_COLOR="#3ac065";
    public static CharSequence mTitle;
    public NavigationView navigationView;
    DatabaseHelper dbHelper;
    User user;

    private CoordinatorLayout coordinatorLayout;
    ValidateConection validateConection;
    private boolean isConnected=true;

    private CallbackManager callbackManager;

    // TAGs
    private static final String TAG_HOME = "tag_home";
    private static final String TAG_LOGIN = "tag_login";
    private static final String TAG_NEWS = "tag_news";
    private static final String TAG_PREFERENCES = "tag_preferences";
    private static final String TAG_BETS = "tag_bets";
    private static final String TAG_MY_INTERESTS = "tag_my_interests";
    private static final String TAG_PROFILE = "tag_profile";
    private static final String TAG_STORE = "tag_store";
    private static final String TAG_MY_MATCH = "tag_my_match";
    private static final String TAG_COINS = "tag_coins";
    private static final String TAG_MATCHS = "tag_matchs";
    private static final String TAG_BETTING = "tag_betting";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);

        validateConection= new ValidateConection(this);
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            validateConection();

                        } catch (Exception e) {
                            Log.e("error", e.getMessage());
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 10000);  //ejecutar en intervalo de 10 segundos.
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager=CallbackManager.Factory.create();

        Intent i = getIntent();
        int payPalSuccess=i.getIntExtra("paypal_success",0);
        setToolbar(); // Setear Toolbar como action bar

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        drawerTitle = getResources().getString(R.string.welcome);
        if (savedInstanceState == null) {
            selectItem(getString(R.string.menu_meetings));
        }

        user=getUser();
        if(user!=null){
            updateMenu(true);
        }else{
            updateMenu(false);
            if (AccessToken.getCurrentAccessToken() != null) {
                updateMenu(true);
            }
        }


        if(payPalSuccess==1){
            int payPalMoney=i.getIntExtra("paypal_money",0);
            int payPalCoins=i.getIntExtra("paypal_coins",0);
            sendSaleCoins(payPalMoney, payPalCoins, user.getId());

        }else {
            //Notification
            showNotification();
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            dbHelper.deteleNotifications(database);
        }


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment fragment;

        Fragment tagHome = getSupportFragmentManager().findFragmentByTag(TAG_HOME);
        Fragment tagLogin = getSupportFragmentManager().findFragmentByTag(TAG_LOGIN);
        Fragment tagNews = getSupportFragmentManager().findFragmentByTag(TAG_NEWS);
        Fragment tagPreferences = getSupportFragmentManager().findFragmentByTag(TAG_PREFERENCES);
        Fragment tagBets = getSupportFragmentManager().findFragmentByTag(TAG_BETS);
        Fragment tagMyInterests = getSupportFragmentManager().findFragmentByTag(TAG_MY_INTERESTS);
        Fragment tagProfile = getSupportFragmentManager().findFragmentByTag(TAG_PROFILE);
        Fragment tagStore = getSupportFragmentManager().findFragmentByTag(TAG_STORE);
        Fragment tagMyMatch = getSupportFragmentManager().findFragmentByTag(TAG_MY_MATCH);
        Fragment tagCoins = getSupportFragmentManager().findFragmentByTag(TAG_COINS);
        Fragment tagMatch = getSupportFragmentManager().findFragmentByTag(TAG_MATCHS);

        Fragment tagBetting = getSupportFragmentManager().findFragmentByTag(TAG_BETTING);


        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                fragment = new HomeFragment();
                if (tagLogin != null) {
                    if (tagLogin.isVisible()) {
                        back(fragment, TAG_LOGIN);
                    }
                }

                else if(tagNews != null) {
                    if (tagNews.isVisible()) {
                        back(fragment, TAG_HOME);
                    }
                }

                else if(tagPreferences != null) {
                    if (tagPreferences.isVisible()) {
                        back(fragment, TAG_HOME);
                    }
                }

                else if(tagBets != null) {
                    if (tagBets.isVisible()) {
                        back(fragment, TAG_HOME);
                    }
                }

                else if(tagMyInterests != null) {
                    if (tagMyInterests.isVisible()) {
                        back(fragment, TAG_HOME);
                    }
                }

                else if(tagProfile != null) {
                    if (tagProfile.isVisible()) {
                        back(fragment, TAG_HOME);
                    }
                }

                else if(tagStore != null) {
                    if (tagStore.isVisible()) {
                        back(fragment, TAG_HOME);
                    }
                }

                else if(tagMyMatch != null) {
                    if (tagMyMatch.isVisible()) {
                        back(fragment, TAG_HOME);
                    }
                }

                else if(tagCoins != null) {
                    if (tagCoins.isVisible()) {
                        back(fragment, TAG_HOME);
                    }
                }

                else if(tagMatch != null) {
                    if (tagMatch.isVisible()) {
                        back(fragment, TAG_HOME);
                    }
                    if(tagBetting.isVisible()){
                        back(fragment, TAG_HOME);
                    }
                }

                else if(tagBetting != null){
                    if(tagBetting.isVisible()){
                        back(fragment, TAG_HOME);
                    }
                }

                return true;
        }



        return super.onKeyDown(keyCode, event);
    }

    private void back(Fragment fragment, String TAG) {
        //zoom de retorno
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
        fragmentManager.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
        fragmentManager.replace(R.id.main_content, fragment, TAG);
        fragmentManager.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
    // btn's actionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment;
/*
        if (id == R.id.btnShoppingCart) {
            showShoppingCart();
            return true;
        }

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            return true;
        }
        */
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        String title = menuItem.getTitle().toString();

                        selectItem(title);
                        mTitle = title;
                        return true;
                    }
                }
        );
    }

    /*Pasando la posicion de la opcion en el menu nos mostrara el activity correspondiente*/
    private void selectItem(String opcSelected) {
        getSupportActionBar().setTitle(opcSelected);

        // Enviar título como arguemento del fragmento
        Fragment fragment = null;
        String TAG = "TEST_A";

        if (opcSelected.equals(getString(R.string.menu_meetings))) {
            mTitle = getString(R.string.menu_meetings);
            TAG = TAG_HOME;
            fragment = new HomeFragment();
        }

        else if (opcSelected.equals(getString(R.string.menu_login))) {
            mTitle = getString(R.string.menu_login);
            TAG = TAG_LOGIN;
            fragment = new LoginFragment();
        }

        else if (opcSelected.equals(getString(R.string.menu_news))) {
            mTitle = getString(R.string.menu_news);
            TAG = TAG_NEWS;
            fragment = new NewsFragment();
        }

        else if (opcSelected.equals(getString(R.string.menu_preferences))) {
            mTitle = getString(R.string.menu_preferences);
            TAG = TAG_PREFERENCES;
            fragment = new PreferencesFragment();
        }

        else if (opcSelected.equals(getString(R.string.menu_my_bets))) {
            mTitle = getString(R.string.menu_my_bets);
            TAG = TAG_BETS;
            fragment = new HistoricalFragment();
        }

        else if (opcSelected.equals(getString(R.string.menu_my_interests))) {
            mTitle = getString(R.string.menu_my_interests);
            TAG = TAG_MY_INTERESTS;
            fragment = new PreferencesFragment();
        }

        else if (opcSelected.equals(getString(R.string.menu_profile))) {
            mTitle = getString(R.string.menu_profile);
            TAG = TAG_PROFILE;
            fragment = new ProfileFragment();
        }

        else if (opcSelected.equals(getString(R.string.menu_store))) {
            mTitle = getString(R.string.menu_store);
            TAG = TAG_STORE;
            fragment = new AwardsFragment();
        }

        else if (opcSelected.equals(getString(R.string.menu_my_match))) {
            mTitle = getString(R.string.menu_my_match);
            TAG = TAG_MY_MATCH;
            fragment = new MyMatchFragmentTab();
        }

        else if (opcSelected.equals(getString(R.string.menu_coins))) {
            mTitle = getString(R.string.menu_coins);
            TAG = TAG_COINS;
           // fragment = new CoinSalesFragment();
            CoinSalesDialog eDialog = new CoinSalesDialog();
            //NewBettingDialog eDialog = new NewBettingDialog();
            // eDialog.listEvidences=listEvidences;
            eDialog.show(getSupportFragmentManager(), "tag_somepopup");
        }


        else if (opcSelected.equals(getString(R.string.menu_settings))) {
            /*
            mTitle = getString(R.string.menu_settings);
            TAG = TAG_HOME;
            //fragment = new SettingsFragment();
            SettingsFragment fragmentS = new SettingsFragment();

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            if(saveInstanceState==null){
                fragmentTransaction.add(R.id.R), fragment, "settings_fragment");
                fragmentTransaction .commit();
            }else{
                fragment=getFragmentManager().findFragmentByTag("settings_fragment")
            }
            */
        }

        else if (opcSelected.equals(getString(R.string.menu_logout))) {
            confirmLogOut();
            //logOutApp();
            mTitle = getString(R.string.matches);
            TAG = TAG_HOME;
            fragment = new HomeFragment();
        }

        else{
            fragment = new HomeFragment();
            //fragment = new LoginFragment();
        }


        if (fragment != null) {
            FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
            fragmentManager.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentManager.replace(R.id.main_content, fragment, TAG);
            //fragmentManager.addToBackStack(TAG);
            fragmentManager.commit();

        } else {
            //Log.e("Error  ", "MostrarFragment" + position);//Si el fragment es nulo mostramos un mensaje de error.
        }
        drawerLayout.closeDrawers(); // Cerrar drawer
        setTitle(mTitle); // Setear título actual

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
        //actionBar.setLogo(R.drawable.icon_header);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(ACTIONBAR_COLOR)));//Cambia el color de fondo de ActionBar
    }

    public void setActionBarTitle(String title){

        setTitle(title);
        getSupportActionBar().setTitle(title);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        mTitle=title;
        actionBar.setTitle(title);

    }



    public void showCountries(Countries countries, Sport sport){
        Fragment fragment = null;
        String TAG = TAG_MATCHS;
        fragment = new MatchFragment();
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();

        final Bundle bundle = new Bundle();
        bundle.putInt("idCountrie", countries.getId());
        bundle.putString("nameCountrie", countries.getNameCountries());
        bundle.putInt("idSport", sport.getIdSport());
        bundle.putString("nameSport", sport.getSportName());


        fragment.setArguments(bundle);
        fragmentManager.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        fragmentManager.replace(R.id.main_content, fragment, TAG);
        fragmentManager.addToBackStack(TAG);
        fragmentManager.commit();
    }

    public void showBettings(int idMatch){
        Fragment fragment = null;
        String TAG = TAG_BETTING;
        fragment = new BettingFragment();
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();


        final Bundle bundle = new Bundle();
        bundle.putInt("idMatch", idMatch);
        fragment.setArguments(bundle);


        fragmentManager.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        fragmentManager.replace(R.id.main_content, fragment, TAG);
        fragmentManager.addToBackStack(TAG);
        fragmentManager.commit();
    }


    @Override
    public void onPossitiveClick() {
        Fragment fragment = null;
        String TAG = "tag_betting";
        //fragment = new CreditCardFragmentAdd();
        FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();

/*
        final Bundle bundle = new Bundle();
        bundle.putInt("idMatch", idMatch);
        fragment.setArguments(bundle);*/


        fragmentManager.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
        fragmentManager.replace(R.id.main_content, fragment, TAG);
        fragmentManager.addToBackStack(TAG);
        fragmentManager.commit();
    }

    @Override
    public void onNegativeClick() {

    }


    public User getUser(){
        User user=null;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            user=dbHelper.getUser(database);
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return user;
    }


    /*
    * 1= Staff
    * 2= Patrocinador
    * 3= Asistente*/
    public void updateMenu(boolean login){
        navigationView.getMenu().clear();
        if(login){
            navigationView.inflateMenu(R.menu.nav_menu_in);
        }else{
            navigationView.inflateMenu(R.menu.nav_menu_out);
        }
    }

    @Override
    public void onAceptedBet(int position, BettingtAdapter adapterBet, List<Betting> bettingList, int teamSelectedChallenging) {
        ProgressDialog progressBet;
        progressBet = new ProgressDialog(this);
        // Showing progress dialog before making http request
        progressBet.setMessage("Aceptado apuesta...");
        progressBet.show();




        sendBet(adapterBet, bettingList, position, progressBet, teamSelectedChallenging);
    }

    @Override
    public void onNegativeBet() {
        //Toast.makeText(this, "Ok bien fine", Toast.LENGTH_LONG).show();
    }


    public void sendBet(final BettingtAdapter adapterBet, final List<Betting> bettingList, final int position, final ProgressDialog progressBet, final int teamSelectedChallenging){
        user=getUser();
        final int updateCoins = user.getCoins() - bettingList.get(position).getBetting();
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());

        //String url= AppConfig.URL_ACCEPTED_BETTING + "&idUserChallenging="+bettingList.get(position).getUser().getId()+"&teamSelection="+teamSelectedChallenging+"&idBetting="+bettingList.get(position).getId()+"&bet="+updateCoins+"&balance="+bettingList.get(position).getBetting();
        String url= AppConfig.URL_ACCEPTED_BETTING + "&idUserChallenging="+getUser().getId()+"&teamSelection="+teamSelectedChallenging+"&idBetting="+bettingList.get(position).getId()+"&bet="+updateCoins+"&balance="+bettingList.get(position).getBetting();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //restar de la base de datos de usuario
                        updateCointsUser(updateCoins);

                        Movement movement = new Movement(1,1,bettingList.get(position).getBetting());
                        insertMovement(movement);

                        //añadir historial
                        parseData(response, adapterBet, bettingList, position, progressBet, teamSelectedChallenging);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError ex) {
                        // Handle error
                        Toast.makeText(getApplicationContext(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(stringRequest);
    }

    private void parseData(String response, BettingtAdapter adapterBet, List<Betting> bettingList, int position, ProgressDialog progressBet, int teamSelectedChallenging){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try{
            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");

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

                progressBet.dismiss();
                bettingList.remove(position);
                adapterBet.notifyDataSetChanged();

            }else{
                String errorMsg = jObj.getString("msg");
                //encodeMessage(errorMsg);
            }

        }  catch (JSONException ex){
            Toast.makeText(getApplicationContext(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            progressBet.dismiss();
        }

        menssage(getString(R.string.menssage_bet_accepted));
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

    private void confirmLogOut(){
        ConfirmDialog objConfirm = new ConfirmDialog(getResources().getString(R.string.confirm), getResources().getString(R.string.message_logout),getResources().getString(R.string.yes), "No");
        FragmentManager fragmentManager = getSupportFragmentManager();
        new ConfirmLogOut(objConfirm).show(fragmentManager, "ConfirmLogOut");
    }

    @Override
    public void onPossitiveBtnClick() {
        logOutApp();
    }

    @Override
    public void onNegativeBtnClick() {

    }

    public void logOutApp(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            dbHelper.deteleUser(database);
            dbHelper.deteleWallet(database);
            dbHelper.deteleMovement(database);
            dbHelper.deteleHistorical(database);
            dbHelper.detelePrizes(database);
            dbHelper.deteleNotifications(database);
            dbHelper.detelePreferences(database);

            LoginManager.getInstance().logOut();
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        updateMenu(false);
        updateMenu(false);
    }


    public void showNotification(){
        List<Notification> lstNotifications=getNotification();
        if(lstNotifications.size()>0){
            NotificationDialog dialog = new NotificationDialog(lstNotifications);
            //NotificationDialog dialog = new NotificationDialog();
            //dialog.listNotifications=lstNotifications;
            dialog.show(getSupportFragmentManager(), "tag_somepopup");
        }
    }

    public List<Notification> getNotification(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        List<Notification> lstNotifications = new ArrayList<>();
        try {
            lstNotifications=dbHelper.getNotifications(database);
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }
        return lstNotifications;
    }



    public void menssage(String menssage) {
        Snackbar.make(navigationView, menssage, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void sendSaleCoins(int payPalMoney, int payPalCoins, int idUser){
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_SET_SALES+idUser+"&money="+payPalMoney+"&coins="+payPalCoins,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseSale(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError ex) {
                        // Handle error
                        Toast.makeText(getApplicationContext(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(stringRequest);
    }
    private void parseSale(String response){
        // Do something with the response
        try{

            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");

            if (success) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);
                snackbar.show();
            }else{
                String errorMsg = jObj.getString("msg");
                //encodeMessage(errorMsg);
            }

        }  catch (JSONException ex){
            Toast.makeText(getApplicationContext(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void validateConection(){
        if(validateConection.isConected()){
            if(isConnected==false){
                isConnected=true;
                Snackbar snackbar = Snackbar.make(coordinatorLayout, getResources().getText(R.string.reconnected_connection), Snackbar.LENGTH_SHORT);

                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(getResources().getColor(R.color.ranting_1));

                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);

                snackbar.show();
            }

        }else{
            if(isConnected==true) {
                isConnected=false;
                Snackbar snackbar = Snackbar.make(coordinatorLayout, getResources().getText(R.string.no_internet), Snackbar.LENGTH_INDEFINITE);

                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(getResources().getColor(R.color.ranting_4));

                // Changing action button text color
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setTextColor(Color.WHITE);

                snackbar.show();
            }
        }
    }
}

