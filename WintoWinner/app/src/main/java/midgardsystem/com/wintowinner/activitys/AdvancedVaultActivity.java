package midgardsystem.com.wintowinner.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mercadopago.callbacks.Callback;
import com.mercadopago.constants.Sites;
import com.mercadopago.core.MercadoPago;
import com.mercadopago.customviews.MPTextView;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.Card;
import com.mercadopago.model.Installment;
import com.mercadopago.model.Issuer;
import com.mercadopago.model.PayerCost;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.Token;
import com.mercadopago.util.ApiUtil;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.util.LayoutUtil;
import com.mercadopago.util.MercadoPagoUtil;

import java.math.BigDecimal;
import java.util.List;

import midgardsystem.com.wintowinner.R;

public class AdvancedVaultActivity  extends SimpleVaultActivity {

    protected BigDecimal mAmount;
    protected View mInstallmentsCard;
    protected FrameLayout mInstallmentsLayout;
    protected MPTextView mInstallmentsText;
    protected List<PayerCost> mPayerCosts;
    protected PayerCost mSelectedPayerCost;
    protected Issuer mSelectedIssuer;
    protected Issuer mTempIssuer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        try {
            mAmount = new BigDecimal(this.getIntent().getStringExtra("amount"));
        } catch (Exception ex) {
            mAmount = null;
        }

        if (mAmount != null) {

            mInstallmentsCard = findViewById(R.id.installmentsCard);
            mInstallmentsLayout = (FrameLayout) findViewById(R.id.installmentsLayout);
            mInstallmentsText = (MPTextView) findViewById(R.id.installmentsLabel);
            mInstallmentsCard.setVisibility(View.GONE);

        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("message", "Invalid parameters");
            setResult(RESULT_CANCELED, returnIntent);
            finish();
        }
    }

    @Override
    protected void setContentView() {

        setContentView(R.layout.activity_advanced_vault);
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
        } else if (mExceptionOnMethod.equals("getInstallmentsAsync")) {
            getInstallmentsAsync();
        } else if (mExceptionOnMethod.equals("getCreateTokenCallback")) {
            if (mSelectedCard != null) {
                createSavedCardToken();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MercadoPago.CUSTOMER_CARDS_REQUEST_CODE) {

            resolveCustomerCardsRequest(resultCode, data);

        } else if (requestCode == MercadoPago.PAYMENT_METHODS_REQUEST_CODE) {

            resolvePaymentMethodsRequest(resultCode, data);

        } else if (requestCode == MercadoPago.INSTALLMENTS_REQUEST_CODE) {

            resolveInstallmentsRequest(resultCode, data);

        } else if (requestCode == MercadoPago.ISSUERS_REQUEST_CODE) {

            resolveIssuersRequest(resultCode, data);

        } else if (requestCode == MercadoPago.NEW_CARD_REQUEST_CODE) {

            resolveCardRequest(resultCode, data);
        }
    }

    protected void resolveCustomerCardsRequest(int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            Card card = JsonUtil.getInstance().fromJson(data.getStringExtra("card"), Card.class);

            if (card != null) {

                // Set selection status
                mPayerCosts = null;
                mToken = null;
                mSelectedCard = card;
                mSelectedPayerCost = null;
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

            // Set selection status
            mTempIssuer = null;
            mTempPaymentMethod = JsonUtil.getInstance().fromJson(data.getStringExtra("paymentMethod"), PaymentMethod.class);

            if (MercadoPagoUtil.isCard(mTempPaymentMethod.getPaymentTypeId())) {  // Card-like methods

                if (mTempPaymentMethod.isIssuerRequired()) {

                    // Call issuer activity
                    startIssuersActivity();

                } else {

                    // Call new cards activity
                    startCardActivity();
                }
            }
        } else {

            if ((data != null) && (data.getStringExtra("apiException") != null)) {
                finishWithApiException(data);
            } else if ((mSelectedCard == null) && (mToken == null)) {
                // if nothing is selected
                finish();
            }
        }
    }

    protected void resolveInstallmentsRequest(int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            // Set selection status
            mSelectedPayerCost = JsonUtil.getInstance().fromJson(data.getStringExtra("payerCost"), PayerCost.class);

            // Update installments view
            mInstallmentsText.setText(mSelectedPayerCost.getRecommendedMessage());

        } else {

            if ((data != null) && (data.getStringExtra("apiException") != null)) {
                finishWithApiException(data);
            }
        }
    }

    protected void resolveIssuersRequest(int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            // Set selection status
            mTempIssuer = JsonUtil.getInstance().fromJson(data.getStringExtra("issuer"), Issuer.class);

            // Call new cards activity
            startCardActivity();

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

    protected void resolveCardRequest(int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            // Set selection status
            mPayerCosts = null;
            mToken = JsonUtil.getInstance().fromJson(data.getStringExtra("token"), Token.class);
            mSelectedCard = null;
            mSelectedPayerCost = null;
            mSelectedPaymentMethod = mTempPaymentMethod;
            mSelectedIssuer = mTempIssuer;

            this.refreshCardData();

        } else {

            if (data != null) {
                if (data.getStringExtra("apiException") != null) {

                    finishWithApiException(data);

                } else if (data.getBooleanExtra("backButtonPressed", false)) {

                    if (mTempPaymentMethod.isIssuerRequired()) {

                        startIssuersActivity();

                    } else {

                        startPaymentMethodsActivity();
                    }
                }
            }
        }
    }

    protected void refreshCardData() {
        // Set customer method selection
        mCustomerMethodsText.setText(getPaymentMethodLabel(mSelectedPaymentMethod.getName(),
                mToken.getLastFourDigits()));
        mPaymentMethodImage.setImageResource(MercadoPagoUtil.getPaymentMethodIcon(mActivity, mSelectedPaymentMethod.getId()));

        // Set security cards visibility
        showSecurityCodeCard(mSelectedPaymentMethod);

        // Get installments
        getInstallmentsAsync();
    }

    private void getInstallmentsAsync() {

        String bin = getSelectedPMBin();
        BigDecimal amount = mAmount;
        Long issuerId = (mSelectedIssuer != null) ? mSelectedIssuer.getId() : null;
        String paymentMethodId = mSelectedPaymentMethod.getId();

        if (bin.length() == MercadoPago.BIN_LENGTH) {
            LayoutUtil.showProgressLayout(mActivity);
            mMercadoPago.getInstallments(bin, amount, issuerId, paymentMethodId, new Callback<List<Installment>>() {
                @Override
                public void success(List<Installment> installments) {

                    LayoutUtil.showRegularLayout(mActivity);

                    if ((installments.size() > 0) && (installments.get(0).getPayerCosts().size() > 0)) {

                        // Set installments cards data and visibility
                        mPayerCosts = installments.get(0).getPayerCosts();
                        mSelectedPayerCost = installments.get(0).getPayerCosts().get(0);

                        if (installments.get(0).getPayerCosts().size() == 1) {

                            mInstallmentsCard.setVisibility(View.GONE);

                        } else {

                            mInstallmentsText.setText(mSelectedPayerCost.getRecommendedMessage());
                            mInstallmentsCard.setVisibility(View.VISIBLE);
                        }

                        // Set button visibility
                        mSubmitButton.setEnabled(true);

                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.mpsdk_invalid_pm_for_current_amount), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void failure(ApiException error) {

                    mExceptionOnMethod = "getInstallmentsAsync";
                    ApiUtil.finishWithApiException(mActivity, error);
                }
            });
        }
    }

    public void onInstallmentsClick(View view) {

        new MercadoPago.StartActivityBuilder()
                .setActivity(mActivity)
                .setPublicKey(mMerchantPublicKey)
                .setPayerCosts(mPayerCosts)
                .setPaymentMethod(mSelectedPaymentMethod)
                .setAmount(mAmount)
                .setSite(Sites.ARGENTINA)
                .startInstallmentsActivity();
    }

    protected void setCustomerMethodSelection() {

        // Set payment method and issuer
        mSelectedPaymentMethod = mSelectedCard.getPaymentMethod();
        mSelectedIssuer = mSelectedCard.getIssuer();

        // Set customer method selection
        mCustomerMethodsText.setText(getPaymentMethodLabel(mSelectedPaymentMethod.getName(), mSelectedCard.getLastFourDigits()));
        mPaymentMethodImage.setImageResource(MercadoPagoUtil.getPaymentMethodIcon(mActivity, mSelectedPaymentMethod.getId()));

        // Set security cards visibility
        showSecurityCodeCard(mSelectedCard.getPaymentMethod());

        // Get installments
        getInstallmentsAsync();
    }

    @Override
    public void submitForm(View view) {

        LayoutUtil.hideKeyboard(mActivity);

        // Validate installments
        if (mSelectedPayerCost == null) {
            return;
        }

        super.submitForm(view);
    }

    @Override
    protected Callback<Token> getCreateTokenCallback() {

        return new Callback<Token>() {
            @Override
            public void success(Token o) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra("token", o.getId());
                if (mSelectedIssuer != null) {
                    returnIntent.putExtra("issuerId", Long.toString(mSelectedIssuer.getId()));
                }
                returnIntent.putExtra("installments", Integer.toString(mSelectedPayerCost.getInstallments()));
                returnIntent.putExtra("paymentMethod", JsonUtil.getInstance().toJson(mSelectedPaymentMethod));
                setResult(RESULT_OK, returnIntent);
                finish();
            }

            @Override
            public void failure(ApiException error) {

                mExceptionOnMethod = "getCreateTokenCallback";
                ApiUtil.finishWithApiException(mActivity, error);
            }
        };
    }

    protected void startIssuersActivity() {

        new MercadoPago.StartActivityBuilder()
                .setActivity(mActivity)
                .setPublicKey(mMerchantPublicKey)
                .setPaymentMethod(mTempPaymentMethod)
                .startIssuersActivity();
    }
}

