package midgardsystem.com.wintowinner.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Award;
import midgardsystem.com.wintowinner.objects.Historical;
import midgardsystem.com.wintowinner.objects.Match;
import midgardsystem.com.wintowinner.objects.Movement;
import midgardsystem.com.wintowinner.objects.Notification;
import midgardsystem.com.wintowinner.objects.Preference;
import midgardsystem.com.wintowinner.objects.Prize;
import midgardsystem.com.wintowinner.objects.Raffle;
import midgardsystem.com.wintowinner.objects.StatusRaffle;
import midgardsystem.com.wintowinner.objects.TypeMatch;
import midgardsystem.com.wintowinner.objects.TypeMovement;
import midgardsystem.com.wintowinner.objects.TypeNotification;
import midgardsystem.com.wintowinner.objects.User;
import midgardsystem.com.wintowinner.objects.Wallet;
import midgardsystem.com.wintowinner.utils.AppConfig;
import midgardsystem.com.wintowinner.utils.Validations;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Gabriel on 25/09/2016.
 */
public class LoginFragment  extends Fragment {
    View rootView;
    DatabaseHelper dbHelper;
    Context context;
    LoginButton btnLoginFacebook;
    private CallbackManager callbackManager;


    TextInputLayout txtEmail;
    TextInputLayout txtPsw;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    static User user;
    Validations validations = new Validations();

    private ProgressDialog loading;
    public static final int DIALOG_FRAGMENT = 1;



    public LoginFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        this.context = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.menu_login));
        dbHelper = new DatabaseHelper(context);
        callbackManager=CallbackManager.Factory.create();

        initFacebook();
        init();

        loading = new ProgressDialog(getActivity());
        /*

        // Showing progress dialog before making http request
        loading.setMessage("Cargando apuestas...");
        loading.show();
*/

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


    public void initFacebook(){
        btnLoginFacebook = (LoginButton) rootView.findViewById(R.id.btnLoginFacebook);
        btnLoginFacebook.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));

        // If using in a fragment
        btnLoginFacebook.setFragment(this);
        // Other app specific specialization


        // Callback registration
        btnLoginFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loading = ProgressDialog.show(context, "Cargando","espere un momento...",false,false);

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("JSON", "" + response.getJSONObject().toString());

                        try {
                            User user = new User();

                            String id = object.getString("id");
                            //String name = object.getString("name");
                            user.setName(object.optString("first_name"));
                            user.setLastNames(object.optString("last_name"));
                            user.setEmail(object.optString("email"));
                            user.setImgUser("https://graph.facebook.com/" + id + "/picture?type=large");
                            //LoginManager.getInstance().logOut();

                            sendFBServer(user);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.dismiss();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,first_name,last_name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public void onResume() {
        super.onResume();
        //goMainScreen(profile);
        //  ((MainActivity) getActivity()).setActionBarTitle(getResources().getString(R.string.title_fragment_contacts));

    }
    @Override
    public void onPause() {
        super.onPause();
    }
    public void onStop() {
        super.onStop();
        //Facebook login
    }


    public void init() {
        user = new User();

        txtEmail = (TextInputLayout) rootView.findViewById(R.id.email);
        txtPsw = (TextInputLayout) rootView.findViewById(R.id.psw);

        RelativeLayout btnLogin = (RelativeLayout) rootView.findViewById(R.id.btnLogin);         // Obtiene el btn de cerrar de menu bar
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        RelativeLayout btnNewAccount = (RelativeLayout) rootView.findViewById(R.id.btnNewAccount);         // Obtiene el btn de cerrar de menu bar
        btnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegister();
            }
        });

    }

    public static String byIdName(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }

    public void validate() {
        hideKeyboard();

        user.setEmail(txtEmail.getEditText().getText().toString());
        user.setPsw(txtPsw.getEditText().getText().toString());

        if (!validateEmail(user.getEmail())) {
            txtEmail.setError(getString(R.string.invalidad_email));
        } else if (!validatePassword(user.getPsw())) {
            txtEmail.setErrorEnabled(false);
            txtPsw.setError(getString(R.string.enter_psw));
        } else {
            txtEmail.setErrorEnabled(false);
            txtPsw.setErrorEnabled(false);
            sendLoginServer(user);
        }

        /*
        boolean valid_Email = validations.isEmail(user.getEmail());
        if (valid_Email == false) {
            txtEmail.addValidator(new RegexpValidator(getString(R.string.invalidad_email), "\\d+"));
            txtEmail.validate();
        } else if (TextUtils.isEmpty(user.getPsw())) {
            txtPsw.addValidator(new RegexpValidator(getString(R.string.enter_psw), "\\d+"));
            txtPsw.validate();
        } else {
            sendServer(user);
        }*/
    }

    private void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean validatePassword(String password) {
        return password.length() > 2;
    }

    //Login
    public void sendLoginServer(final User user){
        //Bloquear Pendiente
        loading = ProgressDialog.show(context, "Cargando","espere un momento...",false,false);

        RequestQueue rq = Volley.newRequestQueue(getActivity());

        user.setEmail(user.getEmail().replace(" ", ""));
        user.setPsw(user.getPsw().replace(" ", ""));

        String url= AppConfig.URL_LOGIN+user.getEmail()+"&password="+user.getPsw();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        parseLoginData(response, user);
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

    }
    private void parseLoginData(String response, User objUser){
        try{

            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            if (success) {
                JSONArray valuesResult= jObj.getJSONArray("result");
                for ( int i=0; i< valuesResult.length(); i++) {
                    JSONObject obj = valuesResult.getJSONObject(i);

                    objUser.setId(obj.getInt("id"));
                    objUser.setIdUser(obj.getInt("id"));
                    objUser.setName(obj.getString("name"));
                    objUser.setLastNames(obj.getString("lastname"));
                    objUser.setImgUser(obj.getString("img"));
                    objUser.setCoins(obj.getInt("coins"));

                     if (!obj.isNull("movements")) {
                        JSONArray valuesMovements = obj.getJSONArray("movements");
                        ArrayList<Movement> listMovement = new ArrayList<Movement>();

                        for (int j = 0; j < valuesMovements.length(); j++) {
                            JSONObject objJSONMovements = valuesMovements.getJSONObject(j);
                            Movement objMovement = new Movement();

                            TypeMovement objTypeMovement = new TypeMovement();
                            objTypeMovement.setId(objJSONMovements.getInt("fkTypeMovement"));

                            objMovement.setFk_typeMovement(objJSONMovements.getInt("fkTypeMovement"));
                            objMovement.setFk_typeBalance(objJSONMovements.getInt("fkTypeBalance"));
                            objMovement.setBalance(objJSONMovements.getInt("balance"));
                            objMovement.setTypeMovement(objTypeMovement);
                            listMovement.add(objMovement);
                        }
                        objUser.setListMovement(listMovement);
                    }

                    if (!obj.isNull("wallet")) {
                        JSONArray valuesWallet = obj.getJSONArray("wallet");
                        ArrayList<Wallet> listWallet= new ArrayList<Wallet>();

                        for (int j = 0; j < valuesWallet.length(); j++) {
                            JSONObject objJSONWallet = valuesWallet.getJSONObject(j);

                            Wallet objWallet = new Wallet();
                            objWallet.setCurrentBalance(objJSONWallet.getInt("currentBalance"));
                            objWallet.setCurrentBalanceCoins(objJSONWallet.getInt("currentBalanceCoins"));

                            objUser.setWallet(objWallet);
                        }
                    }

                    if (! (obj.isNull("prizes"))) {
                        ArrayList<Award> awardList = new ArrayList<Award>();
                        JSONArray valuesPrizeRequest = obj.getJSONArray("prizes");

                        for (int j = 0; j < valuesPrizeRequest.length(); j++) {
                            JSONObject objJSONPrizeRequest = valuesPrizeRequest.getJSONObject(j);
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
                        }

                        for(int j=0; j< awardList.size();j++){
                            dbHelper.insertPrize(database, awardList.get(j), 1);
                        }
                    }

                    if (!obj.isNull("historical")) {
                        JSONArray valuesHistorical= obj.getJSONArray("historical");
                        ArrayList<Historical> listHistorical = new ArrayList<Historical>();

                        for (int j = 0; j < valuesHistorical.length(); j++) {
                            JSONObject objJSONHistoricals = valuesHistorical.getJSONObject(j);
                            Historical objHistorical = new Historical();

                            //objTypeMovement.setId(objJSONHistoricals.getInt("fkTypeMovement"));
                            objHistorical.setIdBetting(objJSONHistoricals.getInt("idBetting"));
                            objHistorical.setBetting(objJSONHistoricals.getInt("betting"));
                            objHistorical.setActive(objJSONHistoricals.getInt("active"));
                            objHistorical.setIdMatch(objJSONHistoricals.getInt("idMatch"));
                            objHistorical.setDate(objJSONHistoricals.getString("date"));
                            objHistorical.setHour(objJSONHistoricals.getString("hour"));
                            objHistorical.setTypeMatchName(objJSONHistoricals.getString("typeMatchName"));
                            objHistorical.setStatus(objJSONHistoricals.getString("status"));
                            objHistorical.setFkTeamHome(objJSONHistoricals.getInt("fk_team_home"));
                            objHistorical.setFkTeamVisit(objJSONHistoricals.getInt("fk_team_visit"));
                            objHistorical.setFkTeamWin(objJSONHistoricals.getInt("fk_team_win"));

                            objHistorical.setFkUserTeam(objJSONHistoricals.getInt("fk_user_team"));
                            objHistorical.setFkUserChallengingTeam(objJSONHistoricals.getInt("fk_user_challenging_team"));
                            objHistorical.setRound(objJSONHistoricals.getString("round"));


                            listHistorical.add(objHistorical);
                        }
                        objUser.setListHistorical(listHistorical);
                    }

                    //Notificacion
                    if (! (obj.isNull("notifications"))) {
                        JSONArray valuesNotifications= obj.getJSONArray("notifications");
                      //  ArrayList<Notification> objNotification = new ArrayList<Notification>();
                        ArrayList<Movement> notificationMovementList = new ArrayList<Movement>();
                        ArrayList<Match> notificationMatchList  = new ArrayList<Match>();
                        ArrayList<Raffle> notificationRaffleList  = new ArrayList<Raffle>();
                        ArrayList<Award> notificationAwardList  = new ArrayList<Award>();

                        for ( int j=0; j< valuesNotifications.length(); j++) {
                            JSONObject objNotification = valuesNotifications.getJSONObject(j);

                            if (! (objNotification.isNull("movements"))) {
                                JSONArray valuesMovements = objNotification.getJSONArray("movements");
                                for (int k = 0; k < valuesMovements.length(); k++) {
                                    JSONObject objJSONMovements = valuesMovements.getJSONObject(j);
                                    Movement objMovement = new Movement();

                                    TypeMovement objTypeMovement = new TypeMovement();
                                    objTypeMovement.setId(objJSONMovements.getInt("fkTypeMovement"));

                                    objMovement.setFk_typeMovement(objJSONMovements.getInt("fkTypeMovement"));
                                    objMovement.setFk_typeBalance(objJSONMovements.getInt("fkTypeBalance"));
                                    objMovement.setBalance(objJSONMovements.getInt("balance"));
                                    objMovement.setTypeMovement(objTypeMovement);
                                    notificationMovementList.add(objMovement);
                                }
                            }

                            if (! (objNotification.isNull("matchs"))) {
                                JSONArray valuesMatchs= objNotification.getJSONArray("matchs");

                                for (int k = 0; k < valuesMatchs.length(); k++) {
                                    JSONObject objJSONMatch = valuesMatchs.getJSONObject(j);
                                    Match objMatch = new Match();

                                    objMatch.setId(objJSONMatch.getInt("id"));
                                    objMatch.setFkLeague(objJSONMatch.getInt("fk_league"));
                                    objMatch.setFkTeamHome(objJSONMatch.getInt("fk_team_home"));
                                    objMatch.setFkTeamVisit(objJSONMatch.getInt("fk_team_visit"));
                                    objMatch.setDate(objJSONMatch.getString("date"));
                                    objMatch.setHour(objJSONMatch.getString("hour"));

                                    objMatch.setStatusName(objJSONMatch.getString("statusName"));
                                    objMatch.setRound(objJSONMatch.getString("round"));
                                    objMatch.setStadium(objJSONMatch.getString("stadium"));
                                    objMatch.setCountriesName(objJSONMatch.getString("countriesName"));
                                    objMatch.setCity(objJSONMatch.getString("city"));

                                    JSONObject valuesTypeMatch = objJSONMatch.getJSONObject("typeMatch");
                                    if (! (objJSONMatch.isNull("typeMatch"))) {
                                        TypeMatch typeMatch = new TypeMatch();
                                        typeMatch.setId(valuesTypeMatch.getInt("id"));
                                        typeMatch.setName(valuesTypeMatch.getString("name"));
                                        objMatch.setFkTypeMatch(typeMatch.getId());
                                    }
                                    notificationMatchList.add(objMatch);
                                }
                            } // end Match


                            if (! (objNotification.isNull("raffle"))) {
                                JSONArray valuesRaffle = objNotification.getJSONArray("raffle");

                                for (int k = 0; k < valuesRaffle.length(); k++) {
                                    JSONObject objJSONRaffles = valuesRaffle.getJSONObject(j);
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
                                    notificationRaffleList.add(objRaffle);
                                }
                            } // End Raffle

                            //get prize request
                            if (! (objNotification.isNull("prizeRequest"))) {
                                JSONArray valuesPrizeRequest = objNotification.getJSONArray("prizeRequest");
                                int numberOfItemsInValuesPrizeRequest = valuesPrizeRequest.length();
                                if(numberOfItemsInValuesPrizeRequest != 0 ){
                                    int o=6;
                                    for (int k = 0; k < numberOfItemsInValuesPrizeRequest; k++) {
                                        JSONObject objJSONPrizeRequest = valuesPrizeRequest.getJSONObject(j);
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
                                        notificationAwardList.add(objAward);
                                    }
                                }
                            }// End prizeRequest
                        } // End notifications

                        //Insert Match
                        /*
                        if(dbHelper.deleteAllMatch(database)){
                            for(int j=0; j< notificationMatchList.size(); j++) {
                                //xyz dbHelper.insertRowMatch(database, matchList.get(i));
                            }
                        }
                        */

                        //Update price request
                        if( notificationAwardList.size()>0) {
                            for (int j = 0; j < notificationAwardList.size(); j++) {
                                dbHelper.updatePrize(database, notificationAwardList.get(i));
                            }
                        }

                        //Insert Movimientos nuevos del tipo add
                        if(notificationMovementList.size()>0) {
                            int addBalanceCoins=0;
                            for (int j = 0; j < notificationMovementList.size(); j++) {
                                addBalanceCoins += notificationMovementList.get(i).getBalance();
                                dbHelper.insertMovement(database, notificationMovementList.get(i));
                            }
                            objUser.setCoins((objUser.getCoins() + addBalanceCoins) );
                            //xyz updateCointsUser(user.getCoins());


                            //Añadir notifciacion sobre monto añadido
                            Notification notification = new Notification();
                            TypeNotification typeNotification = new TypeNotification();
                            typeNotification.setId(1);
                            typeNotification.setTypeNotification("bonificacion");

                            notification.setTypeNotification(typeNotification);
                            notification.setBalanceCoins(addBalanceCoins);

                            //Añadir a base de datos
                            dbHelper.insertNotification(database, notification);
                        }

                        //si obtuvimos algun lugar en el rifa
                        //Insertar notifciacion
                        if(notificationRaffleList.size()>0) {
                            for (int j = 0; j < notificationRaffleList.size(); j++) {
                                Notification notification = new Notification();

                                TypeNotification typeNotification = new TypeNotification();
                                typeNotification.setId(2);
                                typeNotification.setTypeNotification("rifa");

                                notification.setTypeNotification(typeNotification);
                                notification.setPrize(notificationRaffleList.get(i).getPrize());

                                //Añadir a base de datos
                                dbHelper.insertNotification(database, notification);
                            }
                        }
                    }

                    /*3  valueTypeNotification.add(new TypeNotification(3, "info"));*/

                    //Preferences
                    if (!obj.isNull("preferences")) {
                        JSONArray valuesPreferences = obj.getJSONArray("preferences");
                        for (int j = 0; j < valuesPreferences.length(); j++) {
                            JSONObject objJSONPreferences = valuesPreferences.getJSONObject(j);

                            Preference objPreference= new Preference();
                            objPreference.setId(objJSONPreferences.getInt("id"));
                            objPreference.setFkTeam(objJSONPreferences.getInt("fk_team"));

                            dbHelper.insertRowPreferences(database, objPreference);
                        }
                    }


                }


                try {
                    dbHelper.insertUser(database, objUser, false);
                    loading.dismiss();
                    ((MainActivity) getActivity()).updateMenu(true);
                    showHome();
                }
                catch(Exception ex) {
                    Log.e("ERROR", ex.getMessage());
                }


            }else{
                hidePDialog();
                String errorMsg = jObj.getString("msg");
                //encodeMessage(errorMsg);

                //Snackbar.make(rootView, R.string.acepted_terms, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Snackbar.make(rootView, errorMsg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        }  catch (JSONException ex){
            Toast.makeText(getActivity(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    //Register
    public void sendRegisterServer(final User user){
        //Bloquear Pendiente
        RequestQueue rq = Volley.newRequestQueue(getActivity());

        user.setName(user.getName().replace(" ", "+"));
        user.setLastNames(user.getLastNames().replace(" ", "+"));
        user.setEmail(user.getEmail().replace(" ", ""));
        user.setPsw(user.getPsw().replace(" ", ""));

        String url= AppConfig.URL_CREATE_ACCOUNT+user.getName()+"&email="+user.getEmail()+"&lastname="+user.getLastNames()+"&password="+user.getPsw();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        //loading.dismiss();

                        parseRegisterData(response, user);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError ex) {
                        // Handle error
                        hidePDialog();
                        Toast.makeText(getActivity(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(stringRequest);
        //enviar y recibir respuesta
        //true guardar en la bd interna
        //dimiss


    }
    private void parseRegisterData(String response, User objUser){
        hidePDialog();
        try{

            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");

            if (success) {
                JSONArray valuesResult= jObj.getJSONArray("result");
                for ( int i=0; i< valuesResult.length(); i++) {
                    JSONObject obj = valuesResult.getJSONObject(i);

                    objUser.setId(obj.getInt("id"));
                    objUser.setImgUser(obj.getString("img"));
                    objUser.setCoins(obj.getInt("coins"));


                    if (!obj.isNull("movements")) {
                        JSONArray valuesMovements = obj.getJSONArray("movements");
                        ArrayList<Movement> listMovement = new ArrayList<Movement>();

                        for (int j = 0; j < valuesMovements.length(); j++) {
                            JSONObject objJSONMovements = valuesMovements.getJSONObject(i);
                            Movement objMovement = new Movement();

                            TypeMovement objTypeMovement = new TypeMovement();
                            objTypeMovement.setId(objJSONMovements.getInt("fkTypeMovement"));

                            objMovement.setFk_typeBalance(objJSONMovements.getInt("fkTypeBalance"));
                            objMovement.setBalance(objJSONMovements.getInt("balance"));
                            objMovement.setTypeMovement(objTypeMovement);
                            listMovement.add(objMovement);
                        }

                        objUser.setListMovement(listMovement);

                    }

                }

                SQLiteDatabase database = dbHelper.getWritableDatabase();
                try {
                    dbHelper.insertUser(database, objUser, true);
                    ((MainActivity) getActivity()).updateMenu(true);
                    showHome();
                }
                catch(Exception ex) {
                    Log.e("ERROR", ex.getMessage());
                }


            }else{
                String errorMsg = jObj.getString("msg");
                //encodeMessage(errorMsg);

                //Snackbar.make(rootView, R.string.acepted_terms, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Snackbar.make(rootView, errorMsg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        }  catch (JSONException ex){
            Toast.makeText(getActivity(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    //FaceBook
    public void sendFBServer(final User user){
        //Bloquear Pendiente
        //loading = ProgressDialog.show(context, "Cargando","Validando cuenta de facebook...",false,false);

        RequestQueue rq = Volley.newRequestQueue(getActivity());

        user.setEmail(user.getEmail().replace(" ", ""));
        user.setImgUser(user.getImgUser().replace(" ", ""));
        user.setName(user.getName().replace(" ", "+"));
        user.setLastNames(user.getLastNames().replace(" ", "+"));

        String url= AppConfig.URL_LOGIN_FB+user.getName()+"&email="+user.getEmail()+"&lastname="+user.getLastNames()+"&img="+user.getImgUser();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Do something with the response
                        parseDataFB(response, user);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError ex) {
                        // Handle error
                        Toast.makeText(getActivity(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                });

        rq.add(stringRequest);

    }
    private void parseDataFB(String response, User user){
        try{

            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");

            if (success) {
                parseLoginData(response, user);
            }else{
                hidePDialog();
                String errorMsg = jObj.getString("msg");
                //encodeMessage(errorMsg);

                //Snackbar.make(rootView, R.string.acepted_terms, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Snackbar.make(rootView, errorMsg, Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }

        }  catch (JSONException ex){
            Toast.makeText(getActivity(), "Json error: " + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    public void showRegister() {
        Fragment fragment = null;
        fragment = new RegisterFragment();
        String TAG_HOME="";

        if (fragment != null) {

            FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();

            fragmentManager.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentManager.replace(R.id.main_content, fragment, TAG_HOME);
            fragmentManager.addToBackStack(null);
            fragmentManager.commit();

        }
    }
    public void showHome(){
        Fragment fragment = null;
        fragment = new HomeFragment();
        String TAG_HOME="";

        if (fragment != null) {

            FragmentTransaction fragmentManager = getActivity().getSupportFragmentManager().beginTransaction();

            fragmentManager.setCustomAnimations(R.anim.fab_slide_in_from_right, R.anim.slide_out_right);
            fragmentManager.replace(R.id.main_content, fragment, TAG_HOME);
            fragmentManager.addToBackStack(null);
            fragmentManager.commit();

        }
    }

    private void hidePDialog() {
        if (loading != null) {
            loading.dismiss();
            loading = null;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Don't forget to check requestCode before continuing your job
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}

