package controller;

import android.content.Context;
import android.util.Log;

import controller.strategy.QuestionCallBack;
import controller.strategy.QuestionSourceStrategy;

/**
 * Created by abapat on 4/9/2015.
 */
public class QuestionSourcer {

    public QuestionSourcer (QuestionSourceStrategy sourceStrategy) {
        Log.d("DMV", "sourceStrategy = " + sourceStrategy.getClass().getName());
        this.sourceStrategy = sourceStrategy;
        sourceStrategy.readyQuestions();
    }

    public void setSourceStrategy(QuestionSourceStrategy sourceStrategy) {
        this.sourceStrategy = sourceStrategy;
    }

    private QuestionSourceStrategy sourceStrategy;

    public void requestNextQuestion(QuestionCallBack qcb , Context contextOfWebRequestIfApplicable) {
        Log.d("DMV", "sourceStrategy = " + sourceStrategy.getClass().getName());
        if (sourceStrategy != null) {
            Log.d("DMV", "returning question");
            sourceStrategy.requestNextQuestion(qcb, contextOfWebRequestIfApplicable);
        }
    }

    public void requestPreviousQuestion(QuestionCallBack qcb, Context contextOfWebRequestIfApplicable) {
        Log.d("DMV", "sourceStrategy = " + sourceStrategy.getClass().getName());
        if (sourceStrategy != null) {
            Log.d("DMV", "returning question");
            sourceStrategy.requestPreviousQuestion(qcb, contextOfWebRequestIfApplicable);
        }
    }

    public void invalidateImageRequest() {
        Log.d("DMV", "sourceStrategy = " + sourceStrategy.getClass().getName());
        if (sourceStrategy != null) {
            Log.d("DMV", "Invalidate Image Request");
            sourceStrategy.invalidateRequest();
        }
    }
}
