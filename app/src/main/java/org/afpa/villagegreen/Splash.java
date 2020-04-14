package org.afpa.villagegreen;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.afpa.villagegreen.acivities.Login;

public class Splash extends AppCompatActivity {
    private static final int SPLASH_SCREEN = 5000;
    Animation anim;
    ImageView logo;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Initialisation des éléments
        logo = findViewById(R.id.logoSplash);
        anim = AnimationUtils.loadAnimation(getBaseContext(), R.anim.animation);
        mp = MediaPlayer.create(getBaseContext(), R.raw.intro);
        // On lance la musique
        mp.start();
        // Puis l'anim
        logo.setAnimation(anim);
        // Et quand tout est fini, on va à l'accueil
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Déclaration d'une nouvelle intention
                Intent intent = new Intent(Splash.this, Login.class);
                //Démarrage de l'intention
                startActivity(intent);
                //Cloture du Splash Screen
                finish();
            }

        }, SPLASH_SCREEN);
    }
}
