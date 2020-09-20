package cs.bham.ac.uk.assignment3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private ArrayList<FoodProduct> favoriteProducts;
    private RecyclerView listView;
    private FavoritesAdapter productsAdpt;
    private SharedPreferences sharedPref;

    public FavoritesFragment(){
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        // get sharedPreferences from main activity
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        favoriteProducts=getArrayList("storedList");
        if (favoriteProducts==null){
            favoriteProducts=new ArrayList<>();
        }
        // 1. get a reference to recyclerView
        listView = (RecyclerView) rootView.findViewById(R.id.favoritesList);
        productsAdpt = new FavoritesAdapter(favoriteProducts,getContext());
        // 2. set layoutManger
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. set adapter
        listView.setAdapter(productsAdpt);
        // implement callback interface for add and delete favorites list
        productsAdpt.setFavoritesFragmentListener(new FavoritesAdapter.FavoritesFragmentListener() {
            @Override
            public void onRemove(FoodProduct foodProduct, int position) {
                favoriteProducts.remove(foodProduct);
                productsAdpt.notifyItemRemoved(position);
                saveArrayList(favoriteProducts,"storedList");
            }
        });
        return rootView;
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
}
