//package com.example.deepart.features.editImage.easyeditor;
//
//import android.annotation.SuppressLint;
//import android.app.Dialog;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.coordinatorlayout.widget.CoordinatorLayout;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.bottomsheet.BottomSheetBehavior;
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
//import com.ttest.adain.R;
//
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//
//public class StickerBSFragment extends BottomSheetDialogFragment {
//    ArrayList<Uri> croplist2 = new ArrayList<>();
//    public StickerBSFragment(ArrayList<Uri> croplist) {
//        croplist2 = croplist;
//    }
//
//    private StickerListener mStickerListener;
//
//    public void setStickerListener(StickerListener stickerListener) {
//        mStickerListener = stickerListener;
//    }
//
//    public interface StickerListener {
//        void onStickerClick(Bitmap bitmap);
//    }
//
//    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
//
//        @Override
//        public void onStateChanged(@NonNull View bottomSheet, int newState) {
//            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                dismiss();
//            }
//
//        }
//
//        @Override
//        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//        }
//    };
//
//
//    @SuppressLint("RestrictedApi")
//    @Override
//    public void setupDialog(Dialog dialog, int style) {
//        super.setupDialog(dialog, style);
//        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sticker_emoji_dialog, null);
//        dialog.setContentView(contentView);
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
//        CoordinatorLayout.Behavior behavior = params.getBehavior();
//
//        if (behavior != null && behavior instanceof BottomSheetBehavior) {
//            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
//        }
//        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));
//        RecyclerView rvEmoji = contentView.findViewById(R.id.rvEmoji);
//
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
//        rvEmoji.setLayoutManager(gridLayoutManager);
//        StickerAdapter stickerAdapter = new StickerAdapter();
//        rvEmoji.setAdapter(stickerAdapter);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }
//
//    public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {
//
//        /*
//        int[] stickerList = new int[]{
//                R.drawable.frozen1,R.drawable.frozen2,R.drawable.frozen3,
//                R.drawable.piyo1, R.drawable.piyo2, R.drawable.piyo3,
//                R.drawable.animal_forest1, R.drawable.animal_forest2, R.drawable.animal_forest3,
//                R.drawable.pikachu2, R.drawable.pikachu1, R.drawable.pikachu3,R.drawable.line1,R.drawable.bobo1, R.drawable.cat1};
//        */
//
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sticker, parent, false);
//            return new ViewHolder(view);
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//     //       holder.imgSticker.setImageResource(stickerList[position]);
//            holder.imgSticker.setImageURI(croplist2.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//           // return stickerList.length;
//            return croplist2.size();
//        }
//
//        class ViewHolder extends RecyclerView.ViewHolder {
//            ImageView imgSticker;
//            Bitmap tmp = null;
//           // Uri uri = Uri.fromFile(new File(String.valueOf(croplist2.get(getLayoutPosition()))));
//            ViewHolder(View itemView) {
//                super(itemView);
//                imgSticker = itemView.findViewById(R.id.imgSticker);
//
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (mStickerListener != null) {
//                            try {
//                                mStickerListener.onStickerClick(
//                                        tmp = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(croplist2.get(getLayoutPosition()))));
//                            } catch (FileNotFoundException e) {
//                                e.printStackTrace();
//                            }
//                            // BitmapFactory.decodeFile(uri.getPath()));
//                                   // BitmapFactory.decodeResource(getResources(), stickerList[getLayoutPosition()]));
//                                   // Bitmap.createBitmap(croplist2.get(getLayoutPosition())));
//                                  //   BitmapFactory.decodeResource(getResources(), R.drawable.bobo1));
//                        }
//                        dismiss();
//                    }
//                });
//            }
//        }
//    }
//
//    private String convertEmoji(String emoji) {
//        String returnedEmoji = "";
//        try {
//            int convertEmojiToInt = Integer.parseInt(emoji.substring(2), 16);
//            returnedEmoji = getEmojiByUnicode(convertEmojiToInt);
//        } catch (NumberFormatException e) {
//            returnedEmoji = "";
//        }
//        return returnedEmoji;
//    }
//
//    private String getEmojiByUnicode(int unicode) {
//        return new String(Character.toChars(unicode));
//    }
//}
