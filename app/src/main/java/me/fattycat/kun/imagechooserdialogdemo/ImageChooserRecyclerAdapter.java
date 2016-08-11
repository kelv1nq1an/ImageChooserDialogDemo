package me.fattycat.kun.imagechooserdialogdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


/**
 * Author: Kelvinkun
 * Date: 16/8/10
 */

public class ImageChooserRecyclerAdapter extends RecyclerView.Adapter<ImageChooserRecyclerAdapter.ImageChooserViewHolder> {

    private Context context;
    private List<String> imageData = new ArrayList<>();

    public ImageChooserRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<String> data) {
        this.imageData = data;
        notifyDataSetChanged();
    }

    @Override
    public ImageChooserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_image_chooser, parent, false);
        return new ImageChooserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageChooserViewHolder holder, int position) {
        Glide.with(context).load(imageData.get(position)).centerCrop().into(holder.itemImage);
    }

    @Override
    public int getItemCount() {
        return imageData.size();
    }

    class ImageChooserViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;

        ImageChooserViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.item_image_view);
        }
    }
}
