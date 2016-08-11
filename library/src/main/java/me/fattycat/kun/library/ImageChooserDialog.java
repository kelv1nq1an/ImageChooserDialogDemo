package me.fattycat.kun.library;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Author: Kelvinkun
 * Date: 16/8/10
 */

public class ImageChooserDialog extends BottomSheetDialogFragment implements View.OnClickListener, ImageRecyclerAdapter.CameraClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "ImageChooserDialog";
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @IntDef(value = {CHOOSER_TYPE_SINGLE, CHOOSER_TYPE_MULTIPLE})
    public @interface ChooserType {
    }

    public static final int CHOOSER_TYPE_SINGLE = 0412;
    public static final int CHOOSER_TYPE_MULTIPLE = 0413;

    private ImageView iupDismissButton;
    private TextView iupTitle;
    private TextView iupDoneButton;
    private Spinner iupFolderSpinner;

    private ImageRecyclerAdapter iupAdapter;
    private OnImageChooserListener OnImageChooserListener;

    private Map<String, List<String>> groupImages = new HashMap<>();
    private List<String> folderNames = new ArrayList<>();
    private int selected = 0;
    private int chooserType = CHOOSER_TYPE_SINGLE;

    public ImageChooserDialog() {

    }

    public void setChooserType(@ChooserType int type) {
        this.chooserType = type;
    }

    public void setListener(OnImageChooserListener listener) {
        this.OnImageChooserListener = listener;
    }

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View rootView = View.inflate(getContext(), R.layout.fragment_image_chooser, null);
        int imageColumns = 3;

        iupTitle = (TextView) rootView.findViewById(R.id.iup_title);
        iupDoneButton = (TextView) rootView.findViewById(R.id.iup_done_button);
        iupFolderSpinner = (Spinner) rootView.findViewById(R.id.iup_folder_spinner);
        iupDismissButton = (ImageView) rootView.findViewById(R.id.iup_dismiss_button);
        RecyclerView iupRecyclerView = (RecyclerView) rootView.findViewById(R.id.iup_image_recycler_view);

        iupRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), imageColumns));
        iupAdapter = new ImageRecyclerAdapter(getContext(), this);
        iupAdapter.setData(getImagesPath(getContext()));
        iupAdapter.setMode(chooserType);
        iupRecyclerView.setAdapter(iupAdapter);
        iupDismissButton.setOnClickListener(this);
        iupDoneButton.setOnClickListener(this);
        FolderSpinnerAdapter folderSpinnerAdapter = new FolderSpinnerAdapter(getContext());
        folderSpinnerAdapter.setFolders(folderNames);
        iupFolderSpinner.setAdapter(folderSpinnerAdapter);
        iupFolderSpinner.setOnItemSelectedListener(this);
        dialog.setContentView(rootView);
        return dialog;
    }

    private List<String> getImagesPath(Context context) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        List<String> listOfAllImages = new ArrayList<>();
        int column_index_data;
        String pathOfImage;

        String[] projection = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED);
        if (cursor == null) {
            return listOfAllImages;
        }
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            pathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(pathOfImage);
            String folderName = new File(pathOfImage).getParentFile().getName();
            if (!groupImages.containsKey(folderName)) {
                List<String> childImages = new ArrayList<>();
                childImages.add(pathOfImage);
                groupImages.put(folderName, childImages);
                folderNames.add(folderName);
            } else {
                groupImages.get(folderName).add(pathOfImage);
            }
        }
        folderNames.add("All");
        groupImages.put("All", listOfAllImages);
        Collections.sort(folderNames);
        cursor.close();
        return listOfAllImages;
    }

    public List<String> getSelectedImagesPath() {
        return iupAdapter.getSelectedImages();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iup_dismiss_button) {
            dismiss();
        } else if (view.getId() == R.id.iup_done_button) {
            if (OnImageChooserListener != null) {
                OnImageChooserListener.doneSelect(getSelectedImagesPath());
                //iupAdapter.setData(getImagesPath(getContext()));

                dismiss();
            } else {
                throw new RuntimeException("have you forget to implement onImageChooserListener");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if (OnImageChooserListener != null) {
                OnImageChooserListener.cameraCaptureSuccess(imageBitmap);
                getImagesPath(getContext());
                iupAdapter.setData(groupImages.get(folderNames.get(selected)));
            } else {
                throw new RuntimeException("have you forget to implement onImageChooserListener");
            }
        }
    }

    @Override
    public void onCameraClick() {
        dispatchTakePictureIntent();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        iupAdapter.setData(groupImages.get(folderNames.get(i)));
        selected = i;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnImageChooserListener {
        void doneSelect(List<String> images);

        void cameraCaptureSuccess(Bitmap photo);
    }

    private void setCheckboxColor(int color) {
        iupAdapter.setCheckBoxColor(color);
    }

    public void setDoneText(String doneText) {
        this.iupDoneButton.setText(doneText);
    }

    public void setTitleText(String titleText) {
        this.iupTitle.setText(titleText);
    }
}
