package breakfast.dmvpracticetest;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import controller.QuestionSourcer;
import controller.RandomQuestionSourceStrategy;
import controller.strategy.QuestionCallBack;
import controller.strategy.QuestionSourceStrategy;
import controller.strategy.StrategySetterCallBack;
import controller.web.AsyncWebContentFetcherFactory;
import model.QuestionData;


public class DMVTest extends ActionBarActivity implements QuestionCallBack, StrategySetterCallBack {

    int MAX_QUESTIONS = 10;
    QuestionSourcer q;
    int questionNumber = 0;
    int reviewQuestionNumber = 0;
    int correctAnswerCount = 0;
    ArrayList<QuestionData> reviewQuestions = new ArrayList<>();
    boolean isReviewMode = false;

    /**
     * This is an implementation method for sourcing Question Strategy from the web. An AsyncWebRequest is made in onCreate, and the website returns question source implementation strategy
     * @param strategy - className for one of the strategies in controller package
     */
    public void hereIsTheStrategy(String strategy) {
        try {
            Log.d("DMV", "Strategy from the web - " + strategy);
            Class s = Class.forName(strategy);
            q = new QuestionSourcer((QuestionSourceStrategy) s.newInstance());
        } catch (ClassNotFoundException e) {
            Log.d("DMV", "ClassNotFoundException; falling back to RandomQuestionSourceStrategy");
            q = new QuestionSourcer(new RandomQuestionSourceStrategy());
        } catch (InstantiationException e) {
            Log.d("DMV", "InstantiationException; falling back to RandomQuestionSourceStrategy");
            q = new QuestionSourcer(new RandomQuestionSourceStrategy());
        } catch (IllegalAccessException e) {
            Log.d("DMV", "IllegalAccessException; falling back to RandomQuestionSourceStrategy");
            q = new QuestionSourcer(new RandomQuestionSourceStrategy());
        }

        setContentView(R.layout.dmv_test);

        // hide the "Review" button
        findViewById(R.id.launchReview).setVisibility(View.INVISIBLE);

        // request a question via loadView
        loadView();

    }

    private void loadView() {

        // Show the toast if the user is trying to skip a question without making any selection
        // If it is the first question, we'd like to request at least one without returning, so ignore for question 0
        if (questionNumber > 0 &&
            !((RadioButton) findViewById(R.id.test_choice1)).isChecked() &&
            !((RadioButton) findViewById(R.id.test_choice2)).isChecked() &&
            !((RadioButton) findViewById(R.id.test_choice3)).isChecked() &&
            !((RadioButton) findViewById(R.id.test_choice4)).isChecked()) {
            Toast.makeText(getApplicationContext(), "You need to answer this question in order to move to next one", Toast.LENGTH_LONG).show();
            return;
        }

        // If we are at the end of the test
        if (questionNumber == MAX_QUESTIONS) {
            showResults();
            return;
        }

        // Show the loading progress bar, hide the image box, while we get the image
        findViewById(R.id.loading).setVisibility(View.VISIBLE);
        findViewById(R.id.test_image).setVisibility(View.GONE);

        // Since we are moving on to the next question, invalidate old image request
        q.invalidateImageRequest();

        q.requestNextQuestion(this, getApplicationContext());

    }

    private void showResults() {
        // Show results
        ((TextView) findViewById(R.id.test_testResult)).setText(("You scored " + correctAnswerCount + "/10"));

        findViewById(R.id.test_Next).setEnabled(false);

        // Show the option to launch test review
        findViewById(R.id.launchReview).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // when the activity gets created, we are obviously not going to be in review mode
        isReviewMode = false;

        // Make a web request to find out how we are going to source the questions. See {@link #hereIsTheStrategy(String)} method
        AsyncWebContentFetcherFactory.getInstance(getApplicationContext()).makeAsyncWebRequest(null, getString(R.string.strategyWebSource), AsyncWebContentFetcherFactory.ExpectedResponse.strategy, this);
    }

    public void getNextQuestion(View v) {
        // Check if we are in review mode, and get question accordingly
        if (!isReviewMode) {
            // if no selection is made, go back.
            if (!((RadioButton) findViewById(R.id.test_choice1)).isChecked() && !((RadioButton) findViewById(R.id.test_choice2)).isChecked() && !((RadioButton) findViewById(R.id.test_choice3)).isChecked() && !((RadioButton) findViewById(R.id.test_choice4)).isChecked()) {
                return;
            }

            // Get the selected radio button option, for saving it for test review later
            RadioGroup radioButtonGroup = ((RadioGroup) findViewById(R.id.test_choices));
            int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
            View radioButton = radioButtonGroup.findViewById(radioButtonID);
            int selectedOption = radioButtonGroup.indexOfChild(radioButton) + 1;

            // save the option user selected, so we can tell them whether they were right or wrong in review
            reviewQuestions.get(questionNumber-1).setSelectedOption(String.valueOf(selectedOption));

            // Check if the selection option is correct. The test_answer textview is invisible on the screen
            if (((TextView) findViewById(R.id.test_answer)).getText().equals(String.valueOf(selectedOption))) {
                // increment count if the selected answer is correct
                correctAnswerCount++;
            }

            // Request the next question
            loadView();
        } else {
            // if we are in review mode, get the next question from the list of review questions
            launchTestReview(v);
        }
    }

    public void test_optionSelected(View view) {
        // enable the "Next >" button on the screen when the user makes a selection, so they can proceed to next question
        findViewById(R.id.test_Next).setEnabled(true);

    }

    public void launchTestReview(View view) {
        // Set Review Mode flag
        isReviewMode = true;

        // Populate the view with review question data
        hereIsTheQuestion(reviewQuestions.get(reviewQuestionNumber++));

        // Hide the "Review Questions" buttons, since the user already clicked it
        findViewById(R.id.launchReview).setVisibility(View.GONE);

        // Enable the next button, that we hid in "showResults" method
        findViewById(R.id.test_Next).setEnabled(true);
    }

    /**
     * This is the implementation of QuestionCallBack.
     * A new request for image completes with this callback
     * @param bitmap - the bitmap we asked for
     */
    @Override
    public void hereIsTheAttachment(Bitmap bitmap) {
        // If there have been no errors, and we get a bitmap here, show it.
        if (bitmap != null) {
            // Hide the progress bar, and show the ImageView
            findViewById(R.id.test_image).setVisibility(View.VISIBLE);
            findViewById(R.id.loading).setVisibility(View.GONE);

            // Set Imageview source as the image received
            ((ImageView) findViewById(R.id.test_image)).setImageBitmap(bitmap);
        } else {
            findViewById(R.id.test_image).setVisibility(View.VISIBLE);
            findViewById(R.id.loading).setVisibility(View.GONE);

            // Set Imageview source as the image received
            ((ImageView) findViewById(R.id.test_image)).setImageResource(R.drawable.download_error);
        }
    }

    /**
     * This is the implementation of QuestionCallBack
     * When we request a new question, you get the QuestionData from the web here
     * @param question - the question we asked for.
     */
    @Override
    public void hereIsTheQuestion(QuestionData question) {

        if (question == null) {
            Log.d("DMV", "no question returned; getting a new question from RandomQuestionSourceStrategy");
            q = new QuestionSourcer(new RandomQuestionSourceStrategy());
            q.requestNextQuestion(this, getApplicationContext());
            return;
        }

        // We are going to need this question for review later, so save it.
        reviewQuestions.add(question);

        // Now that we have received a new question, increment questionNumber count, so the correct one is shown on the screen
        questionNumber++;

        // Check if we are in review mode, to show appropriate question number count.
        if (!isReviewMode) {
            ((TextView) findViewById(R.id.test_qNo)).setText("Question " + questionNumber + "/10");
        } else {
            ((TextView) findViewById(R.id.test_qNo)).setText("Question " + reviewQuestionNumber+ "/10");

            // If we are in review mode, invalidate outstanding image requests, we'll send a request for the image we need down in this method below
            AsyncWebContentFetcherFactory.getInstanceNoCreate().invalidateImageRequest();
        }

        // Set the view with the appropriate questions, answers and choices
        ((TextView) findViewById(R.id.test_questionText)).setText(question.getQuestionText());
        ((TextView) findViewById(R.id.test_answer)).setText(question.getAnswer());
        ((RadioButton) findViewById(R.id.test_choice1)).setText(question.getChoice1());
        ((RadioButton) findViewById(R.id.test_choice2)).setText(question.getChoice2());
        ((RadioButton) findViewById(R.id.test_choice3)).setText(question.getChoice3());
        ((RadioButton) findViewById(R.id.test_choice4)).setText(question.getChoice4());

        // Since this is a new question, clear all previous selected choices
        ((RadioGroup) findViewById(R.id.test_choices)).clearCheck();

        // Remove colors on all radio buttons
        ((RadioButton) findViewById(R.id.test_choice1)).setBackgroundResource(0);
        ((RadioButton) findViewById(R.id.test_choice2)).setBackgroundResource(0);
        ((RadioButton) findViewById(R.id.test_choice3)).setBackgroundResource(0);
        ((RadioButton) findViewById(R.id.test_choice4)).setBackgroundResource(0);


        // If the question has an attachment, fetch it
        if (question.getAttachmentLink()!= null && !question.getAttachmentLink().isEmpty()) {
            findViewById(R.id.loading).setVisibility(View.VISIBLE);
            findViewById(R.id.test_image).setVisibility(View.GONE);
            AsyncWebContentFetcherFactory.getInstance(getApplicationContext()).makeAsyncWebRequest(this, question.getAttachmentLink(), AsyncWebContentFetcherFactory.ExpectedResponse.image, null);
        } else {
            // No attachment, so hide the image view and hide the progress bar
            findViewById(R.id.loading).setVisibility(View.GONE);
            findViewById(R.id.test_image).setVisibility(View.GONE);
        }

        // If we are in review mode, show the user selected choices.
        if (isReviewMode) {
            int selectedOption = Integer.parseInt(question.getSelectedOption());
            int answer = Integer.parseInt(question.getAnswer());

            // Show "Red" (incorrect) option if the user selected a choice that wasn't the right answer
            if (answer != selectedOption) {
                switch (selectedOption) {
                    case 1:
                        findViewById(R.id.test_choice1).setBackgroundColor(Color.RED);
                        break;
                    case 2:
                        findViewById(R.id.test_choice2).setBackgroundColor(Color.RED);
                        break;
                    case 3:
                        findViewById(R.id.test_choice3).setBackgroundColor(Color.RED);
                        break;
                    case 4:
                        findViewById(R.id.test_choice4).setBackgroundColor(Color.RED);
                        break;
                    default:
                        break;
                }
            }

            // Show answer in green
            switch(answer) {
                case 1:
                    findViewById(R.id.test_choice1).setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    findViewById(R.id.test_choice2).setBackgroundColor(Color.GREEN);
                    break;
                case 3:
                    findViewById(R.id.test_choice3).setBackgroundColor(Color.GREEN);
                    break;
                case 4:
                    findViewById(R.id.test_choice4).setBackgroundColor(Color.GREEN);
                    break;
                default:
                    break;

            }

            // If we are at the last review question, hide the "Next" button, since there is no next question
            if (reviewQuestionNumber > 9) {
                findViewById(R.id.test_Next).setVisibility(View.GONE);
            }
        }
    }


}
