package com.iti.cuisine.utils.glide;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.chip.Chip;
import com.iti.cuisine.R;

public class GlideManager {

    public static void loadInto(String url, ImageView imageView) {
        DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

        Glide.with(imageView)
                .load(url)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(factory))
                .error(R.drawable.img_error_placeholder)
                .placeholder(R.drawable.ic_loading_placeholder)
                .into(imageView);
    }

    public static void loadImageIntoChip(String imageUrl, Chip chip) {
        chip.setChipIconResource(R.drawable.ic_loading_placeholder);

        Glide.with(chip)
                .asBitmap()
                .load(imageUrl)
                .override(48, 48)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        chip.setChipIcon(
                                new BitmapDrawable(chip.getContext().getResources(), bitmap)
                        );
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {}

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        chip.setChipIconResource(R.drawable.logo);
                    }
                });
    }
}
