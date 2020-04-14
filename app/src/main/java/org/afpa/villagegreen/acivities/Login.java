package org.afpa.villagegreen.acivities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.StringRequest;

import org.afpa.villagegreen.R;
import org.afpa.villagegreen.utils.RequestHandler;
import org.afpa.villagegreen.utils.Ulogin;

public class Login extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText user, password;
    private Button btn;
    //private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Controls
        user = findViewById(R.id.logName);
        password = findViewById(R.id.logPass);
        btn = findViewById(R.id.logButton);

        //Listeners
        user.addTextChangedListener(this);
        password.addTextChangedListener(this);
        btn.setOnClickListener(this);
        btn.setEnabled(false);
    }

    /**
     * Active le bouton de validation si les champs user et password sont remplis, sinon le désactive
     */
    private void switchBtn() {
        if (user.getText().length() != 0 && password.getText().length() != 0) {
            btn.setEnabled(true);
        } else {
            btn.setEnabled(false);
        }
    }

    /**
     * Vérification de l'utilisateur : envoie la paire login/mot de passe à l'API.
     * Si la paire est dans la base de données, alors on valide la connexion dans les sharedprefs et
     * on affiche la page des choix
     */


    @Override
    public void onClick(View v) {
        if (v == btn) {
            Ulogin uLog=new Ulogin(this,this.user.getText().toString(),this.password.getText().toString());
            StringRequest req=uLog.strRequest();
            RequestHandler.getInstance(this).addToRequestQueue(req);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * Si une modification d'un champ texte a été effectuée, on appelle la méthode switchBtn()
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {
        switchBtn();
    }
}
