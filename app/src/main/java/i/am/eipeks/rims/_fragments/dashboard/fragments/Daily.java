package i.am.eipeks.rims._fragments.dashboard.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import i.am.eipeks.rims.R;


public class Daily extends Fragment {

    @SuppressWarnings("FieldCanBeLocal")
    private LineChart lineChart;

    @SuppressWarnings("deprecation")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View dailyView = inflater.inflate(R.layout.fragment_daily, container, false);

        lineChart = (LineChart) dailyView.findViewById(R.id.line_chart);

        int[] x_entries = {0,1,2,3,4,5,6,7,8,9};
        int[] y_entries = {0,1,2,3,4,5,6,7,8,9};

        List<Entry> entries = new ArrayList<>();

        for (int counter = 0; counter < x_entries.length; counter++){
            entries.add(new Entry(x_entries[counter], y_entries[counter]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setColor(getResources().getColor(android.R.color.holo_red_light));
        dataSet.setValueTextColor(getResources().getColor(R.color.red_mint_background));

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();

        return dailyView;
    }

}
