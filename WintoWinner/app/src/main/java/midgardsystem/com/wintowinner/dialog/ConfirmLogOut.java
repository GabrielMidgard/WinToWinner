package midgardsystem.com.wintowinner.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import midgardsystem.com.wintowinner.objects.ConfirmDialog;
import midgardsystem.com.wintowinner.utils.ICallConfirmLogOut;

/**
 * Created by Gabriel on 09/10/2016.
 */
public class ConfirmLogOut extends DialogFragment {
    String title;
    String message;
    String positive;
    String negative;

    public ConfirmLogOut() {}
    @SuppressLint("ValidFragment")
    public ConfirmLogOut(ConfirmDialog objConfirm) {
        this.title=objConfirm.getTitle();
        this.message=objConfirm.getMessage();
        this.positive=objConfirm.getPositive();
        this.negative=objConfirm.getNegative();
    }

    // Interfaz de comunicación
    ICallConfirmLogOut listeners;

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

        builder.setTitle("cerrar sesión")
                .setMessage("¿Desea cerrar sesión?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listeners.onPossitiveBtnClick();
                            }
                        })

                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listeners.onNegativeBtnClick();
                            }
                        });
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listeners = (ICallConfirmLogOut) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " no implementó ICallConfirm");
        }
    }
}

