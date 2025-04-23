package com.hfut.mihealth.common.weekReport;

import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.hfut.mihealth.R;

import java.util.ArrayList;
import java.util.List;

public class WeekActivity extends ComponentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_weekreport);
        initView();
    }

    private void initView() {
        LineChart lineChart = findViewById(R.id.line_chart);
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 4));
        entries.add(new Entry(1, 2));
        entries.add(new Entry(2, 6));
        entries.add(new Entry(3, 8));
        entries.add(new Entry(4, 3));

        LineDataSet dataSet = new LineDataSet(entries, "折线图"); // 设置线的名称
        dataSet.setColor(Color.RED); // 设置线的颜色
        dataSet.setLineWidth(2f); // 设置线的宽度

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate(); // 刷新图表视图

    }
}
