package controller.web;

import android.content.Context;
import android.util.Log;

import controller.strategy.QuestionCallBack;
import controller.strategy.StrategySetterCallBack;

/**
 * Created by abapat on 4/25/2015.
 */
public class AsyncWebContentFetcherFactory  {

    private static AsyncWebContentFetcherFactory mInstance;
    public enum ExpectedResponse {text, image, strategy};

    private AsyncWebQuestionContentFetcher asyncWebQuestionContentFetcher;
    private AsyncWebImageContentFetcher asyncWebImageContentFetcher;
    private AsyncWebStrategyFetcher asyncWebStrategyFetcher;

    private AsyncWebContentFetcherFactory() {
    }

    public static synchronized AsyncWebContentFetcherFactory getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AsyncWebContentFetcherFactory();
            return mInstance;
        }
        return mInstance;
    }

    public static synchronized AsyncWebContentFetcherFactory getInstanceNoCreate() {
        if (mInstance != null) {
            return mInstance;
        }
        return null;
    }

    public void makeAsyncWebRequest (QuestionCallBack qcb, String url, ExpectedResponse expectedResponse, StrategySetterCallBack sscb) {
        if (expectedResponse == ExpectedResponse.text) {
            asyncWebQuestionContentFetcher = new AsyncWebQuestionContentFetcher(qcb, url);
            asyncWebQuestionContentFetcher.execute();
        } else if (expectedResponse == ExpectedResponse.image) {
            asyncWebImageContentFetcher = new AsyncWebImageContentFetcher(qcb, url);
            Log.d("DMV", "Creating new image fetcher" + asyncWebImageContentFetcher);
            asyncWebImageContentFetcher.execute();
        } else if (expectedResponse == ExpectedResponse.strategy) {
            asyncWebStrategyFetcher = new AsyncWebStrategyFetcher(sscb, url);
            asyncWebStrategyFetcher.execute();
        }
    }

    public void invalidateImageRequest() {
        if (asyncWebImageContentFetcher != null) {
            asyncWebImageContentFetcher.setInvalid();
        }
    }

}
