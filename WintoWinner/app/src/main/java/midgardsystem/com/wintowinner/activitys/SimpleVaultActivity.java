package midgardsystem.com.wintowinner.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mercadopago.callbacks.Callback;
import com.mercadopago.core.MercadoPago;
import com.mercadopago.core.MerchantServer;
import com.mercadopago.customviews.MPButton;
import com.mercadopago.customviews.MPEditText;
import com.mercadopago.customviews.MPTextView;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.Card;
import com.mercadopago.model.Customer;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.SavedCardToken;
import com.mercadopago.model.Token;
import com.mercadopago.preferences.PaymentPreference;
import com.mercadopago.util.ApiUtil;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.util.LayoutUtil;
import com.mercadopago.util.MercadoPagoUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.utils.ExamplesUtils;

/**
 * Created by TELMEX on 07/07/2017.
 */

public class SimpleVaultActivity  extends AppCompatActivity {

    // Activity parameters
    protected String mMerchantAccessToken;
    protected String mMerchantBaseUrl;
    protected String mMerchantGetCustomerUri;
    protected String mMerchantPublicKey;
    protected List<String> mExcludedPaymentTypes;
    protected List<String> mExcludedPaymentMethodIds;

    // Input controls
    protected View mSecurityCodeCard;
    protected MPEditText mSecurityCodeText;
    protected LinearLayout mCustomerMethodsLayout;
    protected MPTextView mCustomerMethodsText;
    protected ImageView mCVVImage;
    protected MPTextView mCVVDescriptor;
    protected MPButton mSubmitButton;

    // Current values
    protected List<Card> mCards;
    protected Token mToken;
    protected Card mSelectedCard;
    protected PaymentMethod mSelectedPaymentMethod;
    protected PaymentMethod mTempPaymentMethod;
    protected PaymentPreference mPaymentPreference;


    // Local vars
    protected Activity mActivity;
    protected String mExceptionOnMethod;
    protected MercadoPago mMercadoPago;
    protected ImageView mPaymentMethodImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView();
        // Get activity parameters
        getActivityParameters();
        if ((mMerchantPublicKey != null) && (!mMerchantPublicKey.equals(""))) {

            // Set activity
            mActivity = this;
            mActivity.setTitle(getString(R.string.mpsdk_title_activity_vault));

            initializeControls();

            // Init controls visibility
            mSecurityCodeCard.setVisibility(View.GONE);

            // Init MercadoPago object with public key
            mMercadoPago = new MercadoPago.Builder()
                    .setContext(mActivity)
                    .setPublicKey(mMerchantPublicKey)
                    .build();

            // Set customer method first value
            mCustomerMethodsText.setText(getString(com.mercadopago.R.string.mpsdk_select_pm_label));

            // Set "Go" button
            setFormGoButton(mSecurityCodeText);

            // Hide main layout and go for customer's cards
            if ((mMerchantBaseUrl != null) && (!mMerchantBaseUrl.equals("") && (mMerchantGetCustomerUri != null) && (!mMerchantGetCustomerUri.equals("")))) {
                getCustomerCardsAsync();
            }
            createPaymentPreference();
        } else {
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED, returnIntent);
            returnIntent.putExtra("message", "Invalid parameters");
            finish();
        }
    }

    private void initializeControls() {
        /*
        mSecurityCodeCard = findViewById(R.id.securityCodeCard);
        mCVVImage = (ImageView) findViewById(R.id.cVVImage);
        mCVVDescriptor = (MPTextView) findViewById(R.id.cVVDescriptor);
        mSubmitButton = (MPButton) findViewById(R.id.submitButton);
        mCustomerMethodsLayout = (LinearLayout) findViewById(R.id.customerMethodLayout);
        mPaymentMethodImage = (ImageView) findViewById(R.id.pmImage);
        mCustomerMethodsText = (MPTextView) findViewById(R.id.customerMethodLabel);
        mSecurityCodeText = (MPEditText) findViewById(R.id.securityCode);
        */
    }

    private void getActivityParameters() {
        mMerchantPublicKey = this.getIntent().getStringExtra("merchantPublicKey");
        mMerchantBaseUrl = this.getIntent().getStringExtra("merchantBaseUrl");
        mMerchantGetCustomerUri = this.getIntent().getStringExtra("merchantGetCustomerUri");
        mMerchantAccessToken = this.getIntent().getStringExtra("merchantAccessToken");
        if (this.getIntent().getStringExtra("excludedPaymentTypes") != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            mExcludedPaymentTypes = gson.fromJson(this.getIntent().getStringExtra("excludedPaymentTypes"), listType);
        }
        if (this.getIntent().getStringExtra("excludedPaymentMethodIds") != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            mExcludedPaymentMethodIds = gson.fromJson(this.getIntent().getStringExtra("excludedPaymentMethodIds"), listType);
        }
    }

    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("backButtonPressed", true);
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    public void refreshLayout(View view) {

        // Retry method call
        if (mExceptionOnMethod.equals("getCustomerCardsAsync")) {
            getCustomerCardsAsync();
        } else if (mExceptionOnMethod.equals("getCreateTokenCallback")) {
            if (mSelectedCard != null) {
                createSavedCardToken();
            }
        }
    }

    protected void createPaymentPreference() {
        mPaymentPreference = new PaymentPreference();
        mPaymentPreference.setExcludedPaymentMethodIds(this.mExcludedPaymentMethodIds);
        mPaymentPreference.setExcludedPaymentTypeIds(this.mExcludedPaymentTypes);
    }

    public void onCustomerMethodsClick(View view) {

        if ((mCards != null) && (mCards.size() > 0)) {  // customer cards activity

            new MercadoPago.StartActivityBuilder()
                    .setActivity(mActivity)
                    .setCards(mCards)
                    .startCustomerCardsActivity();

        } else {  // payment method activity

            new MercadoPago.StartActivityBuilder()
                    .setActivity(mActivity)
                    .setPublicKey(mMerchantPublicKey)
                    .setPaymentPreference(mPaymentPreference)
                    .startPaymentMethodsActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MercadoPago.CUSTOMER_CARDS_REQUEST_CODE) {

            resolveCustomerCardsRequest(resultCode, data);

        } else if (requestCode == MercadoPago.PAYMENT_METHODS_REQUEST_CODE) {

            resolvePaymentMethodsRequest(resultCode, data);

        } else if (requestCode == ExamplesUtils.CARD_REQUEST_CODE) {

            resolveCardRequest(resultCode, data);
        }
    }

    protected void setContentView() {

        setContentView(R.layout.activity_simple_vault);
    }

    protected void resolveCustomerCardsRequest(int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            Card card = JsonUtil.getInstance().fromJson(data.getStringExtra("card"), Card.class);

            if (card != null) {

                // Set selection status
                mToken = null;
                mSelectedCard = card;
                mSelectedPaymentMethod = null;
                mTempPaymentMethod = null;

                // Set customer method selection
                setCustomerMethodSelection();

            } else {

                startPaymentMethodsActivity();
            }
        } else {

            if ((data != null) && (data.getStringExtra("apiException") != null)) {
                finishWithApiException(data);
            }
        }
    }

    protected void resolvePaymentMethodsRequest(int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            mTempPaymentMethod = JsonUtil.getInstance().fromJson(data.getStringExtra("paymentMethod"), PaymentMethod.class);

            // Call new cards activity
            startCardActivity();

        } else {

            if ((data != null) && (data.getStringExtra("apiException") != null)) {
                finishWithApiException(data);
            } else if ((mSelectedCard == null) && (mToken == null)) {
                // if nothing is selected
                finish();
            }
        }
    }

    protected void resolveCardRequest(int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            // Set selection status
            mToken = JsonUtil.getInstance().fromJson(data.getStringExtra("token"), Token.class);

            mSelectedCard = null;

            mSelectedPaymentMethod = mTempPaymentMethod;

            // Set customer method selection
            mCustomerMethodsText.setText(getPaymentMethodLabel(mSelectedPaymentMethod.getName(),
                    mToken.getLastFourDigits()));
            mPaymentMethodImage.setImageResource(MercadoPagoUtil.getPaymentMethodIcon(mActivity, mSelectedPaymentMethod.getId()));

            // Set security cards visibility
            showSecurityCodeCard(mSelectedPaymentMethod);

            // Set button visibility
            mSubmitButton.setEnabled(true);

        } else {

            if (data != null) {
                if (data.getStringExtra("apiException") != null) {

                    finishWithApiException(data);

                } else if (data.getBooleanExtra("backButtonPressed", false)) {

                    startPaymentMethodsActivity();
                }
            }
        }
    }

    protected String getPaymentMethodLabel(String name, String lastFourDigits) {
        return name + " " + getString(com.mercadopago.R.string.mpsdk_last_digits_label) + " " + lastFourDigits;
    }

    protected void getCustomerCardsAsync() {

        LayoutUtil.showProgressLayout(mActivity);
        MerchantServer.getCustomer(this, mMerchantBaseUrl, mMerchantGetCustomerUri, mMerchantAccessToken, new Callback<Customer>() {
            @Override
            public void success(Customer customer) {

                mCards = getSupportedCustomerCards(customer.getCards());
                LayoutUtil.showRegularLayout(mActivity);
            }

            @Override
            public void failure(ApiException error) {

                mExceptionOnMethod = "getCustomerCardsAsync";
                ApiUtil.finishWithApiException(mActivity, error);
            }
        });
    }

    protected void showSecurityCodeCard(PaymentMethod paymentMethod) {

        if (paymentMethod != null) {

            if (isSecurityCodeRequired()) {

                if ("amex".equals(paymentMethod.getId())) {
                    mCVVDescriptor.setText(String.format(getString(com.mercadopago.R.string.mpsdk_cod_seg_desc_amex), 4));
                } else {
                    mCVVDescriptor.setText(String.format(getString(com.mercadopago.R.string.mpsdk_cod_seg_desc), 3));
                }

                int res = MercadoPagoUtil.getPaymentMethodImage(mActivity, paymentMethod.getId());
                if (res != 0) {
                    mCVVImage.setImageDrawable(getResources().getDrawable(res));
                }

                mSecurityCodeCard.setVisibility(View.VISIBLE);

            } else {

                mSecurityCodeCard.setVisibility(View.GONE);
            }
        }
    }

    protected String getSelectedPMBin() {

        if (mSelectedCard != null) {
            return mSelectedCard.getFirstSixDigits();
        } else {
            return mToken.getFirstSixDigits();
        }
    }

    private boolean isSecurityCodeRequired() {

        if (mSelectedCard != null) {
            return mSelectedCard.isSecurityCodeRequired();
        } else {
            return mSelectedPaymentMethod.isSecurityCodeRequired(getSelectedPMBin());
        }
    }

    private void setFormGoButton(final MPEditText editText) {

        editText.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    submitForm(v);
                }
                return false;
            }
        });
    }

    protected void setCustomerMethodSelection() {

        // Set payment method
        mSelectedPaymentMethod = mSelectedCard.getPaymentMethod();

        // Set customer method selection
        mCustomerMethodsText.setText(getPaymentMethodLabel(mSelectedPaymentMethod.getName(), mSelectedCard.getLastFourDigits()));
        mPaymentMethodImage.setImageResource(MercadoPagoUtil.getPaymentMethodIcon(mActivity, mSelectedPaymentMethod.getId()));

        // Set security cards visibility
        showSecurityCodeCard(mSelectedCard.getPaymentMethod());

        // Set button visibility
        mSubmitButton.setEnabled(true);
    }

    public void submitForm(View view) {

        LayoutUtil.hideKeyboard(mActivity);

        // Create token
        if (mSelectedCard != null) {
            createSavedCardToken();
        } else if (mToken != null) {
            finishWithTokenResult(mToken);
        }
    }


    protected void createSavedCardToken() {

        SavedCardToken savedCardToken = new SavedCardToken(mSelectedCard.getId(), mSecurityCodeText.getText().toString());

        // Validate CVV
        try {
            savedCardToken.validateSecurityCode(this, mSelectedCard);
            mSecurityCodeText.setError(null);
        } catch (Exception ex) {
            mSecurityCodeText.setError(ex.getMessage());
            mSecurityCodeText.requestFocus();
            return;
        }

        // Create token
        LayoutUtil.showProgressLayout(mActivity);
        mMercadoPago.createToken(savedCardToken, getCreateTokenCallback());
    }

    protected Callback<Token> getCreateTokenCallback() {

        return new Callback<Token>() {
            @Override
            public void success(Token token) {
                finishWithTokenResult(token);
            }

            @Override
            public void failure(ApiException error) {

                mExceptionOnMethod = "getCreateTokenCallback";
                ApiUtil.finishWithApiException(mActivity, error);
            }
        };
    }

    private void finishWithTokenResult(Token token) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("token", token.getId());
        returnIntent.putExtra("paymentMethod", JsonUtil.getInstance().toJson(mSelectedPaymentMethod));
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    protected void finishWithApiException(Intent data) {

        setResult(RESULT_CANCELED, data);
        finish();
    }

    protected void startCardActivity() {

        ExamplesUtils.startCardActivity(this, mMerchantPublicKey, mTempPaymentMethod);
    }

    protected void startPaymentMethodsActivity() {

        new MercadoPago.StartActivityBuilder()
                .setActivity(mActivity)
                .setPublicKey(mMerchantPublicKey)
                .setPaymentPreference(mPaymentPreference)
                .startPaymentMethodsActivity();
    }

    private List<Card> getSupportedCustomerCards(List<Card> cards) {
        List<Card> supportedCards = new ArrayList<>();
        if (mPaymentPreference != null) {
            for (Card card : cards) {
                if (mPaymentPreference.isPaymentMethodSupported(card.getPaymentMethod()))
                    supportedCards.add(card);
            }
            return supportedCards;
        } else
            return cards;
    }
}
