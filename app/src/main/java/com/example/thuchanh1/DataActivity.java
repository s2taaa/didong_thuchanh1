package com.example.thuchanh1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DataActivity extends AppCompatActivity {
    List<User> users = new ArrayList<>();
    RecyclerView rcv_user;
    UserAdapter adapter;
    EditText edt_name,edt_email,edt_id;
    Button btn_add,btn_update,btn_get;
    String url = "https://60b094eb1f26610017ffe83c.mockapi.io/user";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().hide();

        rcv_user = findViewById(R.id.rcv_users);
        edt_name = findViewById(R.id.edit_name);
        edt_email = findViewById(R.id.edit_email);
        edt_id = findViewById(R.id.edit_id);

        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_get = findViewById(R.id.btn_get);


        getJsonObjectArray(url, users);

        adapter = new UserAdapter(users, this);
        rcv_user.setAdapter(adapter);
        rcv_user.setLayoutManager(new GridLayoutManager(this, 1));


    }



        private void getJsonObjectArray(String url, List<User> users){
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>(){

                        @Override
                        public void onResponse(JSONArray response) {
                            for(int i =0 ; i<response.length();i++){
                                try {
                                    JSONObject jsonObject = (JSONObject) response.get(i);
                                    User user = new User();
                                    user.setId(jsonObject.getString("id").toString());
                                    user.setEmail(jsonObject.getString("age"));
                                    user.setName(jsonObject.getString("name"));
                                    users.add(user);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    },
                    new Response.ErrorListener(){

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DataActivity.this,
                                    "Error by get Json Array!", Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(jsonArrayRequest);
        }


    }
