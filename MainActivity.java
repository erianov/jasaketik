package com.kanwildki.monev;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnlogin;
    EditText kodeuser,password;
    SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnlogin =  (Button)findViewById(R.id.btnlogin);
        kodeuser = (EditText)findViewById(R.id.editKodeUsr);
        password = (EditText)findViewById(R.id.editPassword);


        //Create Session User

        session = new SessionManagement(getApplicationContext());

   btnlogin.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           Login();
       }
   });

    }

    private void Login()
    {
        String url= "http://asetin.com/login.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    if(response.trim().equals("success"))
                    {
                        Toast.makeText(getApplicationContext(),"Login Berhasil",Toast.LENGTH_SHORT).show();
                        session.createLoginSession(kodeuser.getText().toString().trim(),password.getText().toString().trim());
                        Intent i = new Intent(getApplicationContext(),menu.class);
                        startActivity(i);
                        finish();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Login Gagal",Toast.LENGTH_LONG).show();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,  String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("KodeUser",kodeuser.getText().toString().trim());
                params.put("Password",password.getText().toString().trim());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
