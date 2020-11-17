package com.xzh.imagerecognition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PieActivity extends AppCompatActivity {

    private PieChart mPc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        mPc = findViewById(R.id.pie_status);

        mPc.setUsePercentValues(true);//设置value是否用显示百分数,默认为false
        mPc.getDescription().setEnabled(true);//设置描述
        mPc.setDrawHoleEnabled(true);//是否绘制饼状图中间的圆
        mPc.setHoleRadius(30f);//设置圆所占比例
        mPc.setTransparentCircleRadius(61f);//设置中心透明圈所占比例
        mPc.setHighlightPerTapEnabled(true);//点击是否放大
        mPc.setCenterText("识图结果\n比例分布图");// 设置文字
        mPc.animateXY(1400,1400);//XY轴动画
        mPc.setDragDecelerationFrictionCoef(0.8f);//设置阻尼系数，范围在0~1之间，越小饼图转动越困难
        mPc.setExtraOffsets(15, 10, 15, 15); //设置边距

        Intent intent = getIntent();
        String name1 = intent.getStringExtra("name1");
        String name2 = intent.getStringExtra("name2");
        String name3 = intent.getStringExtra("name3");
        String name4 = intent.getStringExtra("name4");
        String name5 = intent.getStringExtra("name5");

        Double score1 = intent.getDoubleExtra("score1",0.00);
        Double score2 = intent.getDoubleExtra("score2",0.00);
        Double score3 = intent.getDoubleExtra("score3",0.00);
        Double score4 = intent.getDoubleExtra("score4",0.00);
        Double score5 = intent.getDoubleExtra("score5",0.00);


        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry((int)(score1*100),name1));
        entries.add(new PieEntry((int)(score2*100),name2));
        entries.add(new PieEntry((int)(score3*100),name3));
        entries.add(new PieEntry((int)(score4*100),name4));
        entries.add(new PieEntry((int)(score5*100),name5));

        PieDataSet dataSet = new PieDataSet(entries,null);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        dataSet.setValueLinePart1OffsetPercentage(80f);//数据连接线距图形片内部边界的距离，为百分数
        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(0.6f);
        dataSet.setValueLineColor(Color.BLACK);//设置连接线的颜色
        PieData data = new PieData(dataSet);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        mPc.setEntryLabelColor(Color.BLACK);
        mPc.setEntryLabelTextSize(15f);//描述文字的大小

        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(13f);//数据文字的大小
        data.setValueTextColor(Color.BLACK);
        mPc.setData(data);

        Legend legend = mPc.getLegend();
        legend.setTextSize(15f);
        legend.setWordWrapEnabled(true);

        mPc.invalidate();


    }

}
