package midgardsystem.com.wintowinner.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.List;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.adapter.NotificationAdapter;
import midgardsystem.com.wintowinner.objects.Notification;

/**
 * Created by Gabriel on 05/11/2016.
 */
public class NotificationDialog extends DialogFragment {
    public static Context context;
    public List<Notification> listNotifications;

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager staggeredLayoutManager;

    public View rootView;
    private ListView listView;
    private NotificationAdapter adapter;

    public NotificationDialog() {}

    @SuppressLint("ValidFragment")
    public NotificationDialog(List<Notification> listNotifications) {
        this.listNotifications=listNotifications;
    }


    @Override
    public void onAttach(Activity activity) {
        context=(FragmentActivity) activity;
        super.onAttach(activity);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        return createPopup();
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
    public AlertDialog createPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.dialog_notifications, null);

        staggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        builder.setView(rootView);        // Obtiene el btn de cerrar de menu bar
        RelativeLayout btnClose = (RelativeLayout) rootView.findViewById(R.id.btnClose);         // Obtiene el btn de cerrar de menu bar
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        init();

        initializeAdapter();






        return builder.create();
    }

    private void initializeAdapter() {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        recyclerView.setLayoutManager(staggeredLayoutManager);
        recyclerView.setHasFixedSize(true); //Data size is fixed - improves performance

        adapter = new NotificationAdapter(getActivity(), listNotifications);
        recyclerView.setAdapter(adapter);

    }

    public void init(){
/*
        Typeface fontOpenSans = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSansLight.ttf");
        Typeface fontOpenSansBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSansBold.ttf");

        TextView lblTitle = (TextView) rootView.findViewById(R.id.lblTitle);
        TextView lblClose = (TextView) rootView.findViewById(R.id.lblClose);

        lblTitle.setTypeface(fontOpenSansBold);
        lblClose.setTypeface(fontOpenSansBold);

/*
        listView=(ListView) rootView.findViewById(R.id.listTopics);
        try {

            adapter = new TopicsAdapter(getActivity(), listTopics);
            listView.setAdapter(adapter);

        }
        catch (Exception err) {
            System.out.println( err.getMessage( ) );
        }
*/
    }



/*
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }*/

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
