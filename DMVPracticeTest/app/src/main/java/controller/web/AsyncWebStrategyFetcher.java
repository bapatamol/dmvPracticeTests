package controller.web;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import controller.strategy.StrategySetterCallBack;

/**
 * Created by abapat on 5/5/2015.
 */
public class AsyncWebStrategyFetcher extends AsyncWebContentFetcher {

    StrategySetterCallBack sscb;

    AsyncWebStrategyFetcher(StrategySetterCallBack sscb, String url) {
        super(url);
        this.sscb = sscb;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet(this.url));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            Log.d("DMV", Thread.currentThread().getStackTrace()[2].getClassName() + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } catch (IOException e) {
            Log.d("DMV", Thread.currentThread().getStackTrace()[2].getClassName() + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        return responseString;
    }

    @Override
    protected void onPostExecute(Object o) {
        try {
            String responseString = o.toString();
            JSONObject webResponse = new JSONObject(responseString);
            String strategy = webResponse.getString("strategy");
            Log.d("DMV", "Parsed JSON Web Response = " + strategy);
            sscb.hereIsTheStrategy(strategy);
        } catch (JSONException e) {
            Log.d("DMV", "JSON Parsing error from web response; switching to RandomQuestionSourceStrategy");
            sscb.hereIsTheStrategy("controller.RandomQuestionSourceStrategy");
        } catch (Exception e) {
            Log.d("DMV", "JSON Parsing error from web response; switching to RandomQuestionSourceStrategy");
            sscb.hereIsTheStrategy("controller.RandomQuestionSourceStrategy");
        }
    }
}
