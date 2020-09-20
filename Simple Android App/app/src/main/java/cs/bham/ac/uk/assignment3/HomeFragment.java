package cs.bham.ac.uk.assignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<FoodProduct> products;
    private RecyclerView listView;
    private FoodAdapter productsAdpt;
    private SharedPreferences sharedPref;
    private String preferences=null;
    private ArrayList<FoodProduct> storedList;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            if(msg.what == 1){
                Toast.makeText(getActivity(),"Network Error!!!",Toast.LENGTH_LONG).show();
            }
        };
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        products = new ArrayList<FoodProduct>();
        // 1. get a reference to recyclerView
        listView = (RecyclerView) rootView.findViewById(R.id.productList);
        // get stored favorite list from sharedPreference
        storedList=getArrayList("storedList");
        productsAdpt = new FoodAdapter(products,getContext(),storedList);
        // 2. set layoutManger
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. set adapter
        listView.setAdapter(productsAdpt);
        // get sharedPreferences from main activity
        preferences=sharedPref.getString("order_preference",null);
        onRequestFoods();
        // implement callback method from main activity
        ((MainActivity)getActivity()).setFragmentRefreshListener(new MainActivity.FragmentRefreshListener() {
            @Override
            public void onRefresh() {
                onRequestFoods();
            }
        });
        // implement callback method from adapter
        productsAdpt.setHomeFragmentListener(new FoodAdapter.HomeFragmentListener() {
            @Override
            public boolean onAdd(FoodProduct foodProduct,int position) {
                if (storedList==null){
                    storedList=new ArrayList<>();
                }
                for(int i=0;i<storedList.size();i++){
                    if (storedList.get(i).getName().equals(foodProduct.getName())){
                        return false;
                    }
                }
                storedList.add(foodProduct);
                saveArrayList(storedList,"storedList");
                return true;
            }
        });
        return rootView;
    }

    public void onRequestFoods(){
        preferences=sharedPref.getString("order_preference",null);
        // test code
        if (preferences!=null){
            System.out.println(preferences.equals("asc"));
        }
        // update food list accordingly
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        // default order
        if (preferences==null){
            listView.removeAllViews();
            productsAdpt.notifyDataSetChanged();
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, "https://www.sjjg.uk./eat/food-items", null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            populateList(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            sendMessage();
                            error.printStackTrace();
                        }
                    }
            );
            requestQueue.add(getRequest);
        }
        else if (preferences.equals("Breakfast")){
            listView.removeAllViews();
            productsAdpt.notifyDataSetChanged();
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET,
                    "https://www.sjjg.uk./eat/food-items/?prefer="+preferences, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            populateList(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            sendMessage();
                            error.printStackTrace();
                        }
                    }
            );
            requestQueue.add(getRequest);
        }
        else if (preferences.equals("Lunch")){
            listView.removeAllViews();
            productsAdpt.notifyDataSetChanged();
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET,
                    "https://www.sjjg.uk./eat/food-items/?prefer="+preferences, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            populateList(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            sendMessage();
                            error.printStackTrace();
                        }
                    }
            );
            requestQueue.add(getRequest);
        }
        else if (preferences.equals("Dinner")){
            listView.removeAllViews();
            productsAdpt.notifyDataSetChanged();
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET,
                    "https://www.sjjg.uk./eat/food-items/?prefer="+preferences, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            populateList(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            sendMessage();
                            error.printStackTrace();
                        }
                    }
            );
            requestQueue.add(getRequest);
        }
        else if (preferences.equals("asc")){
            listView.removeAllViews();
            productsAdpt.notifyDataSetChanged();
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET,
                    "https://www.sjjg.uk./eat/food-items/?ordering="+preferences, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            populateList(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            sendMessage();
                            error.printStackTrace();
                        }
                    }
            );
            requestQueue.add(getRequest);
        }
        else if (preferences.equals("desc")){
            listView.removeAllViews();
            productsAdpt.notifyDataSetChanged();
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET,
                    "https://www.sjjg.uk./eat/food-items/?ordering="+preferences, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            populateList(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            sendMessage();
                            error.printStackTrace();
                        }
                    }
            );
            requestQueue.add(getRequest);
        }
    }

    private void populateList(JSONArray items){
        products.clear();
        try{
            for (int i =0; i<items.length();i++) {
                JSONObject jo = items.getJSONObject(i);
                products.add(new FoodProduct(jo.getString("name"),jo.getInt("id")));
//                System.out.println(products.get(i).getApiID());
            }
            productsAdpt.setData(products);
            productsAdpt.notifyDataSetChanged();
        }
        catch(JSONException err){
            err.printStackTrace();
            sendMessage();
        }
    }

    // methods below save arraylist using Gson library and sharedPreference
    private void saveArrayList(ArrayList<FoodProduct> list, String key){
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    private ArrayList<FoodProduct> getArrayList(String key){
        Gson gson = new Gson();
        String json = sharedPref.getString(key, null);
        Type type = new TypeToken<ArrayList<FoodProduct>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void sendMessage(){
        Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
    }
}
