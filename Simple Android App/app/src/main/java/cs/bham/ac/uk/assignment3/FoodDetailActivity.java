package cs.bham.ac.uk.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodDetailActivity extends AppCompatActivity {

    private int apiID;
    private String names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        apiID = getIntent().getIntExtra("apiID", 0);
        names = getIntent().getStringExtra("name");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, "https://www.sjjg.uk/eat/recipe-details/" + apiID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        TextView name = findViewById(R.id.tw_name);
                        TextView ing = findViewById(R.id.tw_ingredient);
                        TextView description = findViewById(R.id.tw_description);
                        TextView steps = findViewById(R.id.tw_steps);
                        try {
                            name.setText(names);
                            description.setText(response.getString("description"));
                            JSONArray ingre=response.getJSONArray("ingredients");
                            JSONArray step=response.getJSONArray("steps");
                            for (int i=0;i<ingre.length();i++){
                                ing.append(ingre.getString(i)+", ");
                            }
                            for (int i=0;i<step.length();i++){
                                steps.append(i+1+": "+step.getString(i)+"\n");
                            }
                        } catch (JSONException err) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(getRequest);
    }
}
