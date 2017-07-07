package midgardsystem.com.wintowinner.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by TELMEX on 20/06/2017.
 */

public class ValidateConection {
    Context context;

    public ValidateConection(Context context) {
        this.context = context;
    }

    public Boolean isConected(){
        if(connectedWifi()){
            return true;
        }else{
            if(connectedMobileNetwork()){
                return true;
            }else{
                return false;
            }
        }
    }

    public Boolean connectedWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean connectedMobileNetwork(){
        ConnectivityManager connectivity = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }
}
