package thecircle.seechange.logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import thecircle.seechange.presentation.activities.LoginActivity;
import thecircle.seechange.presentation.activities.StreamActivity;

import static android.content.Context.MODE_PRIVATE;


public class AccountConnectionManager {

    private Boolean success;

    public Boolean login(final String username, final String password, final Context c, final Activity a) {

        RequestQueue queue = Volley.newRequestQueue(c);

        Map<String, String> body = new HashMap<String, String>();
        body.put("username", username);
        body.put("password", password);

        String url = "https://the-circle-account.herokuapp.com/api/account/login";

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(body),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            SharedPreferences.Editor editor = c.getSharedPreferences("CREDENTIALS", MODE_PRIVATE).edit();
                            editor.putString("token", response.getString("token"));
                            editor.putString("privatekey", response.getJSONObject("crt").getString("private"));
                            editor.putString("certificate", response.getJSONObject("crt").getString("cert"));
                            editor.putString("publickey", response.getJSONObject("crt").getString("public"));
                            editor.putString("username", username);
                            editor.apply();

                            // Start the Stream Activity
                            Intent i = new Intent(c, StreamActivity.class);
                            c.startActivity(i);
                            a.finish();


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        // Login Successful
                        success = true;

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Login Failed
                Toast.makeText(c, "Username or password is incorrect", Toast.LENGTH_LONG).show();
                success = false;
                // Start the Login Activity
                Intent i = new Intent(c, LoginActivity.class);
                c.startActivity(i);
                a.finish();
            }
        });

        queue.add(request);



        return success;
    }



}
