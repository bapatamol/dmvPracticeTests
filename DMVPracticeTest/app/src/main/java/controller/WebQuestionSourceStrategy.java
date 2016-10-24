package controller;

import android.content.Context;
import android.util.Log;

import breakfast.dmvpracticetest.R;
import controller.strategy.QuestionCallBack;
import controller.strategy.QuestionSourceStrategy;
import controller.web.AsyncWebContentFetcherFactory;

/**
 * Created by abapat on 4/13/2015.
 */
public class WebQuestionSourceStrategy implements QuestionSourceStrategy {

    QuestionCallBack qcb;

    @Override
    public void readyQuestions() {}

    @Override
    public void requestNextQuestion(final QuestionCallBack qcb, Context context) {
        this.qcb = qcb;
        AsyncWebContentFetcherFactory.getInstance(context).makeAsyncWebRequest(this.qcb, context.getString(R.string.questionWebSource), AsyncWebContentFetcherFactory.ExpectedResponse.text, null);
    }

    @Override
    public void requestPreviousQuestion(QuestionCallBack qcb, Context context) {
    }

    @Override
    public void invalidateRequest() {
        AsyncWebContentFetcherFactory factory = AsyncWebContentFetcherFactory.getInstanceNoCreate();
        if (factory != null) {
            factory.invalidateImageRequest();
        } else {
            Log.d("DMV", "Factory null, this is unexpected");
        }
    }

}
