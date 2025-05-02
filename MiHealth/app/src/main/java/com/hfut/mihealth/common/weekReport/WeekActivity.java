package com.hfut.mihealth.common.weekReport;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.hfut.mihealth.R;
import com.hfut.mihealth.common.weekReport.viewmodel.WeekViewmodel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WeekActivity extends ComponentActivity {
    private WeekViewmodel weekReportViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_weekreport);
        weekReportViewModel = new ViewModelProvider(this).get(WeekViewmodel.class);

        weekReportViewModel.getWeekData().observe(this, new Observer<Map<String, Map<String, Double>>>() {
            @Override
            public void onChanged(Map<String, Map<String, Double>> stringMapMap) {
                //更新数据
                initView();
            }
        });
        weekReportViewModel.getWeekReport().observe(this, new Observer<String>() {

            @Override
            public void onChanged(String s) {
                updateText(s);
            }
        });
    }

    private void initView() {
        initBarChart();
        initPieChart();

        Toolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 获取包含的布局实例并设置值
        View pieItem1 = findViewById(R.id.pie_item_1);
        View colorDot1 = pieItem1.findViewById(R.id.color_dot);
        TextView takeName1 = pieItem1.findViewById(R.id.take_name);
        TextView takeValue1 = pieItem1.findViewById(R.id.take_value);

        // 设置文本内容
        takeName1.setText("碳水:");


        // 创建 GradientDrawable 并设置颜色和圆角
        GradientDrawable gd1 = new GradientDrawable();
        gd1.setColor(ContextCompat.getColor(this, R.color.CarbsYellow));
        gd1.setShape(GradientDrawable.OVAL); // 形状为圆形
        gd1.setCornerRadius(2 * getResources().getDisplayMetrics().density); // 设置圆角半径为5dp

        // 应用到 color_dot
        colorDot1.setBackground(gd1);

        View pieItem2 = findViewById(R.id.pie_item_2);
        View colorDot2 = pieItem2.findViewById(R.id.color_dot);
        TextView takeName2 = pieItem2.findViewById(R.id.take_name);
        takeName2.setText("脂肪:");

        // 创建 GradientDrawable 并设置颜色和圆角
        GradientDrawable gd2 = new GradientDrawable();
        gd2.setColor(ContextCompat.getColor(this, R.color.FatOrange));
        gd2.setShape(GradientDrawable.OVAL); // 形状为圆形
        gd2.setCornerRadius(2 * getResources().getDisplayMetrics().density); // 设置圆角半径为5dp

        // 应用到 color_dot
        colorDot2.setBackground(gd2);

        View pieItem3 = findViewById(R.id.pie_item_3);
        View colorDot3 = pieItem3.findViewById(R.id.color_dot);
        TextView takeName3 = pieItem3.findViewById(R.id.take_name);
        takeName3.setText("蛋白质:");

        // 创建 GradientDrawable 并设置颜色和圆角
        GradientDrawable gd3 = new GradientDrawable();
        gd3.setColor(ContextCompat.getColor(this, R.color.ProteinGreen));
        gd3.setShape(GradientDrawable.OVAL); // 形状为圆形
        gd3.setCornerRadius(2 * getResources().getDisplayMetrics().density); // 设置圆角半径为5dp

        // 应用到 color_dot
        colorDot3.setBackground(gd3);
    }

    private void updateText(String s) {
        TextView textView = findViewById(R.id.week_content);
        textView.setText(s);
    }

    @SuppressLint("SetTextI18n")
    private void initPieChartValue(double carbs, double fat, double protein) {
        DecimalFormat df = new DecimalFormat("#.00"); // 创建一个格式化实例，保留两位小数

        // 碳水
        View pieItem1 = findViewById(R.id.pie_item_1);
        TextView takeValue1 = pieItem1.findViewById(R.id.take_value);
        takeValue1.setText(df.format(carbs) + "g");

        // 脂肪
        View pieItem2 = findViewById(R.id.pie_item_2);
        TextView takeValue2 = pieItem2.findViewById(R.id.take_value);
        takeValue2.setText(df.format(fat) + "g");

        // 蛋白
        View pieItem3 = findViewById(R.id.pie_item_3);
        TextView takeValue3 = pieItem3.findViewById(R.id.take_value);
        takeValue3.setText(df.format(protein) + "g");
    }

    private void initPieChart() {
        Map<String, Double> takeValue = weekReportViewModel.getTakeValue();
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(Objects.requireNonNull(takeValue.get("carbs")).floatValue(), "碳水"));
        entries.add(new PieEntry(Objects.requireNonNull(takeValue.get("protein")).floatValue(), "蛋白质"));
        entries.add(new PieEntry(Objects.requireNonNull(takeValue.get("fat")).floatValue(), "脂肪"));
        initPieChartValue(Objects.requireNonNull(takeValue.get("carbs")).floatValue(),
                Objects.requireNonNull(takeValue.get("fat")).floatValue(),
                Objects.requireNonNull(takeValue.get("protein")).floatValue());
        PieChart pieChart = findViewById(R.id.pie_chart);
        //pieChart.setUsePercentValues(true); // 设置为百分比显示
        pieChart.getDescription().setEnabled(false);//关闭右下角的描述
        pieChart.setDrawHoleEnabled(true); // 启用中心洞
        pieChart.setHoleRadius(30f); // 设置中心洞的半径
        pieChart.setTransparentCircleRadius(0f); // 设置透明圆的半径
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.animateY(1000);
        //图例
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);    //是否显示图例

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ContextCompat.getColor(this, R.color.CarbsYellow)
                , ContextCompat.getColor(this, R.color.ProteinGreen),
                ContextCompat.getColor(this, R.color.FatOrange));
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(10f);
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.invalidate(); // 刷新图表视图
    }

    private void initBarChart() {
        List<Double> data = weekReportViewModel.getCalories();
        // 创建 BarEntry 列表
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            // 添加每个条目到 entries 列表中
            entries.add(new BarEntry(i, data.get(i).floatValue()));
        }
        BarChart barChart = findViewById(R.id.bar_chart);
        barChart.setScaleEnabled(false); // 禁用缩放功能
        barChart.getDescription().setEnabled(false);//关闭右下角的描述
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new ValueFormatter() {
            private final String[] daysOfWeek = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

            @Override
            public String getFormattedValue(float value) {
                int position = (int) value;
                if (position >= 0 && position < daysOfWeek.length) {
                    return daysOfWeek[position];
                } else {
                    return "";
                }
            }
        });
        xAxis.setDrawGridLines(false); // 禁用x轴网格线
        YAxis axisRight = barChart.getAxisRight();
        YAxis yAxis = barChart.getAxisLeft();
        //将右边的y轴设置为不显示
        axisRight.setEnabled(false);
        yAxis.setAxisMinimum(0f);
        barChart.animateY(1000);

        BarDataSet dataSet = new BarDataSet(entries, "卡路里摄入/千卡"); // 设置柱状图的名称
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setColor(ContextCompat.getColor(this, R.color.green));
        dataSet.setValueTextSize(12f);
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.invalidate(); // 刷新图表视图
    }
}
