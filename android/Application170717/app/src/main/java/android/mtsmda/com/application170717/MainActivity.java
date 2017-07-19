package android.mtsmda.com.application170717;

import android.content.Context;
import android.mtsmda.com.application170717.helper.ListHelper;
import android.mtsmda.com.application170717.model.Question;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
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
                Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_LONG).show();
            }
        });
        this.mFalseButton = findViewById(R.id.false_button, Button.class);
        this.mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.incorrect_toast, Toast.LENGTH_LONG).show();
            }
        });
    }

    private <T> T findViewById(int id, Class<T> returnType) {
        return (T) super.findViewById(id);
    }

}
