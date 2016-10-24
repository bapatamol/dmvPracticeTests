package controller.strategy;

import android.content.Context;

/**
 * Created by abapat on 4/8/2015.
 */
public interface QuestionSourceStrategy {

    public void readyQuestions();
    public void requestNextQuestion(QuestionCallBack qcb, Context contextOfWebRequestIfApplicable);
    public void requestPreviousQuestion(QuestionCallBack qcb, Context contextOfWebRequestIfApplicable);
    public void invalidateRequest();
}
