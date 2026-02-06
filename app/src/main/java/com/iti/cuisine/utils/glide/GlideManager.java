package com.iti.cuisine.utils.glide;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.iti.cuisine.R;

public class GlideManager {

    public static void loadInto(String url, ImageView imageView) {

        Glide.with(imageView.getContext())
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.img_loading_placeholder)
                .error(R.drawable.img_error_placeholder)
                .centerCrop()
                .into(imageView);
    }

}
