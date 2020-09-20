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

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private ArrayList<FoodProduct> products;
    private Context mCtx;
    private ArrayList<FoodProduct> favorites;

    // set callback interface for adding to favorites list
    private HomeFragmentListener homeFragmentListener;

    public HomeFragmentListener getHomeFragmentListener(){
        return homeFragmentListener;
    }

    public void setHomeFragmentListener(HomeFragmentListener hf) {
        this.homeFragmentListener = hf;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewHead;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            imageView =(ImageView) itemView.findViewById(R.id.imageViewFavorites);
        }
    }

    public FoodAdapter(ArrayList<FoodProduct> products,Context Ctx,ArrayList<FoodProduct> favorites) {
        this.products = products;
        mCtx=Ctx;
        this.favorites=favorites;
    }

    public void setData(ArrayList<FoodProduct> products){
        this.products=products;
    }

    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
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
        if (favorites!=null){
            for (int i=0;i<favorites.size();i++){
                if (favorites.get(i).getName().equals(products.get(position).getName())){
                    holder.imageView.setActivated(true);
                }
            }
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!holder.imageView.isActivated()){
                    if (getHomeFragmentListener()!=null){
                        if (!getHomeFragmentListener().onAdd(products.get(position),position)){
                            return;
                        }
                        holder.imageView.setActivated(true);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // set callback interface for adding to favorites list
    public interface HomeFragmentListener{
        boolean onAdd(FoodProduct foodProduct,int position);
    }
}
