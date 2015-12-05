package com.doodeec.digitalsandstorm.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Decorator for using recyclerView as a grid
 *
 * @author Dusan Bartos
 */
public class GridSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;
    private int mColumns;

    public GridSpacesItemDecoration(int space, int columnsCount) {
        mSpace = space;
        mColumns = columnsCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = mSpace;
        outRect.bottom = mSpace;

        if (parent.getChildLayoutPosition(view) % mColumns != 0) {
            outRect.right = mSpace;
        }

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) < mColumns) {
            outRect.top = mSpace;
        }
    }
}