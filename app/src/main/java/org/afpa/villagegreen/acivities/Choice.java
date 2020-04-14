package org.afpa.villagegreen.acivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import org.afpa.villagegreen.R;

public class Choice extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btnCat, btnFact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        btnCat = findViewById(R.id.btnCat);
        btnFact = findViewById(R.id.btnFact);
        btnCat.setOnClickListener(this);
        btnFact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnCat) {

            //Choix de la vue catalogue
            //finish();
            startActivity(new Intent(this, Catalog.class));
        } else if (v == btnFact) {
            startActivity(new Intent(this, Facture.class));
            //finish();
        }
    }
}
