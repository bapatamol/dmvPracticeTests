package controller.web;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

import controller.strategy.QuestionCallBack;

/**
 * Created by abapat on 4/23/2015.
 */
class AsyncWebImageContentFetcher extends AsyncWebContentFetcher {

    private boolean isInvalid = false;
    QuestionCallBack qcb;

    AsyncWebImageContentFetcher(QuestionCallBack qcb, String url) {
        super(url);
        this.qcb = qcb;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        Bitmap bitmap = null;
        try {
            response = httpclient.execute(new HttpGet(this.url));
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
                InputStream instream = bufHttpEntity.getContent();
                bitmap = BitmapFactory.decodeStream(instream);
            } else {
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            Log.d("DMV", Thread.currentThread().getStackTrace()[2].getClassName() + Thread.currentThread().getStackTrace()[2].getLineNumber());
        } catch (IOException e) {
            Log.d("DMV", Thread.currentThread().getStackTrace()[2].getClassName() + Thread.currentThread().getStackTrace()[2].getLineNumber());
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Object o) {
        try {
            Bitmap bitmap = (Bitmap) o;
            qcb.hereIsTheAttachment(bitmap);
        } catch (Exception e) {
            Log.d("DMV", Thread.currentThread().getStackTrace()[2].getClassName() + Thread.currentThread().getStackTrace()[2].getLineNumber());
            qcb.hereIsTheAttachment(null);
        }

    }

    public void setInvalid() {
        Log.d("DMV", "setInvalid called -- " + this);
        isInvalid = true;
    }
}
