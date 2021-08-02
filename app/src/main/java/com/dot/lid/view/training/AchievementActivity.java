package com.dot.lid.view.training;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dot.lid.R;
import com.dot.lid.adapter.AchievementAdapter;
import com.dot.lid.app.Injection;
import com.dot.lid.app.MyApplication;
import com.dot.lid.app.ViewModelProviderFactory;
import com.dot.lid.data.Resource;
import com.dot.lid.databinding.ActivityAchievementBinding;
import com.dot.lid.dialog.InstructionDialog;
import com.dot.lid.model.Achievement;
import com.dot.lid.model.TestResult;
import com.dot.lid.utils.Constant;
import com.dot.lid.view.test.BarChartActivity;

import java.util.List;
import java.util.Locale;

import static com.dot.lid.utils.Language.ARABIC;
import static com.dot.lid.utils.Language.ENGLISH;
import static com.dot.lid.utils.Language.GERMAN;

public class AchievementActivity extends AppCompatActivity implements InstructionDialog.InstructionDialogInterface {
    private ActivityAchievementBinding binding;
    private AchievementViewModel viewModel;
    private InstructionDialog instructionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAchievementBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MyApplication myApplication = MyApplication.getInstance();

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
        viewModel = new ViewModelProvider(this, providerFactory).get(AchievementViewModel.class);
        subscribeObserver();
        viewModel.retrieveTestResult();
        viewModel.retrieveAllAchievement();

    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.testTitle.setText(R.string.test);
        binding.speceficPracticeTitle.setText(R.string.specific_practice);
        binding.specificPracticeRec.setLayoutManager(new LinearLayoutManager(this));
        binding.specificPracticeRec.setNestedScrollingEnabled(true);

        binding.toolbar.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void subscribeObserver() {
        viewModel.observerTestResult().observe(this, new Observer<Resource<List<TestResult>>>() {
            @Override
            public void onChanged(Resource<List<TestResult>> resource) {
                if (resource != null) {
                    if (resource.status == Resource.Status.SUCCESS) {
                        if (resource.data != null) {
                            updateTestAchievement(resource.data);
                        }
                    }
                }
            }
        });

        viewModel.observeAchievementLiveData().observe(this, new Observer<Resource<List<Achievement>>>() {
            @Override
            public void onChanged(Resource<List<Achievement>> resource) {
                if (resource != null) {
                    if (resource.status == Resource.Status.SUCCESS) {
                        assert resource.data != null;
                        makeDataAndCallAdapter(resource.data);

                    }
                }
            }
        });
    }

    private void makeDataAndCallAdapter(List<Achievement> data) {
        List<Achievement> achievementList = Constant.getAchievementList();
        if (data.size() > 0) {
            for (Achievement achievement : data) {
                for (int i = 0; i < achievementList.size(); i++) {
                    Achievement main = achievementList.get(i);
                    if (main.getMainCategory() == achievement.getMainCategory() && main.getSubCategory() == achievement.getSubCategory()) {
                        achievementList.set(i, achievement);
                        break;
                    }
                }
            }
        }

        AchievementAdapter adapter = new AchievementAdapter(achievementList);
        binding.specificPracticeRec.setAdapter(adapter);
    }

    private void updateTestAchievement(List<TestResult> testResults) {
        int bronzeBadgeCounter = 0;
        int silverBadgeCounter = 0;
        int goldBadgeCounter = 0;
        int platinumBadgeCounter = 0;
        for (TestResult testResult : testResults) {
            int marks = testResult.getMark();
            if (marks >= 15 && marks <= 22) {
                bronzeBadgeCounter += 1;
            } else if (marks >= 23 && marks <= 29) {
                silverBadgeCounter += 1;
            } else if (marks >= 30 && marks <= 32) {
                goldBadgeCounter += 1;
            } else if (marks >= 33) {
                platinumBadgeCounter += 1;
            }
        }
        updateTestBronzeRating(bronzeBadgeCounter);
        updateTestSilverRating(silverBadgeCounter);
        updateTestGoldRating(goldBadgeCounter);
        updateTestPlatinumRating(platinumBadgeCounter);
    }

    private void updateTestBronzeRating(int number) {
        int achievementStar = R.drawable.ic_star_bronze;
        int star = R.drawable.ic_star_white;
        if (number >= 1 && number <= 4) {
            binding.testAchievementLayout.testAchievementBronze.setSmallImage(achievementStar);
            binding.testAchievementLayout.testAchievementBronze.setMediumImage(star);
            binding.testAchievementLayout.testAchievementBronze.setLargeImage(star);
        } else if (number >= 5 && number <= 9) {
            binding.testAchievementLayout.testAchievementBronze.setSmallImage(achievementStar);
            binding.testAchievementLayout.testAchievementBronze.setMediumImage(achievementStar);
            binding.testAchievementLayout.testAchievementBronze.setLargeImage(star);
        } else if (number >= 10) {
            binding.testAchievementLayout.testAchievementBronze.setSmallImage(achievementStar);
            binding.testAchievementLayout.testAchievementBronze.setMediumImage(achievementStar);
            binding.testAchievementLayout.testAchievementBronze.setLargeImage(achievementStar);
        } else {
            binding.testAchievementLayout.testAchievementBronze.setSmallImage(star);
            binding.testAchievementLayout.testAchievementBronze.setMediumImage(star);
            binding.testAchievementLayout.testAchievementBronze.setLargeImage(star);
        }
    }

    private void updateTestSilverRating(int number) {
        int achievementStar = R.drawable.ic_star_silver;
        int star = R.drawable.ic_star_white;
        if (number >= 1 && number <= 4) {
            binding.testAchievementLayout.testAchievementSilver.setSmallImage(achievementStar);
            binding.testAchievementLayout.testAchievementSilver.setMediumImage(star);
            binding.testAchievementLayout.testAchievementSilver.setLargeImage(star);
        } else if (number >= 5 && number <= 9) {
            binding.testAchievementLayout.testAchievementSilver.setSmallImage(achievementStar);
            binding.testAchievementLayout.testAchievementSilver.setMediumImage(achievementStar);
            binding.testAchievementLayout.testAchievementSilver.setLargeImage(star);
        } else if (number >= 10) {
            binding.testAchievementLayout.testAchievementSilver.setSmallImage(achievementStar);
            binding.testAchievementLayout.testAchievementSilver.setMediumImage(achievementStar);
            binding.testAchievementLayout.testAchievementSilver.setLargeImage(achievementStar);
        } else {
            binding.testAchievementLayout.testAchievementSilver.setSmallImage(star);
            binding.testAchievementLayout.testAchievementSilver.setMediumImage(star);
            binding.testAchievementLayout.testAchievementSilver.setLargeImage(star);
        }
    }

    private void updateTestGoldRating(int number) {
        int achievementStar = R.drawable.ic_star_gold;
        int star = R.drawable.ic_star_white;
        if (number >= 1 && number <= 4) {
            binding.testAchievementLayout.testAchievementGold.setSmallImage(achievementStar);
            binding.testAchievementLayout.testAchievementGold.setMediumImage(star);
            binding.testAchievementLayout.testAchievementGold.setLargeImage(star);
        } else if (number >= 5 && number <= 9) {
            binding.testAchievementLayout.testAchievementGold.setSmallImage(achievementStar);
            binding.testAchievementLayout.testAchievementGold.setMediumImage(achievementStar);
            binding.testAchievementLayout.testAchievementGold.setLargeImage(star);
        } else if (number >= 10) {
            binding.testAchievementLayout.testAchievementGold.setSmallImage(achievementStar);
            binding.testAchievementLayout.testAchievementGold.setMediumImage(achievementStar);
            binding.testAchievementLayout.testAchievementGold.setLargeImage(achievementStar);
        } else {
            binding.testAchievementLayout.testAchievementGold.setSmallImage(star);
            binding.testAchievementLayout.testAchievementGold.setMediumImage(star);
            binding.testAchievementLayout.testAchievementGold.setLargeImage(star);
        }
    }

    private void updateTestPlatinumRating(int number) {
        int achievementStar = R.drawable.ic_star_platinum;
        int star = R.drawable.ic_star_white;
        if (number >= 1 && number <= 4) {
            binding.testAchievementLayout.testAchievementPlatinum.setSmallImage(achievementStar);
            binding.testAchievementLayout.testAchievementPlatinum.setMediumImage(star);
            binding.testAchievementLayout.testAchievementPlatinum.setLargeImage(star);
        } else if (number >= 5 && number <= 9) {
            binding.testAchievementLayout.testAchievementPlatinum.setSmallImage(achievementStar);
            binding.testAchievementLayout.testAchievementPlatinum.setMediumImage(achievementStar);
            binding.testAchievementLayout.testAchievementPlatinum.setLargeImage(star);
        } else if (number >= 10) {
            binding.testAchievementLayout.testAchievementPlatinum.setSmallImage(achievementStar);
            binding.testAchievementLayout.testAchievementPlatinum.setMediumImage(achievementStar);
            binding.testAchievementLayout.testAchievementPlatinum.setLargeImage(achievementStar);
        } else {
            binding.testAchievementLayout.testAchievementPlatinum.setSmallImage(star);
            binding.testAchievementLayout.testAchievementPlatinum.setMediumImage(star);
            binding.testAchievementLayout.testAchievementPlatinum.setLargeImage(star);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.achievement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.achievementRateApp:
                rateTheApp();
                return true;
            case R.id.achievementInstruction:
                openInstructionDialog();
                return true;
            case R.id.achievementBarChart:
                gotoBarChartActivity();
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

    private void gotoBarChartActivity() {
        startActivity(new Intent(AchievementActivity.this, BarChartActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void openInstructionDialog() {
        instructionDialog = new InstructionDialog();
        instructionDialog.show(getSupportFragmentManager(), "InstructionDialog");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}