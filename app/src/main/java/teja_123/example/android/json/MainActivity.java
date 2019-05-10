package teja_123.example.android.json;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    String result ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String res;
        Download task=new Download();
        try {
            res=task.execute("https://www.freecodecamp.org/").get();

            Log.i("testing",res);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public class Download extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
             result = "";
            URL url;
            HttpURLConnection urlcoonc;

            try {
                url = new URL(strings[0]);
                urlcoonc = (HttpURLConnection) url.openConnection();
                InputStream in = urlcoonc.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while (data != -1) {
                    result += (char) data;
                    data = reader.read();
                }
                Log.i("result is ", result);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
       @Override
protected void onPostExecute(String s){
            super.onPostExecute(s);

           try {
               JSONObject json=new JSONObject(result);
               String weather=json.getString("weather");
               Log.i("weather",weather);
               JSONArray array=new JSONArray(weather);
               for(int i=0;i<array.length();i++){
                   JSONObject jsonobj1=array.getJSONObject(i);
                   Log.i("main",jsonobj1.getString("main"));
                   Log.i("description",jsonobj1.getString("description"));
               }
           } catch (JSONException e) {
               e.printStackTrace();
           }

       }
    }


}
