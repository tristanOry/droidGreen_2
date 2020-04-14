package org.afpa.villagegreen.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.afpa.villagegreen.acivities.Choice;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Ulogin{
    private  Response.Listener<String> reponse;
    private  Response.ErrorListener erreur;
    private Context ctx;
    private String user;
    private String pass;

    public Ulogin(final Context ctx, final String user, String pass) {
        this.ctx = ctx;
        this.user=user;
        this.pass=pass;
        reponse=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(ctx,
                            jsonObject.getString("message"),
                            Toast.LENGTH_LONG).show();
                    if(jsonObject.getBoolean("error")==false){
                        SharedPrefsManager.getInstance(ctx).userLogin(jsonObject.getInt("id"),
                                user);
                        Intent intent=new Intent(ctx, Choice.class);
                        ctx.startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        erreur=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ctx, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }

    public StringRequest strRequest() {
        return new StringRequest(Request.Method.POST,
                Constants.LOGIN_URL,
                reponse,
                erreur) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mail", user);
                params.put("password", pass);
                return params;
            }
        };
    }
}