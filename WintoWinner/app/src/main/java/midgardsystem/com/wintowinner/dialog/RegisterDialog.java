package midgardsystem.com.wintowinner.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.activitys.MainActivity;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.Historical;
import midgardsystem.com.wintowinner.objects.Match;
import midgardsystem.com.wintowinner.objects.Movement;
import midgardsystem.com.wintowinner.objects.TypeMovement;
import midgardsystem.com.wintowinner.objects.User;
import midgardsystem.com.wintowinner.objects.Wallet;
import midgardsystem.com.wintowinner.utils.AppConfig;
import midgardsystem.com.wintowinner.utils.Validations;

/**
 * Created by Gabriel on 04/09/2016.
 */
public class RegisterDialog  extends DialogFragment {
    View rootView;
    static User user;
    Validations validations = new Validations();

    TextInputLayout txtName;
    TextInputLayout txtLastNames;
    TextInputLayout txtEmail;
    TextInputLayout txtPsw;
    CheckBox cBoxShowTxtPwd;

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;
    /*
    /*
    CheckBox cBoxShowTxtPwd;
    CheckBox cBoxAccept;
*/
    Match match;
    ProgressDialog progress;
    DatabaseHelper dbHelper;

    public RegisterDialog() {}

    @SuppressLint("ValidFragment")
    public RegisterDialog(Match match) {
        this.match=match;
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
        rootView = inflater.inflate(R.layout.dialog_register, null);
        builder.setView(rootView);


        user = new User();
        init();

        dbHelper = new DatabaseHelper(getContext());

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

        //TextView lblTitle = (TextView) rootView.findViewById(R.id.lblTitle);
        /*
        cBoxAccept = (CheckBox) rootView.findViewById(R.id.checkBoxAccept);
        */

        txtName = (TextInputLayout) rootView.findViewById(R.id.name);
        txtLastNames = (TextInputLayout) rootView.findViewById(R.id.lastNames);
        txtEmail = (TextInputLayout) rootView.findViewById(R.id.email);
        txtPsw = (TextInputLayout) rootView.findViewById(R.id.psw);

        RelativeLayout btnRegister = (RelativeLayout) rootView.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }

        });
    }

    public static String byIdName(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }


    public void validate() {
        hideKeyboard();
        user.setName(txtName.getEditText().getText().toString());
        user.setLastNames(txtLastNames.getEditText().getText().toString());
        user.setEmail(txtEmail.getEditText().getText().toString());
        user.setPsw(txtPsw.getEditText().getText().toString());

        boolean valid_Email = validations.isEmail(user.getEmail());

        if (TextUtils.isEmpty(user.getName())) {
            txtName.setError(getString(R.string.invalidad_email));
        }else if(TextUtils.isEmpty(user.getLastNames())){
            txtName.setErrorEnabled(false);
            txtLastNames.setError(getString(R.string.enter_lastName));
        }else if (!validateEmail(user.getEmail())) {
            txtLastNames.setErrorEnabled(false);
            txtEmail.setError(getString(R.string.invalidad_email));
        } else if (TextUtils.isEmpty(user.getPsw())) {
            txtEmail.setErrorEnabled(false);
            txtPsw.setError(getString(R.string.enter_psw));
        } else {
            progress = ProgressDialog.show(getContext(), "Creando cuenta..",
                    "Esto podria tomar unos segundos...", true);
            txtName.setErrorEnabled(false);
            txtLastNames.setErrorEnabled(false);
            txtEmail.setErrorEnabled(false);
            txtPsw.setErrorEnabled(false);

            sendServer(user);
        }

        /*
        else if (cBoxAccept.isChecked()==false) {
            cBoxAccept.setEnabled(true);
            // ((MainActivity) getActivity()).alertMsn(1, getString(R.string.acepted_terms), 1, 2, true);
            Snackbar.make(rootView, R.string.acepted_terms, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        }
        */
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

    public void sendServer(final User user){
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

                        parseData(response, user);

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
        //enviar y recibir respuesta
        //true guardar en la bd interna
        //dimiss


    }
    private void parseData(String response, User objUser){
        hidePDialog();
        try{

            JSONObject jObj = new JSONObject(response);
            boolean success = jObj.getBoolean("success");

            if (success) {
                JSONArray valuesResult= jObj.getJSONArray("result");
                for ( int i=0; i< valuesResult.length(); i++) {
                    JSONObject obj = valuesResult.getJSONObject(i);

                    objUser.setId(obj.getInt("id"));
                    objUser.setIdUser(obj.getInt("id"));
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

                }

                SQLiteDatabase database = dbHelper.getWritableDatabase();
                try {
                    dbHelper.insertUser(database, objUser, true);
                    ((MainActivity) getActivity()).updateMenu(true);
                    showNewBettingDialog();

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

        dismiss();
    }

    public void showNewBettingDialog() {
        // Create the fragment and show it as a dialog.
        NewBettingDialog eDialog = new NewBettingDialog( ((MainActivity) getActivity()).getUser(), match);
        eDialog.show(getFragmentManager(), "tag_somepopup");
    }

    private void hidePDialog() {
        progress.dismiss();
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


