package android.mtsmda.com.application170717;

import android.content.Context;
import android.mtsmda.com.application170717.activity.MyAppCompatActivity;
import android.mtsmda.com.application170717.helper.ListHelper;
import android.mtsmda.com.application170717.model.Question;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends MyAppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mPreviousButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

    private List<Question> mQuestionBank = ListHelper.getList(new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true), new Question(R.string.question_asia, true),
            new Question(R.string.question_australia, true), new Question(R.string.question_mideast, false),
            new Question(R.string.question_oceans, true));

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        this.mNextButton = findViewById(R.id.next_button, Button.class);
        this.mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.size();
                updateQuestion();
            }
        });

        this.mPreviousButton = findViewById(R.id.previous_button, Button.class);
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
