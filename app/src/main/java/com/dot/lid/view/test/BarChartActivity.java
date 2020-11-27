package com.dot.lid.view.test;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dot.lid.R;
import com.dot.lid.app.Injection;
import com.dot.lid.app.MyApplication;
import com.dot.lid.app.ViewModelProviderFactory;
import com.dot.lid.data.Resource;
import com.dot.lid.databinding.ActivityBarChartBinding;
import com.dot.lid.dialog.InstructionDialog;
import com.dot.lid.model.TestResult;
import com.dot.lid.view.training.AchievementActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.dot.lid.utils.Language.ARABIC;
import static com.dot.lid.utils.Language.ENGLISH;
import static com.dot.lid.utils.Language.GERMAN;

public class BarChartActivity extends AppCompatActivity implements InstructionDialog.InstructionDialogInterface {

    private ActivityBarChartBinding binding;
    private BarChart barChart;
    private MyApplication myApplication;
    private BarChartViewModel viewModel;
    private InstructionDialog instructionDialog;
    private InterstitialAd interstitialAd;
    private int stateFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBarChartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        myApplication = MyApplication.getInstance();
        String currentLanguage = myApplication.getLanguage();
        if (currentLanguage.equals(ARABIC.getLanguage())) {
            updateResources(ARABIC.getLanguage());
        } else if (currentLanguage.equals(GERMAN.getLanguage())) {
            updateResources(GERMAN.getLanguage());
        } else {
            updateResources(ENGLISH.getLanguage());
        }

        setSupportActionBar(binding.toolbar.toolbar);
        ViewModelProviderFactory providerFactory = Injection.provideViewModelProviderFactory(this);
        viewModel = new ViewModelProvider(this, providerFactory).get(BarChartViewModel.class);
        subscribeObserver();
        viewModel.retrieveTestResult();

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_add_unit_id));
        showAd();
    }

    private void subscribeObserver() {
        viewModel.observerTestResult().observe(this, new Observer<Resource<List<TestResult>>>() {
            @Override
            public void onChanged(Resource<List<TestResult>> resource) {
                if (resource != null) {
                    switch (resource.status) {
                        case LOADING:
                            break;
                        case ERROR:
                            prepareChartData(createChartData(null));
                            break;
                        case SUCCESS:
                            prepareChartData(createChartData(resource.data));
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        barChart = binding.barChart;
        configureChartAppearance();
        binding.toolbar.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateFlag = 2;
                showAd();
            }
        });
    }

    @Override
    public void onBackPressed() {
        stateFlag = 2;
        showAd();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_chart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.barChartRateApp:
                rateTheApp();
                return true;
            case R.id.barChartInstruction:
                instructionDialog = new InstructionDialog();
                instructionDialog.show(getSupportFragmentManager(), "InstructionDialog");
                return true;
            case R.id.barChartAchievement:
                startActivity(new Intent(BarChartActivity.this, AchievementActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureChartAppearance() {
        barChart.getDescription().setEnabled(false);
        barChart.fitScreen();
        barChart.setBorderColor(Color.RED);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setTextSize(12f);

        YAxis yAxis = barChart.getAxis(YAxis.AxisDependency.LEFT);
        yAxis.setTextColor(Color.WHITE);
        yAxis.setAxisMinimum(0f);
        yAxis.setTextSize(12f);
        yAxis.setAxisMaximum(33f);
        yAxis.setLabelCount(33, true);

        YAxis rightAxis = barChart.getAxis(YAxis.AxisDependency.RIGHT);
        rightAxis.setEnabled(false);
    }

    private BarDataSet createChartData(List<TestResult> resultList) {
        List<BarEntry> entries = new ArrayList<>();
        if (resultList == null) {
            BarEntry barEntry = new BarEntry(0, 0);
            entries.add(barEntry);
        } else {
            barChart.getXAxis().setLabelCount(resultList.size());
            for (TestResult testResult : resultList) {
                float x = (float) (testResult.getId());
                BarEntry barEntry = new BarEntry(x, testResult.getMark());
                entries.add(barEntry);
            }
        }
        BarDataSet set = new BarDataSet(entries, "");
        return set;
    }

    private void prepareChartData(BarDataSet dataSet) {
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.invalidate();
    }

    private void updateResources(String language) {
        Locale locale = new Locale(language);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);
    }

    @Override
    public void onInstructionDialogCancel() {
        instructionDialog.dismiss();
    }


    private void rateTheApp() {
        String url = MyApplication.appLink;
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    private void showAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                interstitialAd.show();
            }

            @Override
            public void onAdClosed() {
                if (stateFlag == 2) {
                    finish();
                }
            }

            @Override
            public void onAdFailedToLoad(int i) {
                if (stateFlag == 2) {
                    finish();
                }
            }
        });
    }

}