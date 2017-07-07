package midgardsystem.com.wintowinner.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import midgardsystem.com.wintowinner.R;
import midgardsystem.com.wintowinner.dateSource.DatabaseHelper;
import midgardsystem.com.wintowinner.utils.ICallback;
import midgardsystem.com.wintowinner.utils.Task;

/**
 * Created by Gabriel on 13/08/2016.
 */
public class SplashActivity  extends Activity implements ICallback {
    DatabaseHelper dbHelper;
    private Context context;
    int configSuccess=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        context=this;
        Intent intent = getIntent();
        configSuccess = intent.getIntExtra("configSuccess", 0);

        dbHelper = DatabaseHelper.getInstance(this);

        doInBackground();

        Task t = new Task( this );
        t.execute();
    }


    @Override
    public void callback() {
        final ImageView img = (ImageView) findViewById( R.id.imgSplash );
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash_out);
        anim.setAnimationListener( new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent;
                //intent = new Intent( getApplicationContext(), FacebookActivity.class);


                intent = new Intent( getApplicationContext(), MainActivity.class);
                //validar si es nueva instalacion
                if(isNewInstalation()==true){
                    //Cargar Web Service
                    // primera vez enviarle un datos

                    intent = new Intent( getApplicationContext(), ConfigActivity.class);
                    intent.putExtra("onlyMatch", 0);
                }else{
                    if(configSuccess==1){
                        intent = new Intent( getApplicationContext(), MainActivity.class);
                    }else{
                        intent = new Intent( getApplicationContext(), ConfigActivity.class);
                        intent.putExtra("onlyMatch", 1);
                    }

                }

                startActivity( intent );
                finish();
            }
        });
        anim.setFillAfter(true);
        img.startAnimation(anim);
    }

    @Override
    public void time() {
        try {
            Thread.sleep( 2500 );
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    protected void doInBackground(String... params) {
    }

    public Boolean isNewInstalation(){
        Boolean isNewInstalation=false;
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            isNewInstalation= dbHelper.isNewInstalation(database);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isNewInstalation;
    }

}
