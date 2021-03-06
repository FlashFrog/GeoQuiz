package co.example.leo.geoquiz;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.leo.geoquiz.answer_is_true";

    private static final String EXTRA_ANSWER_SHOWN = "com.example.leo.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;

    private Button mShowAnswer;




    public static Intent newIntent(Context packageContext, boolean answerIsTrue){
        Intent i = new Intent(packageContext,CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return i;
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);
        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);
        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerIsTrue){
                    mAnswerTextView.setText("true");
                }else{
                    mAnswerTextView.setText("false");
                }
                setAnswerShownResult(true);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    int cx = mShowAnswer.getWidth()/2;
                    int cy = mShowAnswer.getHeight()/2;
                    float radius = mShowAnswer.getWidth();
                    android.animation.Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswer,cx,cy,radius,0);
                    anim.addListener(new AnimatorListenerAdapter(){
                        @Override
                        public void onAnimationEnd(android.animation.Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                }else{
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }
            }
        });
        TextView apilevel = (TextView)findViewById(R.id.api_level);
        apilevel.setText("API level"+Build.VERSION.SDK_INT);
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);
    }
}
