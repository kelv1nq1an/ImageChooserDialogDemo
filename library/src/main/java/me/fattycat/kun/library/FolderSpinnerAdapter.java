package me.fattycat.kun.library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Kelvinkun
 * Date: 16/8/11
 */

public class FolderSpinnerAdapter extends BaseAdapter {
    private List<String> folderNames = new ArrayList<>();
    private Context context;

    public FolderSpinnerAdapter(Context context) {
        this.context = context;
    }

    public void setFolders(List<String> folders) {
        this.folderNames = folders;
    }

    @Override
    public int getCount() {
        return folderNames.size();
    }

    @Override
    public Object getItem(int i) {
        return folderNames.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_folder, viewGroup, false);
        TextView folderTitle = (TextView) view.findViewById(R.id.item_folder_title);
        folderTitle.setText(folderNames.get(i));
        return view;
    }

}
