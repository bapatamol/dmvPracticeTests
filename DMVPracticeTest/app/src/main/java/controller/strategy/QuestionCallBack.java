package controller.strategy;

import android.graphics.Bitmap;

import model.QuestionData;

/**
 * Created by abapat on 4/13/2015.
 */
public interface QuestionCallBack {

    public void hereIsTheQuestion(QuestionData q);
    public void hereIsTheAttachment(Bitmap bitmap);
}
