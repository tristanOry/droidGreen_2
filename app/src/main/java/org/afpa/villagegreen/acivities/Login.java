package org.afpa.villagegreen.acivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.afpa.villagegreen.R;
import org.afpa.villagegreen.utils.Constants;
import org.afpa.villagegreen.utils.RequestHandler;
import org.afpa.villagegreen.utils.SharedPrefsManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
            final Context ctx=getApplicationContext();

            Response.Listener reponse=new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(ctx,
                                jsonObject.getString("message"),
                                Toast.LENGTH_LONG).show();
                        if(jsonObject.getBoolean("error")==false){
                            SharedPrefsManager.getInstance(ctx).userLogin(jsonObject.getInt("id"),
                                    user.toString());
                            startActivity(new Intent(ctx,Choice.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            Response.ErrorListener erreur=new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            };

            StringRequest req=new StringRequest(Request.Method.POST,
                    Constants.LOGIN_URL,
                    reponse,
                    erreur) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("mail", user.getText().toString());
                    params.put("pass",password.getText().toString());
                    return params;
                }
            };
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
