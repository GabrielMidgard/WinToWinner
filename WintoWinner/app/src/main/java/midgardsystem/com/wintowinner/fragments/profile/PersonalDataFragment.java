package midgardsystem.com.wintowinner.fragments.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.objects.User;

/**
 * Created by Gabriel on 02/10/2016.
 */
@SuppressLint("ValidFragment")
public class PersonalDataFragment  extends Fragment {
    View rootView;
    User user;

    TextInputEditText txtName;
    TextInputEditText txtLastNames;
    TextInputEditText txtTown;
    TextInputEditText txtEmail;
    TextInputEditText txtPsw;

    RelativeLayout btnUpdate;

    public PersonalDataFragment() {    }

    public PersonalDataFragment(User user) {
        this.user=user;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_personal_data, container, false);

        init();

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

    public static Fragment newInstance(User user) {
        return new PersonalDataFragment(user);
    }

    public void init() {

        txtName = (TextInputEditText) rootView.findViewById(R.id.txtName);
        txtLastNames = (TextInputEditText) rootView.findViewById(R.id.txtLastNames);
        //txtTown = (TextInputEditText) rootView.findViewById(R.id.txtTown);
        txtEmail = (TextInputEditText) rootView.findViewById(R.id.txtEmail);
        txtPsw = (TextInputEditText) rootView.findViewById(R.id.txtPsw);


        txtName.setText(user.getName());
        txtLastNames.setText(user.getLastNames());
       // txtTown.setText(user.get());
        txtEmail.setText(user.getEmail());
        txtPsw.setText(user.getPsw());


        btnUpdate = (RelativeLayout) rootView.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

    }

    public  void updateUser(){}
}


