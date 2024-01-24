//package com.example.deepart.features.editImage.easyeditor;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Typeface;
//import android.graphics.drawable.BitmapDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.util.Log;
//import android.view.View;
//import android.view.animation.AnticipateOvershootInterpolator;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.constraintlayout.widget.ConstraintSet;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.transition.ChangeBounds;
//import androidx.transition.TransitionManager;
//import com.example.deepart.R;
//import com.example.deepart.features.editImage.easyeditor.base.BaseActivity;
//import com.example.deepart.features.editImage.easyeditor.filters.FilterListener;
//import com.example.deepart.features.editImage.easyeditor.filters.FilterViewAdapter;
//import com.example.deepart.features.editImage.easyeditor.tools.EditingToolsAdapter;
//import com.example.deepart.features.editImage.easyeditor.tools.ToolType;
//import com.example.deepart.features.main.container.MainActivity;
//import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
//import ja.burhanrashid52.photoeditor.PhotoEditor;
//import ja.burhanrashid52.photoeditor.PhotoEditorView;
//import ja.burhanrashid52.photoeditor.PhotoFilter;
//import ja.burhanrashid52.photoeditor.SaveSettings;
//import ja.burhanrashid52.photoeditor.TextStyleBuilder;
//import ja.burhanrashid52.photoeditor.ViewType;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import com.theartofdev.edmodo.cropper.CropImage;
//
//
//public class EditImageActivity extends BaseActivity implements OnPhotoEditorListener,
//        View.OnClickListener,
//        PropertiesBSFragment.Properties,
//        EmojiBSFragment.EmojiListener,
//        StickerBSFragment.StickerListener, EditingToolsAdapter.OnItemSelected, FilterListener {
//
//    private static final String TAG = EditImageActivity.class.getSimpleName();
//    public static final String EXTRA_IMAGE_PATHS = "extra_image_paths";
//    private static final int CAMERA_REQUEST = 52;
//    private static final int PICK_REQUEST = 53;
//    private PhotoEditor mPhotoEditor;
//    private PhotoEditorView mPhotoEditorView;
//    private PropertiesBSFragment mPropertiesBSFragment;
//    private EmojiBSFragment mEmojiBSFragment;
//    private StickerBSFragment mStickerBSFragment;
//    private TextView mTxtCurrentTool;
//    private Typeface mWonderFont;
//    private RecyclerView mRvTools, mRvFilters;
//    private EditingToolsAdapter mEditingToolsAdapter = new EditingToolsAdapter(this);
//    private FilterViewAdapter mFilterViewAdapter = new FilterViewAdapter(this);
//    private ConstraintLayout mRootView;
//    private ConstraintSet mConstraintSet = new ConstraintSet();
//    private boolean mIsFilterVisible;
//    private static final int REQUEST_CODE = 0;
//
//    private Bitmap styimage = null;
//    private Bitmap startimage = null;
//
//    private ArrayList<Uri> croplist = new ArrayList<>();
//    ;
//
//    private Bitmap tmp_image = null;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        makeFullScreen();
//        setContentView(R.layout.activity_edit_image);
//
//        initViews();
//
//        mWonderFont = Typeface.createFromAsset(getAssets(), "beyond_wonderland.ttf");
//
//        mPropertiesBSFragment = new PropertiesBSFragment();
//        mEmojiBSFragment = new EmojiBSFragment();
//        mStickerBSFragment = new StickerBSFragment(croplist);
//        mStickerBSFragment.setStickerListener(this);
//        mEmojiBSFragment.setEmojiListener(this);
//        mPropertiesBSFragment.setPropertiesChangeListener(this);
//
//        LinearLayoutManager llmTools = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        mRvTools.setLayoutManager(llmTools);
//        mRvTools.setAdapter(mEditingToolsAdapter);
//
//        LinearLayoutManager llmFilters = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        mRvFilters.setLayoutManager(llmFilters);
//        mRvFilters.setAdapter(mFilterViewAdapter);
//
//
//        //Typeface mTextRobotoTf = ResourcesCompat.getFont(this, R.font.roboto_medium);
//        //Typeface mEmojiTypeFace = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");
//
//        mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
//                .setPinchTextScalable(true) // set flag to make text scalable when pinch
//                //.setDefaultTextTypeface(mTextRobotoTf)
//                //.setDefaultEmojiTypeface(mEmojiTypeFace)
//                .build(); // build photo editor sdk
//
//        mPhotoEditor.setOnPhotoEditorListener(this);
//
////        Toast.makeText(this, "Select an Image to Continue", Toast.LENGTH_LONG).show();
//
//        //Set Image Dynamically
//        // mPhotoEditorView.getSource().setImageResource(R.drawable.color_palette);
//
//    }
//
//    private void initViews() {
//        ImageView imgUndo;
//        ImageView imgRedo;
//        ImageView imgGallery;
//        ImageView imgSave;
//        ImageView imgClose;
//
//        mPhotoEditorView = findViewById(R.id.photoEditorView);
//        mTxtCurrentTool = findViewById(R.id.txtCurrentTool);
//        mRvTools = findViewById(R.id.rvConstraintTools);
//        mRvFilters = findViewById(R.id.rvFilterView);
//        mRootView = findViewById(R.id.rootView);
//
//        imgUndo = findViewById(R.id.imgUndo);
//        imgUndo.setOnClickListener(this);
//
//        imgRedo = findViewById(R.id.imgRedo);
//        imgRedo.setOnClickListener(this);
//
////        imgGallery = findViewById(R.id.imgGallery);
////        imgGallery.setOnClickListener(this);
//
//        imgSave = findViewById(R.id.ok);
//        imgSave.setOnClickListener(this);
//
//        imgClose = findViewById(R.id.imgClose);
//        imgClose.setOnClickListener(this);
//
//        /// 변화된 사진
//        byte[] byteArray = getIntent().getByteArrayExtra("home_to_edit_image");
//        if (byteArray != null) {
//            Bitmap image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//
//            mPhotoEditorView.getSource().setImageBitmap(image);
//        }
//
//        /// 스타일 이미지
//        byte[] byteArray2 = getIntent().getByteArrayExtra("home_to_edit_image2");
//        if (byteArray2 != null) {
//            Bitmap image2 = BitmapFactory.decodeByteArray(byteArray2, 0, byteArray2.length);
//
//            styimage = image2;
//        }
//
//        /// 원본 사진
//        byte[] byteArray3 = getIntent().getByteArrayExtra("home_to_edit_image3");
//        if (byteArray3 != null) {
//            Bitmap image3 = BitmapFactory.decodeByteArray(byteArray3, 0, byteArray3.length);
//
//            startimage = image3;
//        }
//
//    }
//
//    @Override
//    public void onEditTextChangeListener(final View rootView, String text, int colorCode) {
//        TextEditorDialogFragment textEditorDialogFragment =
//                TextEditorDialogFragment.show(this, text, colorCode);
//        textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
//            @Override
//            public void onDone(String inputText, int colorCode) {
//                final TextStyleBuilder styleBuilder = new TextStyleBuilder();
//                styleBuilder.withTextColor(colorCode);
//
//                mPhotoEditor.editText(rootView, inputText, styleBuilder);
//                mTxtCurrentTool.setText("텍스트");
//            }
//        });
//    }
//
//    @Override
//    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
//        Log.d(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
//    }
//
//    @Override
//    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {
//        Log.d(TAG, "onRemoveViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
//    }
//
//    @Override
//    public void onStartViewChangeListener(ViewType viewType) {
//        Log.d(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
//    }
//
//    @Override
//    public void onStopViewChangeListener(ViewType viewType) {
//        Log.d(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.imgUndo:
//                mPhotoEditor.undo();
//                break;
//
//            case R.id.imgRedo:
//                mPhotoEditor.redo();
//                break;
//
//            case R.id.ok:
//                saveImage();
//                break;
//
//            case R.id.imgClose:
//                onBackPressed();
//                break;
//
//        }
//    }
//
//    public static Bitmap tmp = null;
//    public static Bitmap tmp2 = null;
//    public static Bitmap tmp3 = null;
//
//    /// 완료하기 전달
//    @SuppressLint("MissingPermission")
//    private void saveImage() {
//        Intent intent = new Intent(this, MainActivity.class);
//        tmp = ((BitmapDrawable) mPhotoEditorView.getSource().getDrawable()).getBitmap();
//        tmp2 = styimage;
//        tmp3 = startimage;
//        if (requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            showLoading("Saving...");
//            File file = new File("storage/emulated/0/"
//                    + File.separator + "easy_edit"
//                    + System.currentTimeMillis() + ".png");
//            try {
//                file.createNewFile();
//
//                SaveSettings saveSettings = new SaveSettings.Builder()
//                        .setClearViewsEnabled(true)
//                        .setTransparencyEnabled(true)
//                        .build();
//
//                mPhotoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
//                    @Override
//                    public void onSuccess(@NonNull String imagePath) {
//                        hideLoading();
//                        showSnackbar("Image Saved Successfully");
//                        mPhotoEditorView.getSource().setImageURI(Uri.fromFile(new File(imagePath)));
////                        showLoading(imagePath);
//                        tmp = ((BitmapDrawable) mPhotoEditorView.getSource().getDrawable()).getBitmap();
//
//
////                        // 편집된 사진 전송
////                        ////        //    1024     ->   256
////                        tmp = MainActivity.resizeBitmapImage(tmp,512);
////                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                        tmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
////                        //   Log.e(TAG, "getWidth  " + tmp.getWidth() + "getHeight   " + tmp.getHeight()   +  tmp.get);
////                        byte[] byteArray = stream.toByteArray();
////                        intent.putExtra("edit_image", byteArray);
////
////
////
////
////                        // 스타일이미지 전송
////                        ////        //    1024     ->   256
////                        tmp2 = MainActivity.resizeBitmapImage(tmp2,400);
////                        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
////                        tmp2.compress(Bitmap.CompressFormat.JPEG, 100, stream2);
////                        //   Log.e(TAG, "getWidth  " + tmp.getWidth() + "getHeight   " + tmp.getHeight()   +  tmp.get);
////                        byte[] byteArray2 = stream2.toByteArray();
////                        intent.putExtra("edit_image2", byteArray2);
////
////
////
////                        // 원본이미지 전송
////                        ////        //    1024     ->   256
//////                        tmp3 = MainActivity.resizeBitmapImage(tmp3,400);
////                        ByteArrayOutputStream stream3 = new ByteArrayOutputStream();
////                        tmp3.compress(Bitmap.CompressFormat.JPEG, 100, stream3);
////                        //   Log.e(TAG, "getWidth  " + tmp.getWidth() + "getHeight   " + tmp.getHeight()   +  tmp.get);
////                        byte[] byteArray3 = stream3.toByteArray();
////                        intent.putExtra("edit_image3", byteArray3);
////
////                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        hideLoading();
//                        showSnackbar("Failed to save Image");
//                    }
//
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//                hideLoading();
//                showSnackbar(e.getMessage());
//            }
//        }
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // 갤러리 접근
//        if (requestCode == REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                try {
//                    InputStream in = getContentResolver().openInputStream(data.getData());
//
//                    Bitmap img = BitmapFactory.decodeStream(in);
//                    in.close();
//
//                    CropImage.activity(getImageUri(img)).start(this);
//
//
//                    //////////////////////////      tmp_image = img;//////////////////////
//                } catch (Exception e) {
//
//                }
//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
//            }
//        }
//
//
//        // 사진 오려 붙아기
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//
//                croplist.add(result.getUri());
//                //stickerList = new ArrayList<>();
//                // ((ImageView) findViewById(R.id.imageview)).setImageURI(result.getUri());
////                StickerBSFragment.stickerList2.add(result.getUri());
//                //stickerList.add(result.getUri());
//                Toast.makeText(
//                                this, " [스티커] 기능에 추가되었습니다.", Toast.LENGTH_LONG)
//                        .show();
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Toast.makeText(this, "실패" + result.getError(), Toast.LENGTH_LONG).show();
//            }
//        }
//
//
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case CAMERA_REQUEST:
//                    mPhotoEditor.clearAllViews();
//                    Bitmap photo = (Bitmap) data.getExtras().get("data");
//                    mPhotoEditorView.getSource().setImageBitmap(photo);
//                    break;
//                case PICK_REQUEST:
//                    try {
//                        mPhotoEditor.clearAllViews();
//                        Uri uri = data.getData();
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                        mPhotoEditorView.getSource().setImageBitmap(bitmap);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//            }
//        }
//    }
//
//    @Override
//    public void onColorChanged(int colorCode) {
//        mPhotoEditor.setBrushColor(colorCode);
//        mTxtCurrentTool.setText(R.string.label_brush);
//    }
//
//    @Override
//    public void onOpacityChanged(int opacity) {
//        mPhotoEditor.setOpacity(opacity);
//        mTxtCurrentTool.setText(R.string.label_brush);
//    }
//
//    @Override
//    public void onBrushSizeChanged(int brushSize) {
//        mPhotoEditor.setBrushSize(brushSize);
//        mTxtCurrentTool.setText(R.string.label_brush);
//    }
//
//    @Override
//    public void onEmojiClick(String emojiUnicode) {
//        mPhotoEditor.addEmoji(emojiUnicode);
//        mTxtCurrentTool.setText(R.string.label_emoji);
//
//    }
//
//    @Override
//    public void onStickerClick(Bitmap bitmap) {
//        mPhotoEditor.addImage(bitmap);
//        mTxtCurrentTool.setText(R.string.label_sticker);
//    }
//
//    @Override
//    public void isPermissionGranted(boolean isGranted, String permission) {
//        if (isGranted) {
//            saveImage();
//        }
//    }
//
//    private void showSaveDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("Are you want to exit without saving image ?");
//        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                saveImage();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        builder.setNeutralButton("Discard", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//        builder.create().show();
//
//    }
//
//    @Override
//    public void onFilterSelected(PhotoFilter photoFilter) {
//        mPhotoEditor.setFilterEffect(photoFilter);
//    }
//
//    @Override
//    public void onToolSelected(ToolType toolType) {
//        switch (toolType) {
//            case BRUSH:
//                mPhotoEditor.setBrushDrawingMode(true);
//                mTxtCurrentTool.setText(R.string.label_brush);
//                mPropertiesBSFragment.show(getSupportFragmentManager(), mPropertiesBSFragment.getTag());
//                break;
//            case TEXT:
//                TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(this);
//                textEditorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
//                    @Override
//                    public void onDone(String inputText, int colorCode) {
//                        final TextStyleBuilder styleBuilder = new TextStyleBuilder();
//                        styleBuilder.withTextColor(colorCode);
//
//                        mPhotoEditor.addText(inputText, styleBuilder);
//                        mTxtCurrentTool.setText(R.string.label_text);
//                    }
//                });
//                break;
//            case ERASER:
//                mPhotoEditor.brushEraser();
//                mTxtCurrentTool.setText(R.string.label_eraser);
//                break;
//            case FILTER:
//                mTxtCurrentTool.setText(R.string.label_filter);
//                showFilter(true);
//                break;
//            case EMOJI:
//                mEmojiBSFragment.show(getSupportFragmentManager(), mEmojiBSFragment.getTag());
//                break;
//            case STICKER:
//                mStickerBSFragment.show(getSupportFragmentManager(), mStickerBSFragment.getTag());
//                break;
//        }
//    }
//
//
//    void showFilter(boolean isVisible) {
//        mIsFilterVisible = isVisible;
//        mConstraintSet.clone(mRootView);
//
//        if (isVisible) {
//            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.START);
//            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
//                    ConstraintSet.PARENT_ID, ConstraintSet.START);
//            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.END,
//                    ConstraintSet.PARENT_ID, ConstraintSet.END);
//        } else {
//            mConstraintSet.connect(mRvFilters.getId(), ConstraintSet.START,
//                    ConstraintSet.PARENT_ID, ConstraintSet.END);
//            mConstraintSet.clear(mRvFilters.getId(), ConstraintSet.END);
//        }
//
//        ChangeBounds changeBounds = new ChangeBounds();
//        changeBounds.setDuration(350);
//        changeBounds.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
//        TransitionManager.beginDelayedTransition(mRootView, changeBounds);
//
//        mConstraintSet.applyTo(mRootView);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (mIsFilterVisible) {
//            showFilter(false);
//            mTxtCurrentTool.setText(R.string.app_name);
//        } else if (!mPhotoEditor.isCacheEmpty()) {
//            showSaveDialog();
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    // Bitmap --------> URI 변환 코드
//    public Uri getImageUri(Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "a2", null);
//        return Uri.parse(path);
//    }
//
//    public void cut(View view) {
//        // start picker to get image for cropping and then use the image in cropping activity
//        /*
//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .start(this);
//*/
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, REQUEST_CODE);
//
//
//        CropImage.activity();
//
//    }
//}
