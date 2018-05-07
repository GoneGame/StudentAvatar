package student.studentavater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login;
    private RequestQueue requestQueue;
    private static final String URL = "http://172.17.8.47/websiteapp/php/login_app.php";
    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.editLogin);
        password = (EditText)findViewById(R.id.editPass);
        login = (Button)findViewById(R.id.buttonLogin);

        requestQueue = Volley.newRequestQueue(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.names().get(0).equals("success"))
                            {
                                //add the action to open new activity and toast or log message
                                Toast.makeText(MainActivity.this, "Successfully login", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, MainMenu.class));

                            }
                            else
                            {
                                //show error
                                Toast.makeText(MainActivity.this, "Error" + jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("user_name", username.getText().toString());
                        hashMap.put("user_pass", password.getText().toString());
                        return hashMap;
                    }
                };

                requestQueue.add(stringRequest);

            }
        });

    }
}
