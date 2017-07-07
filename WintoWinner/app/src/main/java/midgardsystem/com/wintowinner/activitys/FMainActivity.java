package midgardsystem.com.wintowinner.activitys;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

import midgardsystem.com.wintowinner.R;

/**
 * Created by TELMEX on 30/04/2017.
 */

public class FMainActivity extends AppCompatActivity {
    private CallbackManager callbackManager;
    Button btnShare, btnShareImg;

    JSONObject response, profile_pic_data, profile_pic_url;
    ImageView user_picture;
    ShareDialog shareDialog;
    int REQUEST_CAMARA=0, SELECT_FILE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f_main);

        if(AccessToken.getCurrentAccessToken()==null){
            goLoginScreen();
        }

        init();
    }

    private void init(){
        btnShare = (Button) findViewById(R.id.btnShare);
        btnShareImg = (Button) findViewById(R.id.btnShareImg);
        shareDialog= new ShareDialog(this);

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ShareDialog.canShow(ShareLinkContent.class)){
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Win To Winner")
                            .setImageUrl(Uri.parse("http://wintowin.com.mx/_img/_social/fb.png"))
                            .setContentDescription("Comparte, Diviertete y siempre gana porque ganas")
                            //.setContentUrl(Uri.parse("https://developers.facebook.com"))
                            .build();
                    shareDialog.show(linkContent); //Show facebook ShareDialog
                }
            }
        });


    }
    private void goLoginScreen(){
        Intent intent = new Intent(this, FacebookActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logout(View view){
        LoginManager.getInstance().logOut();
    }
}
