package breakfast.dmvpracticetest;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import controller.QuestionSourcer;
import controller.SerialQuestionSourceStrategy;
import controller.strategy.QuestionCallBack;
import model.QuestionData;


public class PracticeQuestions extends ActionBarActivity implements QuestionCallBack {

    QuestionSourcer q;

    private void loadView(boolean isPreviousQuestion) {
        if (!isPreviousQuestion) {
            q.requestNextQuestion(this, getApplicationContext());
        } else {
            q.requestPreviousQuestion(this, getApplicationContext());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        q = new QuestionSourcer(new SerialQuestionSourceStrategy());
        setContentView(R.layout.practice_questions);
        loadView(false);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practice_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void donePressed(View v) {
        if (!((RadioButton) findViewById(R.id.choice1)).isChecked() && !((RadioButton) findViewById(R.id.choice2)).isChecked() && !((RadioButton) findViewById(R.id.choice3)).isChecked() && !((RadioButton) findViewById(R.id.choice4)).isChecked()) {
            return;
        }
        RadioGroup radioButtonGroup = ((RadioGroup) findViewById(R.id.choices));
        int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
        View radioButton = radioButtonGroup.findViewById(radioButtonID);
        int selectedOption = radioButtonGroup.indexOfChild(radioButton) + 1;

        Log.d("DMV", String.valueOf(selectedOption));
        if (((TextView) findViewById(R.id.answer)).getText().equals(String.valueOf(selectedOption))) {
            radioButton.setBackgroundColor(Color.GREEN);
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
            ((Button) findViewById(R.id.tellMe)).setEnabled(false);
            ((Button) findViewById(R.id.done)).setEnabled(false);
        } else {
            radioButton.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Try again", Toast.LENGTH_SHORT).show();
            ((Button) findViewById(R.id.done)).setEnabled(false);
        }

    }

    public void getNextQuestion(View v) {
        loadView(false);

    }

    public void getPreviousQuestion(View v) {
        loadView(true);

    }

    public void getAnswerPressed(View v) {
        String answer = ((TextView) findViewById(R.id.answer)).getText().toString();
        Toast.makeText(getApplicationContext(), "Answer is choice # "+ answer, Toast.LENGTH_LONG).show();
    }


    public void optionSelected(View view) {
        ((Button) findViewById(R.id.done)).setEnabled(true);
    }

    @Override
    public void hereIsTheQuestion(QuestionData question) {

        ((TextView) findViewById(R.id.questionText)).setText(question.getQuestionText());
        ((TextView) findViewById(R.id.answer)).setText(question.getAnswer());

        ((RadioButton) findViewById(R.id.choice1)).setText(question.getChoice1());
        ((RadioButton) findViewById(R.id.choice1)).setChecked(false);
        ((RadioButton) findViewById(R.id.choice1)).setBackgroundResource(0);
        ((RadioButton) findViewById(R.id.choice2)).setText(question.getChoice2());
        ((RadioButton) findViewById(R.id.choice2)).setChecked(false);
        ((RadioButton) findViewById(R.id.choice2)).setBackgroundResource(0);
        ((RadioButton) findViewById(R.id.choice3)).setText(question.getChoice3());
        ((RadioButton) findViewById(R.id.choice3)).setChecked(false);
        ((RadioButton) findViewById(R.id.choice3)).setBackgroundResource(0);
        ((RadioButton) findViewById(R.id.choice4)).setText(question.getChoice4());
        ((RadioButton) findViewById(R.id.choice4)).setChecked(false);
        ((RadioButton) findViewById(R.id.choice4)).setBackgroundResource(0);

        ((RadioGroup) findViewById(R.id.choices)).clearCheck();

        ((Button) findViewById(R.id.done)).setEnabled(false);
        ((Button) findViewById(R.id.tellMe)).setEnabled(true);

    }

    @Override
    public void hereIsTheAttachment(Bitmap bitmap) {
        ((ImageView) findViewById(R.id.test_image)).setImageBitmap(bitmap);
    }
}
