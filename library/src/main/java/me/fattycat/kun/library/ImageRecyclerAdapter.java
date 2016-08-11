package me.fattycat.kun.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Kelvinkun
 * Date: 16/8/10
 */

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder> {

    private Context context;
    private List<String> imageData = new ArrayList<>();
    private List<Boolean> imageCheckList = new ArrayList<>();
    private CameraClickListener onCameraClickListener;
    private int mode;
    private int singleSelectedIndex = 0;

    public ImageRecyclerAdapter(Context context, CameraClickListener listener) {
        this.onCameraClickListener = listener;
        this.context = context;
    }

    public void setData(List<String> data) {
        this.imageData = data;
        for (int i = 0; i < imageData.size(); i++) {
            imageCheckList.add(false);
        }
        notifyDataSetChanged();
    }

    public List<String> getSelectedImages() {
        List<String> selectedImage = new ArrayList<>();

        if ((mode == ImageChooserDialog.CHOOSER_TYPE_SINGLE) && singleSelectedIndex != 0) {
            selectedImage.add(imageData.get(imageData.size() - singleSelectedIndex));
        } else {
            for (int i = 0; i < imageCheckList.size(); i++) {
                if (imageCheckList.get(i)) {
                    selectedImage.add(imageData.get(imageData.size() - i));
                }
            }
        }
        return selectedImage;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        if (position == 0) {
            Glide.with(context).load(R.drawable.ic_photo_camera_black_48dp).into(holder.itemImage);
            holder.itemCheck.setVisibility(View.GONE);
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onCameraClickListener.onCameraClick();
                }
            });
        } else {
            final int index = position;
            Glide.with(context).load(imageData.get(imageData.size() - position)).centerCrop().into(holder.itemImage);
            if (mode == ImageChooserDialog.CHOOSER_TYPE_MULTIPLE) {
                holder.itemCheck.setVisibility(View.VISIBLE);
                holder.itemCheck.setChecked(imageCheckList.get(position));
                holder.itemCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imageCheckList.set(index, !imageCheckList.get(index));
                    }
                });
            } else {
                if (singleSelectedIndex == index) {
                    holder.itemCheck.setVisibility(View.VISIBLE);
                    holder.itemCheck.setChecked(true);
                } else {
                    holder.itemCheck.setVisibility(View.GONE);
                    holder.itemCheck.setChecked(false);
                }
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        singleSelectedIndex = index;
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return imageData.size() + 1;
    }

    public void setMode(int type) {
        this.mode = type;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        View root;
        ImageView itemImage;
        CheckBox itemCheck;

        ImageViewHolder(View itemView) {
            super(itemView);
            root = itemView;
            itemImage = (ImageView) itemView.findViewById(R.id.item_image_view);
            itemCheck = (CheckBox) itemView.findViewById(R.id.item_image_check);
        }
    }

    interface CameraClickListener {
        void onCameraClick();
    }
}
