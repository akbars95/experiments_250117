package android.mtsmda.com.application170717.activity;

import android.content.Context;
import android.content.Intent;
import android.mtsmda.com.application170717.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends MyAppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.mtsmda.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.mtsmda.android.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chear);

        this.mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        this.mAnswerTextView = findViewByIdTextView(R.id.answer_text_view);
        this.mShowAnswerButton = findViewByIdButton(R.id.show_answer_button);
        this.mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerTextView.setText(mAnswerIsTrue ? R.string.true_button : R.string.false_button);
                setAnswerShownResult(true);
            }
        });

    }

    public static boolean wasAnswerShown(Intent resultIntent){
        return resultIntent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent intentData = new Intent();
        intentData.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, intentData);
    }

}
