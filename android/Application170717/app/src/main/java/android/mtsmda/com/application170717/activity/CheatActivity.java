package android.mtsmda.com.application170717.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.mtsmda.com.application170717.R;
import android.mtsmda.com.application170717.helper.AndroidVersion;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends MyAppCompatActivity {

    private static final String TAG = CheatActivity.class.getSimpleName();

    private static final String EXTRA_ANSWER_IS_TRUE = "com.mtsmda.android.geoquiz.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.mtsmda.android.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private TextView mAndroidVersionShowTextView;
    private Button mShowAnswerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chear);

        this.mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        Log.i(TAG, "mAnswerIsTrue" + mAnswerIsTrue);
        this.mAnswerTextView = findViewByIdTextView(R.id.answer_text_view);
        this.mShowAnswerButton = findViewByIdButton(R.id.show_answer_button);
        this.mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnswerTextView.setText(mAnswerIsTrue ? R.string.true_button : R.string.false_button);
                setAnswerShownResult(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth() / 2;
                    int cy = mShowAnswerButton.getHeight() / 2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator animator = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    animator.start();
                } else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });
        this.mAndroidVersionShowTextView = findViewByIdTextView(R.id.android_version);
        {
            String codeName = AndroidVersion.getCodeName(Build.VERSION.SDK_INT);
            Log.i(TAG, "CODENAME - " + codeName);
            this.mAndroidVersionShowTextView.setText(codeName);
        }

    }

    public static boolean wasAnswerShown(Intent resultIntent) {
        return resultIntent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Log.i(TAG, "answerIsTrue" + answerIsTrue);
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        Log.i(TAG, "null == intent" + (null == intent));
        return intent;
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent intentData = new Intent();
        intentData.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, intentData);
    }

}
