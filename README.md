# ImageChooserDialogDemo
Another image chooser dialog which uses BottomSheetDialogFragment.

## Usage

``` java
public class ImageChooserActivity extends AppCompatActivity implements ImageChooserDialog.OnImageChooserListener {

    private ImageChooserDialog imageChooserDialog;
    ...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_chooser);
        imageChooserDialog = new ImageChooserDialog();
        imageChooserDialog.setListener(ImageChooserActivity.this);
        imageChooserDialog.setChooserType(ImageChooserDialog.CHOOSER_TYPE_MULTIPLE);
        // imageChooserDialog.setChooserType(ImageChooserDialog.CHOOSER_TYPE_SINGLE);
        imageChooserDialog.show(getSupportFragmentManager(), imageChooserDialog.getTag());
        ...
    }

    @Override
    public void doneSelect(List<String> images) {
        // you can get a list of image paths of user's choice
    }

    @Override
    public void cameraCaptureSuccess(Bitmap photo) {
        // you can get the bitmap of what the camera take directly.
        // when this been called, dialog would not be dismissed because
        // the photo would show in dialog for user to choose. 
    }
}

```

## Author

@Fattycat.me

## License

```
    Copyright (C) 2016 Fattycat.me<kelv1nq1an>
 
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

```
