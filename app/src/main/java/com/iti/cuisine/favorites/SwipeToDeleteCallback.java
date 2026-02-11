package com.iti.cuisine.favorites;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.cuisine.R;

public abstract class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private final ColorDrawable background;
    private final Drawable deleteIcon;
    private final int iconMargin;

    public SwipeToDeleteCallback(Context context) {
        super(0, ItemTouchHelper.LEFT);
        background = new ColorDrawable(context.getColor(R.color.md_theme_error));
        deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete);
        deleteIcon.setTint(context.getColor(R.color.md_theme_onError));
        iconMargin = 24;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c,
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            float dX, float dY,
                            int actionState,
                            boolean isCurrentlyActive) {

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();

        background.setBounds(
                itemView.getRight() + (int) dX,
                itemView.getTop(),
                itemView.getRight(),
                itemView.getBottom()
        );
        background.draw(c);

        int deleteIconTop = itemView.getTop() + (itemHeight - deleteIcon.getIntrinsicHeight()) / 2;
        int deleteIconBottom = deleteIconTop + deleteIcon.getIntrinsicHeight();
        int deleteIconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth();
        int deleteIconRight = itemView.getRight() - iconMargin;

        deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        deleteIcon.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

}