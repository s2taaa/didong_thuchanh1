package com.example.thuchanh1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        // gọi hàm getdata

        getDataFromMockAPI(url);

        btn_add.setOnClickListener(v->{
            String txt_Name = edt_name.getText().toString();
            if(TextUtils.isEmpty(txt_Name)){
                Toast.makeText(DataActivity.this,"Nhap Name....",Toast.LENGTH_SHORT).show();
                return;
            }
            String txt_email = edt_email.getText().toString();
            if(TextUtils.isEmpty(txt_email)){
                Toast.makeText(DataActivity.this,"Nhap email....",Toast.LENGTH_SHORT).show();
                return;
            }

            postAPI(url);
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PutAPI(url);
                getDataFromMockAPI(url);

            }
        });

    }
    private void getDataFromMockAPI(String url) {
        users = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i =0 ; i<response.length();i++){
                            try {
                               // lấy từng user bỏ vào trong list users  từ api
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                User user = new User();
                                user.setId(jsonObject.getString("id"));
                                user.setName(jsonObject.getString("name"));
                                user.setEmail(jsonObject.getString("email"));
                                users.add(user);
                                // bỏ user vào trong useradapter, set useradapter vào recyclerview
                                adapter = new UserAdapter(users,DataActivity.this);
                                rcv_user.setAdapter(adapter);
                                rcv_user.setLayoutManager(new GridLayoutManager(DataActivity.this ,1));
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
    // HAM THEM
    private void postAPI(String url) {
        // METHOD POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DataActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DataActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map = new HashMap<>();
                map.put("name",edt_name.getText().toString());
                map.put("email",edt_email.getText().toString());

                //lấy các trường trừ id từ plain text
                return  map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        getDataFromMockAPI(url);
    }
    // HAM SUA
    // PUT THI CAN 1 PLAIN TEXT CHUA ID DE SUA
    private void PutAPI(String url){
        // nhập id vào plaintext đẻ có thể tìm thấy user cần sửa
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url + "/" + edt_id.getText().toString(),
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DataActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DataActivity.this, "Error by Put data!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                //lấy các trường trừ id từ plain text
                params.put("name",edt_name.getText().toString());
                params.put("email",edt_email.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



}
