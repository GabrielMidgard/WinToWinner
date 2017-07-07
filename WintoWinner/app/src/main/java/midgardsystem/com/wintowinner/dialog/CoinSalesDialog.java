package midgardsystem.com.wintowinner.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.mercadopago.constants.PaymentTypes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.WintoWinnerApp;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.objects.User;
import midgardsystem.com.wintowinner.utils.ExamplesUtils;
import midgardsystems.lib.numericUpDown.NumericUpDown;

/**
 * Created by TELMEX on 26/04/2017.
 */

public class CoinSalesDialog  extends DialogFragment implements NumericUpDown.OnQuantityChangeListener{
    View rootView;
    private int pricePerProduct = 180;
    private NumericUpDown quantityView;
    private User user;
    TextView txtCoins;

    protected List<String> mExcludedPaymentTypes = new ArrayList<String>() {{
        add(PaymentTypes.TICKET);
        add(PaymentTypes.ATM);
    }};

    RadioButton bid_50;
    RadioButton bid_100;
    RadioButton bid_200;
    RadioButton bid_500;

    ImageLoader imageLoader = WintoWinnerApp.getInstance().getImageLoader();
    DatabaseHelper dbHelper;

    public CoinSalesDialog() {}

    @SuppressLint("ValidFragment")
    public CoinSalesDialog(User user) {
        this.user=user;
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
        rootView = inflater.inflate(R.layout.dialog_coins_sales, null);
        builder.setView(rootView);
        dbHelper = new DatabaseHelper(getContext());

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

        txtCoins= (TextView)rootView.findViewById(R.id.txtCoins);

        final RelativeLayout btnSale = (RelativeLayout)rootView.findViewById(R.id.btnSale);

        btnSale.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Call final vault activity
                        ExamplesUtils.startAdvancedVaultActivity(getActivity(), ExamplesUtils.DUMMY_MERCHANT_PUBLIC_KEY_EXAMPLES_SERVICE,
                                ExamplesUtils.DUMMY_MERCHANT_BASE_URL, ExamplesUtils.DUMMY_MERCHANT_GET_CUSTOMER_URI,
                                ExamplesUtils.DUMMY_MERCHANT_ACCESS_TOKEN, new BigDecimal(quantityView.getQuantity()), mExcludedPaymentTypes);
                        /*
                        Intent intent = new Intent( getContext(), PayPalActivity.class);
                        intent.putExtra("costSales", quantityView.getQuantity());
                        startActivity(intent);
                        //EvaluationGlobalMarketActivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_in_left);
                        //this.finish();
                        */
                    }
                }
        );


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


        quantityView.setOnQuantityChangeListener(this);
        quantityView.setQuantityClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int coins=quantityView.getQuantity()*1000;
                txtCoins.setText(coins+"");
            }

        });
    }

    public static String byIdName(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }


    @Override
    public void onQuantityChanged(int newQuantity, boolean programmatically) {

        int coins=newQuantity*1000;
        txtCoins.setText(coins+"");
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
