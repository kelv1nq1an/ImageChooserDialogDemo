package me.fattycat.kun.imagechooserdialogdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import me.fattycat.kun.library.ImageChooserDialog;


public class ImageChooserActivity extends AppCompatActivity implements ImageChooserDialog.OnImageChooserListener {

    private Button imageChooserButton;
    private Button imageChooserButtonSingle;
    private ImageView imageChooserPhoto;
    private RecyclerView imageChooserRecyclerView;
    private ImageChooserRecyclerAdapter imageRecyclerAdapter;
    ImageChooserDialog imageChooserDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_chooser);
        imageChooserDialog = new ImageChooserDialog();
        imageChooserDialog.setListener(ImageChooserActivity.this);
        imageChooserButton = (Button) findViewById(R.id.image_chooser_button);
        imageChooserButtonSingle = (Button) findViewById(R.id.image_chooser_button_single);
        imageChooserPhoto = (ImageView) findViewById(R.id.image_chooser_photo);
        imageChooserRecyclerView = (RecyclerView) findViewById(R.id.image_chooser_recycler_view);
        imageChooserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooserDialog.setChooserType(ImageChooserDialog.CHOOSER_TYPE_MULTIPLE);
                imageChooserDialog.show(getSupportFragmentManager(), imageChooserDialog.getTag());
            }
        });

        imageChooserButtonSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooserDialog.setChooserType(ImageChooserDialog.CHOOSER_TYPE_SINGLE);
                imageChooserDialog.show(getSupportFragmentManager(), imageChooserDialog.getTag());
            }
        });

        imageRecyclerAdapter = new ImageChooserRecyclerAdapter(this);
        imageChooserRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        imageChooserRecyclerView.setAdapter(imageRecyclerAdapter);

    }

    @Override
    public void doneSelect(List<String> images) {
        imageRecyclerAdapter.setData(images);
    }

    @Override
    public void cameraCaptureSuccess(Bitmap photo) {
        imageChooserPhoto.setImageBitmap(photo);
    }
}
