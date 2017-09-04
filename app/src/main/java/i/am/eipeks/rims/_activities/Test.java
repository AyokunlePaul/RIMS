package i.am.eipeks.rims._activities;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;

import i.am.eipeks.rims.R;
import i.am.eipeks.rims._adapters.SeatNumberAdapter;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_test);

        recyclerView.setAdapter(new SeatNumberAdapter(this, 18, new ArrayList<Integer>()));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 10));
        recyclerView.addItemDecoration(new GridSpacing(10, dpToPx(2), true));
    }

    private class GridSpacing extends RecyclerView.ItemDecoration{

        private int spanCount, spacing;
        private boolean includeEdge;

        GridSpacing(int spanCount, int spacing, boolean includeEdge){
            this.spanCount = spanCount;
            this.includeEdge = includeEdge;
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if (includeEdge){
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;

                if (position < spanCount) {
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing;
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
