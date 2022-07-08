package com.example.blockchain;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=findViewById(R.id.imageView3);
//        Animation mg = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
//        img.startAnimation(mg);
//        RotateAnimation rotate = new RotateAnimation(
//                0, 360,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f
//        );
//
//        rotate.setDuration(900);
//        rotate.setRepeatCount(Animation.INFINITE);
//        img.startAnimation(rotate);

        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(1400);
        img.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        }, 3400);
    }
}