package controller.web;

import android.os.AsyncTask;

/**
 * Created by abapat on 4/25/2015.
 */
abstract class AsyncWebContentFetcher extends AsyncTask {
    String url;

    AsyncWebContentFetcher(String url) {
        this.url = url;
    }




}
