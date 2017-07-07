package midgardsystems.lib.numericUpDown;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AlertDialog;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Global on 01/12/2015.
 *
 */
public class NumericUpDown extends LinearLayout implements View.OnClickListener {

        private Drawable quantityBackground, addButtonBackground, removeButtonBackground;

        private String addButtonText, removeButtonText;

        private int quantity, maxQuantity, minQuantity, bid;
        private int quantityPadding;

        private int quantityTextColor, addButtonTextColor, removeButtonTextColor;

        private Button mButtonAdd, mButtonRemove;
        private TextView mTextViewQuantity;

        public interface OnQuantityChangeListener {
                void onQuantityChanged(int newQuantity, boolean programmatically);
                void onLimitReached();
        }

        private OnQuantityChangeListener onQuantityChangeListener;
        private View.OnClickListener mTextViewClickListener;
        private View.OnClickListener mButtonAddClickListener;
        private View.OnClickListener mButtonRemoveClickListener;

        public NumericUpDown(Context context, int i) {
            super(context);
            init(null, 0);
        }

        public NumericUpDown(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(attrs, 0);
        }

        public NumericUpDown(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init(attrs, defStyle);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        private void init(AttributeSet attrs, int defStyle) {
            final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.QuantityView, defStyle, 0);

            addButtonText = getResources().getString(R.string.add);
            if (a.hasValue(R.styleable.QuantityView_addButtonText)) {
                addButtonText = a.getString(R.styleable.QuantityView_addButtonText);
            }
            addButtonBackground = ContextCompat.getDrawable(getContext(), R.drawable.btn_selector);
            if (a.hasValue(R.styleable.QuantityView_addButtonBackground)) {
                addButtonBackground = a.getDrawable(R.styleable.QuantityView_addButtonBackground);
            }
            addButtonTextColor = a.getColor(R.styleable.QuantityView_addButtonTextColor, Color.BLACK);

            removeButtonText = getResources().getString(R.string.remove);
            if (a.hasValue(R.styleable.QuantityView_removeButtonText)) {
                removeButtonText = a.getString(R.styleable.QuantityView_removeButtonText);
            }
            removeButtonBackground = ContextCompat.getDrawable(getContext(), R.drawable.btn_selector);
            if (a.hasValue(R.styleable.QuantityView_removeButtonBackground)) {
                removeButtonBackground = a.getDrawable(R.styleable.QuantityView_removeButtonBackground);
            }
            removeButtonTextColor = a.getColor(R.styleable.QuantityView_removeButtonTextColor, Color.BLACK);

            quantity = a.getInt(R.styleable.QuantityView_quantity, 0);
            maxQuantity = a.getInt(R.styleable.QuantityView_maxQuantity, Integer.MAX_VALUE);
            minQuantity = a.getInt(R.styleable.QuantityView_minQuantity, 0);
            bid=a.getInt(R.styleable.QuantityView_bid,0);

            quantityPadding = (int) a.getDimension(R.styleable.QuantityView_quantityPadding, pxFromDp(24));
            quantityTextColor = a.getColor(R.styleable.QuantityView_quantityTextColor, Color.BLACK);
            quantityBackground = ContextCompat.getDrawable(getContext(), R.drawable.bg_selector);
            if (a.hasValue(R.styleable.QuantityView_quantityBackground)) {
                quantityBackground = a.getDrawable(R.styleable.QuantityView_quantityBackground);
            }

            a.recycle();
            int dp10 = pxFromDp(10);

            mButtonAdd = new Button(getContext());
            mButtonAdd.setGravity(Gravity.CENTER);
            mButtonAdd.setPadding(dp10, dp10, dp10, dp10);
            mButtonAdd.setMinimumHeight(0);
            mButtonAdd.setMinimumWidth(0);
            mButtonAdd.setMinHeight(0);
            mButtonAdd.setMinWidth(0);
            setAddButtonBackground(addButtonBackground);
            setAddButtonText(addButtonText);
            setAddButtonTextColor(addButtonTextColor);

            mButtonRemove = new Button(getContext());
            mButtonRemove.setGravity(Gravity.CENTER);
            mButtonRemove.setPadding(dp10, dp10, dp10, dp10);
            mButtonRemove.setMinimumHeight(0);
            mButtonRemove.setMinimumWidth(0);
            mButtonRemove.setMinHeight(0);
            mButtonRemove.setMinWidth(0);
            setRemoveButtonBackground(removeButtonBackground);
            setRemoveButtonText(removeButtonText);
            setRemoveButtonTextColor(removeButtonTextColor);

            mTextViewQuantity = new TextView(getContext());
            mTextViewQuantity.setGravity(Gravity.CENTER);
            setQuantityTextColor(quantityTextColor);
            setQuantity(quantity);
            setQuantityBackground(quantityBackground);
            setQuantityPadding(quantityPadding);

            setOrientation(HORIZONTAL);

            addView(mButtonRemove, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            addView(mTextViewQuantity, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
            addView(mButtonAdd, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            mButtonAdd.setOnClickListener(this);
            mButtonRemove.setOnClickListener(this);
            mTextViewQuantity.setOnClickListener(this);
        }


    public void setQuantityClickListener(View.OnClickListener ocl) {
        mTextViewClickListener = ocl;
        mButtonAddClickListener=ocl;
        mButtonRemoveClickListener=ocl;
    }

    @Override
    public void onClick(View v) {
        if (v == mButtonAdd) {
            if (quantity + bid > maxQuantity) {
                if (onQuantityChangeListener != null) onQuantityChangeListener.onLimitReached();
            } else {
                quantity += bid;
                mTextViewQuantity.setText(String.valueOf(quantity));
                if (onQuantityChangeListener != null) {
                  //  onQuantityChangeListener.onQuantityChanged(quantity, false);
                    mButtonAddClickListener.onClick(v);
                }
            }
        } else if (v == mButtonRemove) {
            if (quantity - bid < minQuantity) {
                if (onQuantityChangeListener != null) onQuantityChangeListener.onLimitReached();
            } else {
                quantity -= bid;
                mTextViewQuantity.setText(String.valueOf(quantity));
                if (onQuantityChangeListener != null){
                    // onQuantityChangeListener.onQuantityChanged(quantity, false);
                    mButtonRemoveClickListener.onClick(v);
                }
            }
        } else if (v == mTextViewQuantity) {
            if (mTextViewClickListener != null) {
                mTextViewClickListener.onClick(v);
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(byIdName( getContext(), "change_quantity"));

            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_changequantity, null, false);
            final EditText et = (EditText) inflate.findViewById(R.id.txtChangeQuantity);
            et.setText(String.valueOf(quantity));

            builder.setView(inflate);
            builder.setPositiveButton(byIdName( getContext(), "change"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String newQuantity = et.getText().toString();
                    if (isNumber(newQuantity)) {
                        int intNewQuantity = Integer.parseInt(newQuantity);
                        setQuantity(intNewQuantity);
                    }
                }
            }).setNegativeButton(byIdName(getContext(), "cancel"), null);
            builder.show();
        }


    }


    public OnQuantityChangeListener getOnQuantityChangeListener() {
        return onQuantityChangeListener;
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener onQuantityChangeListener) {
        this.onQuantityChangeListener = onQuantityChangeListener;
    }

    public Drawable getQuantityBackground() {
        return quantityBackground;
    }

    public void setQuantityBackground(Drawable quantityBackground) {
        this.quantityBackground = quantityBackground;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mTextViewQuantity.setBackground(quantityBackground);
        } else {
            mTextViewQuantity.setBackgroundDrawable(quantityBackground);
        }
    }

    public Drawable getAddButtonBackground() {
        return addButtonBackground;
    }

    public void setAddButtonBackground(Drawable addButtonBackground) {
        this.addButtonBackground = addButtonBackground;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mButtonAdd.setBackground(addButtonBackground);
        } else {
            mButtonAdd.setBackgroundDrawable(addButtonBackground);
        }
    }

    public Drawable getRemoveButtonBackground() {
        return removeButtonBackground;
    }

    public void setRemoveButtonBackground(Drawable removeButtonBackground) {
        this.removeButtonBackground = removeButtonBackground;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mButtonRemove.setBackground(removeButtonBackground);
        } else {
            mButtonRemove.setBackgroundDrawable(removeButtonBackground);
        }
    }

    public String getAddButtonText() {
        return addButtonText;
    }

    public void setAddButtonText(String addButtonText) {
        this.addButtonText = addButtonText;
        mButtonAdd.setText(addButtonText);
    }

    public String getRemoveButtonText() {
        return removeButtonText;
    }

    public void setRemoveButtonText(String removeButtonText) {
        this.removeButtonText = removeButtonText;
        mButtonRemove.setText(removeButtonText);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        boolean limitReached = false;

        if (quantity > maxQuantity) {
            quantity = maxQuantity;
            limitReached = true;
            if (onQuantityChangeListener != null) onQuantityChangeListener.onLimitReached();
        }
        if (quantity < minQuantity) {
            quantity = minQuantity;
            limitReached = true;
            if (onQuantityChangeListener != null) onQuantityChangeListener.onLimitReached();
        }


        if (!limitReached && onQuantityChangeListener != null)
            onQuantityChangeListener.onQuantityChanged(quantity, true);

        this.quantity = quantity;
        mTextViewQuantity.setText(String.valueOf(this.quantity));
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public int getBidQuantity() {
        return bid;
    }

    public void setBidQuantity(int bidQuantity) {
        this.bid = bidQuantity;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public int getQuantityPadding() {
        return quantityPadding;
    }

    public void setQuantityPadding(int quantityPadding) {
        this.quantityPadding = quantityPadding;
        mTextViewQuantity.setPadding(quantityPadding, 0, quantityPadding, 0);
    }

    public int getQuantityTextColor() {
        return quantityTextColor;
    }

    public void setQuantityTextColor(int quantityTextColor) {
        this.quantityTextColor = quantityTextColor;
        mTextViewQuantity.setTextColor(quantityTextColor);
    }

    public void setQuantityTextColorRes(int quantityTextColorRes) {
        this.quantityTextColor = ContextCompat.getColor(getContext(), quantityTextColorRes);
        mTextViewQuantity.setTextColor(quantityTextColor);
    }

    public int getAddButtonTextColor() {
        return addButtonTextColor;
    }

    public void setAddButtonTextColor(int addButtonTextColor) {
        this.addButtonTextColor = addButtonTextColor;
        mButtonAdd.setTextColor(addButtonTextColor);
    }

    public void setAddButtonTextColorRes(int addButtonTextColorRes) {
        this.addButtonTextColor = ContextCompat.getColor(getContext(), addButtonTextColorRes);
        mButtonAdd.setTextColor(addButtonTextColor);
    }

    public int getRemoveButtonTextColor() {
        return removeButtonTextColor;
    }

    public void setRemoveButtonTextColor(int removeButtonTextColor) {
        this.removeButtonTextColor = removeButtonTextColor;
        mButtonRemove.setTextColor(removeButtonTextColor);
    }

    public void setRemoveButtonTextColorRes(int removeButtonTextColorRes) {
        this.removeButtonTextColor = ContextCompat.getColor(getContext(), removeButtonTextColorRes);
        mButtonRemove.setTextColor(removeButtonTextColor);
    }

    private int dpFromPx(final float px) {
        return (int) (px / getResources().getDisplayMetrics().density);
    }

    private int pxFromDp(final float dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }


    private boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public static String byIdName(Context context, String name) {
        Resources res = context.getResources();
        return res.getString(res.getIdentifier(name, "string", context.getPackageName()));
    }

}
