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

import controller.strategy.QuestionCallBack;
import model.QuestionData;

/**
 * Created by abapat on 4/16/2015.
 */
class AsyncWebQuestionContentFetcher extends AsyncWebContentFetcher{


    QuestionCallBack qcb;

    AsyncWebQuestionContentFetcher(QuestionCallBack qcb, String url) {
        super(url);
        this.qcb = qcb;
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
            Log.d("DMV", "Question received from web - " + responseString);
            QuestionData q = new QuestionData();
            JSONObject webResponse = new JSONObject(responseString);
            q.setQuestionText(webResponse.getString("questionText"));
            q.setAnswer(webResponse.getString("answer").trim());
            q.setChoice1(webResponse.getString("choice1").trim());
            q.setChoice2(webResponse.getString("choice2").trim());
            q.setChoice3(webResponse.getString("choice3").trim());
            q.setChoice4(webResponse.getString("choice4").trim());
            q.setAttachmentLink(webResponse.getString("attachmentLink").trim());
            qcb.hereIsTheQuestion(q);
        } catch (JSONException e) {
            Log.d("DMV", "JSON Parsing error from web response");
            e.printStackTrace();
        } catch (Exception e) {
            qcb.hereIsTheQuestion(null);
        }
    }
}
