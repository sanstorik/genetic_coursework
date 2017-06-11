package develop.sanstorik.com.genetic_coursework;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GreetActivity extends AppCompatActivity {
    private ImageView valakasImage;
    private ImageView introImage;
    private TextView introText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greet);

        introImage = (ImageView)findViewById(R.id.introIV);
        introText = (TextView)findViewById(R.id.introTV);

        introImage.setImageAlpha(0);
        introText.setAlpha(0);

        valakasImage = (ImageView)findViewById(R.id.vlksIV);
        valakasAnimation();
    }

    private void valakasAnimation(){
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator rotation = ObjectAnimator.ofFloat(valakasImage, "rotation", 0, 360);
        rotation.setDuration(5000);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(valakasImage, "scaleX", valakasImage.getScaleY(), 0);
        scaleX.setDuration(1500);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(valakasImage, "scaleY", valakasImage.getScaleY(), 0);
        scaleY.setDuration(1500);

        animatorSet
                .play(scaleX)
                .with(scaleY)
                .after(rotation);

        animatorSet
                .play(introAlphaAnim())
                .with(introTextAnim());

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                valakasImage.setVisibility(ImageView.GONE);
            }
        });

        animatorSet.start();
    }

    private Animator introAlphaAnim(){
        ObjectAnimator alpha = ObjectAnimator.ofInt(introImage, "imageAlpha", 0, 255);
        alpha.setDuration(1000);

        return alpha;
    }

    private Animator introTextAnim(){
        ObjectAnimator  introTextAlpha = ObjectAnimator.ofFloat(introText, "alpha", 0, 1);
        introTextAlpha.setDuration(1000);

        return introTextAlpha;
    }
}
