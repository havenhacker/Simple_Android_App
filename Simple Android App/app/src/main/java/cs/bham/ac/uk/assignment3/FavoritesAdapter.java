package cs.bham.ac.uk.assignment3;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private ArrayList<FoodProduct> products;
    private Context mCtx;

    // set callback interface for removing items in favorite list
    private FavoritesFragmentListener favoritesFragmentListener;

    public FavoritesFragmentListener getFavoritesFragmentListener(){
        return favoritesFragmentListener;
    }

    public void setFavoritesFragmentListener(FavoritesFragmentListener ffl){
        this.favoritesFragmentListener=ffl;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHead;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewHead = (TextView) itemView.findViewById(R.id.fav_textViewHead);
            imageView =(ImageView) itemView.findViewById(R.id.fav_imageView);
        }
    }

    public FavoritesAdapter(ArrayList<FoodProduct> products, Context Ctx) {
        this.products = products;
        mCtx=Ctx;
    }

    public void setData(ArrayList<FoodProduct> products){
        this.products=products;
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.textViewHead.setText(products.get(position).toString());
        holder.textViewHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( v.getContext(), FoodDetailActivity.class);
                intent.putExtra("apiID", products.get(position).getApiID());
                intent.putExtra("name", products.get(position).toString());
                v.getContext().startActivity(intent);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFavoritesFragmentListener()!=null){
                    // handle situation when position is out of index range
                    if (position<0){
                        getFavoritesFragmentListener().onRemove(products.get(position+1),position);
                        return;
                    }
                    else if (products.size()==position){
                        getFavoritesFragmentListener().onRemove(products.get(position-1),position);
                        return;
                    }
                    else if (position>products.size()){
                        getFavoritesFragmentListener().onRemove(products.get(position-2),position);
                    }
                    getFavoritesFragmentListener().onRemove(products.get(position),position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // set callback interface for removing items in favorite list
    public interface FavoritesFragmentListener{
        void onRemove(FoodProduct foodProduct, int position);
    }
}
