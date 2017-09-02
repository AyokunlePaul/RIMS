package i.am.eipeks.rims._activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import i.am.eipeks.rims.R;
import i.am.eipeks.rims._adapters.TestAdapter;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_test);

        recyclerView.setAdapter(new TestAdapter(this, 18));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 10));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}
