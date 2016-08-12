# ImageChooserDialogDemo
Another image chooser dialog which uses BottomSheetDialogFragment.



## Usage
1. In your dependencies, add `compile 'me.fattycat.kun:imagechooserdialog:1.0.1'`
2. follow the example below

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
        // imageChooserDialog.setTitleText("Choose Image");
        // imageChooserDialog.setDoneText("Done");
        imageChooserDialog.show(getSupportFragmentManager(), imageChooserDialog.getTag());
        ...
    }

    @Override
    public void doneSelect(List<String> imagePaths) {
        // you can get a list of image paths of user's choice
    }

    @Override
    public void cameraCaptureSuccess(Bitmap photoBitmap) {
        // you can get the bitmap of what the camera take directly.
        // when this been called, dialog would not be dismissed because
        // the photo would show in dialog for user to choose. 
        // you can mannuly call imageChooserDialog.dismiss() to dismiss the dialog.
    }
}

```

## Todo
- [ ] modify color

## Are you using this library
If you have any questions, issues, or just want to let me know how you are using this library, feel free to create a new issue and let me know. All feedback is well appreciated!

## Author

@Fattycat.me

## License

```
    Copyright (C) 2016 Fattycat.me<kelv1nq1an>.
 
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

```
