package midgardsystem.com.wintowinner.utils;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by Gabriel on 13/08/2016.
 */
public class Task  extends AsyncTask<String, Integer, String> {
    public int post_case = 0;
    private ICallback call;

    public Task(ICallback call) {
        this.call = call;
    }

    protected void onPreExecute() {
        Log.i("Entrar", "Asynctast");
    }

    protected String doInBackground(String... arg0) {
        try {
            call.time();
        } catch (Exception e) {
        }
        return null;
    }

    protected void onPostExecute(String s) {
        call.callback();
    }
}
