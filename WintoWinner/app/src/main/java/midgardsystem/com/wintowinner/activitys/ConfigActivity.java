package midgardsystem.com.wintowinner.activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Award;
import midgardsystem.com.wintowinner.objects.Continent;
import midgardsystem.com.wintowinner.objects.ContinentsCountries;
import midgardsystem.com.wintowinner.objects.CountrieLeagues;
import midgardsystem.com.wintowinner.objects.Countries;
import midgardsystem.com.wintowinner.objects.League;
import midgardsystem.com.wintowinner.objects.LeagueTeam;
import midgardsystem.com.wintowinner.objects.Match;
import midgardsystem.com.wintowinner.objects.Movement;
import midgardsystem.com.wintowinner.objects.Notification;
import midgardsystem.com.wintowinner.objects.Preference;
import midgardsystem.com.wintowinner.objects.Prize;
import midgardsystem.com.wintowinner.objects.Raffle;
import midgardsystem.com.wintowinner.objects.Sport;
import midgardsystem.com.wintowinner.objects.StatusRaffle;
import midgardsystem.com.wintowinner.objects.Team;
import midgardsystem.com.wintowinner.objects.TypeDate;
import midgardsystem.com.wintowinner.objects.TypeMatch;
import midgardsystem.com.wintowinner.objects.TypeMovement;
import midgardsystem.com.wintowinner.objects.TypeNotification;
import midgardsystem.com.wintowinner.objects.User;
import midgardsystem.com.wintowinner.utils.AppConfig;
import midgardsystem.com.wintowinner.utils.ValidateConection;

/**
 * Created by Gabriel Vazquez on 05/04/2017.
 */

public class ConfigActivity  extends AppCompatActivity {
    private Context context;
    ProgressDialog progress;

    private CoordinatorLayout coordinatorLayout;
    ValidateConection validateConection;
    private boolean isConnected=true;

    private Handler mHandler = new Handler();
    int onlyMatch;
    DatabaseHelper dbHelper;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_config);

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
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        Intent intent = getIntent();
        onlyMatch = intent.getIntExtra("onlyMatch", 0);

        context=this;
        dbHelper = new DatabaseHelper(context);

        if(isConnected==true){
            progress = ProgressDialog.show(context, "Configurando..",
                    "Esto podria tomar unos segundos...", true);

            startPreview();
        }


    }

    private void startPreview(){

        mHandler.postDelayed(new Runnable() {
            public void run() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                doInBackground();
                            }
                        });
                    }
                }).start();
            }
        }, 2000);


    }

    protected void doInBackground(String... params) {
        if(onlyMatch==1){

            deleteBasic();

            user=getUser();
            if(user!=null){
                getConfigLogin();
            }else{
                getConfig();
            }
        }else{
            getConfig();
        }
    }

    private void getConfig() {
        RequestQueue rq = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_GET_CONFIG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseConfig(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError ex) {
                        // Handle error
                        Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(stringRequest);
    }
    private void parseConfig(String response){
        // Do something with the response
        try{

            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");

            if (success) {
                JSONArray values= jObj.getJSONArray("result");

                JSONObject objJSSports = null;
                JSONObject objJSContinents = null;
                JSONObject objJSContinentsCountrys = null;
                JSONObject objJSContries = null;
                JSONObject objJSCountryLeague = null;
                JSONObject objJSLeagues = null;
                JSONObject objJSMatchs = null;
                for ( int i=0; i< values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);

                    objJSSports = obj.getJSONObject("sport");
                    objJSContinents = obj.getJSONObject("continents");
                    objJSContinentsCountrys = obj.getJSONObject("continentsCountry");
                    objJSContries = obj.getJSONObject("contries");
                    objJSCountryLeague=obj.getJSONObject("countryLeague");
                    objJSLeagues = obj.getJSONObject("league");
                    objJSMatchs = obj.getJSONObject("match");
                }

                parseSport(objJSSports);
                parseContinents(objJSContinents);
                parseContinentsCountrys(objJSContinentsCountrys);
                parseCountries(objJSContries);
                parseCountryLeague(objJSCountryLeague);
                parseLeagues(objJSLeagues);
                parseMatches(objJSMatchs);


                updateConfig();
            }else{
                String errorMsg = jObj.getString("msg");
                //encodeMessage(errorMsg);
            }

        }  catch (JSONException ex){
            Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void getConfigLogin() {
        RequestQueue rq = Volley.newRequestQueue(context.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.URL_GET_CONFIG_LOG_IN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseConfigLogin(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError ex) {
                        // Handle error
                        Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(stringRequest);
    }
    private void parseConfigLogin(String response){
        // Do something with the response
        try{
            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");

            if (success) {
                JSONArray values= jObj.getJSONArray("result");

                JSONObject objJSSports = null;
                JSONObject objJSContinents = null;
                JSONObject objJSContinentsCountrys = null;
                JSONObject objJSContries = null;
                JSONObject objJSCountryLeague = null;
                JSONObject objJSLeagues = null;
                JSONObject objJSMatchs = null;

                JSONArray objJSMovements = null;
                JSONArray objJSRaffle = null;
                JSONArray objJSPrizeRequest = null;

                for ( int i=0; i< values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);

                    objJSSports = obj.getJSONObject("sport");
                    objJSContinents = obj.getJSONObject("continents");
                    objJSContinentsCountrys = obj.getJSONObject("continentsCountry");
                    objJSContries = obj.getJSONObject("contries");
                    objJSCountryLeague=obj.getJSONObject("countryLeague");
                    objJSLeagues = obj.getJSONObject("league");
                    objJSMatchs = obj.getJSONObject("match");

                    if (! (obj.isNull("movements"))) {
                        objJSMovements = obj.getJSONArray("movements");
                    }

                    if (! (obj.isNull("raffle"))) {
                        objJSRaffle = obj.getJSONArray("raffle");
                    }

                    //get prize request
                    if (! (obj.isNull("prizeRequest"))) {
                        objJSPrizeRequest = obj.getJSONArray("prizeRequest");
                    }
                }

                parseSport(objJSSports);
                parseContinents(objJSContinents);
                parseContinentsCountrys(objJSContinentsCountrys);
                parseCountries(objJSContries);
                parseCountryLeague(objJSCountryLeague);
                parseLeagues(objJSLeagues);
                parseMatches(objJSMatchs);

                parseMovements(objJSMovements);
                parseRaffle(objJSRaffle);
                parsePrizeRequest(objJSPrizeRequest);

                updateConfig();
            }else{
                String errorMsg = jObj.getString("msg");
                //encodeMessage(errorMsg);
            }

        }  catch (JSONException ex){
            Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void parseSport(JSONObject objJS){
        final List<Sport> listSports  = new ArrayList<Sport>();
        // Do something with the response
        try{
            boolean success = objJS.getBoolean("success");

            if (success) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                JSONArray values= objJS.getJSONArray("result");

                for ( int i=0; i< values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);
                    Sport objSport = new Sport(obj.getInt("id"), obj.getString("name"), obj.getString("img"));
                    listSports.add(objSport);
                }

                for(int i=0; i< listSports.size();i++){
                    dbHelper.insertRowSports(database, listSports.get(i));
                }


            }else{
                String errorMsg = objJS.getString("msg");
                //encodeMessage(errorMsg);
            }

        }  catch (JSONException ex){
            Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void parseContinents(JSONObject objJS){
        final List<Continent> listContinents  = new ArrayList<Continent>();
        // Do something with the response
        try{
            boolean success = objJS.getBoolean("success");

            if (success) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                JSONArray values= objJS.getJSONArray("result");

                for ( int i=0; i< values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);
                    Continent objContinent = new Continent(obj.getInt("id"), obj.getString("name"));
                    listContinents.add(objContinent);
                }

                for(int i=0; i< listContinents.size();i++){
                    dbHelper.insertRowContinents(database, listContinents.get(i));
                }


            }else{
                String errorMsg = objJS.getString("msg");
                //encodeMessage(errorMsg);
            }

        }  catch (JSONException ex){
            Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void parseContinentsCountrys(JSONObject objJS){
        final List<ContinentsCountries> listCC  = new ArrayList<ContinentsCountries>();
        // Do something with the response
        try{
            boolean success = objJS.getBoolean("success");

            if (success) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                JSONArray values= objJS.getJSONArray("result");

                for ( int i=0; i< values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);
                    ContinentsCountries objCC = new ContinentsCountries();
                    objCC.setFkContinent(obj.getInt("fk_continent"));
                    objCC.setFkCountrie(obj.getInt("fk_countrie"));
                    listCC.add(objCC);
                }

                for(int i=0; i< listCC.size();i++){
                    dbHelper.insertRowContinentsCountries(database, listCC.get(i));
                }

            }else{
                String errorMsg = objJS.getString("msg");
                //encodeMessage(errorMsg);
            }

        }  catch (JSONException ex){
            Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void parseCountries(JSONObject objJS){
        final List<Countries> listCountries  = new ArrayList<Countries>();
        // Do something with the response
        try{
            boolean success = objJS.getBoolean("success");

            if (success) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                JSONArray values= objJS.getJSONArray("result");

                for ( int i=0; i< values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);
                    Countries objCountry = new Countries(obj.getInt("id"), obj.getString("name"));
                    listCountries.add(objCountry);
                }

                for(int i=0; i< listCountries.size();i++){
                    dbHelper.insertRowCountries(database, listCountries.get(i));
                }

            }else{
                String errorMsg = objJS.getString("msg");
                //encodeMessage(errorMsg);
            }

        }  catch (JSONException ex){
            Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void parseCountryLeague(JSONObject objJS){
        final List<CountrieLeagues> listCL  = new ArrayList<CountrieLeagues>();
        // Do something with the response
        try{
            boolean success = objJS.getBoolean("success");

            if (success) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                JSONArray values= objJS.getJSONArray("result");

                for ( int i=0; i< values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);
                    CountrieLeagues objCL = new CountrieLeagues();
                    objCL.setFkCountrie(obj.getInt("fk_countrie"));
                    objCL.setFkLeague(obj.getInt("fk_league"));
                    listCL.add(objCL);
                }

                for(int i=0; i< listCL.size();i++){
                    dbHelper.insertRowCountrieLeagues(database, listCL.get(i));
                }

            }else{
                String errorMsg = objJS.getString("msg");
                //encodeMessage(errorMsg);
            }

        }  catch (JSONException ex){
            Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void parseLeagues(JSONObject objJS){
        final List<League> leagueList  = new ArrayList<League>();
        // Do something with the response
        try{
            boolean success = objJS.getBoolean("success");

            if (success) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                JSONArray values= objJS.getJSONArray("result");

                for ( int i=0; i< values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);
                    League objLeague = new League();
                    objLeague.setId(obj.getInt("id"));
                    objLeague.setNameLeague(obj.getString("name"));
                    objLeague.setImgLeague(obj.getString("img"));
                    objLeague.setFkSport(obj.getInt("fk_sport"));

                    JSONObject objTeams = obj.getJSONObject("teams");
                    if (objTeams != null) {

                        success = objTeams.getBoolean("success");
                        JSONArray valuesTeams= objTeams.getJSONArray("result");
                        if (success) {
                            for ( int j=0; j< valuesTeams.length(); j++) {
                                JSONObject jObjTeam = valuesTeams.getJSONObject(j);
                                Team team = new Team();
                                team.setId(jObjTeam.getInt("id"));
                                team.setName(jObjTeam.getString("name"));
                                team.setImg(jObjTeam.getString("img"));

                                dbHelper.insertRowTeam(database, team);

                                LeagueTeam leagueTeam = new LeagueTeam();
                                leagueTeam.setFkLeague(objLeague.getId());
                                leagueTeam.setFkTeam(team.getId());
                                dbHelper.insertRowLeaguesTeam(database, leagueTeam);
                            }
                        }
                    }
                    leagueList.add(objLeague);
                }
                for(int i=0; i< leagueList.size();i++){
                    dbHelper.insertRowLeagues(database, leagueList.get(i));
                }

            }else{
                String errorMsg = objJS.getString("msg");
                //encodeMessage(errorMsg);
            }
        }  catch (JSONException ex){
            Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void parseMatches(JSONObject objJS){
        final List<Match> matchList  = new ArrayList<Match>();
        // Do something with the response
        try{
            boolean success = objJS.getBoolean("success");

            if (success) {
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                JSONArray values= objJS.getJSONArray("result");

                for ( int i=0; i< values.length(); i++) {
                    JSONObject obj = values.getJSONObject(i);
                    Match objMatch = new Match();
                    objMatch.setId(obj.getInt("id"));
                    objMatch.setFkLeague(obj.getInt("fk_league"));
                    objMatch.setFkTeamHome(obj.getInt("fk_team_home"));
                    objMatch.setFkTeamVisit(obj.getInt("fk_team_visit"));
                    objMatch.setDate(obj.getString("date"));
                    objMatch.setHour(obj.getString("hour"));

                    //objMatch.setStatusName(obj.getString("statusName"));
                    objMatch.setRound(obj.getString("round"));
                    objMatch.setStadium(obj.getString("stadium"));
                    objMatch.setCountriesName(obj.getString("countriesName"));
                    objMatch.setCity(obj.getString("city"));


                    //Obtener typeMatch
                    if (! (obj.isNull("typeMatch"))) {
                        JSONObject valuesTypeMatch = obj.getJSONObject("typeMatch");

                        TypeMatch typeMatch = new TypeMatch();
                        typeMatch.setId(valuesTypeMatch.getInt("id"));
                        typeMatch.setName(valuesTypeMatch.getString("name"));
                        objMatch.setFkTypeMatch(typeMatch.getId());
                    }

                    //Obtener typeDate
                    if (! (obj.isNull("typeDate"))) {
                        JSONObject valuesTypeMatch = obj.getJSONObject("typeDate");

                        TypeDate typeDate = new TypeDate();
                        typeDate.setId(valuesTypeMatch.getInt("id"));
                        typeDate.setName(valuesTypeMatch.getString("name"));
                        objMatch.setFkTypeDate(typeDate.getId());
                    }
                    matchList.add(objMatch);
                }
                if(dbHelper.deteleAllMatch(database)){
                    for(int i=0; i< matchList.size(); i++) {
                        dbHelper.insertRowMatch(database, matchList.get(i));
                    }
                }

            }else{
                String errorMsg = objJS.getString("msg");
                //encodeMessage(errorMsg);
            }
        }  catch (JSONException ex){
            Toast.makeText(context, "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void parseMovements(JSONArray arrayJS){
        ArrayList<Movement> movementList = new ArrayList<Movement>();

            for (int j = 0; j < arrayJS.length(); j++) {
                JSONObject objJSONMovements = null;
                try {
                    objJSONMovements = arrayJS.getJSONObject(j);
                    Movement objMovement = new Movement();

                    TypeMovement objTypeMovement = new TypeMovement();
                    objTypeMovement.setId(objJSONMovements.getInt("fkTypeMovement"));

                    objMovement.setFk_typeMovement(objJSONMovements.getInt("fkTypeMovement"));
                    objMovement.setFk_typeBalance(objJSONMovements.getInt("fkTypeBalance"));
                    objMovement.setBalance(objJSONMovements.getInt("balance"));
                    objMovement.setTypeMovement(objTypeMovement);
                    movementList.add(objMovement);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

    }
    private void parseRaffle(JSONArray arrayJS){
        ArrayList<Raffle> raffleList  = new ArrayList<Raffle>();
        for (int j = 0; j < arrayJS.length(); j++) {

            JSONObject objJSONRaffles = null;

            try {
                objJSONRaffles = arrayJS.getJSONObject(j);
                Raffle objRaffle = new Raffle();

                objRaffle.setFkMatch(objJSONRaffles.getInt("fkMatch"));
                objRaffle.setBalanceCoins(objJSONRaffles.getInt("balanceCoins"));
                objRaffle.setBalanceMoney(objJSONRaffles.getInt("balanceMoney"));

                //Obtener status de la apuesta
                if (! (objJSONRaffles.isNull("statusRaffle"))) {
                    JSONObject valuesStatusRaffle = objJSONRaffles.getJSONObject("statusRaffle");

                    StatusRaffle objStatusRaffle= new StatusRaffle();
                    objStatusRaffle.setId(valuesStatusRaffle.getInt("id"));
                    objStatusRaffle.setStatus(valuesStatusRaffle.getString("status"));
                    objRaffle.setStatusRaffle(objStatusRaffle);
                }

                //Conseguir el premio en elc aso de que hubiece ganado
                if (! (objJSONRaffles.isNull("prizes"))) {
                    JSONObject valuesPrizes = objJSONRaffles.getJSONObject("prizes");
                    Prize objPrize = new Prize();
                    objPrize.setPrizesName(valuesPrizes.getString("prizesName"));
                    objPrize.setPrizesImg(valuesPrizes.getString("prizesImg"));
                    objRaffle.setPrize(objPrize);
                }
                raffleList.add(objRaffle);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void parsePrizeRequest(JSONArray arrayJS){
        ArrayList<Award> awardList  = new ArrayList<Award>();
        for (int j = 0; j < arrayJS.length(); j++) {
            JSONObject objJSONPrizeRequest = null;
            try {
                objJSONPrizeRequest = arrayJS.getJSONObject(j);
                Award objAward = new Award();
                objAward.setRequestCreationDate(objJSONPrizeRequest.getString("requestCreationDate"));

                //Obtener premio
                if (! (objJSONPrizeRequest.isNull("prize"))) {
                    JSONObject valuesPrize = objJSONPrizeRequest.getJSONObject("prize");

                    objAward.setId(valuesPrize.getInt("id"));
                    objAward.setName(valuesPrize.getString("prizesName"));
                    objAward.setImg(valuesPrize.getString("prizesImg"));
                }

                //Obtener status del premio
                if (! (objJSONPrizeRequest.isNull("statusPrizes"))) {
                    JSONObject valuesPrize = objJSONPrizeRequest.getJSONObject("statusPrizes");

                    objAward.setFkStatusPrize(valuesPrize.getInt("id"));
                    objAward.setStatusPrize(valuesPrize.getString("status"));
                }
                awardList.add(objAward);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void updateConfig(){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            dbHelper.updateConfig(database);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //showSplashScreen();
        showMain();
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

    public void deleteBasic(){
        SQLiteDatabase database=null;
        try {
            database = dbHelper.getWritableDatabase();
            dbHelper.deteleSports(database);
            dbHelper.deteleContinents(database);
            dbHelper.deteleContinentsCountry(database);
            dbHelper.deteleCountry(database);
            dbHelper.deteleCountryLeagues(database);
            dbHelper.deteleLeagues(database);
            dbHelper.deleteLeagueTeams(database);
            dbHelper.deleteTeams(database);
            dbHelper.deteleAllMatch(database);
        }
        catch(Exception ex) {
            Log.e("ERROR", ex.getMessage());
        }finally {
            if(database!=null)
                database.close();
        }
    }

    void showSplashScreen(){
        Intent intent;
        intent = new Intent( getApplicationContext(), SplashActivity.class);
        intent.putExtra("configSuccess", 1);
        //intent = new Intent( getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
    void showMain(){
        progress.dismiss();
        Intent intent;
        intent = new Intent( getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
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

