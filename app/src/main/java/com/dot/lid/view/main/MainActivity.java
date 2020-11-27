package com.dot.lid.view.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.dot.lid.R;
import com.dot.lid.adapter.StateSpinnerAdapter;
import com.dot.lid.app.MyApplication;
import com.dot.lid.databinding.ActivityMainBinding;
import com.dot.lid.dialog.InstructionDialog;
import com.dot.lid.utils.Constant;
import com.dot.lid.view.test.BarChartActivity;
import com.dot.lid.view.test.TestActivity;
import com.dot.lid.view.training.AchievementActivity;
import com.dot.lid.view.training.TrainingActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Locale;

import static com.dot.lid.utils.Constant.TEST_ACTIVITY_KEY;
import static com.dot.lid.utils.Constant.TRAINING_ACTIVITY_KEY;
import static com.dot.lid.utils.Constant.getStateItem;
import static com.dot.lid.utils.Language.ARABIC;
import static com.dot.lid.utils.Language.ENGLISH;
import static com.dot.lid.utils.Language.GERMAN;

public class MainActivity extends AppCompatActivity implements InstructionDialog.InstructionDialogInterface {
    private static final String manualUrl = "http://technoven.de/lid/lid-user-manual.html";
    public static int CURRENT_POSITION = 0;
    private ActivityMainBinding binding;
    private MyApplication myApplication;
    private StateSpinnerAdapter adapter;
    private InstructionDialog instructionDialog;
    private InterstitialAd interstitialAd;
    private int stateFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
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
        adapter = new StateSpinnerAdapter(this, Constant.getStateList());

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_add_unit_id));
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
        binding.spinner.setAdapter(adapter);
        binding.trainingTV.setText(R.string.training);

        binding.toolbar.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showExitDialog();
            }
        });

        binding.trainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stateFlag = 2;
                showAd();
            }
        });

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CURRENT_POSITION = i;
                if (i == 0) {
                    binding.testIV.setImageResource(R.drawable.ic_arrow_drop_down);
                } else {
                    binding.testIV.setImageResource(R.drawable.ic_arrow_right);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CURRENT_POSITION == 0) {
                    binding.spinner.performClick();
                } else {
                    stateFlag = 3;
                    showAd();
                }
            }
        });
    }

    private void showAnimation() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.rateApp:
                rateTheApp();
                return true;
            case R.id.instruction:
                instructionDialog = new InstructionDialog();
                instructionDialog.show(getSupportFragmentManager(), "InstructionDialog");
                return true;
            case R.id.achievement:
                startActivity(new Intent(MainActivity.this, AchievementActivity.class));
                showAnimation();
                return true;
            case R.id.barChart:
                startActivity(new Intent(MainActivity.this, BarChartActivity.class));
                showAnimation();
                return true;
            case R.id.howToUse:
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.URL_KEY, manualUrl);
                intent.putExtra(WebViewActivity.TITLE_KEY, "How To Use");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateResources(String language) {
        Locale locale = new Locale(language);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, displayMetrics);
    }

    private void showExitDialog() {
        Button yesBT, noBT;
        ImageView closeBT;
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View view = getLayoutInflater().inflate(R.layout.layout_exit_app_dialog, viewGroup, false);
        yesBT = view.findViewById(R.id.exitYesButton);
        noBT = view.findViewById(R.id.exitNoButton);
        closeBT = view.findViewById(R.id.closeButton);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        yesBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                stateFlag = 1;
                showAd();
            }
        });
        noBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        closeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

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
                doAfterAdClosed();
            }

            @Override
            public void onAdFailedToLoad(int i) {
                doAfterAdClosed();
            }
        });
    }

    private void doAfterAdClosed() {
        switch (stateFlag) {
            case 1:
                finish();
                showAnimation();
                break;
            case 2:
                Intent trainingIntent = new Intent(MainActivity.this, TrainingActivity.class);
                trainingIntent.putExtra(TRAINING_ACTIVITY_KEY, true);
                startActivity(trainingIntent);
                showAnimation();
                break;
            case 3:
                Intent testIntent = new Intent(MainActivity.this, TestActivity.class);
                testIntent.putExtra(TEST_ACTIVITY_KEY, getStateItem(CURRENT_POSITION));
                startActivity(testIntent);
                showAnimation();
                break;
        }
    }
}
