package midgardsystem.com.wintowinner.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.adapter.BettingtAdapter;
import midgardsystem.com.wintowinner.objects.Betting;
import midgardsystem.com.wintowinner.objects.ConfirmDialog;
import midgardsystem.com.wintowinner.utils.ICallConfirmBet;

/**
 * Created by Gabriel on 19/09/2016.
 */
public class ConfirmBet  extends DialogFragment {
    String title;
    String message;
    String positive;
    String negative;
    int position;
    BettingtAdapter adapter;
    List<Betting> bettingList;
    int teamSelectedChallenging;

    // Interfaz de comunicación
    ICallConfirmBet listeners;

    public ConfirmBet() {}
    @SuppressLint("ValidFragment")
    public ConfirmBet(ConfirmDialog objConfirm, int position, BettingtAdapter adapter, List<Betting> bettingList, int teamSelectedChallenging) {
        this.title=objConfirm.getTitle();
        this.message=objConfirm.getMessage();
        this.positive=objConfirm.getPositive();
        this.negative=objConfirm.getNegative();
        this.position=position;
        this.adapter=adapter;
        this.bettingList=bettingList;
        this.teamSelectedChallenging=teamSelectedChallenging;
    }



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

        builder.setTitle(getResources().getString(R.string.confirm))
                .setMessage(getResources().getString(R.string.message_ConfirmAcceptBets))
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listeners.onAceptedBet(position, adapter, bettingList, teamSelectedChallenging);
                                //getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, getActivity().getIntent());
                            }
                        })

                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listeners.onNegativeBet();
                                //getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, getActivity().getIntent());
                            }
                        });
        return builder.create();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listeners = (ICallConfirmBet) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " no implementó ICallConfirm");
        }
    }
    /*
    http://stackoverflow.com/questions/13733304/callback-to-a-fragment-from-a-dialogfragment
*/
}

