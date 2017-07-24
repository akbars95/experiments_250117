package android.mtsmda.com.application170717;

import android.content.Context;
import android.mtsmda.com.application170717.activity.MyAppCompatActivity;
import android.mtsmda.com.application170717.helper.ListHelper;
import android.mtsmda.com.application170717.model.Question;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidRuntimeException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class MainActivity extends MyAppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String KEY_INDEX = "index";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mPreviousButton;
    private ImageButton mNextButton;
    private TextView mQuestionTextView;

    private List<Question> mQuestionBank = ListHelper.getList(new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true), new Question(R.string.question_asia, true),
            new Question(R.string.question_australia, true), new Question(R.string.question_mideast, false),
            new Question(R.string.question_oceans, true));

    private int mCurrentIndex = 0;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_main);
        if(null != savedInstanceState){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        this.mTrueButton = findViewById(R.id.true_button, Button.class);
        this.mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        this.mFalseButton = findViewById(R.id.false_button, Button.class);
        this.mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        this.mQuestionTextView = findViewById(R.id.question_text_view, TextView.class);
        updateQuestion();

        this.mNextButton = findViewById(R.id.next_button, ImageButton.class);
        this.mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size();
                updateQuestion();
            }
        });

        this.mPreviousButton = findViewById(R.id.previous_button, ImageButton.class);
        this.mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex--;
                if(mCurrentIndex < 0){
                    mCurrentIndex = mQuestionBank.size() - 1;
                }else{
                    mCurrentIndex = mCurrentIndex % mQuestionBank.size();
                }

                updateQuestion();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank.get(mCurrentIndex).isAnswerTrue();
        int messageResId;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        this.mQuestionTextView.setText(mQuestionBank.get(mCurrentIndex).getTextResId());
    }

}
