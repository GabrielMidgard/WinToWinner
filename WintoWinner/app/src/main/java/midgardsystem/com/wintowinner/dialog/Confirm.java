package midgardsystem.com.wintowinner.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import midgardsystem.com.wintowinner.objects.ConfirmDialog;
import midgardsystem.com.wintowinner.utils.ICallConfirm;

/**
 * Created by Gabriel on 22/08/2016.
 */
public class Confirm  extends DialogFragment {
    String title;
    String message;
    String positive;
    String negative;

    public Confirm() {}
    @SuppressLint("ValidFragment")
    public Confirm(ConfirmDialog objConfirm) {
        this.title=objConfirm.getTitle();
        this.message=objConfirm.getMessage();
        this.positive=objConfirm.getPositive();
        this.negative=objConfirm.getNegative();
    }

    // Interfaz de comunicación
    ICallConfirm listeners;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createConfirm();
    }

    /**
     * Crea un popUp de alerta sencillo
     * @return Nuevo popUp
     */
    public AlertDialog createConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positive,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listeners.onPossitiveClick();
                    }
                });
        builder.setNegativeButton(negative,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listeners.onNegativeClick();
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listeners = (ICallConfirm) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " no implementó ICallConfirmPurchase");
        }
    }
}


